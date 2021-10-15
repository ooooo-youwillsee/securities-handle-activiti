package com.ooooo.activiti.service;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@SpringBootTest
public class ReceiveTaskTest {
	
	@Autowired
	private RuntimeService runtimeService;
	
	
	/**
	 * receiveTask, 下一步
	 */
	@Test
	public void triggerNext() {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("ReceiveTaskTest.triggerNext");
		assertNotNull(processInstance.getId());
		
		Execution execution = runtimeService.createExecutionQuery()
		                                    .processInstanceId(processInstance.getId())
		                                    .onlyChildExecutions()
		                                    .singleResult();
		runtimeService.trigger(execution.getId());
		
		execution = runtimeService.createExecutionQuery()
		                          .processInstanceId(processInstance.getId())
		                          .activityId("waitState2")
		                          .onlyChildExecutions()
		                          .singleResult();
		
		assertNotNull(execution);
	}
	
	
}
