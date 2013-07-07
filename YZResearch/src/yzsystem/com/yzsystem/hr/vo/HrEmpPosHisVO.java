package com.yzsystem.hr.vo;

import java.util.Date;

import com.imti.framework.web.vo.BaseVO;

public class HrEmpPosHisVO extends BaseVO {

	/**
	 * ˵����������Ե�ҵ����
	 */
	private static final long serialVersionUID = -3373759767674001943L;

	private String empPosHisId;
	private String startDate;//��ʼʱ��
	private String endDate;//����ʱ��
	private String position;//ְλ
	private String remark;//��ע
	private String hrEmployeeId;//ְԱ����
	private String hrOrgId;//��������
	private String hrOrgName;//��������
	private String groupName;//�����ڲ���������
	
	public HrEmpPosHisVO(){
		
	}
	public HrEmpPosHisVO(String startDate,String endDate,
			String position,String remark,String hrEmployeeId,
			String hrOrgId, String hrOrgName, String groupName){
		this.startDate = startDate;
		this.endDate = endDate;
		this.position = position;
		this.remark = remark;
		this.hrEmployeeId = hrEmployeeId;
		this.hrOrgId = hrOrgId;
		this.hrOrgName = hrOrgName;
		this.groupName = groupName;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
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
	public String getHrOrgName() {
		return hrOrgName;
	}
	public void setHrOrgName(String hrOrgName) {
		this.hrOrgName = hrOrgName;
	}
}
