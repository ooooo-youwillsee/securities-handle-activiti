package com.ooooo.activiti.cmd;

import com.ooooo.api.enums.ActivityType;
import com.ooooo.dto.Void;
import java.util.List;
import lombok.AllArgsConstructor;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.apache.commons.lang3.StringUtils;

import static com.ooooo.api.enums.ActivityType.END_EVENT;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@AllArgsConstructor
public class CurrentActivityCmd implements Command<Void> {
	
	private final String processInstanceId;
	
	@Override
	public Void execute(CommandContext commandContext) {
		Void currentActivityEntity = new Void();
		
		ExecutionEntityManager executionEntityManager = commandContext.getExecutionEntityManager();
		List<ExecutionEntity> executions = executionEntityManager.findChildExecutionsByProcessInstanceId(processInstanceId);
		if (executions == null) {
			currentActivityEntity.setActivityType(END_EVENT);
			return currentActivityEntity;
		}
		
		// current element
		ExecutionEntity execution = executions.get(0);
		
		FlowElement element = execution.getCurrentFlowElement();
		// eg: ReceiveTask -> receiveTask
		String elementName = StringUtils.uncapitalize(element.getClass().getSimpleName());
		
		ActivityType activityType = ActivityType.of(elementName);
		currentActivityEntity.setActivityType(activityType);
		currentActivityEntity.setActivityId(execution.getActivityId());
		
		return currentActivityEntity;
	}
}
