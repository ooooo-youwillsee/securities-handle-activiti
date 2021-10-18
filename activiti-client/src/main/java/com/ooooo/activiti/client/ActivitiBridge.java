package com.ooooo.activiti.client;


import com.ooooo.activiti.client.dto.req.ReceiveTaskUrlConfigForm;
import com.ooooo.activiti.client.dto.req.ServiceTaskUrlConfigForm;
import com.ooooo.activiti.client.dto.resp.ReceiveTaskUrlConfigResult;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
public interface ActivitiBridge {
	
	ReceiveTaskUrlConfigResult query(ReceiveTaskUrlConfigForm form);
	
	ServiceTaskUrlConfigForm query(ServiceTaskUrlConfigForm form);
}
