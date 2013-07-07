package com.yzsystem.hr.service.impl;

import java.util.List;

import com.imti.framework.common.DateUtil;
import com.imti.framework.common.ImtiAssert;
import com.imti.framework.common.ImtiListUtil;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.framework.web.service.impl.BaseServiceImpl;
import com.imti.sysmgr.aop.LogInfoAspect;
import com.yzsystem.common.constant.StatusConstant;
import com.yzsystem.hr.dao.HrDao;
import com.yzsystem.hr.po.HrEmployeePO;
import com.yzsystem.hr.po.HrOrgPO;
import com.yzsystem.hr.service.HrService;
import com.yzsystem.hr.vo.HrEmpPosHisVO;
import com.yzsystem.hr.vo.HrEmployeeVO;
import com.yzsystem.hr.vo.HrOrgVO;

public class HrServiceImpl extends BaseServiceImpl implements HrService {

	@Override
	public HrDao getBaseDao() {
		return (HrDao)SpringBeanUtil.getBean("hrDao");
	}

	@SuppressWarnings("unchecked")
	@Override
	@LogInfoAspect(rootModule="���ٻ�չϵͳ",secondModule="��������",thirdModule="���Ź���",operateName="����")
	public void insertHrOrg(HrOrgVO hrOrg) {
		ImtiAssert.isNotBlank(hrOrg.getOrgName(), "���Ʋ���Ϊ�գ�");
		ImtiAssert.isNotBlank(hrOrg.getOrgCode(), "���벻��Ϊ�գ�");
		ImtiAssert.isNotBlank(hrOrg.getOrgParentId(), "�ϼ�����Ϊ�գ�");
		//��֤���ƻ��߱����Ƿ��ظ�
		String whereHQL = " and po.orgParentId='" + hrOrg.getOrgParentId() + "'";
		List<HrOrgVO> list = getByConditionWithNoPage(HrOrgPO.class, whereHQL);
		for(HrOrgVO oldOrg : list){
			if(oldOrg.getOrgName().equals(hrOrg.getOrgName())){
				ImtiAssert.warn("�����Ѿ����ڣ�");
			}else if(oldOrg.getOrgCode().equals(hrOrg.getOrgCode())){
				ImtiAssert.warn("�����Ѿ����ڣ�");
			}
		}
		hrOrg.setDelFlag(StatusConstant.DELETE_NO);
		super.insert(hrOrg);
	}

	@SuppressWarnings("unchecked")
	@Override
	@LogInfoAspect(rootModule="���ٻ�չϵͳ",secondModule="��������",thirdModule="���Ź���",operateName="�޸�")
	public void updateHrOrg(HrOrgVO hrOrg) {
		ImtiAssert.isNotBlank(hrOrg.getOrgId(), "��ʶ���϶�ʧ��");
		ImtiAssert.isNotBlank(hrOrg.getOrgName(), "���Ʋ���Ϊ�գ�");
		ImtiAssert.isNotBlank(hrOrg.getOrgCode(), "���벻��Ϊ�գ�");
		ImtiAssert.isNotBlank(hrOrg.getOrgParentId(), "�ϼ�����Ϊ�գ�");
		//��֤���ƻ��߱����Ƿ��ظ�
		String whereHQL = " and po.orgParentId='" + hrOrg.getOrgParentId() + "'";
		List<HrOrgVO> list = getByConditionWithNoPage(HrOrgPO.class, whereHQL);
		for(HrOrgVO oldOrg : list){
			if(oldOrg.getOrgName().equals(hrOrg.getOrgName()) && !oldOrg.getOrgId().equals(hrOrg.getOrgId())){
				ImtiAssert.warn("�����Ѿ����ڣ�");
			}else if(oldOrg.getOrgCode().equals(hrOrg.getOrgCode())  && !oldOrg.getOrgId().equals(hrOrg.getOrgId())){
				ImtiAssert.warn("�����Ѿ����ڣ�");
			}
		}
		super.update(hrOrg);
	}

	@Override
	@LogInfoAspect(rootModule="���ٻ�չϵͳ",secondModule="��������",thirdModule="���Ź���",operateName="ɾ��")
	public void deleteHrOrgById(String orgId) {
		ImtiAssert.isNotBlank(orgId, "��ʶ���϶�ʧ��");
		getBaseDao().deleteHrOrgById(orgId);
	}

	@SuppressWarnings("unchecked")
	@Override
	@LogInfoAspect(rootModule="���ٻ�չϵͳ",secondModule="��������",thirdModule="ְԱ����",operateName="����")
	public void insertHrEmployee(HrEmployeeVO hrEmployee) {
		ImtiAssert.isNotBlank(hrEmployee.getEmployeeNo(), "���Ų���Ϊ�գ�");
		ImtiAssert.isNotBlank(hrEmployee.getEmployeeName(), "��������Ϊ�գ�");
		ImtiAssert.isNotBlank(hrEmployee.getEntryJobDate(), "��ְ���ڲ���Ϊ�գ�");
		ImtiAssert.isNotBlank(hrEmployee.getIdentification(), "���֤���벻��Ϊ�գ�");
		
		//���ǵ�Ա����������ܶ�(���Բ��в�ѯ����ְԱ�ķ�ʽ��У��)
		List<HrEmployeeVO> list = getByConditionWithNoPage(HrEmployeePO.class, " and po.employeeNo='" + hrEmployee.getEmployeeNo() + "' and po.employeeState<" + StatusConstant.POST_LEAVE);
		if(ImtiListUtil.isNotEmpty(list)){
			ImtiAssert.warn("�����Ѿ����ڣ�");
		}
		getBaseDao().insertHrEmployee(hrEmployee);
	}

	@SuppressWarnings("unchecked")
	@Override
	@LogInfoAspect(rootModule="���ٻ�չϵͳ",secondModule="��������",thirdModule="ְԱ����",operateName="�޸�")
	public void updateHrEmployee(HrEmployeeVO hrEmployee) {
		ImtiAssert.isNotBlank(hrEmployee.getEmployeeNo(), "���Ų���Ϊ�գ�");
		ImtiAssert.isNotBlank(hrEmployee.getEmployeeName(), "��������Ϊ�գ�");
		ImtiAssert.isNotBlank(hrEmployee.getEntryJobDate(), "��ְ���ڲ���Ϊ�գ�");
		ImtiAssert.isNotBlank(hrEmployee.getIdentification(), "���֤���벻��Ϊ�գ�");
		//���ǵ�Ա����������ܶ�(���Բ��в�ѯ����ְԱ�ķ�ʽ��У��)
		String whereHQL = " and po.employeeNo='" + hrEmployee.getEmployeeNo() + "' and po.employeeState<" + StatusConstant.POST_LEAVE;
		List<HrEmployeeVO> list = getByHql("0", "2", " SELECT po from com.yzsystem.hr.po.HrEmployeePO po left join fetch po.org org where 1=1 " + whereHQL);
		if(ImtiListUtil.isNotEmpty(list)){
			for(HrEmployeeVO oldHrEmployee : list){
				if(!oldHrEmployee.getEmployeeNo().equals(hrEmployee.getEmployeeNo())){
					ImtiAssert.warn("�����Ѿ����ڣ�");
				}
			}
		}
		getBaseDao().updateHrEmployee(hrEmployee);
	}

	@Override
	@LogInfoAspect(rootModule="���ٻ�չϵͳ",secondModule="��������",thirdModule="ְԱ����",operateName="ɾ��")
	public void deleteHrEmployee(String employeeId) {
		ImtiAssert.isNotBlank(employeeId, "��ʶ���϶�ʧ��");
		//TODO ��֤�Ƿ�����ɾ��
		getBaseDao().delete(HrEmployeePO.class, " and po.employeeId='"+employeeId+"'");
	}

	@Override
	@LogInfoAspect(rootModule="���ٻ�չϵͳ",secondModule="��������",thirdModule="ְԱ����",operateName="��ְ")
	public void transPosition(String employeeId, String destOrgId,String employeePostName, String memo) {
		ImtiAssert.isNotBlank(employeeId, "��ʶ���϶�ʧ��");
		ImtiAssert.isNotBlank(destOrgId, "�������ű�ʶ���϶�ʧ��");
		ImtiAssert.isNotBlank(employeePostName, "ְλ����Ϊ�գ�");
		HrEmployeeVO employee = (HrEmployeeVO)this.getByPk(HrEmployeePO.class, employeeId);
		HrOrgVO hrOrg = (HrOrgVO)this.getByPk(HrOrgPO.class, destOrgId);
		
		String orgId = employee.getOrgId();
		String orgName = employee.getOrgName();
		String groupName = employee.getGroupName();
		String oldPostName = employee.getEmployeePostName();
		String oldMemo = employee.getMemo();
		String startDate = employee.getEntryJobDate();
		
		employee.setEmployeePostName(employeePostName);//ְλ
		employee.setOrgId(destOrgId);//����
		employee.setOrgName(hrOrg.getOrgParentName());//����
		employee.setGroupName(hrOrg.getOrgName());//����
		employee.setEntryJobDate(DateUtil.getCurrentDate());
		HrEmpPosHisVO  empPosHis = new HrEmpPosHisVO(startDate, DateUtil.getCurrentDate(),
				oldPostName,oldMemo, employeeId, 
				orgId,orgName, groupName);
		super.update(employee);
		super.insert(empPosHis);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void groupEmployee(String employeeId, String orgId) {
		ImtiAssert.isNotBlank(employeeId, "ְԱ���϶�ʧ��");
		ImtiAssert.isNotBlank(orgId, "�������϶�ʧ��");
		List<HrEmployeeVO> list = getByHql("0", "2", " SELECT po from com.yzsystem.hr.po.HrEmployeePO po left join fetch po.org org where po.employeeId='" + employeeId + "'");
		ImtiAssert.isNotBlank(list.get(0).getOrgId(), "��ְԱ�Ѿ����й�����");
		getBaseDao().groupEmployee(employeeId, orgId);
	}
}
