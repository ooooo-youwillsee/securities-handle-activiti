package com.ooooo.api.dto.resp;

import com.ooooo.common.annotation.ImportantField;
import lombok.Data;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Data
public class StartResult {
	
	@ImportantField
	private String processInstanceId;
	
}
