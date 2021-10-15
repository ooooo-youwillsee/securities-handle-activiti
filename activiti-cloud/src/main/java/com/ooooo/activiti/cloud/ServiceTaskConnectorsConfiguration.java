package com.ooooo.activiti.cloud;

import com.alibaba.fastjson.JSON;
import com.ooooo.activiti.common.entity.config.ServiceTaskConfigEntity;
import com.ooooo.activiti.common.entity.config.ServiceTaskConfigEntity.ServiceType;
import com.ooooo.activiti.common.entity.invoke.ServiceTaskInvokeRequestEntity;
import com.ooooo.activiti.common.entity.invoke.ServiceTaskInvokeResponseEntity;
import com.ooooo.activiti.extension.ExtensionDefaultServiceTaskBehavior;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.activiti.api.process.model.IntegrationContext;
import org.activiti.api.process.runtime.connector.Connector;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import static com.ooooo.activiti.common.constants.ActivitiConfigConstants.DEFAULT_SERVICE_TASK_CONFIG_ENTITY_KEY;
import static com.ooooo.activiti.common.constants.ActivitiConfigConstants.SERVICE_TASK_CONFIG_ENTITY_KEY;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @see ExtensionDefaultServiceTaskBehavior
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class ServiceTaskConnectorsConfiguration {
	
	public static final String SERIVCE_TASK_RESTTEMPLATE_BEAN = "serivceTaskRestTemplate";
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	public Connector defaultServiceTaskConnector() {
		return integrationContext -> {
			String currentActivityId = getCurrentActivityId(integrationContext);
			List<ServiceTaskConfigEntity> serviceTaskConfigEntityList = getServiceTaskConfigEntityList(integrationContext);
			
			// find serviceTaskConfigEntity corresponding to currentActivityId
			ServiceTaskConfigEntity selectedServiceTaskConfigEntity = null;
			if (serviceTaskConfigEntityList != null) {
				for (ServiceTaskConfigEntity configEntity : serviceTaskConfigEntityList) {
					if (currentActivityId.equals(configEntity.getActivityId())) {
						selectedServiceTaskConfigEntity = configEntity;
						break;
					}
				}
			}
			
			// use default serviceTaskConfigEntity
			if (selectedServiceTaskConfigEntity == null) {
				selectedServiceTaskConfigEntity = getDefaultServiceTaskConfigEntity(integrationContext);
			}
			
			// invoke url
			ServiceTaskInvokeResponseEntity responseEntity = invoke(selectedServiceTaskConfigEntity, integrationContext);
			
			// handle outBoundVariables
			if (responseEntity != null && responseEntity.getOutBoundVariables() != null) {
				integrationContext.addOutBoundVariables(responseEntity.getOutBoundVariables());
			}
			
			return integrationContext;
		};
	}
	
	@Bean
	@ConditionalOnMissingBean(name = SERIVCE_TASK_RESTTEMPLATE_BEAN)
	public RestTemplate serivceTaskRestTemplate() {
		OkHttpClient client = new OkHttpClient.Builder().build();
		
		OkHttp3ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory(client);
		requestFactory.setReadTimeout(60 * 1000 * 3);
		requestFactory.setConnectTimeout(1000 * 10);
		RestTemplate template = new RestTemplate(requestFactory);
		// utf8
		template.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		// 可以接受其他类型的状态码， 例如400
		template.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) {
				return false;
			}
			
			@Override
			public void handleError(ClientHttpResponse response) {
			}
		});
		return template;
	}
	
	private ServiceTaskConfigEntity getDefaultServiceTaskConfigEntity(IntegrationContext integrationContext) {
		Map<String, Object> inBoundVariables = integrationContext.getInBoundVariables();
		
		String configJson = (String) inBoundVariables.get(DEFAULT_SERVICE_TASK_CONFIG_ENTITY_KEY);
		if (StringUtils.isBlank(configJson)) {
			return null;
		}
		
		return JSON.parseObject(configJson, ServiceTaskConfigEntity.class);
	}
	
	private List<ServiceTaskConfigEntity> getServiceTaskConfigEntityList(IntegrationContext integrationContext) {
		Map<String, Object> inBoundVariables = integrationContext.getInBoundVariables();
		
		String configJson = (String) inBoundVariables.get(SERVICE_TASK_CONFIG_ENTITY_KEY);
		if (StringUtils.isBlank(configJson)) {
			return null;
		}
		
		return JSON.parseArray(configJson, ServiceTaskConfigEntity.class);
	}
	
	private String getCurrentActivityId(IntegrationContext integrationContext) {
		return integrationContext.getClientId();
	}
	
	private ServiceTaskInvokeResponseEntity invoke(ServiceTaskConfigEntity serviceTaskConfig, IntegrationContext context) {
		if (serviceTaskConfig == null) {
			log.warn("serviceTask invoke Url is ignored, because serviceTaskConfig is null, processDefinitionId: {}, activityId: {}", context.getProcessDefinitionId(), context.getClientId());
			return null;
		}
		
		// build requestEntity
		ServiceTaskInvokeRequestEntity requestEntity = new ServiceTaskInvokeRequestEntity();
		requestEntity.setProcessDefinitionId(context.getProcessDefinitionId());
		requestEntity.setProcessDefinitionKey(context.getProcessDefinitionKey());
		requestEntity.setParentProcessInstanceId(context.getParentProcessInstanceId());
		requestEntity.setProcessInstanceId(context.getProcessInstanceId());
		requestEntity.setActivityId(context.getClientId());
		requestEntity.setActivityName(context.getClientName());
		requestEntity.setActivityType(context.getClientType());
		
		ServiceType serviceType = serviceTaskConfig.getServiceType();
		
		switch (serviceType) {
			case HTTP:
				return doInvokeHttp(requestEntity);
			default:
				log.error("serviceTask invoke url is ignored, url: {}, serviceType: {}", serviceTaskConfig.getUrl(), serviceTaskConfig.getServiceType().getValue());
		}
		
		return null;
	}
	
	/**
	 * invoke depend on restTemplate
	 *
	 * @param requestEntity
	 * @return
	 * @see #serivceTaskRestTemplate()
	 */
	private ServiceTaskInvokeResponseEntity doInvokeHttp(ServiceTaskInvokeRequestEntity requestEntity) {
		
		return null;
	}
	
	
}
