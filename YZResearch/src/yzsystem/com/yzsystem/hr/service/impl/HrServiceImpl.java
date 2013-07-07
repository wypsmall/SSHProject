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
	@LogInfoAspect(rootModule="粤召会展系统",secondModule="行政人事",thirdModule="部门管理",operateName="新增")
	public void insertHrOrg(HrOrgVO hrOrg) {
		ImtiAssert.isNotBlank(hrOrg.getOrgName(), "名称不能为空！");
		ImtiAssert.isNotBlank(hrOrg.getOrgCode(), "编码不能为空！");
		ImtiAssert.isNotBlank(hrOrg.getOrgParentId(), "上级不能为空！");
		//验证名称或者编码是否重复
		String whereHQL = " and po.orgParentId='" + hrOrg.getOrgParentId() + "'";
		List<HrOrgVO> list = getByConditionWithNoPage(HrOrgPO.class, whereHQL);
		for(HrOrgVO oldOrg : list){
			if(oldOrg.getOrgName().equals(hrOrg.getOrgName())){
				ImtiAssert.warn("名称已经存在！");
			}else if(oldOrg.getOrgCode().equals(hrOrg.getOrgCode())){
				ImtiAssert.warn("编码已经存在！");
			}
		}
		hrOrg.setDelFlag(StatusConstant.DELETE_NO);
		super.insert(hrOrg);
	}

	@SuppressWarnings("unchecked")
	@Override
	@LogInfoAspect(rootModule="粤召会展系统",secondModule="行政人事",thirdModule="部门管理",operateName="修改")
	public void updateHrOrg(HrOrgVO hrOrg) {
		ImtiAssert.isNotBlank(hrOrg.getOrgId(), "标识资料丢失！");
		ImtiAssert.isNotBlank(hrOrg.getOrgName(), "名称不能为空！");
		ImtiAssert.isNotBlank(hrOrg.getOrgCode(), "编码不能为空！");
		ImtiAssert.isNotBlank(hrOrg.getOrgParentId(), "上级不能为空！");
		//验证名称或者编码是否重复
		String whereHQL = " and po.orgParentId='" + hrOrg.getOrgParentId() + "'";
		List<HrOrgVO> list = getByConditionWithNoPage(HrOrgPO.class, whereHQL);
		for(HrOrgVO oldOrg : list){
			if(oldOrg.getOrgName().equals(hrOrg.getOrgName()) && !oldOrg.getOrgId().equals(hrOrg.getOrgId())){
				ImtiAssert.warn("名称已经存在！");
			}else if(oldOrg.getOrgCode().equals(hrOrg.getOrgCode())  && !oldOrg.getOrgId().equals(hrOrg.getOrgId())){
				ImtiAssert.warn("编码已经存在！");
			}
		}
		super.update(hrOrg);
	}

	@Override
	@LogInfoAspect(rootModule="粤召会展系统",secondModule="行政人事",thirdModule="部门管理",operateName="删除")
	public void deleteHrOrgById(String orgId) {
		ImtiAssert.isNotBlank(orgId, "标识资料丢失！");
		getBaseDao().deleteHrOrgById(orgId);
	}

	@SuppressWarnings("unchecked")
	@Override
	@LogInfoAspect(rootModule="粤召会展系统",secondModule="行政人事",thirdModule="职员管理",operateName="新增")
	public void insertHrEmployee(HrEmployeeVO hrEmployee) {
		ImtiAssert.isNotBlank(hrEmployee.getEmployeeNo(), "工号不能为空！");
		ImtiAssert.isNotBlank(hrEmployee.getEmployeeName(), "姓名不能为空！");
		ImtiAssert.isNotBlank(hrEmployee.getEntryJobDate(), "入职日期不能为空！");
		ImtiAssert.isNotBlank(hrEmployee.getIdentification(), "身份证号码不能为空！");
		
		//考虑到员工数量不会很多(所以才有查询所有职员的方式来校验)
		List<HrEmployeeVO> list = getByConditionWithNoPage(HrEmployeePO.class, " and po.employeeNo='" + hrEmployee.getEmployeeNo() + "' and po.employeeState<" + StatusConstant.POST_LEAVE);
		if(ImtiListUtil.isNotEmpty(list)){
			ImtiAssert.warn("工号已经存在！");
		}
		getBaseDao().insertHrEmployee(hrEmployee);
	}

	@SuppressWarnings("unchecked")
	@Override
	@LogInfoAspect(rootModule="粤召会展系统",secondModule="行政人事",thirdModule="职员管理",operateName="修改")
	public void updateHrEmployee(HrEmployeeVO hrEmployee) {
		ImtiAssert.isNotBlank(hrEmployee.getEmployeeNo(), "工号不能为空！");
		ImtiAssert.isNotBlank(hrEmployee.getEmployeeName(), "姓名不能为空！");
		ImtiAssert.isNotBlank(hrEmployee.getEntryJobDate(), "入职日期不能为空！");
		ImtiAssert.isNotBlank(hrEmployee.getIdentification(), "身份证号码不能为空！");
		//考虑到员工数量不会很多(所以才有查询所有职员的方式来校验)
		String whereHQL = " and po.employeeNo='" + hrEmployee.getEmployeeNo() + "' and po.employeeState<" + StatusConstant.POST_LEAVE;
		List<HrEmployeeVO> list = getByHql("0", "2", " SELECT po from com.yzsystem.hr.po.HrEmployeePO po left join fetch po.org org where 1=1 " + whereHQL);
		if(ImtiListUtil.isNotEmpty(list)){
			for(HrEmployeeVO oldHrEmployee : list){
				if(!oldHrEmployee.getEmployeeNo().equals(hrEmployee.getEmployeeNo())){
					ImtiAssert.warn("工号已经存在！");
				}
			}
		}
		getBaseDao().updateHrEmployee(hrEmployee);
	}

	@Override
	@LogInfoAspect(rootModule="粤召会展系统",secondModule="行政人事",thirdModule="职员管理",operateName="删除")
	public void deleteHrEmployee(String employeeId) {
		ImtiAssert.isNotBlank(employeeId, "标识资料丢失！");
		//TODO 验证是否允许删除
		getBaseDao().delete(HrEmployeePO.class, " and po.employeeId='"+employeeId+"'");
	}

	@Override
	@LogInfoAspect(rootModule="粤召会展系统",secondModule="行政人事",thirdModule="职员管理",operateName="调职")
	public void transPosition(String employeeId, String destOrgId,String employeePostName, String memo) {
		ImtiAssert.isNotBlank(employeeId, "标识资料丢失！");
		ImtiAssert.isNotBlank(destOrgId, "调往部门标识资料丢失！");
		ImtiAssert.isNotBlank(employeePostName, "职位不能为空！");
		HrEmployeeVO employee = (HrEmployeeVO)this.getByPk(HrEmployeePO.class, employeeId);
		HrOrgVO hrOrg = (HrOrgVO)this.getByPk(HrOrgPO.class, destOrgId);
		
		String orgId = employee.getOrgId();
		String orgName = employee.getOrgName();
		String groupName = employee.getGroupName();
		String oldPostName = employee.getEmployeePostName();
		String oldMemo = employee.getMemo();
		String startDate = employee.getEntryJobDate();
		
		employee.setEmployeePostName(employeePostName);//职位
		employee.setOrgId(destOrgId);//调动
		employee.setOrgName(hrOrg.getOrgParentName());//部门
		employee.setGroupName(hrOrg.getOrgName());//分组
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
		ImtiAssert.isNotBlank(employeeId, "职员资料丢失！");
		ImtiAssert.isNotBlank(orgId, "部门资料丢失！");
		List<HrEmployeeVO> list = getByHql("0", "2", " SELECT po from com.yzsystem.hr.po.HrEmployeePO po left join fetch po.org org where po.employeeId='" + employeeId + "'");
		ImtiAssert.isNotBlank(list.get(0).getOrgId(), "该职员已经进行过分组");
		getBaseDao().groupEmployee(employeeId, orgId);
	}
}
