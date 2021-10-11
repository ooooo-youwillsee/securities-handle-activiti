package com.ooooo.api.dto.req;

import com.ooooo.api.ImportField;
import lombok.Data;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Data
public class EndForm {
	
	@ImportField
	private String processInstanceId;
	
}
