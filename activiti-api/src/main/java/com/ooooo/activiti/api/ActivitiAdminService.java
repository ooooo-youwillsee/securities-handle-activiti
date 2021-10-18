package com.ooooo.activiti.api;

import com.ooooo.activiti.api.dto.req.DeployProcessForm;
import com.ooooo.activiti.api.dto.req.ExportProcessForm;
import com.ooooo.activiti.api.dto.req.ProcessDefinitionForm;
import com.ooooo.activiti.api.dto.resp.DeployProcessResult;
import com.ooooo.activiti.api.dto.resp.ExportProcessResult;
import com.ooooo.activiti.api.dto.resp.ProcessDefinitionResult;
import com.ooooo.activiti.common.annotation.Internal;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Internal
public interface ActivitiAdminService {
	
	String ACTIVITI_ADMIN_SERVICE_BEAN_NAME = "ActivitiAdminService";
	
	DeployProcessResult deploy(DeployProcessForm form);
	
	ExportProcessResult export(ExportProcessForm form);
	
	ProcessDefinitionResult processDefinition(ProcessDefinitionForm form);
}
