package com.ooooo.activiti.api.impl;

import com.ooooo.api.FlowAPIService;
import com.ooooo.api.dto.req.CurrentForm;
import com.ooooo.api.dto.req.EndForm;
import com.ooooo.api.dto.req.NextForm;
import com.ooooo.api.dto.req.StartForm;
import com.ooooo.api.dto.resp.CurrentResult;
import com.ooooo.api.dto.resp.EndResult;
import com.ooooo.api.dto.resp.NextResult;
import com.ooooo.api.dto.resp.StartResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ooooo.api.enums.ActivityType.END_EVENT;
import static com.ooooo.api.enums.ActivityType.RECEIVE_TASK;
import static com.ooooo.api.enums.ActivityType.USER_TASK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * @author leizhijie
 * @since 1.0.0
 */
@SpringBootTest
class ActivitiFlowAPIServiceImplTest {
	
	private final ThreadLocal<String> processInstanceId = new ThreadLocal<>();
	private final ThreadLocal<String> processDefinitionKey = new ThreadLocal<>();
	
	@Autowired
	private FlowAPIService flowAPIService;
	
	@Test
	void start() {
		processDefinitionKey.set("ActivitiFlowAPIServiceImplTest");
		
		StartForm form = new StartForm(processDefinitionKey.get());
		StartResult result = flowAPIService.start(form);
		
		assertNotNull(result.getProcessInstanceId());
		processInstanceId.set(result.getProcessInstanceId());
	}
	
	@Test
	void current() {
		start();
		CurrentForm form = new CurrentForm(processInstanceId.get());
		CurrentResult result = flowAPIService.current(form);
		
		assertEquals(RECEIVE_TASK, result.getActivityType());
		assertEquals("waitState1", result.getActivityId());
	}
	
	@Test
	void next() {
		start();
		
		NextResult result = flowAPIService.next(new NextForm(processInstanceId.get()));
		assertEquals(RECEIVE_TASK, result.getActivityType());
		assertEquals("waitState2", result.getActivityId());
		
		result = flowAPIService.next(new NextForm(processInstanceId.get()));
		assertEquals(RECEIVE_TASK, result.getActivityType());
		assertEquals("waitState3", result.getActivityId());
		
		// waiting execute serviceTask
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ignored) {
		}
		
		//result = flowAPIService.next(new NextForm(processInstanceId.get()));
		//assertEquals(SERVICE_TASK, result.getActivityType());
		//assertEquals("service1", result.getActivityId());
		//
		//result = flowAPIService.next(new NextForm(processInstanceId.get()));
		//assertEquals(SERVICE_TASK, result.getActivityType());
		//assertEquals("service2", result.getActivityId());
		//
		//result = flowAPIService.next(new NextForm(processInstanceId.get()));
		//assertEquals(SERVICE_TASK, result.getActivityType());
		//assertEquals("service3", result.getActivityId());
		
		result = flowAPIService.next(new NextForm(processInstanceId.get()));
		assertEquals(USER_TASK, result.getActivityType());
		assertEquals("task1", result.getActivityId());
		
		result = flowAPIService.next(new NextForm(processInstanceId.get()));
		assertEquals(USER_TASK, result.getActivityType());
		assertEquals("task2", result.getActivityId());
		
		result = flowAPIService.next(new NextForm(processInstanceId.get()));
		assertEquals(END_EVENT, result.getActivityType());
		assertNull(result.getActivityId());
	}
	
	@Test
	void prev() {
	}
	
	@Test
	void back() {
	}
	
	@Test
	void end() {
		start();
		
		EndResult result = flowAPIService.end(new EndForm(processInstanceId.get()));
		
		CurrentResult currentResult = flowAPIService.current(new CurrentForm(processInstanceId.get()));
		assertEquals(END_EVENT, currentResult.getActivityType());
		assertNull(currentResult.getActivityId());
		
	}
}