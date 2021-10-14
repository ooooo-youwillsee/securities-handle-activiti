package com.ooooo.api.dto.resp;

import com.ooooo.common.annotation.ImportantField;
import com.ooooo.common.enums.ActivityType;
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
public class NextResult {
	
	@ImportantField
	private String activityId;
	
	@ImportantField
	private ActivityType activityType;
}
