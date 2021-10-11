package com.ooooo.api;

import com.ooooo.api.event.ActivityEvent;

/**
 * @author leizhijie
 * @since 1.0.0
 */
public interface FlowEventService {
	
	void onStartedFlowEvent(ActivityEvent e);
	
	void onNextStepEvent(ActivityEvent e);
	
	void onPrevStepEvent(ActivityEvent e);
	
	void onBackedFlowEvent(ActivityEvent e);
	
	void onEndedFlowEvent(ActivityEvent e);
}
