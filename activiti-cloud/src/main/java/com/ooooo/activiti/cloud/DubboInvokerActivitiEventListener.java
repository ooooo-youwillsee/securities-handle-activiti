package com.ooooo.activiti.cloud;

import com.ooooo.activiti.client.DubboActivitiEventListener;
import com.ooooo.activiti.common.event.ActivityEvent;
import com.ooooo.activiti.service.IActivitiEventListener;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@Component
public class DubboInvokerActivitiEventListener implements IActivitiEventListener {
	
	@DubboReference(check = false)
	private DubboActivitiEventListener dubboActivitiEventService;
	
	@Override
	public void onProcessStartedEvent(ActivityEvent e) {
	
	}
	
	@Override
	public void onNextActivityEvent(ActivityEvent e) {
	
	}
	
	@Override
	public void onPrevActivityEvent(ActivityEvent e) {
	
	}
	
	@Override
	public void onBackedActivityEvent(ActivityEvent e) {
	
	}
	
	@Override
	public void onProcessEndedEvent(ActivityEvent e) {
	
	}
}
