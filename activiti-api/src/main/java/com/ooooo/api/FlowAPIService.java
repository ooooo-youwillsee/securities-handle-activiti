package com.ooooo.api;

import com.ooooo.api.dto.req.*;
import com.ooooo.api.dto.resp.*;

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
