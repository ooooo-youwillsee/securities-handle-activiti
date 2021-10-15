package com.ooooo.activiti.common.enums;

import lombok.Getter;

/**
 * @author leizhijie
 * @since 1.0.0
 */
public enum ActivityType {
	
	START_EVENT("startEvent"),
	
	SERVICE_TASK("serviceTask"),
	
	RECEIVE_TASK("receiveTask"),
	
	USER_TASK("userTask"),
	
	END_EVENT("endEvent"),
	
	;
	
	@Getter
	private final String type;
	
	
	ActivityType(String type) {
		this.type = type;
	}
	
	public static ActivityType of(String type) {
		if (type == null || type.isEmpty()) {
			throw new IllegalArgumentException("type is null");
		}
		// eg: ReceiveTask -> receiveTask
		type = type.substring(0, 1).toLowerCase() + type.substring(1);
		
		for (ActivityType activityType : ActivityType.values()) {
			if (activityType.getType().equals(type)) {
				return activityType;
			}
		}
		
		throw new IllegalArgumentException("type['" + type + "'] was not exist in " + ActivityType.class.getName());
	}
	
}
