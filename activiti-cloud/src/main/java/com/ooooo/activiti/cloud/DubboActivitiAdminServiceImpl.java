package com.ooooo.activiti.cloud;

import com.ooooo.activiti.api.ActivitiAdminService;
import com.ooooo.activiti.api.dto.req.DeployProcessForm;
import com.ooooo.activiti.api.dto.req.ExportProcessForm;
import com.ooooo.activiti.api.dto.req.ProcessDefinitionForm;
import com.ooooo.activiti.api.dto.resp.DeployProcessResult;
import com.ooooo.activiti.api.dto.resp.ExportProcessResult;
import com.ooooo.activiti.api.dto.resp.ProcessDefinitionResult;
import com.ooooo.activiti.api.dubbo.DubboActivitiAdminService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@DubboService
public class DubboActivitiAdminServiceImpl implements DubboActivitiAdminService {
	
	@Autowired
	@Qualifier(ACTIVITI_ADMIN_SERVICE_BEAN_NAME)
	private ActivitiAdminService adminService;
	
	@Override
	public DeployProcessResult deploy(DeployProcessForm form) {
		return adminService.deploy(form);
	}
	
	@Override
	public ExportProcessResult export(ExportProcessForm form) {
		return adminService.export(form);
	}
	
	@Override
	public ProcessDefinitionResult processDefinition(ProcessDefinitionForm form) {
		return adminService.processDefinition(form);
	}
	
}
