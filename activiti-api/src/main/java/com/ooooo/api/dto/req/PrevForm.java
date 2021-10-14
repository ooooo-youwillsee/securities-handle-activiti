package com.ooooo.api.dto.req;

import com.ooooo.common.annotation.ImportantField;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
public class PrevForm {
	
	@ImportantField
	private String processInstanceId;
	
}
