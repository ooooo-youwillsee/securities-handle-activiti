package com.ooooo.api.event;

import com.ooooo.api.ImportField;
import lombok.Data;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Data
public class ActivityEvent {
	
	@ImportField
	private String processInstanceId;
	
	@ImportField
	private String activityId;
}
