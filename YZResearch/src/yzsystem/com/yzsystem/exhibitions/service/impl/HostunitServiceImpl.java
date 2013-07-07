package com.yzsystem.exhibitions.service.impl;

import java.util.List;

import com.imti.framework.common.ImtiAssert;
import com.imti.framework.common.ImtiListUtil;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.framework.web.service.impl.BaseServiceImpl;
import com.imti.sysmgr.aop.LogInfoAspect;
import com.yzsystem.exhibitions.common.HostunitUtil;
import com.yzsystem.exhibitions.common.search.HostunitSearch;
import com.yzsystem.exhibitions.dao.HostunitDao;
import com.yzsystem.exhibitions.po.HostunitPO;
import com.yzsystem.exhibitions.service.HostunitService;
import com.yzsystem.exhibitions.vo.HostunitVO;

public class HostunitServiceImpl extends BaseServiceImpl implements
		HostunitService {

	@Override
	public HostunitDao getBaseDao() {
		
		return (HostunitDao)SpringBeanUtil.getBean("hostunitDao");
	}

	@Override
	public int countValidHostunit(HostunitSearch search) {
		String whereHQL = HostunitUtil.getHostunitCondition(search) + " and po.delFlag=1 ";
		return getCountByCondition(HostunitPO.class, whereHQL);
	}

	@Override
	@LogInfoAspect(rootModule="粤召会展系统",secondModule="展会项目",thirdModule="主办单位",operateName="删除")
	public void deleteHostunit(String hostUnitId) {
		ImtiAssert.isNotBlank(hostUnitId, "主办单位关键标识丢失，不能删除");
		getBaseDao().deleteHostunit(hostUnitId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HostunitVO> getValidHostunit(HostunitSearch search) {
		String whereHQL = HostunitUtil.getHostunitCondition(search) + " and po.delFlag=1 ";
		return getByCondition(HostunitPO.class, search.getStart(), search.getLimit(), whereHQL);
	}

	@Override
	@LogInfoAspect(rootModule="粤召会展系统",secondModule="展会项目",thirdModule="主办单位",operateName="新增")
	public void insetHostunit(HostunitVO hostunit) {
		HostunitUtil.validParams(hostunit);
		insert(hostunit);
	}

	@SuppressWarnings("unchecked")
	@Override
	@LogInfoAspect(rootModule="粤召会展系统",secondModule="展会项目",thirdModule="主办单位",operateName="修改")
	public void updateHostunit(HostunitVO hostunit) {
		HostunitUtil.validParams(hostunit);
		ImtiAssert.isNotBlank(hostunit.getHostUnitId(), "主办单位关键标识丢失，不能修改");
		/*
		 * 校验名称是否重复
		 */
		String whereHQL = " and po.hostUnitId<>'"+hostunit.getHostUnitId()+"'  and po.hostUnitName='"+hostunit.getHostUnitName()+"'";
		List list = getByConditionWithNoPage(HostunitPO.class, whereHQL);
		if(ImtiListUtil.isNotEmpty(list)){
			throw new RuntimeException("主办单位【"+hostunit.getHostUnitName()+"】名称已重复");
		}
		update(hostunit);
	}

}
