package com.yzsystem.hr.common.search;

import com.imti.sysmgr.bean.Page;

public class HrSearch extends Page{

	private String employeeNo;//ְԱ����
	private String employeeName;//ְԱ����
	private Short employeeState;//��ְ״̬��ʵϰ�������ڡ���ʽְԱ������ְ��1ʵϰ2������3��ʽְԱ4����ְ
	private String orgId;//��������
	private String contractDateBegin;//��ͬ�������ڲ�ѯ��ʼ����
	private String contractDateEnd;//��ͬ�������ڲ�ѯ��������
	
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
