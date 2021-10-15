package com.ooooo.activiti.api.dto.req;

import com.ooooo.activiti.common.annotation.ImportantField;
import lombok.Data;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@Data
public class ProcessDefinitionForm {
	
	@ImportantField
	private String processDefinitionKey;
}
