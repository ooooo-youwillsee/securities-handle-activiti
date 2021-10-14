package com.ooooo.common.enums;

import java.util.Objects;

/**
 * @author leizhijie
 * @since 1.0.0
 */
public class ActivityTypeHelper {
	
	public static boolean isServiceTask(ActivityType activityType) {
		return Objects.equals(activityType, ActivityType.SERVICE_TASK);
	}
	
	public static boolean isUserTask(ActivityType activityType) {
		return Objects.equals(activityType, ActivityType.USER_TASK);
	}
	
	public static boolean isReceiveTask(ActivityType activityType) {
		return Objects.equals(activityType, ActivityType.RECEIVE_TASK);
	}
	
}
