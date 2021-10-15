package com.ooooo.activiti.config;

import com.ooooo.activiti.cmd.JumpActivityCmd;
import com.ooooo.activiti.service.ProcessService;
import java.util.List;
import org.activiti.api.process.model.BPMNSequenceFlow;
import org.activiti.api.process.model.events.BPMNSequenceFlowTakenEvent;
import org.activiti.api.process.runtime.events.listener.BPMNElementEventListener;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @see JumpActivityCmd
 * @since 1.0.0
 */
@Configuration
public class JumpActivityConfiguration {
	
	public static final String OUTGOING_FLOWS = "outgoingFlows";
	
	@Autowired
	private ProcessService processService;
	
	@Bean
	public BPMNElementEventListener<BPMNSequenceFlowTakenEvent> jumpActivityTakenEventBPMNElementEventListener() {
		return event -> {
			BPMNSequenceFlow entity = event.getEntity();
			String processDefinitionId = entity.getProcessDefinitionId();
			String sourceActivityElementId = entity.getSourceActivityElementId();
			
			FlowElement sourceFlowElement = processService.getFlowElement(processDefinitionId, sourceActivityElementId);
			
			if (sourceFlowElement instanceof FlowNode) {
				FlowNode sourceFlowNode = (FlowNode) sourceFlowElement;
				List<ExtensionAttribute> extensionAttributes = sourceFlowNode.getAttributes().remove(OUTGOING_FLOWS);
				
				if (extensionAttributes != null) {
					List<SequenceFlow> outgoingFlows = sourceFlowNode.getOutgoingFlows();
					outgoingFlows.clear();
					extensionAttributes.forEach(extensionAttribute -> {
						String sequenceFlowId = extensionAttribute.getValue();
						SequenceFlow sequenceFlowElement = (SequenceFlow) processService.getFlowElement(processDefinitionId, sequenceFlowId);
						outgoingFlows.add(sequenceFlowElement);
					});
				}
			}
			
		};
	}
	
	
}
