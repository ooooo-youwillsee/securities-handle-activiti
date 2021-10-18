package com.ooooo.activiti.config;

import com.ooooo.activiti.api.ActivitiAPIService;
import com.ooooo.activiti.api.ActivitiAdminService;
import com.ooooo.activiti.api.ActivitiEventService;
import com.ooooo.activiti.api.impl.ActivitiAPIServiceImpl;
import com.ooooo.activiti.api.impl.ActivitiAdminServiceImpl;
import com.ooooo.activiti.api.impl.ActivitiEventServiceImpl;
import com.ooooo.activiti.extension.ExtensionDefaultServiceTaskBehavior;
import org.activiti.runtime.api.connector.DefaultServiceTaskBehavior;
import org.activiti.runtime.api.connector.IntegrationContextBuilder;
import org.activiti.runtime.api.impl.VariablesMappingProvider;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.ooooo.activiti.api.ActivitiAPIService.ACTIVITI_API_SERVICE_BEAN_NAME;
import static com.ooooo.activiti.api.ActivitiAdminService.ACTIVITI_ADMIN_SERVICE_BEAN_NAME;
import static com.ooooo.activiti.api.ActivitiEventService.ACTIVITI_EVENT_SERVICE_BEAN_NAME;
import static org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory.DEFAULT_SERVICE_TASK_BEAN_NAME;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Configuration
public class ActivitiConfiguration {
	
	@Autowired
	private SpringProcessEngineConfiguration configuration;
	
	
	@Bean(name = DEFAULT_SERVICE_TASK_BEAN_NAME)
	@ConditionalOnMissingBean(name = DEFAULT_SERVICE_TASK_BEAN_NAME)
	public DefaultServiceTaskBehavior defaultServiceTaskBehavior(ApplicationContext applicationContext, IntegrationContextBuilder integrationContextBuilder, VariablesMappingProvider outboundVariablesProvider) {
		return new ExtensionDefaultServiceTaskBehavior(applicationContext, integrationContextBuilder, outboundVariablesProvider);
	}
	
	
	@Bean(ACTIVITI_ADMIN_SERVICE_BEAN_NAME)
	@ConditionalOnMissingBean(name = ACTIVITI_ADMIN_SERVICE_BEAN_NAME)
	public ActivitiAdminService activitiAdminService() {
		return new ActivitiAdminServiceImpl();
	}
	
	@Bean(ACTIVITI_API_SERVICE_BEAN_NAME)
	@ConditionalOnMissingBean(name = ACTIVITI_API_SERVICE_BEAN_NAME)
	public ActivitiAPIService activitiAPIService() {
		return new ActivitiAPIServiceImpl();
	}
	
	@Bean(ACTIVITI_EVENT_SERVICE_BEAN_NAME)
	@ConditionalOnMissingBean(name = ACTIVITI_EVENT_SERVICE_BEAN_NAME)
	public ActivitiEventService activitiEventService() {
		return new ActivitiEventServiceImpl();
	}
	
}
