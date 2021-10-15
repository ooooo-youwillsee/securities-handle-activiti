package com.ooooo.activiti;

import com.alibaba.fastjson.JSON;
import com.ooooo.activiti.common.entity.invoke.ServiceTaskInvokeRequestEntity;
import com.ooooo.activiti.common.entity.invoke.ServiceTaskInvokeResponseEntity;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/serviceTask")
public class ServiceTaskController {
	
	private static final Map<String, Map<String, Object>> variablesMap = new HashMap<>();
	
	static {
		variablesMap.put("service1", Collections.singletonMap("service1", "service1-value"));
		variablesMap.put("service2", Collections.singletonMap("service2", "service2-value"));
		variablesMap.put("service3", Collections.singletonMap("service3", "service3-value"));
	}
	
	@PostMapping("/invoke")
	public ServiceTaskInvokeResponseEntity invoke(@RequestBody ServiceTaskInvokeRequestEntity requestEntity) {
		log.info("{} invoke, activityId: {}, requestEntity: {}", getClass().getSimpleName(), requestEntity.getActivityId(), JSON.toJSONString(requestEntity));
		
		String activityId = requestEntity.getActivityId();
		Map<String, Object> variables = variablesMap.get(activityId);
		
		ServiceTaskInvokeResponseEntity responseEntity = new ServiceTaskInvokeResponseEntity();
		responseEntity.setOutBoundVariables(variables);
		return responseEntity;
	}
}
