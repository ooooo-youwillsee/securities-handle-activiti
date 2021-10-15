package com.ooooo.activiti.client.dto.resp;

import java.util.Map;
import lombok.Data;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@Data
public class InvokeUrlForm {
	
	private String processInstanceId;
	
	private String activityId;
	
	/**
	 * 流程中变量参数
	 */
	private Map<String, Object> variables;
}
