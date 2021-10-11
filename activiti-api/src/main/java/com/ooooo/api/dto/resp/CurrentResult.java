package com.ooooo.api.dto.resp;

import com.ooooo.api.ImportField;
import com.ooooo.api.enums.ActivityType;
import lombok.Data;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Data
public class CurrentResult {
	
	@ImportField
	private String activityId;
	
	@ImportField
	private ActivityType activityType;
}
