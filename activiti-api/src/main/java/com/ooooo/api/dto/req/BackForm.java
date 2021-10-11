package com.ooooo.api.dto.req;

import com.ooooo.api.ImportField;
import lombok.Data;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Data
public class BackForm {
	
	@ImportField
	private String processInstanceId;
	
	@ImportField
	private String activityId;
	
}
