package com.ooooo.activiti.client.config;

import com.ooooo.activiti.common.config.ActivitiExtensionProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@Slf4j
@Configuration
@ConditionalOnClass(value = {ApplicationContext.class})
@EnableConfigurationProperties(ActivitiExtensionProperties.class)
public class ActivitiCheckConfiguration {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private ActivitiExtensionProperties activitiExtensionProperties;
	
	///**
	// * check the bean of {@link ActivitiBridge}
	// *
	// * @return
	// */
	//@Bean
	//public InitializingBean checkActivitiBridgeBean() {
	//	return () -> applicationContext.getBean(ActivitiBridge.class);
	//}
	//
	///**
	// * check the bean of {@link ActivitiEventService}
	// *
	// * @return
	// */
	//@Bean
	//public InitializingBean checkActivitiEventServiceBean() {
	//	return () -> {
	//		ActivitiEventInvokeType eventInvokeType = activitiExtensionProperties.getEventInvokeType();
	//		switch (eventInvokeType) {
	//			case HTTP:
	//				checkHttpActivitiEventServiceBean();
	//				break;
	//			case DUBBO:
	//				checkDubboActivitiEventServiceBean();
	//				break;
	//			case NONE:
	//				log.info("spring.activiti.eventInvokeType = 'NONE', disabled ProcessEvent dispatch");
	//				break;
	//			default:
	//				throw new IllegalArgumentException("'spring.activiti.eventInvokeType' is wrong");
	//		}
	//	};
	//}
	//
	//private void checkDubboActivitiEventServiceBean() {
	//
	//}
	//
	//private void checkHttpActivitiEventServiceBean() {
	//
	//}
	//
	
}
