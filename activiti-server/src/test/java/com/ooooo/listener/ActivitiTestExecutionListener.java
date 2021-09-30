package com.ooooo.listener;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * @author leizhijie
 * @since 1.0.0
 */
public class ActivitiTestExecutionListener extends AbstractTestExecutionListener {
	
	
	@Override
	public void beforeTestExecution(TestContext testContext) throws Exception {
		GenericApplicationContext context = (GenericApplicationContext) testContext.getApplicationContext();
		String className = testContext.getTestClass().getName();
		String methodName = testContext.getTestMethod().getName();
		RuntimeService runtimeService = context.getBean(RuntimeService.class);
		
		String processDefinitionId = className + "." + methodName;
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionId);
		
		String processInstanceHolderBeanName = ProcessInstanceHolder.class.getName();
		if (context.containsBean(processInstanceHolderBeanName)) {
			ProcessInstanceHolder processInstanceHolder = context.getBean(ProcessInstanceHolder.class);
			processInstanceHolder.setProcessInstance(processInstance);
		} else {
			context.registerBean(ProcessInstanceHolder.class.getName(), ProcessInstanceHolder.class, () -> new ProcessInstanceHolder(processInstance));
		}
		
	}
}
