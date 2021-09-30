package com.ooooo.listener;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
public class ProcessInstanceHolder {
	
	private ProcessInstance processInstance;
	
}
