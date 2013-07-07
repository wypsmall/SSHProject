package com.yzsystem.hr.po;

import java.io.Serializable;
import java.util.Date;

import com.imti.framework.web.po.BasePO;

public class HrEmpPosHisPO extends BasePO implements Serializable {
	
	/**
	 * ˵����������Ե�ҵ����
	 */
	private static final long serialVersionUID = 4178039802496619324L;
	private String empPosHisId;
	private Date startDate;//��ʼʱ��
	private Date endDate;//����ʱ��
	private String position;//ְλ
	private String remark;//��ע
	private String groupName;//�����ڲ���������
	private String hrEmployeeId;
	private String hrOrgId;
	
	public HrEmpPosHisPO(){
		
	}
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getEmpPosHisId() {
		return empPosHisId;
	}
	public void setEmpPosHisId(String empPosHisId) {
		this.empPosHisId = empPosHisId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getHrEmployeeId() {
		return hrEmployeeId;
	}
	public void setHrEmployeeId(String hrEmployeeId) {
		this.hrEmployeeId = hrEmployeeId;
	}
	public String getHrOrgId() {
		return hrOrgId;
	}
	public void setHrOrgId(String hrOrgId) {
		this.hrOrgId = hrOrgId;
	}
}
