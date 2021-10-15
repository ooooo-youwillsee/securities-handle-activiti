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
	StartProcessResult start(StartProcessForm form);
	
	
	/**
	 * 流程当前节点
	 *
	 * @param form
	 * @return
	 */
	CurrentActivityResult current(CurrentActivityForm form);
	
	/**
	 * 流程下一步
	 *
	 * @param form
	 * @return
	 */
	NextActivityResult next(NextActivityForm form);
	
	/**
	 * 流程上一步
	 *
	 * @param form
	 * @return
	 */
	PrevActivityResult prev(PrevActivityForm form);
	
	/**
	 * 打回流程
	 *
	 * @param form
	 * @return
	 */
	BackActivityResult back(BackProcessForm form);
	
	/**
	 * 结束流程
	 *
	 * @param form
	 * @return
	 */
	EndProcessResult end(EndProcessForm form);
}
