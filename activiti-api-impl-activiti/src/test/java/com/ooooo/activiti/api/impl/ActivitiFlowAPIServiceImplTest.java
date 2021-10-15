package com.ooooo.activiti.api.impl;

import com.ooooo.activiti.api.FlowAPIService;
import com.ooooo.activiti.api.dto.req.BackProcessForm;
import com.ooooo.activiti.api.dto.req.CurrentActivityForm;
import com.ooooo.activiti.api.dto.req.EndProcessForm;
import com.ooooo.activiti.api.dto.req.NextActivityForm;
import com.ooooo.activiti.api.dto.req.PrevActivityForm;
import com.ooooo.activiti.api.dto.req.StartProcessForm;
import com.ooooo.activiti.api.dto.resp.CurrentActivityResult;
import com.ooooo.activiti.api.dto.resp.EndProcessResult;
import com.ooooo.activiti.api.dto.resp.NextActivityResult;
import com.ooooo.activiti.api.dto.resp.PrevActivityResult;
import com.ooooo.activiti.api.dto.resp.StartProcessResult;
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
		
		StartProcessForm form = new StartProcessForm(processDefinitionKey.get());
		StartProcessResult result = flowAPIService.startProcess(form);
		
		assertNotNull(result.getProcessInstanceId());
		processInstanceId.set(result.getProcessInstanceId());
	}
	
	@Test
	void current() {
		start();
		CurrentActivityForm form = new CurrentActivityForm(processInstanceId.get());
		CurrentActivityResult result = flowAPIService.currentActivity(form);
		
		assertEquals(RECEIVE_TASK, result.getActivityType());
		assertEquals("waitState1", result.getActivityId());
	}
	
	@Test
	void next() {
		start();
		
		CurrentActivityResult currentActivityResult = flowAPIService.currentActivity(new CurrentActivityForm(processInstanceId.get()));
		assertEquals(RECEIVE_TASK, currentActivityResult.getActivityType());
		assertEquals("waitState1", currentActivityResult.getActivityId());
		
		NextActivityResult nextActivityResult = flowAPIService.nextActivity(new NextActivityForm(processInstanceId.get()));
		assertEquals(RECEIVE_TASK, nextActivityResult.getActivityType());
		assertEquals("waitState2", nextActivityResult.getActivityId());
		
		nextActivityResult = flowAPIService.nextActivity(new NextActivityForm(processInstanceId.get()));
		assertEquals(RECEIVE_TASK, nextActivityResult.getActivityType());
		assertEquals("waitState3", nextActivityResult.getActivityId());
		
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
		
		nextActivityResult = flowAPIService.nextActivity(new NextActivityForm(processInstanceId.get()));
		assertEquals(USER_TASK, nextActivityResult.getActivityType());
		assertEquals("task1", nextActivityResult.getActivityId());
		
		nextActivityResult = flowAPIService.nextActivity(new NextActivityForm(processInstanceId.get()));
		assertEquals(USER_TASK, nextActivityResult.getActivityType());
		assertEquals("task2", nextActivityResult.getActivityId());
		
		nextActivityResult = flowAPIService.nextActivity(new NextActivityForm(processInstanceId.get()));
		assertEquals(END_EVENT, nextActivityResult.getActivityType());
		assertNull(nextActivityResult.getActivityId());
	}
	
	@Test
	void prev() {
		start();
		
		CurrentActivityResult currentActivityResult = flowAPIService.currentActivity(new CurrentActivityForm(processInstanceId.get()));
		assertEquals(RECEIVE_TASK, currentActivityResult.getActivityType());
		assertEquals("waitState1", currentActivityResult.getActivityId());
		
		NextActivityResult nextActivityResult = flowAPIService.nextActivity(new NextActivityForm(processInstanceId.get()));
		assertEquals(RECEIVE_TASK, nextActivityResult.getActivityType());
		assertEquals("waitState2", nextActivityResult.getActivityId());
		
		nextActivityResult = flowAPIService.nextActivity(new NextActivityForm(processInstanceId.get()));
		assertEquals(RECEIVE_TASK, nextActivityResult.getActivityType());
		assertEquals("waitState3", nextActivityResult.getActivityId());
		
		for (int i = 0; i < 5; i++) {
			// prev
			PrevActivityResult prevActivityResult = flowAPIService.prevActivity(new PrevActivityForm(processInstanceId.get()));
			assertEquals(RECEIVE_TASK, prevActivityResult.getActivityType());
			assertEquals("waitState2", prevActivityResult.getActivityId());
			
			nextActivityResult = flowAPIService.nextActivity(new NextActivityForm(processInstanceId.get()));
			assertEquals(RECEIVE_TASK, nextActivityResult.getActivityType());
			assertEquals("waitState3", nextActivityResult.getActivityId());
		}
		
		nextActivityResult = flowAPIService.nextActivity(new NextActivityForm(processInstanceId.get()));
		assertEquals(USER_TASK, nextActivityResult.getActivityType());
		assertEquals("task1", nextActivityResult.getActivityId());
		
		for (int i = 0; i < 5; i++) {
			nextActivityResult = flowAPIService.nextActivity(new NextActivityForm(processInstanceId.get()));
			assertEquals(USER_TASK, nextActivityResult.getActivityType());
			assertEquals("task2", nextActivityResult.getActivityId());
			
			// prev
			PrevActivityResult prevResult = flowAPIService.prevActivity(new PrevActivityForm(processInstanceId.get()));
			assertEquals(USER_TASK, prevResult.getActivityType());
			assertEquals("task1", prevResult.getActivityId());
		}
		
		// end
		flowAPIService.endProcess(new EndProcessForm(processInstanceId.get()));
		
		// current
		currentActivityResult = flowAPIService.currentActivity(new CurrentActivityForm(processInstanceId.get()));
		assertEquals(END_EVENT, currentActivityResult.getActivityType());
		assertNull(currentActivityResult.getActivityId());
	}
	
	@Test
	void back() {
		start();
		
		for (int i = 0; i < 5; i++) {
			
			CurrentActivityResult currentActivityResult = flowAPIService.currentActivity(new CurrentActivityForm(processInstanceId.get()));
			assertEquals(RECEIVE_TASK, currentActivityResult.getActivityType());
			assertEquals("waitState1", currentActivityResult.getActivityId());
			
			NextActivityResult nextActivityResult = flowAPIService.nextActivity(new NextActivityForm(processInstanceId.get()));
			assertEquals(RECEIVE_TASK, nextActivityResult.getActivityType());
			assertEquals("waitState2", nextActivityResult.getActivityId());
			
			nextActivityResult = flowAPIService.nextActivity(new NextActivityForm(processInstanceId.get()));
			assertEquals(RECEIVE_TASK, nextActivityResult.getActivityType());
			assertEquals("waitState3", nextActivityResult.getActivityId());
			
			nextActivityResult = flowAPIService.nextActivity(new NextActivityForm(processInstanceId.get()));
			assertEquals(USER_TASK, nextActivityResult.getActivityType());
			assertEquals("task1", nextActivityResult.getActivityId());
			
			// back
			flowAPIService.backActivity(new BackProcessForm(processInstanceId.get(), "waitState1"));
		}
		
		// end
		flowAPIService.endProcess(new EndProcessForm(processInstanceId.get()));
		
		// current
		CurrentActivityResult currentActivityResult = flowAPIService.currentActivity(new CurrentActivityForm(processInstanceId.get()));
		assertEquals(END_EVENT, currentActivityResult.getActivityType());
		assertNull(currentActivityResult.getActivityId());
	}
	
	@Test
	void end() {
		start();
		
		EndProcessResult endProcessResult = flowAPIService.endProcess(new EndProcessForm(processInstanceId.get()));
		
		CurrentActivityResult currentActivityResult = flowAPIService.currentActivity(new CurrentActivityForm(processInstanceId.get()));
		assertEquals(END_EVENT, currentActivityResult.getActivityType());
		assertNull(currentActivityResult.getActivityId());
	}
}