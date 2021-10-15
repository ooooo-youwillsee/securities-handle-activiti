package com.ooooo.activiti.common.entity.config;

import lombok.Data;
import lombok.Getter;

import static com.ooooo.activiti.common.entity.config.ServiceTaskConfigEntity.ServiceType.DUBBO;
import static com.ooooo.activiti.common.entity.config.ServiceTaskConfigEntity.ServiceType.HTTP;
import static com.ooooo.activiti.common.entity.config.ServiceTaskConfigEntity.ServiceType.HTTPS;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@Data
public class ServiceTaskConfigEntity {
	
	private String activityId;
	
	/**
	 * back-end url
	 * <p>for example: http://localhost:8080/some/url</p>
	 */
	private String url;
	
	
	private ServiceType serviceType;
	
	
	public ServiceType getServiceType() {
		if (url == null || url.trim().isEmpty()) {
			throw new IllegalArgumentException("the config url of activityId['" + activityId + "'] is null ");
		}
		
		if (url.startsWith("http://")) {
			return HTTP;
		} else if (url.startsWith("https://")) {
			return HTTPS;
		} else if (url.startsWith("dubbo://")) {
			return DUBBO;
		}
		return null;
	}
	
	
	public enum ServiceType {
		
		HTTP("http"),
		HTTPS("https"),
		DUBBO("dubbo"),
		
		;
		
		@Getter
		private final String value;
		
		ServiceType(String value) {
			this.value = value;
		}
	}
}
