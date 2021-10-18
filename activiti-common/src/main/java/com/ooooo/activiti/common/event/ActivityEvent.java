package com.ooooo.activiti.common.event;

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
public class ActivityEvent {
	
	@ImportantField
	private String processDefinitionKey;
	
	@ImportantField
	private String processDefinitionId;
	
	@ImportantField
	private String processInstanceId;
	
	@ImportantField
	private String activityId;
	
	@ImportantField
	private String activityName;
	
	@ImportantField
	private String activityType;
	
	private Long timestamp = System.currentTimeMillis();
	
	public void setActivityType(String activityType) {
		if (activityType == null || activityType.isEmpty()) {
			throw new IllegalArgumentException("type is null");
		}
		// eg: ReceiveTask -> receiveTask
		this.activityType = activityType.substring(0, 1).toLowerCase() + activityType.substring(1);
	}
	
}
