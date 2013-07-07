package com.imti.sysmgr.vo;

import java.io.Serializable;
import java.util.Date;

import com.imti.framework.common.StringUtils;
import com.imti.framework.web.vo.BaseVO;

public class LoginLogVO extends BaseVO implements Serializable {
	private String id;//����
	private String loginId;//��¼�˻�
	private String userName;//�û���
	private String loginIP;//��¼IP
	private String loginTime;//��¼ʱ��
	private String loginSuccess;//�Ƿ��¼�ɹ�
	private String type;//1 ��ʾ ��¼ 0 ��ʾ�˳�
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
