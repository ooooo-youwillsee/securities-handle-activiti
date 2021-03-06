package com.ooooo.activiti.cmd;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.lang3.RandomStringUtils;

import static com.ooooo.activiti.cmd.CommonContextHelper.getCommandExecutor;
import static com.ooooo.activiti.cmd.CommonContextHelper.getExecutionEntities;
import static com.ooooo.activiti.cmd.CommonContextHelper.getFlowElement;
import static org.apache.commons.beanutils.BeanUtils.cloneBean;

/**
 * jump to the activity
 *
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@AllArgsConstructor
public class JumpActivityCmd implements Command<Void> {
	
	private final String processInstanceId;
	
	private final FlowNode targetFlowNode;
	
	
	@SneakyThrows
	@Override
	public Void execute(CommandContext commandContext) {
		List<ExecutionEntity> executions = getExecutionEntities(commandContext, processInstanceId);
		if (executions == null) {
			return null;
		}
		
		for (ExecutionEntity curExecution : executions) {
			// get current flowElement
			Activity curFlowElement = (Activity) cloneBean(getFlowElement(commandContext, processInstanceId, curExecution.getActivityId()));
			curExecution.setCurrentFlowElement(curFlowElement);
			
			// get targetFlowNode inComingFlows sequenceFlow, then set sourceFlowElement
			SequenceFlow targetIncomingSequenceFlow = (SequenceFlow) cloneBean(targetFlowNode.getIncomingFlows().get(0));
			targetIncomingSequenceFlow.setId(RandomStringUtils.randomAlphabetic(6));
			targetIncomingSequenceFlow.setSourceFlowElement(curFlowElement);
			targetIncomingSequenceFlow.setSourceRef(curFlowElement.getId());
			
			// set new outgoingFlow
			curFlowElement.setOutgoingFlows(Collections.singletonList(targetIncomingSequenceFlow));
			
			// next activity
			CommandExecutor commandExecutor = getCommandExecutor(commandContext);
			commandExecutor.execute(new NextActivityCmd(processInstanceId, null));
		}
		
		return null;
	}
	
	
}
