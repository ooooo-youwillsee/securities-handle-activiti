package com.ooooo.api.dto.req;

import com.ooooo.api.ImportField;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
public class PrevForm {
	
	@ImportField
	private String processInstanceId;
	
}
