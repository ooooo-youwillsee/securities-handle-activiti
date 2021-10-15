package com.ooooo.activiti.api;

import com.ooooo.activiti.api.dto.req.ExportForm;
import com.ooooo.activiti.api.dto.resp.DeployForm;
import com.ooooo.activiti.api.dto.resp.DeployResult;
import com.ooooo.activiti.api.dto.resp.ExportResult;

/**
 * @author leizhijie
 * @since 1.0.0
 */
public interface FlowAdminService {
	
	DeployResult deploy(DeployForm form);
	
	ExportResult export(ExportForm form);
	
	
}
