package com.ooooo.activiti.cloud;

import com.ooooo.activiti.api.ActivitiAPIService;
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
import com.ooooo.activiti.api.dubbo.DubboActivitiAPIService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@DubboService
public class DubboActivitiAPIServiceImpl implements DubboActivitiAPIService {
	
	@Autowired
	@Qualifier(ACTIVITI_API_SERVICE_BEAN_NAME)
	private ActivitiAPIService apiService;
	
	@Override
	public StartProcessResult startProcess(StartProcessForm form) {
		return apiService.startProcess(form);
	}
	
	@Override
	public CurrentActivityResult currentActivity(CurrentActivityForm form) {
		return apiService.currentActivity(form);
	}
	
	@Override
	public NextActivityResult nextActivity(NextActivityForm form) {
		return apiService.nextActivity(form);
	}
	
	@Override
	public PrevActivityResult prevActivity(PrevActivityForm form) {
		return apiService.prevActivity(form);
	}
	
	@Override
	public BackActivityResult backActivity(BackProcessForm form) {
		return apiService.backActivity(form);
	}
	
	@Override
	public EndProcessResult endProcess(EndProcessForm form) {
		return apiService.endProcess(form);
	}
	
}
