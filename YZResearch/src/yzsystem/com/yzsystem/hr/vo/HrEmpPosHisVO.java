package com.yzsystem.hr.vo;

import java.util.Date;

import com.imti.framework.web.vo.BaseVO;

public class HrEmpPosHisVO extends BaseVO {

	/**
	 * 说明清楚此属性的业务含义
	 */
	private static final long serialVersionUID = -3373759767674001943L;

	private String empPosHisId;
	private String startDate;//开始时间
	private String endDate;//结束时间
	private String position;//职位
	private String remark;//备注
	private String hrEmployeeId;//职员主键
	private String hrOrgId;//部门主键
	private String hrOrgName;//部门名称
	private String groupName;//部门内部分组名称
	
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
