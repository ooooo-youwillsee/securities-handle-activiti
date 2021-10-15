package com.ooooo.activiti.config;

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
	
}
