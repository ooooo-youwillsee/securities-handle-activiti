package com.ooooo.activiti.common.entity.config;

import lombok.Data;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@Data
public class ReceiveTaskConfigEntity {
	
	private String activityId;
	
	/**
	 * front-end rul
	 * <p>for example: /login</p>
	 */
	private String url;
}
