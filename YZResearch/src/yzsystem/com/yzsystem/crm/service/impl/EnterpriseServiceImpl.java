package com.yzsystem.crm.service.impl;

import java.util.List;

import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.framework.web.service.impl.BaseServiceImpl;
import com.yzsystem.crm.common.EnterpriseUtil;
import com.yzsystem.crm.common.search.EnterpriseSearch;
import com.yzsystem.crm.dao.EnterpriseDao;
import com.yzsystem.crm.po.EnterprisePO;
import com.yzsystem.crm.service.EnterpriseService;
import com.yzsystem.crm.vo.EnterpriseVO;

public class EnterpriseServiceImpl extends BaseServiceImpl implements
		EnterpriseService {

	@Override
	public EnterpriseDao getBaseDao() {
		
		return (EnterpriseDao)SpringBeanUtil.getBean("enterpriseDao");
	}

	@Override
	public int countEnterprise(EnterpriseSearch search) {
		String whereHQL = EnterpriseUtil.getCondition(search);
		return getCountByCondition(EnterprisePO.class, whereHQL);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EnterpriseVO> getEnterpriseList(EnterpriseSearch search) {
		String whereHQL = EnterpriseUtil.getCondition(search);
		return getByCondition(EnterprisePO.class, search.getStart(), search.getLimit(), whereHQL);
	}

}
