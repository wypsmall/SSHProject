package com.yzsystem.tresearch.service;

import java.util.List;

import com.neo.common.service.IBaseService;
import com.neo.common.vo.IValueObject;
import com.yzsystem.tresearch.vo.FlowAppVO;

public interface IFlowAppService extends IBaseService {
	public List<IValueObject> getFlows(FlowAppVO flowAppVO, Integer start, Integer pagesize);
	public Integer getFlowsCount(FlowAppVO flowAppVO);
	public void submitFlow(FlowAppVO flowAppVO);
	public void replyFlow(FlowAppVO flowAppVO);
}
