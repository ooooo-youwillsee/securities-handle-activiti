package com.ooooo.activiti.api.impl;

import com.ooooo.activiti.cmd.CurrentActivityCmd;
import com.ooooo.activiti.cmd.EndProcessCmd;
import com.ooooo.activiti.extension.CommandService;
import com.ooooo.api.FlowAPIService;
import com.ooooo.api.dto.req.*;
import com.ooooo.api.dto.resp.*;
import com.ooooo.dto.Void;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ooooo.api.enums.ActivityType.END_EVENT;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Slf4j
@Component
public class ActivitiFlowAPIServiceImpl implements FlowAPIService {
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private CommandService commandService;
	
	@Override
	public StartResult start(StartForm form) {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(form.getProcessDefinitionKey(), form.getVariables());
		
		StartResult result = new StartResult();
		result.setProcessInstanceId(processInstance.getId());
		return result;
	}
	
	@Override
	public CurrentResult current(CurrentForm form) {
		Void activity = commandService.execute(new CurrentActivityCmd(form.getProcessInstanceId()));
		
		CurrentResult result = new CurrentResult();
		result.setActivityId(activity.getActivityId());
		result.setActivityType(activity.getActivityType());
		return result;
	}
	
	@Override
	public NextResult next(NextForm form) {
		String processInstanceId = form.getProcessInstanceId();
		
		// query current
		Void curActivity = commandService.execute(new CurrentActivityCmd(processInstanceId));
		
		if (curActivity.getActivityType().equals(END_EVENT)) {
			log.warn("next warn!, processInstanceId: {}, activityType: {}", processInstanceId, END_EVENT.getType());
			NextResult result = new NextResult();
			result.setActivityId(curActivity.getActivityId());
			result.setActivityType(curActivity.getActivityType());
			return result;
		}
		
		// query current, then handle result
		Void nextActivity = commandService.execute(new CurrentActivityCmd(processInstanceId));
		
		NextResult result = new NextResult();
		result.setActivityId(nextActivity.getActivityId());
		result.setActivityType(nextActivity.getActivityType());
		return result;
	}
	
	
	@Override
	public PrevResult prev(PrevForm form) {
		return null;
	}
	
	@Override
	public BackResult back(BackForm form) {
		return null;
	}
	
	@Override
	public EndResult end(EndForm form) {
		commandService.execute(new EndProcessCmd(form.getProcessInstanceId()));
		EndResult result = new EndResult();
		return result;
	}
	
	
	/**
	 * 查询 ACT_RU_EXECUTION  表
	 *
	 * @param processInstanceId
	 * @return
	 */
	private ExecutionEntity getExecution(String processInstanceId) {
		Execution execution = runtimeService.createExecutionQuery()
		                                    .processInstanceId(processInstanceId)
		                                    .onlyChildExecutions()
		                                    .singleResult();
		return (ExecutionEntity) execution;
	}
	
	
}
