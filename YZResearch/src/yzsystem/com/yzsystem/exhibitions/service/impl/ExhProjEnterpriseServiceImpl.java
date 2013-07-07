package com.yzsystem.exhibitions.service.impl;

import java.util.List;

import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.framework.web.service.impl.BaseServiceImpl;
import com.yzsystem.exhibitions.common.ExhProUtil;
import com.yzsystem.exhibitions.common.search.ExhProSearch;
import com.yzsystem.exhibitions.dao.ExhProjEnterpriseDao;
import com.yzsystem.exhibitions.service.ExhProjEnterpriseService;
import com.yzsystem.exhibitions.vo.ExhibitionEnterpriseVO;

public class ExhProjEnterpriseServiceImpl extends BaseServiceImpl implements
		ExhProjEnterpriseService {

	@Override
	public ExhProjEnterpriseDao getBaseDao() {
		return (ExhProjEnterpriseDao)SpringBeanUtil.getBean("exhProjEnterpriseDao");
	}

	@Override
	public int countExhEnterprise(ExhProSearch search) {
		String whereHQL = ExhProUtil.getExhProEnterCondition(search);
		StringBuffer hql = new StringBuffer(" ");
		hql.append(" select count(po) from ExhibitionEnterprisePO po ").
			append(" left join po.enterprise ee").
			append(" left join po.exhPro ep").
			append(" where 1=1 ").append(whereHQL);
		return getBaseDao().countByHql(hql.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExhibitionEnterpriseVO> getExhEnterpriseList(ExhProSearch search) {
		String whereHQL = ExhProUtil.getExhProEnterCondition(search);
		StringBuffer hql = new StringBuffer(" ");
		hql.append(" select po from ExhibitionEnterprisePO po ").
			append(" left join fetch po.enterprise ee ").
			append(" left join fetch po.exhPro ep ").
			append(" where 1=1 ").append(whereHQL);
		return getByHql(search.getStart(), search.getLimit(), hql.toString());
	}

}
