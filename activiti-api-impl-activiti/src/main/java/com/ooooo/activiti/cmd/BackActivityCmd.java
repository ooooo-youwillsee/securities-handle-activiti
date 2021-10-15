package com.ooooo.activiti.cmd;

import lombok.AllArgsConstructor;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandExecutor;

import static com.ooooo.activiti.cmd.CommonContextHelper.getCommandExecutor;
import static com.ooooo.activiti.cmd.CommonContextHelper.getFlowElement;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@AllArgsConstructor
public class BackActivityCmd implements Command<Void> {
	
	private String processInstanceId;
	
	private String activityId;
	
	@Override
	public Void execute(CommandContext commandContext) {
		CommandExecutor commandExecutor = getCommandExecutor(commandContext);
		FlowElement flowElement = getFlowElement(commandContext, processInstanceId, activityId);
		
		if (flowElement instanceof Activity) {
			commandExecutor.execute(new JumpActivityCmd(processInstanceId, (Activity) flowElement));
			return null;
		}
		
		throw new IllegalArgumentException("processInstanceId: " + processInstanceId + ", activityId: " + activityId + " is not a instance of Activity");
	}
}
