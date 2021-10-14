package com.ooooo.activiti.cmd;

import java.util.List;
import lombok.AllArgsConstructor;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.HistoricActivityInstanceQueryImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManager;

import static com.ooooo.activiti.cmd.CommonContextHelper.getCommandExecutor;
import static com.ooooo.activiti.cmd.CommonContextHelper.getExecutionEntities;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@AllArgsConstructor
public class PrevActivityCmd implements Command<Void> {
	
	private String processInstanceId;
	
	
	@Override
	public Void execute(CommandContext commandContext) {
		List<ExecutionEntity> executionEntities = getExecutionEntities(commandContext, processInstanceId);
		if (executionEntities == null || executionEntities.size() > 1) {
			throw new IllegalArgumentException("processInstanceId: " + processInstanceId + " can't execute PrevActivityCmd, because processs is finished");
		}
		
		// get services
		CommandExecutor commandExecutor = getCommandExecutor(commandContext);
		HistoricActivityInstanceEntityManager entityManager = CommonContextHelper.getHistoricActivityInstanceEntityManager(commandContext);
		
		// find historicActivityInstances
		HistoricActivityInstanceQueryImpl activityInstanceQuery = new HistoricActivityInstanceQueryImpl(commandContext).processInstanceId(processInstanceId)
		                                                                                                               .orderByHistoricActivityInstanceStartTime();
		List<HistoricActivityInstance> historicActivityInstances = entityManager.findHistoricActivityInstancesByQueryCriteria(activityInstanceQuery, null);
		
		// find last finished activity
		HistoricActivityInstance lastFinishedActivityInstance = null;
		for (int i = historicActivityInstances.size() - 1; i >= 0; i--) {
			HistoricActivityInstance historicActivityInstance = historicActivityInstances.get(i);
			if (historicActivityInstance.getEndTime() != null) {
				lastFinishedActivityInstance = historicActivityInstance;
				break;
			}
		}
		if (lastFinishedActivityInstance == null) {
			throw new IllegalArgumentException("processInstanceId: " + processInstanceId + " can't execute PrevActivityCmd, because lastFinishedActivityInstance is null");
		}
		
		// find the flow element of last activity
		String lastActivityId = lastFinishedActivityInstance.getActivityId();
		FlowElement lastFlowElement = CommonContextHelper.getFlowElement(commandContext, processInstanceId, lastActivityId);
		
		if (lastFlowElement instanceof FlowNode) {
			commandExecutor.execute(new JumpActivityCmd(processInstanceId, (FlowNode) lastFlowElement));
			return null;
		}
		
		throw new IllegalArgumentException("processInstanceId: " + processInstanceId + ", activityId: " + lastActivityId + " is not a instance of FlowNode");
	}
}
