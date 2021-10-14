package com.ooooo.activiti.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ooooo.constants.ActivitiConfigConstants.SKIP_SERVICE_TASK_KEY;
import static com.ooooo.constants.ActivitiConfigConstants.SKIP_SERVICE_TASK_VALUE;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@SpringBootTest
public class ServiceTaskTest {
	
	@Autowired
	private RuntimeService runtimeService;
	
	/**
	 * 边界定时器，循环执行serviceTask
	 */
	@Test
	public void testBoundaryEvent() {
		Map<String, Object> variables = new HashMap<>();
		variables.put("exception_cnt", 3);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("ServiceTaskTest.testBoundaryEvent", variables);
		assertNotNull(processInstance.getId());
		
		try {
			new CountDownLatch(1).await(60, TimeUnit.SECONDS);
		} catch (InterruptedException ignored) {
		}
	}
	
	
	/**
	 * serviceTask 执行异常时， 重新再执行serviceTask
	 */
	@Test
	public void testServiceTaskContinueWithException() {
		Map<String, Object> variables = new HashMap<>();
		variables.put("exception_cnt", 1);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("ServiceTaskTest.testServiceTaskContinueWithException", variables);
		assertNotNull(processInstance.getId());
		
		try {
			new CountDownLatch(1).await(5, TimeUnit.SECONDS);
			
			runtimeService.createExecutionQuery()
			              .processInstanceId(processInstance.getId())
			              .onlyChildExecutions()
			              .list()
			              .forEach(execution -> {
				              runtimeService.setVariable(execution.getId(), "exception_cnt", 0);
				              runtimeService.trigger(execution.getId());
			              });
			new CountDownLatch(1).await(10, TimeUnit.SECONDS);
		} catch (InterruptedException ignored) {
		}
	}
	
	
	/**
	 * serviceTask 执行异常，跳过当前的serviceTask继续执行
	 */
	@Test
	public void testServiceTaskForceNextWithException() {
		Map<String, Object> variables = new HashMap<>();
		variables.put("exception_cnt", 1);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("ServiceTaskTest.testServiceTaskForceNextWithException", variables);
		assertNotNull(processInstance.getId());
		
		try {
			new CountDownLatch(1).await(5, TimeUnit.SECONDS);
			
			runtimeService.createExecutionQuery()
			              .processInstanceId(processInstance.getId())
			              .onlyChildExecutions()
			              .list()
			              .forEach(execution -> {
				              runtimeService.setVariable(execution.getId(), SKIP_SERVICE_TASK_KEY, SKIP_SERVICE_TASK_VALUE);
				              runtimeService.trigger(execution.getId());
			              });
			new CountDownLatch(1).await(10, TimeUnit.SECONDS);
		} catch (InterruptedException ignored) {
		}
	}
}
