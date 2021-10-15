package com.ooooo.activiti.common.entity.invoke;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceTaskInvokeResponseEntity {
	
	private Map<String, Object> outBoundVariables;
}
