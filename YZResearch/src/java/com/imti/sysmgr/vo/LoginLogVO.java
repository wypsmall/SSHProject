package com.imti.sysmgr.vo;

import java.io.Serializable;
import java.util.Date;

import com.imti.framework.common.StringUtils;
import com.imti.framework.web.vo.BaseVO;

public class LoginLogVO extends BaseVO implements Serializable {
	private String id;//主键
	private String loginId;//登录账户
	private String userName;//用户名
	private String loginIP;//登录IP
	private String loginTime;//登录时间
	private String loginSuccess;//是否登录成功
	private String type;//1 表示 登录 0 表示退出
	private String ztId;
	public static final String LOGIN_SUCCESS_YES = "1";
	public static final String LOGIN_SUCCESS_NO = "0";
	public static final String LOGIN = "1";
	public static final String LOGOUT = "0";
	
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
	public String getLoginIP() {
		return loginIP;
	}
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	public String getLoginSuccess() {
		return loginSuccess;
	}
	public void setLoginSuccess(String loginSuccess) {
		this.loginSuccess = loginSuccess;
	}
	public String getLoginTime() {
		if(StringUtils.isEmpty(loginTime)){
			return new Date().toString();
		}
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getZtId() {
		return ztId;
	}
	public void setZtId(String ztId) {
		this.ztId = ztId;
	}
	
	
}
