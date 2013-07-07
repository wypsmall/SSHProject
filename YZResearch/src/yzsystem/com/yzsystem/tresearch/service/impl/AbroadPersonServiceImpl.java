package com.yzsystem.tresearch.service.impl;

import java.util.List;

import com.imti.framework.component.vopomapping.utils.POUtil;
import com.imti.framework.web.dao.BaseDao;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.framework.web.service.impl.BaseServiceImpl;
import com.yzsystem.tresearch.common.AbroadPersonUtil;
import com.yzsystem.tresearch.common.search.AbroadPersonSearch;
import com.yzsystem.tresearch.dao.AbroadPersonDao;
import com.yzsystem.tresearch.po.AbroadPersonPO;
import com.yzsystem.tresearch.service.AbroadPersonService;
import com.yzsystem.tresearch.vo.AbroadPersonVO;
import com.yzsystem.tresearch.vo.ExhComVO;

public class AbroadPersonServiceImpl extends BaseServiceImpl implements
		AbroadPersonService {

	@Override
	public BaseDao getBaseDao() {
		return (AbroadPersonDao)SpringBeanUtil.getBean("abroadPersonDao");
	}

	public int getExhComVOCount(AbroadPersonSearch search) {
		String whereHQL = AbroadPersonUtil.getExhComCondition(search);
		StringBuffer hql = new StringBuffer(" ");
		hql.append(" select count(po) from ExhComPO po ").
			append(" left join po.companyPO cp").
			append(" left join po.exhibitionPO ex").
			append(" where 1=1 ").append(whereHQL);
		return getBaseDao().countByHql(hql.toString());
	}
	
	public List<ExhComVO> getExhComVOList(AbroadPersonSearch search) {
		String whereHQL = AbroadPersonUtil.getExhComCondition(search);
		StringBuffer hql = new StringBuffer(" ");
		hql.append(" select po from ExhComPO po ").
		append(" left join po.companyPO cp").
		append(" left join po.exhibitionPO ex").
		append(" where 1=1 ").append(whereHQL);
		return getByHql(search.getStart(), search.getLimit(), hql.toString());
	}
	
	public int getPersonVOCount(AbroadPersonSearch search) {
		String whereHQL = AbroadPersonUtil.getPersonsCondition(search);
		StringBuffer hql = new StringBuffer(" ");
		hql.append(" select count(po) from AbroadPersonPO po ").
			append(" left join po.companyPO cp").
			append(" left join po.exhibitionPO ex").
			append(" where 1=1 ").append(whereHQL);
		return getBaseDao().countByHql(hql.toString());
	}
	
	public List<AbroadPersonVO> getPersonVOList(AbroadPersonSearch search) {
		String whereHQL = AbroadPersonUtil.getPersonsCondition(search);
		StringBuffer hql = new StringBuffer(" ");
		hql.append(" select po from AbroadPersonPO po ").
		append(" left join po.companyPO cp").
		append(" left join po.exhibitionPO ex").
		append(" where 1=1 ").append(whereHQL);
		return getByHql(search.getStart(), search.getLimit(), hql.toString());
	}

	@Override
	public void insertAbroadPerson(AbroadPersonVO person) {
		AbroadPersonPO po = (AbroadPersonPO)POUtil.copyVoToPo(person);
		getBaseDao().insert(po);
	}

	@Override
	public void updateAbroadPerson(AbroadPersonVO person) {
		AbroadPersonPO po = (AbroadPersonPO)POUtil.copyVoToPo(person);
		getBaseDao().update(po);
	}

	@Override
	public List<AbroadPersonVO> exportPersonData() {
		StringBuffer hql = new StringBuffer(" ");
		hql.append(" select po from AbroadPersonPO po ").
		append(" left join po.companyPO cp").
		append(" left join po.exhibitionPO ex").
		append(" where 1=1 ");
		return getByHql("0", "10000", hql.toString());
	}

}
