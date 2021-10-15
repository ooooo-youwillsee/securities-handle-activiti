package com.ooooo.activiti.api.dto.resp;

import com.ooooo.activiti.common.annotation.ImportantField;
import com.ooooo.activiti.common.enums.ActivityType;
import lombok.Data;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Data
public class CurrentResult {
	
	@ImportantField
	private String activityId;
	
	@ImportantField
	private ActivityType activityType;
}
