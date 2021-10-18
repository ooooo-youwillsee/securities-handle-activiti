package com.ooooo.activiti.common.config;

import com.ooooo.activiti.common.enums.ActivitiEventInvokeType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@Data
@ConfigurationProperties("spring.activiti")
public class ActivitiExtensionProperties {
	
	private ActivitiEventInvokeType eventInvokeType;
	
	
}
