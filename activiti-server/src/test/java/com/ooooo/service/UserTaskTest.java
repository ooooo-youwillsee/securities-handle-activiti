package com.ooooo.service;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@SpringBootTest
public class UserTaskTest {
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	
	/**
	 * userTask, 下一步
	 */
	@Test
	public void complete() {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("UserTaskTest.complete");
		assertNotNull(processInstance.getId());
		
		Task task = taskService.createTaskQuery()
		                       .processInstanceId(processInstance.getId())
		                       .singleResult();
		assertNotNull(task);
		taskService.complete(task.getId());
		
		Task nextTask = taskService.createTaskQuery()
		                           .processInstanceId(processInstance.getId())
		                           .singleResult();
		
		assertNull(nextTask);
	}
	
}
