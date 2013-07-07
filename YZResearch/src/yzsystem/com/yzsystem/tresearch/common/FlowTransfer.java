package com.yzsystem.tresearch.common;

import java.util.ArrayList;
import java.util.List;

import com.neo.common.transfer.BaseTransfer;
import com.yzsystem.tresearch.po.FlowAppDO;
import com.yzsystem.tresearch.po.UserDO;
import com.yzsystem.tresearch.vo.FlowAppVO;

public class FlowTransfer extends BaseTransfer {
	@Override
	public Object getVO(Class voClassName, Object... domainObjects) {
		if(voClassName.equals(FlowAppVO.class)) {
			FlowAppDO flowAppDO = (FlowAppDO)domainObjects[0];
			FlowAppVO flowAppVO = (FlowAppVO)super.getVO(voClassName, flowAppDO);
			flowAppVO.setFlwProposerId(flowAppDO.getFlwProposer().getFid());
			flowAppVO.setFlwProposerName(flowAppDO.getFlwProposer().getFuserName());
			if(null != flowAppDO.getFlwAuditor()) {
				flowAppVO.setFlwAuditorId(flowAppDO.getFlwAuditor().getFid());
				flowAppVO.setFlwAuditorName(flowAppDO.getFlwAuditor().getFuserName());
			}
			return flowAppVO;
		}
		return super.getVO(voClassName, domainObjects);
	}
	@Override
	public List getVOList(Class voClassName, List doList) {
		if(nullList(doList)){
			return null;
		}
		List templist = new ArrayList();
		for (Object obj : doList) {
			Object destObj = getVO(voClassName, obj);
			templist.add(destObj);
		}
		return templist;
	}
	@Override
	public Object getDO(Class domainObjectClassName, Object... viewObject) {
		if(domainObjectClassName.equals(FlowAppDO.class)) {
			FlowAppVO flowAppVO = (FlowAppVO)viewObject[0];
			FlowAppDO flowAppDO = (FlowAppDO)super.getDO(domainObjectClassName, viewObject);
			UserDO flwProposer = new UserDO();
			flwProposer.setFid(flowAppVO.getFlwProposerId());
			flowAppDO.setFlwProposer(flwProposer);
			
			UserDO flwAuditor = new UserDO();
			flwAuditor.setFid(flowAppVO.getFlwAuditorId());
			flowAppDO.setFlwAuditor(flwAuditor);
			return flowAppDO;
		}
		return null;
	}
	
}
