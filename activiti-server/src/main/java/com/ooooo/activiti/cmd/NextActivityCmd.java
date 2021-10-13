package com.ooooo.activiti.cmd;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.ReceiveTask;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@AllArgsConstructor
public class NextActivityCmd implements Command<Void> {
	
	private final String processInstanceId;
	
	private final Map<String, java.lang.Object> variables;
	
	@Override
	public Void execute(CommandContext commandContext) {
		// get services
		RuntimeService runtimeService = Context.getProcessEngineConfiguration().getRuntimeService();
		TaskService taskService = Context.getProcessEngineConfiguration().getTaskService();
		ExecutionEntityManager executionEntityManager = commandContext.getExecutionEntityManager();
		
		List<ExecutionEntity> executions = executionEntityManager.findChildExecutionsByProcessInstanceId(processInstanceId);
		if (executions == null) {
			return null;
		}
		
		// current
		ExecutionEntity execution = executions.get(0);
		FlowElement element = execution.getCurrentFlowElement();
		String executionId = execution.getId();
		
		// invoke to next activity
		if (element instanceof ReceiveTask) {
			runtimeService.trigger(executionId, variables);
		} else if (element instanceof ServiceTask) {
			runtimeService.trigger(executionId, variables);
		} else if (element instanceof UserTask) {
			String taskId = taskService.createTaskQuery()
			                           .processInstanceId(processInstanceId)
			                           .executionId(executionId)
			                           .singleResult()
			                           .getId();
			taskService.complete(taskId, variables);
		}
		
		return null;
	}
}
