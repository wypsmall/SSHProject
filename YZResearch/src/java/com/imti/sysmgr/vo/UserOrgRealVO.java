package com.imti.sysmgr.vo;

import java.io.Serializable;

import com.imti.framework.web.vo.BaseVO;

public class UserOrgRealVO extends BaseVO implements Serializable {
	private String id;//主键
	private String userId;//用户id
	private String orgId;//部门id
	private String ztId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getZtId() {
		return ztId;
	}
	public void setZtId(String ztId) {
		this.ztId = ztId;
	}
	
	
	
}
