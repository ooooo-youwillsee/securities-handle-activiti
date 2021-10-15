package com.ooooo.activiti.api.impl;

import com.ooooo.activiti.api.FlowAPIService;
import com.ooooo.activiti.api.dto.req.BackForm;
import com.ooooo.activiti.api.dto.req.CurrentForm;
import com.ooooo.activiti.api.dto.req.EndForm;
import com.ooooo.activiti.api.dto.req.NextForm;
import com.ooooo.activiti.api.dto.req.PrevForm;
import com.ooooo.activiti.api.dto.req.StartForm;
import com.ooooo.activiti.api.dto.resp.CurrentResult;
import com.ooooo.activiti.api.dto.resp.EndResult;
import com.ooooo.activiti.api.dto.resp.NextResult;
import com.ooooo.activiti.api.dto.resp.PrevResult;
import com.ooooo.activiti.api.dto.resp.StartResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ooooo.activiti.common.enums.ActivityType.END_EVENT;
import static com.ooooo.activiti.common.enums.ActivityType.RECEIVE_TASK;
import static com.ooooo.activiti.common.enums.ActivityType.USER_TASK;
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
		
		CurrentResult currentResult = flowAPIService.current(new CurrentForm(processInstanceId.get()));
		assertEquals(RECEIVE_TASK, currentResult.getActivityType());
		assertEquals("waitState1", currentResult.getActivityId());
		
		NextResult nextResult = flowAPIService.next(new NextForm(processInstanceId.get()));
		assertEquals(RECEIVE_TASK, nextResult.getActivityType());
		assertEquals("waitState2", nextResult.getActivityId());
		
		nextResult = flowAPIService.next(new NextForm(processInstanceId.get()));
		assertEquals(RECEIVE_TASK, nextResult.getActivityType());
		assertEquals("waitState3", nextResult.getActivityId());
		
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
		
		nextResult = flowAPIService.next(new NextForm(processInstanceId.get()));
		assertEquals(USER_TASK, nextResult.getActivityType());
		assertEquals("task1", nextResult.getActivityId());
		
		nextResult = flowAPIService.next(new NextForm(processInstanceId.get()));
		assertEquals(USER_TASK, nextResult.getActivityType());
		assertEquals("task2", nextResult.getActivityId());
		
		nextResult = flowAPIService.next(new NextForm(processInstanceId.get()));
		assertEquals(END_EVENT, nextResult.getActivityType());
		assertNull(nextResult.getActivityId());
	}
	
	@Test
	void prev() {
		start();
		
		CurrentResult currentResult = flowAPIService.current(new CurrentForm(processInstanceId.get()));
		assertEquals(RECEIVE_TASK, currentResult.getActivityType());
		assertEquals("waitState1", currentResult.getActivityId());
		
		NextResult nextResult = flowAPIService.next(new NextForm(processInstanceId.get()));
		assertEquals(RECEIVE_TASK, nextResult.getActivityType());
		assertEquals("waitState2", nextResult.getActivityId());
		
		nextResult = flowAPIService.next(new NextForm(processInstanceId.get()));
		assertEquals(RECEIVE_TASK, nextResult.getActivityType());
		assertEquals("waitState3", nextResult.getActivityId());
		
		for (int i = 0; i < 5; i++) {
			// prev
			PrevResult prevResult = flowAPIService.prev(new PrevForm(processInstanceId.get()));
			assertEquals(RECEIVE_TASK, prevResult.getActivityType());
			assertEquals("waitState2", prevResult.getActivityId());
			
			nextResult = flowAPIService.next(new NextForm(processInstanceId.get()));
			assertEquals(RECEIVE_TASK, nextResult.getActivityType());
			assertEquals("waitState3", nextResult.getActivityId());
		}
		
		nextResult = flowAPIService.next(new NextForm(processInstanceId.get()));
		assertEquals(USER_TASK, nextResult.getActivityType());
		assertEquals("task1", nextResult.getActivityId());
		
		for (int i = 0; i < 5; i++) {
			nextResult = flowAPIService.next(new NextForm(processInstanceId.get()));
			assertEquals(USER_TASK, nextResult.getActivityType());
			assertEquals("task2", nextResult.getActivityId());
			
			// prev
			PrevResult prevResult = flowAPIService.prev(new PrevForm(processInstanceId.get()));
			assertEquals(USER_TASK, prevResult.getActivityType());
			assertEquals("task1", prevResult.getActivityId());
		}
		
		// end
		flowAPIService.end(new EndForm(processInstanceId.get()));
		
		// current
		currentResult = flowAPIService.current(new CurrentForm(processInstanceId.get()));
		assertEquals(END_EVENT, currentResult.getActivityType());
		assertNull(currentResult.getActivityId());
	}
	
	@Test
	void back() {
		start();
		
		for (int i = 0; i < 5; i++) {
			
			CurrentResult currentResult = flowAPIService.current(new CurrentForm(processInstanceId.get()));
			assertEquals(RECEIVE_TASK, currentResult.getActivityType());
			assertEquals("waitState1", currentResult.getActivityId());
			
			NextResult nextResult = flowAPIService.next(new NextForm(processInstanceId.get()));
			assertEquals(RECEIVE_TASK, nextResult.getActivityType());
			assertEquals("waitState2", nextResult.getActivityId());
			
			nextResult = flowAPIService.next(new NextForm(processInstanceId.get()));
			assertEquals(RECEIVE_TASK, nextResult.getActivityType());
			assertEquals("waitState3", nextResult.getActivityId());
			
			nextResult = flowAPIService.next(new NextForm(processInstanceId.get()));
			assertEquals(USER_TASK, nextResult.getActivityType());
			assertEquals("task1", nextResult.getActivityId());
			
			// back
			flowAPIService.back(new BackForm(processInstanceId.get(), "waitState1"));
		}
		
		// end
		flowAPIService.end(new EndForm(processInstanceId.get()));
		
		// current
		CurrentResult currentResult = flowAPIService.current(new CurrentForm(processInstanceId.get()));
		assertEquals(END_EVENT, currentResult.getActivityType());
		assertNull(currentResult.getActivityId());
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