package com.ooooo.activiti.api;

import com.ooooo.activiti.api.dto.req.DeployProcessForm;
import com.ooooo.activiti.api.dto.req.ExportProcessForm;
import com.ooooo.activiti.api.dto.req.ProcessDefinitionForm;
import com.ooooo.activiti.api.dto.resp.DeployProcessResult;
import com.ooooo.activiti.api.dto.resp.ExportProcessResult;
import com.ooooo.activiti.api.dto.resp.ProcessDefinitionResult;

/**
 * @author leizhijie
 * @since 1.0.0
 */
public interface FlowAdminService {
	
	DeployProcessResult deploy(DeployProcessForm form);
	
	ExportProcessResult export(ExportProcessForm form);
	
	ProcessDefinitionResult processDefinition(ProcessDefinitionForm form);
}
