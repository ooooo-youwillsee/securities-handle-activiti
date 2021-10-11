package com.ooooo.activiti.api.impl;

import com.ooooo.api.FlowAPIService;
import com.ooooo.api.dto.req.*;
import com.ooooo.api.dto.resp.*;
import com.ooooo.api.enums.ActivityType;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.*;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;
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
	
	@Override
	public StartResult start(StartForm form) {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(form.getProcessDefinitionKey(), form.getVariables());
		
		StartResult result = new StartResult();
		result.setProcessInstanceId(processInstance.getId());
		return result;
	}
	
	@Override
	public CurrentResult current(CurrentForm form) {
		String processInstanceId = form.getProcessInstanceId();
		CurrentResult result = new CurrentResult();
		
		// query current
		ExecutionEntity currentExecution = getExecution(processInstanceId);
		if (currentExecution == null) {
			log.warn("current warn!, processInstanceId: {}, 'currentExecution' is null", processInstanceId);
			result.setActivityType(END_EVENT);
			return result;
		}
		
		FlowElement currentFlowElement = currentExecution.getCurrentFlowElement();
		if (currentFlowElement == null) {
			log.error("current error!, processInstanceId: {}, executionId: {}, activityId: {}", processInstanceId, currentExecution.getId(), currentExecution.getActivityId());
			throw new IllegalArgumentException("processInstanceId: " + processInstanceId + ", 'currentFlowElement' is null ");
		}
		
		result.setActivityId(currentExecution.getActivityId());
		result.setActivityType(ActivityType.valueOf(currentFlowElement.getClass().getSimpleName()));
		
		return result;
	}
	
	@Override
	public NextResult next(NextForm form) {
		String processInstanceId = form.getProcessInstanceId();
		NextResult result = new NextResult();
		
		// query current
		ExecutionEntity currentExecution = getExecution(processInstanceId);
		if (currentExecution == null) {
			log.warn("next warn!, processInstanceId: {}, 'currentExecution' is null", processInstanceId);
			result.setActivityType(END_EVENT);
			return result;
		}
		
		FlowElement currentFlowElement = currentExecution.getCurrentFlowElement();
		if (currentFlowElement == null) {
			log.error("next error!, processInstanceId: {}, executionId: {}, activityId: {}", processInstanceId, currentExecution.getId(), currentExecution.getActivityId());
			throw new IllegalArgumentException("processInstanceId: " + processInstanceId + ", 'currentFlowElement' is null ");
		}
		
		// invoke to next activity
		if (currentFlowElement instanceof ReceiveTask) {
			runtimeService.trigger(currentExecution.getId(), form.getVariables());
		} else if (currentFlowElement instanceof ServiceTask) {
			runtimeService.trigger(currentExecution.getId(), form.getVariables());
		} else if (currentFlowElement instanceof UserTask) {
			taskService.complete(currentExecution.getId(), form.getVariables());
		}
		
		// query current, then handle result
		CurrentResult current = current(new CurrentForm(processInstanceId));
		
		result.setActivityId(current.getActivityId());
		result.setActivityType(current.getActivityType());
		
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
		String processInstanceId = form.getProcessInstanceId();
		EndResult result = new EndResult();
		
		// set the end element for execution
		ExecutionEntity currentExecution = getExecution(processInstanceId);
		FlowElement endEventElement = findFlowElement(currentExecution.getProcessDefinitionId(), END_EVENT.getType());
		currentExecution.setCurrentFlowElement(endEventElement);
		
		// continue
		Context.getAgenda().planContinueProcessOperation(currentExecution);
		
		return result;
	}
	
	private FlowElement findFlowElement(String processDefinitionId, String activityId) {
		Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);
		FlowElement flowElement = process.getFlowElement(activityId, true);
		return flowElement;
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
