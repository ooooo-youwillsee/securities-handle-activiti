package com.ooooo.activiti.api.impl;

import com.ooooo.activiti.cmd.*;
import com.ooooo.activiti.service.CommandService;
import com.ooooo.api.FlowAPIService;
import com.ooooo.api.dto.req.*;
import com.ooooo.api.dto.resp.*;
import com.ooooo.dto.ActivityEntity;
import com.ooooo.dto.ProcessEnitty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ooooo.common.enums.ActivityType.END_EVENT;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Slf4j
@Component
public class ActivitiFlowAPIServiceImpl implements FlowAPIService {
	
	@Autowired
	private CommandService commandService;
	
	@Override
	public StartResult start(StartForm form) {
		log.info("start, processDefinitionKey: {}", form.getProcessDefinitionKey());
		
		ProcessEnitty processEnitty = commandService.execute(new StartProcessCmd(form.getProcessDefinitionKey(), form.getVariables()));
		
		log.info("start, processDefinitionKey: {}, processInstanceId: {}", form.getProcessDefinitionKey(), processEnitty.getProcessInstanceId());
		
		StartResult result = new StartResult();
		result.setProcessInstanceId(processEnitty.getProcessInstanceId());
		return result;
	}
	
	@Override
	public CurrentResult current(CurrentForm form) {
		ActivityEntity activity = commandService.execute(new CurrentActivityCmd(form.getProcessInstanceId()));
		
		CurrentResult result = new CurrentResult();
		result.setActivityId(activity.getActivityId());
		result.setActivityType(activity.getActivityType());
		return result;
	}
	
	@Override
	public NextResult next(NextForm form) {
		String processInstanceId = form.getProcessInstanceId();
		
		// query current activity
		ActivityEntity curActivity = commandService.execute(new CurrentActivityCmd(processInstanceId));
		if (curActivity.getActivityType().equals(END_EVENT)) {
			log.warn("next warn!, processInstanceId: {}, activityType: {}", processInstanceId, END_EVENT.getType());
			NextResult result = new NextResult();
			result.setActivityId(curActivity.getActivityId());
			result.setActivityType(curActivity.getActivityType());
			return result;
		}
		
		// next activity
		commandService.execute(new NextActivityCmd(processInstanceId, form.getVariables()));
		
		// query current activity
		ActivityEntity nextActivity = commandService.execute(new CurrentActivityCmd(processInstanceId));
		
		NextResult result = new NextResult();
		result.setActivityId(nextActivity.getActivityId());
		result.setActivityType(nextActivity.getActivityType());
		return result;
	}
	
	
	@Override
	public PrevResult prev(PrevForm form) {
		// prev activity
		commandService.execute(new PrevActivityCmd(form.getProcessInstanceId()));
		// query current activity
		ActivityEntity curActivity = commandService.execute(new CurrentActivityCmd(form.getProcessInstanceId()));
		
		PrevResult result = new PrevResult();
		result.setActivityId(curActivity.getActivityId());
		result.setActivityType(curActivity.getActivityType());
		return result;
	}
	
	@Override
	public BackResult back(BackForm form) {
		commandService.execute(new BackActivityCmd(form.getProcessInstanceId(), form.getActivityId()));
		
		BackResult result = new BackResult();
		return result;
	}
	
	@Override
	public EndResult end(EndForm form) {
		commandService.execute(new EndProcessCmd(form.getProcessInstanceId()));
		
		EndResult result = new EndResult();
		return result;
	}
	
}
