package com.ooooo.activiti.config;

import com.ooooo.activiti.api.ActivitiEventService;
import com.ooooo.activiti.common.event.ActivityEvent;
import com.ooooo.activiti.service.ProcessService;
import java.util.LinkedList;
import java.util.Queue;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.model.BPMNSequenceFlow;
import org.activiti.api.process.model.events.BPMNSequenceFlowTakenEvent;
import org.activiti.api.process.runtime.events.listener.BPMNElementEventListener;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.ooooo.activiti.api.ActivitiEventService.ACTIVITI_EVENT_SERVICE_BEAN_NAME;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class ActivitiEventConfiguration {
	
	@Autowired
	@Qualifier(ACTIVITI_EVENT_SERVICE_BEAN_NAME)
	private ActivitiEventService eventService;
	
	@Autowired
	private ProcessService processService;
	
	@Bean
	public BPMNElementEventListener<BPMNSequenceFlowTakenEvent> sequenceFlowTakenEventEventListener() {
		return event -> {
			BPMNSequenceFlow entity = event.getEntity();
			String processDefinitionId = entity.getProcessDefinitionId();
			
			// sourcr element
			String sourceActivityElementId = entity.getSourceActivityElementId();
			FlowElement sourceFlowElement = processService.getFlowElement(processDefinitionId, sourceActivityElementId);
			
			// target element
			String targetActivityElementId = entity.getTargetActivityElementId();
			FlowElement targetFlowElement = processService.getFlowElement(processDefinitionId, targetActivityElementId);
			
			// calc
			int distance = calcDistance(processDefinitionId, sourceFlowElement, targetFlowElement);
			
			ActivityEvent activityEvent = new ActivityEvent();
			activityEvent.setProcessDefinitionId(processDefinitionId);
			activityEvent.setProcessDefinitionKey(processService.getProcessDefinitionKey(processDefinitionId));
			activityEvent.setProcessInstanceId(entity.getProcessInstanceId());
			
			onActivityEvent(entity, sourceFlowElement, targetFlowElement, distance, activityEvent);
		};
	}
	
	private void onActivityEvent(BPMNSequenceFlow entity, FlowElement sourceFlowElement, FlowElement targetFlowElement, int distance, ActivityEvent activityEvent) {
		if (distance == 1) {
			if (targetFlowElement instanceof EndEvent) {
				// next activity is endEvent
				EndEvent endEvent = (EndEvent) targetFlowElement;
				activityEvent.setActivityId(endEvent.getId());
				activityEvent.setActivityName(endEvent.getName());
				activityEvent.setActivityType(endEvent.getClass().getSimpleName());
				eventService.onProcessEndedEvent(activityEvent);
			} else if (sourceFlowElement instanceof StartEvent) {
				// prev activity is startEvent
				StartEvent startEvent = (StartEvent) sourceFlowElement;
				activityEvent.setActivityId(startEvent.getId());
				activityEvent.setActivityName(startEvent.getName());
				activityEvent.setActivityType(startEvent.getClass().getSimpleName());
				eventService.onProcessStartedEvent(activityEvent);
			} else if (targetFlowElement instanceof Activity) {
				// next activity is Activity
				Activity activity = (Activity) targetFlowElement;
				activityEvent.setActivityId(activity.getId());
				activityEvent.setActivityName(activity.getName());
				activityEvent.setActivityType(activity.getClass().getSimpleName());
				eventService.onNextActivityEvent(activityEvent);
			} else {
				log.warn("ignore targetFlowElement[{}]", targetFlowElement.getClass().getSimpleName());
			}
		}
		
		if (distance > 1) {
			log.warn("ignore targetFlowElement[{}], because normal operation will not be performed here", targetFlowElement.getClass().getSimpleName());
		}
		
		if (distance == -1) {
			if (targetFlowElement instanceof Activity) {
				// prev activity is Activity
				Activity activity = (Activity) targetFlowElement;
				activityEvent.setActivityId(activity.getId());
				activityEvent.setActivityName(activity.getName());
				activityEvent.setActivityType(activity.getClass().getSimpleName());
				eventService.onPrevActivityEvent(activityEvent);
			} else {
				log.warn("ignore targetFlowElement[{}]", targetFlowElement.getClass().getSimpleName());
			}
		}
		
		if (distance < -1) {
			if (targetFlowElement instanceof Activity) {
				// prev activity is Activity
				Activity activity = (Activity) targetFlowElement;
				activityEvent.setActivityId(activity.getId());
				activityEvent.setActivityName(activity.getName());
				activityEvent.setActivityType(activity.getClass().getSimpleName());
				eventService.onBackedActivityEvent(activityEvent);
			} else {
				log.warn("ignore targetFlowElement[{}]", targetFlowElement.getClass().getSimpleName());
			}
		}
		
		if (distance == 0) {
			log.error("process[processInstanceId={}] is wrong, because normal operation will not be performed here", entity.getProcessInstanceId());
		}
	}
	
	/**
	 * calculate distance from sourceFlowElement to targetFlowElement
	 *
	 * @param processDefinitionId
	 * @param sourceFlowElement
	 * @param targetFlowElement
	 * @return
	 */
	private int calcDistance(String processDefinitionId, FlowElement sourceFlowElement, FlowElement targetFlowElement) {
		if (!(sourceFlowElement instanceof FlowNode) || !(targetFlowElement instanceof FlowNode)) {
			return 0;
		}
		FlowNode sourceFlowNode = (FlowNode) sourceFlowElement;
		FlowNode targetFlowNode = (FlowNode) targetFlowElement;
		
		int x = calcDistanceToStartEvent(processDefinitionId, sourceFlowNode);
		int y = calcDistanceToStartEvent(processDefinitionId, targetFlowNode);
		
		return y - x;
	}
	
	private int calcDistanceToStartEvent(String processDefinitionId, FlowNode flowNode) {
		StartEvent startEvent = processService.getStartEvent(processDefinitionId);
		if (startEvent.getId().equals(flowNode.getId())) {
			return 0;
		}
		
		int depth = 1;
		Queue<FlowNode> queue = new LinkedList<>();
		queue.offer(startEvent);
		
		while (!queue.isEmpty()) {
			for (int i = 0, sz = queue.size(); i < sz; i++) {
				FlowNode node = queue.poll();
				for (SequenceFlow flow : node.getOutgoingFlows()) {
					FlowElement element = flow.getTargetFlowElement();
					if (element.getId().equals(flowNode.getId())) {
						return depth;
					}
					
					if (element instanceof FlowNode) {
						queue.add((FlowNode) element);
					}
				}
			}
			depth++;
		}
		
		return 0;
	}
	
	
}
