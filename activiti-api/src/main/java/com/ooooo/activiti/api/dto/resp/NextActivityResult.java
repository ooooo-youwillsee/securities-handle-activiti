package com.ooooo.activiti.api.dto.resp;

import com.ooooo.activiti.common.annotation.ImportantField;
import com.ooooo.activiti.common.enums.ActivityType;
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
public class NextActivityResult {
	
	@ImportantField
	private String activityId;
	
	@ImportantField
	private ActivityType activityType;
}
