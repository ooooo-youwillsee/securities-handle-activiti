package com.ooooo.activiti.api.impl;

import com.ooooo.activiti.api.FlowAPIService;
import com.ooooo.activiti.api.dto.req.BackProcessForm;
import com.ooooo.activiti.api.dto.req.CurrentActivityForm;
import com.ooooo.activiti.api.dto.req.EndProcessForm;
import com.ooooo.activiti.api.dto.req.NextActivityForm;
import com.ooooo.activiti.api.dto.req.PrevActivityForm;
import com.ooooo.activiti.api.dto.req.StartProcessForm;
import com.ooooo.activiti.api.dto.resp.BackActivityResult;
import com.ooooo.activiti.api.dto.resp.CurrentActivityResult;
import com.ooooo.activiti.api.dto.resp.EndProcessResult;
import com.ooooo.activiti.api.dto.resp.NextActivityResult;
import com.ooooo.activiti.api.dto.resp.PrevActivityResult;
import com.ooooo.activiti.api.dto.resp.StartProcessResult;
import com.ooooo.activiti.cmd.BackActivityCmd;
import com.ooooo.activiti.cmd.CurrentActivityCmd;
import com.ooooo.activiti.cmd.EndProcessCmd;
import com.ooooo.activiti.cmd.NextActivityCmd;
import com.ooooo.activiti.cmd.PrevActivityCmd;
import com.ooooo.activiti.cmd.StartProcessCmd;
import com.ooooo.activiti.dto.ActivityEntity;
import com.ooooo.activiti.dto.ProcessEnitty;
import com.ooooo.activiti.service.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ooooo.activiti.common.enums.ActivityType.END_EVENT;

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
	public StartProcessResult start(StartProcessForm form) {
		log.info("start, processDefinitionKey: {}", form.getProcessDefinitionKey());
		
		ProcessEnitty processEnitty = commandService.execute(new StartProcessCmd(form.getProcessDefinitionKey(), form.getVariables()));
		
		log.info("start, processDefinitionKey: {}, processInstanceId: {}", form.getProcessDefinitionKey(), processEnitty.getProcessInstanceId());
		
		StartProcessResult result = new StartProcessResult();
		result.setProcessInstanceId(processEnitty.getProcessInstanceId());
		return result;
	}
	
	@Override
	public CurrentActivityResult current(CurrentActivityForm form) {
		ActivityEntity activity = commandService.execute(new CurrentActivityCmd(form.getProcessInstanceId()));
		
		CurrentActivityResult result = new CurrentActivityResult();
		result.setActivityId(activity.getActivityId());
		result.setActivityType(activity.getActivityType());
		return result;
	}
	
	@Override
	public NextActivityResult next(NextActivityForm form) {
		String processInstanceId = form.getProcessInstanceId();
		
		// query current activity
		ActivityEntity curActivity = commandService.execute(new CurrentActivityCmd(processInstanceId));
		if (curActivity.getActivityType().equals(END_EVENT)) {
			log.warn("next warn!, processInstanceId: {}, activityType: {}", processInstanceId, END_EVENT.getType());
			NextActivityResult result = new NextActivityResult();
			result.setActivityId(curActivity.getActivityId());
			result.setActivityType(curActivity.getActivityType());
			return result;
		}
		
		// next activity
		commandService.execute(new NextActivityCmd(processInstanceId, form.getVariables()));
		
		// query current activity
		ActivityEntity nextActivity = commandService.execute(new CurrentActivityCmd(processInstanceId));
		
		NextActivityResult result = new NextActivityResult();
		result.setActivityId(nextActivity.getActivityId());
		result.setActivityType(nextActivity.getActivityType());
		return result;
	}
	
	
	@Override
	public PrevActivityResult prev(PrevActivityForm form) {
		// prev activity
		commandService.execute(new PrevActivityCmd(form.getProcessInstanceId()));
		// query current activity
		ActivityEntity curActivity = commandService.execute(new CurrentActivityCmd(form.getProcessInstanceId()));
		
		PrevActivityResult result = new PrevActivityResult();
		result.setActivityId(curActivity.getActivityId());
		result.setActivityType(curActivity.getActivityType());
		return result;
	}
	
	@Override
	public BackActivityResult back(BackProcessForm form) {
		commandService.execute(new BackActivityCmd(form.getProcessInstanceId(), form.getActivityId()));
		
		BackActivityResult result = new BackActivityResult();
		return result;
	}
	
	@Override
	public EndProcessResult end(EndProcessForm form) {
		commandService.execute(new EndProcessCmd(form.getProcessInstanceId()));
		
		EndProcessResult result = new EndProcessResult();
		return result;
	}
	
}
