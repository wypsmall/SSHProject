package com.yzsystem.tresearch.dao;

import java.util.List;

import com.neo.common.dao.IAirBaseDao;
import com.neo.common.model.IDomainObject;
import com.yzsystem.tresearch.po.FlowAppDO;
import com.yzsystem.tresearch.vo.FlowAppVO;

public interface IFlowAppDao extends IAirBaseDao {
	public List<IDomainObject> getFlowPage(FlowAppVO flowAppVO, Integer start,Integer pagesize);
	public Integer getFlowPageCount(FlowAppVO flowAppVO);
	public void submitFlow(FlowAppDO flowAppDO);
	public void replyFlow(FlowAppVO flowAppVO);
}
