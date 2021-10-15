package com.ooooo.activiti.client;


import com.ooooo.activiti.client.dto.req.InvokeUrlResult;
import com.ooooo.activiti.client.dto.resp.InvokeUrlForm;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
public interface ActivitiBridge {
	
	InvokeUrlResult query(InvokeUrlForm form);
}
