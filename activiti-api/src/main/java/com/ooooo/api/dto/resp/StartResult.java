package com.ooooo.api.dto.resp;

import com.ooooo.api.ImportField;
import lombok.Data;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Data
public class StartResult {
	
	@ImportField
	private String processInstanceId;
	
}
