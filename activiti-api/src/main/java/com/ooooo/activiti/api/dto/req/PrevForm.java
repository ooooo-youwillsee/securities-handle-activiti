package com.ooooo.activiti.api.dto.req;

import com.ooooo.activiti.common.annotation.ImportantField;
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
