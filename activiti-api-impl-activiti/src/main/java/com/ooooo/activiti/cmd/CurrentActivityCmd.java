package com.ooooo.activiti.cmd;

import com.ooooo.activiti.common.enums.ActivityType;
import com.ooooo.activiti.dto.ActivityEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.apache.commons.lang3.StringUtils;

import static com.ooooo.activiti.common.enums.ActivityType.END_EVENT;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@AllArgsConstructor
public class CurrentActivityCmd implements Command<ActivityEntity> {
	
	private final String processInstanceId;
	
	@Override
	public ActivityEntity execute(CommandContext commandContext) {
		ActivityEntity activityEntity = new ActivityEntity();
		
		ExecutionEntityManager executionEntityManager = commandContext.getExecutionEntityManager();
		List<ExecutionEntity> executions = executionEntityManager.findChildExecutionsByProcessInstanceId(processInstanceId);
		if (executions == null || executions.isEmpty()) {
			activityEntity.setActivityType(END_EVENT);
			return activityEntity;
		}
		
		// current element
		ExecutionEntity execution = executions.get(0);
		
		FlowElement element = execution.getCurrentFlowElement();
		// eg: ReceiveTask -> receiveTask
		String elementName = StringUtils.uncapitalize(element.getClass().getSimpleName());
		
		ActivityType activityType = ActivityType.of(elementName);
		activityEntity.setActivityType(activityType);
		activityEntity.setActivityId(execution.getActivityId());
		
		return activityEntity;
	}
}
