package com.ooooo.api.dto.req;

import com.ooooo.api.ImportField;
import java.util.Map;
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
public class StartForm {
	
	@ImportField
	private String processDefinitionKey;
	
	/**
	 * 向流程中存储变量参数
	 */
	private Map<String, Object> variables;
	
	
	public StartForm(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
}
