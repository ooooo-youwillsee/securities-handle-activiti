package com.ooooo.activiti.cmd;

import com.ooooo.activiti.dto.ProcessEnitty;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.runtime.ProcessInstance;

import static com.ooooo.activiti.cmd.CommonContextHelper.getRuntimeService;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@AllArgsConstructor
public class StartProcessCmd implements Command<ProcessEnitty> {
	
	private String processDefinitionKey;
	
	private Map<String, Object> variables;
	
	@Override
	public ProcessEnitty execute(CommandContext commandContext) {
		RuntimeService runtimeService = getRuntimeService(commandContext);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
		
		ProcessEnitty processEnitty = new ProcessEnitty();
		processEnitty.setProcessDefinitionKey(processDefinitionKey);
		processEnitty.setProcessInstanceId(processInstance.getId());
		return processEnitty;
	}
}
