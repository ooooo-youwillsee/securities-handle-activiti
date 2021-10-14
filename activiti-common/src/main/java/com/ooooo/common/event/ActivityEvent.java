package com.ooooo.common.event;

import com.ooooo.common.annotation.ImportantField;
import lombok.Data;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Data
public class ActivityEvent {
	
	@ImportantField
	private String processInstanceId;
	
	@ImportantField
	private String activityId;
}
