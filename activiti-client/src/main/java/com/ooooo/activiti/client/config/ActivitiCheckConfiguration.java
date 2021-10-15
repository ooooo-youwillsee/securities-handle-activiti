package com.ooooo.activiti.client.config;

import com.ooooo.activiti.client.ActivitiBridge;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
//@ConditionalOnClass(value = {ApplicationContext.class})
//@Configuration
public class ActivitiCheckConfiguration {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	public InitializingBean checkActivitiClientBean() {
		return () -> {
			applicationContext.getBean(ActivitiBridge.class);
		};
	}
	
	
}
