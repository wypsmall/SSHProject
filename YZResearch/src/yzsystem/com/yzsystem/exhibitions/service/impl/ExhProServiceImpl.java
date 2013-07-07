package com.yzsystem.exhibitions.service.impl;

import java.util.List;

import com.imti.framework.common.ImtiAssert;
import com.imti.framework.common.ImtiListUtil;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.framework.web.service.impl.BaseServiceImpl;
import com.imti.sysmgr.aop.LogInfoAspect;
import com.yzsystem.common.constant.StatusConstant;
import com.yzsystem.common.constant.SysParamConstant;
import com.yzsystem.exhibitions.common.ExhProUtil;
import com.yzsystem.exhibitions.common.search.ExhProSearch;
import com.yzsystem.exhibitions.dao.ExhProDao;
import com.yzsystem.exhibitions.po.ExhProModulePO;
import com.yzsystem.exhibitions.po.ExhibitionProjectPO;
import com.yzsystem.exhibitions.service.ExhProService;
import com.yzsystem.exhibitions.vo.ExhProModuleVO;
import com.yzsystem.exhibitions.vo.ExhibitionProjectVO;

public class ExhProServiceImpl extends BaseServiceImpl implements ExhProService {

	@Override
	public ExhProDao getBaseDao() {
		
		return (ExhProDao)SpringBeanUtil.getBean("exhProDao");
	}

	@Override
	@LogInfoAspect(rootModule="粤召会展系统",secondModule="展会项目",thirdModule="展会项目",operateName="新增")
	public void insertExhPro(ExhibitionProjectVO exhibitionProject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@LogInfoAspect(rootModule="粤召会展系统",secondModule="展会项目",thirdModule="展会项目",operateName="修改")
	public void updateExhPro(ExhibitionProjectVO exhibitionProject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int countExhPro(ExhProSearch search) {
		String whereHQL = ExhProUtil.getExhProCondition(search);
		return getCountByCondition(ExhibitionProjectPO.class, whereHQL);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExhibitionProjectVO> getExhProList(ExhProSearch search) {
		String whereHQL = ExhProUtil.getExhProCondition(search);
		return getByCondition(ExhibitionProjectPO.class, search.getStart(), search.getLimit(), whereHQL);
	}

	@SuppressWarnings("unchecked")
	@Override
	@LogInfoAspect(rootModule="粤召会展系统",secondModule="展会项目",thirdModule="展会模板",operateName="保存")
	public void saveExhProModule(ExhProModuleVO mod) {
		ImtiAssert.isNotBlank(mod.getExhProId(), "展会项目不能为空！");
		ImtiAssert.isNotBlank(mod.getParamId(), "模板项不能为空！");
		ImtiAssert.isNotBlank(mod.getCharges(), "费用不能为空！");
		if(0 == mod.getMeasurementUnits()){
			ImtiAssert.isNotBlank(mod.getParamId(), "数量不能为0！");
		}
		String whereHql = " and po.exhProId=" + mod.getExhProId() + " and po.moduleItem.paramId=" + mod.getParamId();
		List<ExhProModuleVO> list = getByConditionWithNoPage(ExhProModulePO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			ImtiAssert.warn("该项已经“存在”");
		}else {
			mod.setLockStatus(StatusConstant.LOCK_STATUS_NO);
			insert(mod);
		}
	}
	@Override
	@LogInfoAspect(rootModule="粤召会展系统",secondModule="展会项目",thirdModule="展会模板",operateName="删除")
	public void deleteExhProModule(String exhProModuleId) {
		ImtiAssert.isNotBlank(exhProModuleId, "模板标识丢失！");
		String whereHql = " and po.exhProModuleId=" + exhProModuleId;
		ExhProModuleVO exhProModule = (ExhProModuleVO)getByPk(ExhProModulePO.class, exhProModuleId);
		if(StatusConstant.LOCK_STATUS_YES.intValue() == exhProModule.getLockStatus().intValue()){
			ImtiAssert.warn("该项已经“提交”,不能“删除”");
		}else {
			getBaseDao().delete(ExhProModulePO.class, whereHql);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ExhProModuleVO> getExhProTransList(ExhProSearch search) {
		ImtiAssert.isNotBlank(search.getExhProId(), "展会项目标识不能为空！");
		String whereHql = " and po.exhProId=" + search.getExhProId() + " and po.moduleItem.paramModuleCode='" + SysParamConstant.TRANSLATE_PARAM + "'";
		return getByConditionWithNoPage(ExhProModulePO.class, whereHql);
	}

	@Override
	public List<ExhProModuleVO> getExhProAdvList(ExhProSearch search) {
		ImtiAssert.isNotBlank(search.getExhProId(), "展会项目标识不能为空！");
		return getBaseDao().getExhProAdvList(search);
	}

	@Override
	@LogInfoAspect(rootModule="粤召会展系统",secondModule="展会项目",thirdModule="展会模板",operateName="提交模板项")
	public void submitExhProModule(String exhProModuleId) {
		ImtiAssert.isNotBlank(exhProModuleId, "模板标识丢失！");
		ExhProModuleVO exhProModule = (ExhProModuleVO)getByPk(ExhProModulePO.class, exhProModuleId);
		if(StatusConstant.LOCK_STATUS_YES.intValue() == exhProModule.getLockStatus().intValue()){
			ImtiAssert.warn("该项已经“提交”,不能“重复提交”");
		}else {
			getBaseDao().submitExhProModule(exhProModuleId);
		}
	}
}
