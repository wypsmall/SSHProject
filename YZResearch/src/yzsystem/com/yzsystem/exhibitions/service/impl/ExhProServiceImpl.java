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
	@LogInfoAspect(rootModule="���ٻ�չϵͳ",secondModule="չ����Ŀ",thirdModule="չ����Ŀ",operateName="����")
	public void insertExhPro(ExhibitionProjectVO exhibitionProject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@LogInfoAspect(rootModule="���ٻ�չϵͳ",secondModule="չ����Ŀ",thirdModule="չ����Ŀ",operateName="�޸�")
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
	@LogInfoAspect(rootModule="���ٻ�չϵͳ",secondModule="չ����Ŀ",thirdModule="չ��ģ��",operateName="����")
	public void saveExhProModule(ExhProModuleVO mod) {
		ImtiAssert.isNotBlank(mod.getExhProId(), "չ����Ŀ����Ϊ�գ�");
		ImtiAssert.isNotBlank(mod.getParamId(), "ģ�����Ϊ�գ�");
		ImtiAssert.isNotBlank(mod.getCharges(), "���ò���Ϊ�գ�");
		if(0 == mod.getMeasurementUnits()){
			ImtiAssert.isNotBlank(mod.getParamId(), "��������Ϊ0��");
		}
		String whereHql = " and po.exhProId=" + mod.getExhProId() + " and po.moduleItem.paramId=" + mod.getParamId();
		List<ExhProModuleVO> list = getByConditionWithNoPage(ExhProModulePO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			ImtiAssert.warn("�����Ѿ������ڡ�");
		}else {
			mod.setLockStatus(StatusConstant.LOCK_STATUS_NO);
			insert(mod);
		}
	}
	@Override
	@LogInfoAspect(rootModule="���ٻ�չϵͳ",secondModule="չ����Ŀ",thirdModule="չ��ģ��",operateName="ɾ��")
	public void deleteExhProModule(String exhProModuleId) {
		ImtiAssert.isNotBlank(exhProModuleId, "ģ���ʶ��ʧ��");
		String whereHql = " and po.exhProModuleId=" + exhProModuleId;
		ExhProModuleVO exhProModule = (ExhProModuleVO)getByPk(ExhProModulePO.class, exhProModuleId);
		if(StatusConstant.LOCK_STATUS_YES.intValue() == exhProModule.getLockStatus().intValue()){
			ImtiAssert.warn("�����Ѿ����ύ��,���ܡ�ɾ����");
		}else {
			getBaseDao().delete(ExhProModulePO.class, whereHql);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ExhProModuleVO> getExhProTransList(ExhProSearch search) {
		ImtiAssert.isNotBlank(search.getExhProId(), "չ����Ŀ��ʶ����Ϊ�գ�");
		String whereHql = " and po.exhProId=" + search.getExhProId() + " and po.moduleItem.paramModuleCode='" + SysParamConstant.TRANSLATE_PARAM + "'";
		return getByConditionWithNoPage(ExhProModulePO.class, whereHql);
	}

	@Override
	public List<ExhProModuleVO> getExhProAdvList(ExhProSearch search) {
		ImtiAssert.isNotBlank(search.getExhProId(), "չ����Ŀ��ʶ����Ϊ�գ�");
		return getBaseDao().getExhProAdvList(search);
	}

	@Override
	@LogInfoAspect(rootModule="���ٻ�չϵͳ",secondModule="չ����Ŀ",thirdModule="չ��ģ��",operateName="�ύģ����")
	public void submitExhProModule(String exhProModuleId) {
		ImtiAssert.isNotBlank(exhProModuleId, "ģ���ʶ��ʧ��");
		ExhProModuleVO exhProModule = (ExhProModuleVO)getByPk(ExhProModulePO.class, exhProModuleId);
		if(StatusConstant.LOCK_STATUS_YES.intValue() == exhProModule.getLockStatus().intValue()){
			ImtiAssert.warn("�����Ѿ����ύ��,���ܡ��ظ��ύ��");
		}else {
			getBaseDao().submitExhProModule(exhProModuleId);
		}
	}
}
