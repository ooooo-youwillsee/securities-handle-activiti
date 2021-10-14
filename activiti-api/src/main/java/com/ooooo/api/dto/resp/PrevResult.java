package com.ooooo.api.dto.resp;

import com.ooooo.common.annotation.ImportantField;
import com.ooooo.common.enums.ActivityType;
import lombok.Data;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Data
public class PrevResult {
	
	@ImportantField
	private String activityId;
	
	@ImportantField
	private ActivityType activityType;
}
