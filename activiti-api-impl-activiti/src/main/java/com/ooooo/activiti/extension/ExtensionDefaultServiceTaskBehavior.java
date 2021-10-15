package com.ooooo.activiti.extension;

import com.ooooo.activiti.config.ActivitiConfiguration;
import org.activiti.api.process.model.IntegrationContext;
import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.runtime.api.connector.DefaultServiceTaskBehavior;
import org.activiti.runtime.api.connector.IntegrationContextBuilder;
import org.activiti.runtime.api.impl.VariablesMappingProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import static com.ooooo.activiti.constants.ActivitiConfigConstants.SKIP_SERVICE_TASK_KEY;
import static com.ooooo.activiti.constants.ActivitiConfigConstants.SKIP_SERVICE_TASK_VALUE;
import static org.activiti.runtime.api.impl.MappingExecutionContext.buildMappingExecutionContext;

/**
 * @author leizhijie
 * @see DefaultServiceTaskBehavior
 * @see ActivitiConfiguration
 * @since 1.0.0
 */
public class ExtensionDefaultServiceTaskBehavior extends DefaultServiceTaskBehavior {
	
	private final ApplicationContext applicationContext;
	private final IntegrationContextBuilder integrationContextBuilder;
	private final VariablesMappingProvider outboundVariablesProvider;
	
	public ExtensionDefaultServiceTaskBehavior(ApplicationContext applicationContext, IntegrationContextBuilder integrationContextBuilder, VariablesMappingProvider outboundVariablesProvider) {
		super(applicationContext, integrationContextBuilder, outboundVariablesProvider);
		this.applicationContext = applicationContext;
		this.integrationContextBuilder = integrationContextBuilder;
		this.outboundVariablesProvider = outboundVariablesProvider;
	}
	
	@Override
	public void execute(DelegateExecution execution) {
		Connector connector = getConnector(getImplementation(execution));
		IntegrationContext integrationContext = null;
		try {
			integrationContext = connector.apply(integrationContextBuilder.from(execution));
		} catch (Exception e) {
			return;
		}
		
		execution.setVariables(outboundVariablesProvider.calculateOutPutVariables(buildMappingExecutionContext(execution),
		                                                                          integrationContext.getOutBoundVariables()));
		leave(execution);
	}
	
	private String getImplementation(DelegateExecution execution) {
		return ((ServiceTask) execution.getCurrentFlowElement()).getImplementation();
	}
	
	private Connector getConnector(String implementation) {
		return applicationContext.getBean(implementation,
		                                  Connector.class);
	}
	
	@Override
	public void trigger(DelegateExecution execution, String signalName, Object signalData) {
		RuntimeService runtimeService = applicationContext.getBean(RuntimeService.class);
		String skipServiceTaskValue = (String) runtimeService.getVariable(execution.getId(), SKIP_SERVICE_TASK_KEY);
		if (StringUtils.equals(skipServiceTaskValue, SKIP_SERVICE_TASK_VALUE)) {
			leave(execution);
			return;
		}
		
		execute(execution);
	}
}
