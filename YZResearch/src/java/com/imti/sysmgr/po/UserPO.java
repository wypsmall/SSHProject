package com.imti.sysmgr.po;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.imti.framework.web.po.BasePO;

public class UserPO extends BasePO implements Serializable {
	private String id;//主键
	private String loginId;//账号
	private String password;//密码
	private String userNick;//昵称
	private String userName;//用户姓名
	private String valid;//账号是否锁定无效 0 无效 1 有效
	private String orgId;//组织机构
	private String loginType;//登录类型（扩展字段）
	private String companyCode;//分公司编码
	private String userType;//区别用户类型 0 普通内部用户 1 一般客户用户 
	private Set roleSet = new HashSet();
	private Set orgSet = new HashSet();
	private String finCode;
	private String ztId;
	private int isCreater;
	//下面的两个字段值用来显示
	private String orgName;
	
	
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserNick() {
		return userNick;
	}
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Set getRoleSet() {
		return roleSet;
	}
	public void setRoleSet(Set roleSet) {
		this.roleSet = roleSet;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Set getOrgSet() {
		return orgSet;
	}
	public void setOrgSet(Set orgSet) {
		this.orgSet = orgSet;
	}
	public String getFinCode() {
		return finCode;
	}
	public void setFinCode(String finCode) {
		this.finCode = finCode;
	}
	public String getZtId() {
		return ztId;
	}
	public void setZtId(String ztId) {
		this.ztId = ztId;
	}
	public int getIsCreater() {
		return isCreater;
	}
	public void setIsCreater(int isCreater) {
		this.isCreater = isCreater;
	}
	
}
