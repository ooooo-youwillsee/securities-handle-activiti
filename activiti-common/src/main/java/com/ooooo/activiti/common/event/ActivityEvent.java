package com.ooooo.activiti.common.event;

import com.ooooo.activiti.common.annotation.ImportantField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityEvent {
	
	@ImportantField
	private String processInstanceId;
	
	@ImportantField
	private String activityId;
	
	@ImportantField
	private String activityType;
}
