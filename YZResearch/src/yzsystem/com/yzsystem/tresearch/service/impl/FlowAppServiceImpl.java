package com.yzsystem.tresearch.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.neo.common.model.IDomainObject;
import com.neo.common.service.impl.BaseServiceImpl;
import com.neo.common.vo.IValueObject;
import com.yzsystem.tresearch.dao.IFlowAppDao;
import com.yzsystem.tresearch.po.FlowAppDO;
import com.yzsystem.tresearch.service.IFlowAppService;
import com.yzsystem.tresearch.vo.FlowAppVO;

public class FlowAppServiceImpl extends BaseServiceImpl implements IFlowAppService {
	private IFlowAppDao flowAppDao;

	public void setFlowAppDao(IFlowAppDao flowAppDao) {
		this.flowAppDao = flowAppDao;
	}

	@Override
	public List<IValueObject> getFlows(FlowAppVO flowAppVO, Integer start, Integer pagesize) {
		List<IDomainObject> res = flowAppDao.getFlowPage(flowAppVO, start, pagesize);
		List<IValueObject> vos = baseTransfer.getVOList(FlowAppVO.class, res);
		if(vos == null)
			vos = new ArrayList<IValueObject>();
		return vos;
	}

	@Override
	public void replyFlow(FlowAppVO flowAppVO) {
		flowAppDao.replyFlow(flowAppVO);
	}

	@Override
	public void submitFlow(FlowAppVO flowAppVO) {
		FlowAppDO flowAppDO = (FlowAppDO) baseTransfer.getDO(FlowAppDO.class, flowAppVO);
		flowAppDO.setFlwId(null);
		flowAppDao.submitFlow(flowAppDO);
	}

	@Override
	public Integer getFlowsCount(FlowAppVO flowAppVO) {
		return flowAppDao.getFlowPageCount(flowAppVO);
	}

}
