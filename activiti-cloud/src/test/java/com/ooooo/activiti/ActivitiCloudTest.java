package com.ooooo.activiti;

import com.alibaba.fastjson.JSON;
import com.ooooo.activiti.api.FlowAPIService;
import com.ooooo.activiti.api.FlowAdminService;
import com.ooooo.activiti.api.dto.req.CurrentActivityForm;
import com.ooooo.activiti.api.dto.req.NextActivityForm;
import com.ooooo.activiti.api.dto.req.ProcessDefinitionForm;
import com.ooooo.activiti.api.dto.req.StartProcessForm;
import com.ooooo.activiti.api.dto.resp.CurrentActivityResult;
import com.ooooo.activiti.api.dto.resp.NextActivityResult;
import com.ooooo.activiti.api.dto.resp.ProcessDefinitionResult;
import com.ooooo.activiti.api.dto.resp.StartProcessResult;
import com.ooooo.activiti.common.entity.config.ServiceTaskConfigEntity;
import com.ooooo.activiti.common.enums.ActivityType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.ooooo.activiti.common.constants.ActivitiConfigConstants.SERVICE_TASK_CONFIG_ENTITY_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
@SpringBootTest
public class ActivitiCloudTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiCloudTest.class);
	
	@Autowired
	private FlowAPIService apiService;
	
	@Value("${server.port}")
	private String port;
	
	@MockBean
	private FlowAdminService adminService;
	
	private final Map<String, Object> variables = new HashMap<>();
	
	private final String processDefinitionKey = "activiti-cloud-process";
	
	private String processInstanceId;
	
	/**
	 * @see ServiceTaskController
	 */
	@Test
	public void test() {
		// query processDefintion
		ProcessDefinitionResult processDefinitionResult = adminService.processDefintion(new ProcessDefinitionForm(processDefinitionKey));
		LOGGER.info("processDefinitionResult: {}", JSON.toJSONString(processDefinitionResult.getActivityDefinitionList()));
		
		// then set serviceTask invoke url configuration
		List<ServiceTaskConfigEntity> serviceTaskConfigEntityList = new ArrayList<>();
		serviceTaskConfigEntityList.add(new ServiceTaskConfigEntity("service1", "http://localhost:" + port + "/serviceTask/invoke"));
		serviceTaskConfigEntityList.add(new ServiceTaskConfigEntity("service2", "http://localhost:" + port + "/serviceTask/invoke"));
		serviceTaskConfigEntityList.add(new ServiceTaskConfigEntity("service3", "http://localhost:" + port + "/serviceTask/invoke"));
		
		variables.put(SERVICE_TASK_CONFIG_ENTITY_KEY, JSON.toJSONString(serviceTaskConfigEntityList));
		
		// start process
		StartProcessResult startProcessResult = apiService.startProcess(new StartProcessForm(processDefinitionKey, variables));
		processInstanceId = startProcessResult.getProcessInstanceId();
		
		// current activity
		CurrentActivityResult currentActivityResult = apiService.currentActivity(new CurrentActivityForm(processDefinitionKey));
		assertEquals(ActivityType.RECEIVE_TASK, currentActivityResult.getActivityType());
		assertEquals("waitState1", currentActivityResult.getActivityId());
		
		// next activity
		NextActivityResult nextActivityResult = apiService.nextActivity(new NextActivityForm(processInstanceId));
		assertEquals(ActivityType.RECEIVE_TASK, nextActivityResult.getActivityType());
		assertEquals("waitState2", nextActivityResult.getActivityId());
		
		// next activity
		nextActivityResult = apiService.nextActivity(new NextActivityForm(processInstanceId));
		assertEquals(ActivityType.RECEIVE_TASK, nextActivityResult.getActivityType());
		assertEquals("waitState3", nextActivityResult.getActivityId());
		
		// next activity
		nextActivityResult = apiService.nextActivity(new NextActivityForm(processInstanceId));
		//assertEquals(ActivityType.RECEIVE_TASK, nextActivityResult.getActivityType());
		//assertEquals("waitState2", nextActivityResult.getActivityId());
	}
}
