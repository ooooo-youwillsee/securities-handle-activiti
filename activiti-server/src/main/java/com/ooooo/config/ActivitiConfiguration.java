package com.ooooo.config;

import com.ooooo.custom.CustomDefaultServiceTaskBehavior;
import org.activiti.runtime.api.connector.DefaultServiceTaskBehavior;
import org.activiti.runtime.api.connector.IntegrationContextBuilder;
import org.activiti.runtime.api.impl.VariablesMappingProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Configuration
public class ActivitiConfiguration {
	
	@Bean(
			name = {"defaultServiceTaskBehavior"}
	)
	@ConditionalOnMissingBean(
			name = {"defaultServiceTaskBehavior"}
	)
	public DefaultServiceTaskBehavior defaultServiceTaskBehavior(ApplicationContext applicationContext, IntegrationContextBuilder integrationContextBuilder, VariablesMappingProvider outboundVariablesProvider) {
		return new CustomDefaultServiceTaskBehavior(applicationContext, integrationContextBuilder, outboundVariablesProvider);
	}
	
	
}
