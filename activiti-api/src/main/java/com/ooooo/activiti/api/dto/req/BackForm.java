package com.ooooo.activiti.api.dto.req;

import com.ooooo.activiti.common.annotation.ImportantField;
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
public class BackForm {
	
	@ImportantField
	private String processInstanceId;
	
	@ImportantField
	private String activityId;
	
}
