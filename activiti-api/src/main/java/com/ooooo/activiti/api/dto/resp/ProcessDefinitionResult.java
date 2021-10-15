package com.ooooo.activiti.api.dto.resp;

import com.ooooo.activiti.common.enums.ActivityType;
import java.util.List;
import lombok.Data;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@Data
public class ProcessDefinitionResult {
	
	private List<ProcessActivityDefinition> activityDefinitionList;
	
	/**
	 * behalf an activity
	 */
	@Data
	public static class ProcessActivityDefinition {
		
		private String activityId;
		
		private String activityName;
		
		private ActivityType activityType;
	}
}
