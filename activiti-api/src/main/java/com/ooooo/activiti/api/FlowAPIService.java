package com.ooooo.activiti.api;

import com.ooooo.activiti.api.dto.req.BackForm;
import com.ooooo.activiti.api.dto.req.CurrentForm;
import com.ooooo.activiti.api.dto.req.EndForm;
import com.ooooo.activiti.api.dto.req.NextForm;
import com.ooooo.activiti.api.dto.req.PrevForm;
import com.ooooo.activiti.api.dto.req.StartForm;
import com.ooooo.activiti.api.dto.resp.BackResult;
import com.ooooo.activiti.api.dto.resp.CurrentResult;
import com.ooooo.activiti.api.dto.resp.EndResult;
import com.ooooo.activiti.api.dto.resp.NextResult;
import com.ooooo.activiti.api.dto.resp.PrevResult;
import com.ooooo.activiti.api.dto.resp.StartResult;

/**
 * @author leizhijie
 * @since 1.0.0
 */
public interface FlowAPIService {
	
	/**
	 * 启动流程
	 *
	 * @param form
	 * @return
	 */
	StartResult start(StartForm form);
	
	
	CurrentResult current(CurrentForm form);
	
	/**
	 * 流程下一步
	 *
	 * @param form
	 * @return
	 */
	NextResult next(NextForm form);
	
	/**
	 * 流程上一步
	 *
	 * @param form
	 * @return
	 */
	PrevResult prev(PrevForm form);
	
	/**
	 * 打回流程
	 *
	 * @param form
	 * @return
	 */
	BackResult back(BackForm form);
	
	/**
	 * 结束流程
	 *
	 * @param form
	 * @return
	 */
	EndResult end(EndForm form);
}
