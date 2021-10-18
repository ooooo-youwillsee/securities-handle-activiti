package com.ooooo.activiti.api;

import com.ooooo.activiti.common.annotation.Internal;
import com.ooooo.activiti.common.event.ActivityEvent;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Internal
public interface ActivitiEventService {
	
	String ACTIVITI_EVENT_SERVICE_BEAN_NAME = "ActivitiEventService";
	
	void onProcessStartedEvent(ActivityEvent e);
	
	void onNextActivityEvent(ActivityEvent e);
	
	void onPrevActivityEvent(ActivityEvent e);
	
	void onBackedActivityEvent(ActivityEvent e);
	
	void onProcessEndedEvent(ActivityEvent e);
}
