package com.ooooo.activiti.api.impl;

import com.ooooo.activiti.api.ActivitiEventService;
import com.ooooo.activiti.common.event.ActivityEvent;
import com.ooooo.activiti.service.IActivitiEventListener;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@Slf4j
public class ActivitiEventServiceImpl implements ActivitiEventService {
	
	@Autowired
	private ObjectProvider<List<IActivitiEventListener>> listenersObjectProvider;
	
	@Override
	public void onProcessStartedEvent(ActivityEvent e) {
		listenersObjectProvider.ifAvailable(listeners -> listeners.forEach(l -> l.onProcessStartedEvent(e)));
	}
	
	@Override
	public void onNextActivityEvent(ActivityEvent e) {
		listenersObjectProvider.ifAvailable(listeners -> listeners.forEach(l -> l.onNextActivityEvent(e)));
	}
	
	@Override
	public void onPrevActivityEvent(ActivityEvent e) {
		listenersObjectProvider.ifAvailable(listeners -> listeners.forEach(l -> l.onPrevActivityEvent(e)));
	}
	
	@Override
	public void onBackedActivityEvent(ActivityEvent e) {
		listenersObjectProvider.ifAvailable(listeners -> listeners.forEach(l -> l.onBackedActivityEvent(e)));
	}
	
	@Override
	public void onProcessEndedEvent(ActivityEvent e) {
		listenersObjectProvider.ifAvailable(listeners -> listeners.forEach(l -> l.onProcessEndedEvent(e)));
	}
}
