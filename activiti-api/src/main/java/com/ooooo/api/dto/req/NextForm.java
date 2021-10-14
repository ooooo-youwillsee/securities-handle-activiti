package com.ooooo.api.dto.req;

import com.ooooo.common.annotation.ImportantField;
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
public class NextForm {
	
	@ImportantField
	private String processInstanceId;
	
	/**
	 * 向流程中存储变量参数
	 */
	private Map<String, Object> variables;
	
	
	public NextForm(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
}
