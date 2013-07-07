package com.imti.sysmgr.po;

import java.io.Serializable;
import java.util.Set;

import com.imti.framework.web.po.BasePO;

public class SysRolePO extends BasePO implements Serializable {

	private String id;
	private String roleName;
	private String roleCode;
	private String roleMemo;
	private String ztId;
	private Set userSet;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleMemo() {
		return roleMemo;
	}
	public void setRoleMemo(String roleMemo) {
		this.roleMemo = roleMemo;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Set getUserSet() {
		return userSet;
	}
	public void setUserSet(Set userSet) {
		this.userSet = userSet;
	}
	public String getZtId() {
		return ztId;
	}
	public void setZtId(String ztId) {
		this.ztId = ztId;
	}
	
}
