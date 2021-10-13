package com.ooooo.activiti.cmd;

import com.ooooo.activiti.extension.CommandService;
import java.util.List;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManager;
import org.activiti.engine.repository.ProcessDefinition;

/**
 * helper class for CommonContext
 *
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @see CommandContext
 * @see CommandExecutor
 * @see CommandService
 * @since 1.0.0
 */
public class CommonContextHelper {
	
	public static CommandExecutor getCommandExecutor(CommandContext commandContext) {
		return commandContext.getProcessEngineConfiguration().getCommandExecutor();
	}
	
	public static RuntimeService getRuntimeService(CommandContext commandContext) {
		return commandContext.getProcessEngineConfiguration().getRuntimeService();
	}
	
	public static TaskService getTaskService(CommandContext commandContext) {
		return commandContext.getProcessEngineConfiguration().getTaskService();
	}
	
	public static HistoricActivityInstanceEntityManager getHistoricActivityInstanceEntityManager(CommandContext commandContext) {
		return commandContext.getProcessEngineConfiguration().getHistoricActivityInstanceEntityManager();
	}
	
	// ==========================================================
	
	public static List<ExecutionEntity> getExecutionEntities(CommandContext commandContext, String processInstanceId) {
		ExecutionEntityManager executionEntityManager = commandContext.getExecutionEntityManager();
		List<ExecutionEntity> executions = executionEntityManager.findChildExecutionsByProcessInstanceId(processInstanceId);
		return executions;
	}
	
	public static Process getProcess(CommandContext commandContext, String processInstanceId) {
		List<ExecutionEntity> executionEntities = getExecutionEntities(commandContext, processInstanceId);
		if (executionEntities == null) {
			return null;
		}
		
		String processDefinitionId = executionEntities.get(0).getProcessDefinitionId();
		DeploymentManager deploymentManager = commandContext.getProcessEngineConfiguration().getDeploymentManager();
		ProcessDefinition processDefinitionEntity = deploymentManager.findDeployedProcessDefinitionById(processDefinitionId);
		return deploymentManager.resolveProcessDefinition(processDefinitionEntity).getProcess();
	}
	
	
	public static FlowElement getFlowElement(CommandContext commandContext, String processInstanceId, String flowElementId) {
		Process process = getProcess(commandContext, processInstanceId);
		return process.getFlowElement(flowElementId, true);
	}
	
	
}
