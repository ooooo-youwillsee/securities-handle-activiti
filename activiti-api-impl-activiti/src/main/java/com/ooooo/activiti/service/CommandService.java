package com.ooooo.activiti.service;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * for executing  command
 *
 * @author leizhijie
 * @since 1.0.0
 */
@Component
public class CommandService {
	
	@Autowired
	private SpringProcessEngineConfiguration springProcessEngineConfiguration;
	
	public <T> T execute(Command<T> command) {
		return springProcessEngineConfiguration.getCommandExecutor().execute(command);
	}
	
}
