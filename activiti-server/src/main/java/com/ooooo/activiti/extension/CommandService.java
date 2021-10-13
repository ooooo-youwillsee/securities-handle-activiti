package com.ooooo.activiti.extension;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.stereotype.Component;

/**
 * for executing  command
 *
 * @author leizhijie
 * @since 1.0.0
 */
@Component
public class CommandService {
	
	private final CommandExecutor commandExecutor;
	
	private final SpringProcessEngineConfiguration springProcessEngineConfiguration;
	
	public CommandService(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
		this.springProcessEngineConfiguration = springProcessEngineConfiguration;
		this.commandExecutor = springProcessEngineConfiguration.getCommandExecutor();
	}
	
	public <T> T execute(Command<T> command) {
		return commandExecutor.execute(command);
	}
	
}
