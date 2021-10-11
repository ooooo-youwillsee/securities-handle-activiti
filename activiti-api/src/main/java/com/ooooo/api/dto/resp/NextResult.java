package com.ooooo.api.dto.resp;

import com.ooooo.api.ImportField;
import com.ooooo.api.enums.ActivityType;
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
	
	@ImportField
	private String activityId;
	
	@ImportField
	private ActivityType activityType;
}
