package com.ooooo.api;

import com.ooooo.api.dto.req.ExportForm;
import com.ooooo.api.dto.resp.DeployForm;
import com.ooooo.api.dto.resp.DeployResult;
import com.ooooo.api.dto.resp.ExportResult;

/**
 * @author leizhijie
 * @since 1.0.0
 */
public interface FlowAdminService {
	
	DeployResult deploy(DeployForm form);
	
	ExportResult export(ExportForm form);
	
	
}
