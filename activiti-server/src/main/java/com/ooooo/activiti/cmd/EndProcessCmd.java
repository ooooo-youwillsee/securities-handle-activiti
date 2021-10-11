package com.ooooo.activiti.cmd;

import java.util.List;
import lombok.AllArgsConstructor;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@AllArgsConstructor
public class EndProcessCmd implements Command<Void> {
	
	private final String processInstanceId;
	
	@Override
	public Void execute(CommandContext commandContext) {
		ExecutionEntityManager executionEntityManager = commandContext.getExecutionEntityManager();
		List<ExecutionEntity> executions = executionEntityManager.findChildExecutionsByProcessInstanceId(processInstanceId);
		if (executions == null) {
			return null;
		}
		
		FlowElement endEventElement = getEndEventElement(executions.get(0).getProcessDefinitionId());
		
		executions.forEach(execution -> {
			execution.setCurrentFlowElement(endEventElement);
			commandContext.getAgenda().planContinueProcessOperation(execution);
		});
		
		return null;
	}
	
	/**
	 * get element for &lt;endEvent id="end"&gt;&lt;/endEvent&gt;
	 *
	 * @param processDefinitionId
	 * @return
	 */
	private FlowElement getEndEventElement(String processDefinitionId) {
		Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);
		for (FlowElement flowElement : process.getFlowElements()) {
			if (flowElement instanceof EndEvent) {
				return flowElement;
			}
		}
		throw new IllegalArgumentException("not found element['endEvent'], processDefinitionId: " + processDefinitionId);
	}
	
}
