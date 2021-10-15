package com.ooooo.activiti.common.entity.invoke;

import lombok.Data;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@Data
public class ServiceTaskInvokeRequestEntity {
	
	private String processInstanceId;
	
	private String parentProcessInstanceId;
	
	private String processDefinitionId;
	
	private String processDefinitionKey;
	
	private String activityId;
	
	private String activityName;
	
	private String activityType;
	
}
