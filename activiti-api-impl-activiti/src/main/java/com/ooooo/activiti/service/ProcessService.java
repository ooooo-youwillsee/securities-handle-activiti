package com.ooooo.activiti.service;

import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@Component
public class ProcessService {
	
	@Autowired
	private SpringProcessEngineConfiguration processEngineConfiguration;
	
	public Process getProcessById(String processDefinitionId) {
		DeploymentManager deploymentManager = processEngineConfiguration.getDeploymentManager();
		ProcessDefinition processDefinitionEntity = deploymentManager.findDeployedProcessDefinitionById(processDefinitionId);
		return deploymentManager.resolveProcessDefinition(processDefinitionEntity).getProcess();
	}
	
	public Process getProcessByKey(String processDefinitionKey) {
		DeploymentManager deploymentManager = processEngineConfiguration.getDeploymentManager();
		ProcessDefinition processDefinitionEntity = deploymentManager.findDeployedLatestProcessDefinitionByKey(processDefinitionKey);
		return deploymentManager.resolveProcessDefinition(processDefinitionEntity).getProcess();
	}
	
	public FlowElement getFlowElement(String processDefinitionId, String flowElementId) {
		Process process = getProcessById(processDefinitionId);
		return process.getFlowElement(flowElementId, true);
	}
	
}
