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

import static com.ooooo.api.enums.ActivityType.RECEIVE_TASK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


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
		
		NextForm form = new NextForm(processInstanceId.get());
		NextResult result = flowAPIService.next(form);
		
		assertNotNull(result.getActivityId());
		assertNotNull(result.getActivityType());
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
		
		EndForm form = new EndForm();
		EndResult result = flowAPIService.end(form);
		
	}
}