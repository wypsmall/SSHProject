package com.imti.sysmgr.vo;

import java.io.Serializable;

import com.imti.framework.web.vo.BaseVO;

public class SysRoleRightVO extends BaseVO implements Serializable {
	private String id;
	private String roleId;
	private String operatorId;
	private String rightType;//1��ʾ��Դ 0��ʾ����
	private String ztId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRightType() {
		return rightType;
	}
	public void setRightType(String rightType) {
		this.rightType = rightType;
	}
	public String getZtId() {
		return ztId;
	}
	public void setZtId(String ztId) {
		this.ztId = ztId;
	}
	
}
