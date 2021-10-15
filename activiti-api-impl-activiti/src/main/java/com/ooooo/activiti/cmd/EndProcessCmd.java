package com.ooooo.activiti.cmd;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandExecutor;

import static com.ooooo.activiti.cmd.CommonContextHelper.getCommandExecutor;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@AllArgsConstructor
public class EndProcessCmd implements Command<Void> {
	
	private final String processInstanceId;
	
	@SneakyThrows
	@Override
	public Void execute(CommandContext commandContext) {
		EndEvent endEventElement = getEndEventElement(commandContext, processInstanceId);
		CommandExecutor commandExecutor = getCommandExecutor(commandContext);
		commandExecutor.execute(new JumpActivityCmd(processInstanceId, endEventElement));
		return null;
	}
	
	/**
	 * get element for &lt;endEvent id="end"&gt;&lt;/endEvent&gt;
	 *
	 * @param processInstanceId
	 * @return
	 */
	private EndEvent getEndEventElement(CommandContext commandContext, String processInstanceId) {
		Process process = CommonContextHelper.getProcess(commandContext, processInstanceId);
		for (FlowElement flowElement : process.getFlowElements()) {
			if (flowElement instanceof EndEvent) {
				return (EndEvent) flowElement;
			}
		}
		throw new IllegalArgumentException("not found element['endEvent'], processDefinitionId: " + processInstanceId);
	}
	
}
