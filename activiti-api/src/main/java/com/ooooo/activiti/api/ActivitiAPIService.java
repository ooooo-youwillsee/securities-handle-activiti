package com.ooooo.activiti.api;

import com.ooooo.activiti.api.dto.req.BackProcessForm;
import com.ooooo.activiti.api.dto.req.CurrentActivityForm;
import com.ooooo.activiti.api.dto.req.EndProcessForm;
import com.ooooo.activiti.api.dto.req.NextActivityForm;
import com.ooooo.activiti.api.dto.req.PrevActivityForm;
import com.ooooo.activiti.api.dto.req.StartProcessForm;
import com.ooooo.activiti.api.dto.resp.BackActivityResult;
import com.ooooo.activiti.api.dto.resp.CurrentActivityResult;
import com.ooooo.activiti.api.dto.resp.EndProcessResult;
import com.ooooo.activiti.api.dto.resp.NextActivityResult;
import com.ooooo.activiti.api.dto.resp.PrevActivityResult;
import com.ooooo.activiti.api.dto.resp.StartProcessResult;
import com.ooooo.activiti.common.annotation.Internal;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Internal
public interface ActivitiAPIService {
	
	String ACTIVITI_API_SERVICE_BEAN_NAME = "ActivitiAPIService";
	
	/**
	 * 启动流程
	 *
	 * @param form
	 * @return
	 */
	StartProcessResult startProcess(StartProcessForm form);
	
	
	/**
	 * 流程当前节点
	 *
	 * @param form
	 * @return
	 */
	CurrentActivityResult currentActivity(CurrentActivityForm form);
	
	/**
	 * 流程下一步
	 *
	 * @param form
	 * @return
	 */
	NextActivityResult nextActivity(NextActivityForm form);
	
	/**
	 * 流程上一步
	 *
	 * @param form
	 * @return
	 */
	PrevActivityResult prevActivity(PrevActivityForm form);
	
	/**
	 * 打回流程
	 *
	 * @param form
	 * @return
	 */
	BackActivityResult backActivity(BackProcessForm form);
	
	/**
	 * 结束流程
	 *
	 * @param form
	 * @return
	 */
	EndProcessResult endProcess(EndProcessForm form);
}
