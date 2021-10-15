package com.ooooo.activiti.common.constants;

import com.ooooo.activiti.common.entity.config.ServiceTaskConfigEntity;

/**
 * @author leizhijie
 * @since 1.0.0
 */
public interface ActivitiConfigConstants {
	
	/**
	 * see com.ooooo.activiti.extension.ExtensionDefaultServiceTaskBehavior#trigger}
	 */
	String SKIP_SERVICE_TASK_KEY = "__SKIP_SERVICE_TASK";
	
	/**
	 * see com.ooooo.activiti.extension.ExtensionDefaultServiceTaskBehavior#trigger
	 */
	String SKIP_SERVICE_TASK_VALUE = "1";
	
	/**
	 * see com.ooooo.activit.cloud.ServiceTaskConnectorsConfiguration
	 *
	 * @see ServiceTaskConfigEntity
	 */
	String SERVICE_TASK_CONFIG_ENTITY_KEY = "__SERVICE_TASK_CONFIG_ENTITY";
	
	/**
	 * see com.ooooo.activit.cloud.ServiceTaskConnectorsConfiguration
	 *
	 * @see ServiceTaskConfigEntity
	 */
	String DEFAULT_SERVICE_TASK_CONFIG_ENTITY_KEY = "__DEFAULT_SERVICE_TASK_CONFIG_ENTITY";
}
