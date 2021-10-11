package com.ooooo.api.dto.req;

import com.ooooo.api.ImportField;
import java.util.Map;
import lombok.Data;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Data
public class StartForm {
	
	@ImportField
	private String processDefinitionKey;
	
	/**
	 * 向流程中存储变量参数
	 */
	private Map<String, Object> variables;
	
}
