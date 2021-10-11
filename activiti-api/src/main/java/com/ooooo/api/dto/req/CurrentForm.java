package com.ooooo.api.dto.req;

import com.ooooo.api.ImportField;
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
public class CurrentForm {
	
	@ImportField
	private String processInstanceId;
}
