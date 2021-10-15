package com.ooooo.activiti.api.impl;

import com.ooooo.activiti.api.FlowAdminService;
import com.ooooo.activiti.api.dto.req.DeployProcessForm;
import com.ooooo.activiti.api.dto.req.ExportProcessForm;
import com.ooooo.activiti.api.dto.req.ProcessDefinitionForm;
import com.ooooo.activiti.api.dto.resp.DeployProcessResult;
import com.ooooo.activiti.api.dto.resp.ExportProcessResult;
import com.ooooo.activiti.api.dto.resp.ProcessDefinitionResult;
import com.ooooo.activiti.api.dto.resp.ProcessDefinitionResult.ProcessActivityDefinition;
import com.ooooo.activiti.common.enums.ActivityType;
import com.ooooo.activiti.service.ProcessService;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ooooo.activiti.common.enums.ActivityType.of;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@Slf4j
@Component
public class ActivitiFlowAdminService implements FlowAdminService {
	
	@Autowired
	private ProcessService processService;
	
	@Override
	public DeployProcessResult deploy(DeployProcessForm form) {
		return null;
	}
	
	@Override
	public ExportProcessResult export(ExportProcessForm form) {
		return null;
	}
	
	@Override
	public ProcessDefinitionResult processDefintion(ProcessDefinitionForm form) {
		Process process = processService.getProcessByKey(form.getProcessDefinitionKey());
		
		Set<String> activityTypes = Arrays.stream(ActivityType.values()).map(ActivityType::getType).collect(Collectors.toSet());
		
		List<ProcessActivityDefinition> activityDefinitions = process.getFlowElements()
		                                                             .stream()
		                                                             .filter(element -> activityTypes.contains(element.getClass().getSimpleName()))
		                                                             .map(element -> {
			                                                             ProcessActivityDefinition activityDefinition = new ProcessActivityDefinition();
			                                                             activityDefinition.setActivityId(element.getId());
			                                                             activityDefinition.setActivityName(element.getName());
			                                                             activityDefinition.setActivityType(of(element.getClass().getSimpleName()));
			
			                                                             return activityDefinition;
		                                                             })
		                                                             .collect(Collectors.toList());
		
		ProcessDefinitionResult result = new ProcessDefinitionResult();
		result.setActivityDefinitionList(activityDefinitions);
		return result;
	}
}
