package com.ooooo.activiti.api.dto.resp;

import com.ooooo.activiti.common.annotation.ImportantField;
import lombok.Data;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Data
public class StartProcessResult {
	
	@ImportantField
	private String processInstanceId;
	
}
