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
	@LogInfoAspect(rootModule="���ٻ�չϵͳ",secondModule="չ����Ŀ",thirdModule="���쵥λ",operateName="ɾ��")
	public void deleteHostunit(String hostUnitId) {
		ImtiAssert.isNotBlank(hostUnitId, "���쵥λ�ؼ���ʶ��ʧ������ɾ��");
		getBaseDao().deleteHostunit(hostUnitId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HostunitVO> getValidHostunit(HostunitSearch search) {
		String whereHQL = HostunitUtil.getHostunitCondition(search) + " and po.delFlag=1 ";
		return getByCondition(HostunitPO.class, search.getStart(), search.getLimit(), whereHQL);
	}

	@Override
	@LogInfoAspect(rootModule="���ٻ�չϵͳ",secondModule="չ����Ŀ",thirdModule="���쵥λ",operateName="����")
	public void insetHostunit(HostunitVO hostunit) {
		HostunitUtil.validParams(hostunit);
		insert(hostunit);
	}

	@SuppressWarnings("unchecked")
	@Override
	@LogInfoAspect(rootModule="���ٻ�չϵͳ",secondModule="չ����Ŀ",thirdModule="���쵥λ",operateName="�޸�")
	public void updateHostunit(HostunitVO hostunit) {
		HostunitUtil.validParams(hostunit);
		ImtiAssert.isNotBlank(hostunit.getHostUnitId(), "���쵥λ�ؼ���ʶ��ʧ�������޸�");
		/*
		 * У�������Ƿ��ظ�
		 */
		String whereHQL = " and po.hostUnitId<>'"+hostunit.getHostUnitId()+"'  and po.hostUnitName='"+hostunit.getHostUnitName()+"'";
		List list = getByConditionWithNoPage(HostunitPO.class, whereHQL);
		if(ImtiListUtil.isNotEmpty(list)){
			throw new RuntimeException("���쵥λ��"+hostunit.getHostUnitName()+"���������ظ�");
		}
		update(hostunit);
	}

}
