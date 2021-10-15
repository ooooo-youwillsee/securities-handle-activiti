package com.ooooo.activiti.dto;

import com.ooooo.activiti.common.enums.ActivityType;
import lombok.Data;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Data
public class ActivityEntity {
	
	private String activityId;
	
	private ActivityType activityType;
	
}
