package com.yzsystem.sysparam.service.impl;

import java.util.List;

import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.framework.web.service.impl.BaseServiceImpl;
import com.yzsystem.common.constant.SysParamConstant;
import com.yzsystem.sysparam.dao.SysParamDao;
import com.yzsystem.sysparam.po.SysParamPO;
import com.yzsystem.sysparam.service.SysParamService;
import com.yzsystem.sysparam.vo.SysParamVO;

public class SysParamServiceImpl extends BaseServiceImpl implements SysParamService {

	@Override
	public SysParamDao getBaseDao() {
		
		return (SysParamDao)SpringBeanUtil.getBean("sysParamDao");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysParamVO> getTransParam() {
		return getByConditionWithNoPage(SysParamPO.class, " and po.paramModuleCode='"+SysParamConstant.TRANSLATE_PARAM+"'  order by po.displayIndex");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysParamVO> getAdvertisementJournal() {
		String condition = " and po.paramModuleCode='"+SysParamConstant.ADVERTISEMENT_PARAM+"'  " +
				" and po.paramCode='"+SysParamConstant.ADVERTISEMENT_JOURNAL_PARAM+"' order by po.displayIndex";
		return getByConditionWithNoPage(SysParamPO.class, condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysParamVO> getAdvertisementVisit() {
		String condition = " and po.paramModuleCode='"+SysParamConstant.ADVERTISEMENT_PARAM+"'  " +
				" and po.paramCode='"+SysParamConstant.ADVERTISEMENT_VISIT_PARAM+"' order by po.displayIndex";
		return getByConditionWithNoPage(SysParamPO.class, condition);
	}
}
