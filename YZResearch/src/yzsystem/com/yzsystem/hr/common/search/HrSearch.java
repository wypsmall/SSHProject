package com.yzsystem.hr.common.search;

import com.imti.sysmgr.bean.Page;

public class HrSearch extends Page{

	private String employeeNo;//职员工号
	private String employeeName;//职员姓名
	private Short employeeState;//在职状态（实习、试用期、正式职员、已离职）1实习2试用期3正式职员4已离职
	private String orgId;//部门主键
	private String contractDateBegin;//合同到期日期查询开始日期
	private String contractDateEnd;//合同到期日期查询结束日期
	
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Short getEmployeeState() {
		return employeeState;
	}
	public void setEmployeeState(Short employeeState) {
		this.employeeState = employeeState;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getContractDateBegin() {
		return contractDateBegin;
	}
	public void setContractDateBegin(String contractDateBegin) {
		this.contractDateBegin = contractDateBegin;
	}
	public String getContractDateEnd() {
		return contractDateEnd;
	}
	public void setContractDateEnd(String contractDateEnd) {
		this.contractDateEnd = contractDateEnd;
	}
}
