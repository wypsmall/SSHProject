package com.imti.sysmgr.po;

import java.io.Serializable;
import java.sql.Timestamp;

import com.imti.framework.web.po.BasePO;

public class LoginLogPO extends BasePO implements Serializable {
	private String id;//����
	private String loginId;//��¼�˻�
	private String userName;//�û���
	private String loginIP;//��¼IP
	private Timestamp   loginTime;//��¼ʱ��
	private String loginSuccess;//�Ƿ��¼�ɹ�
	private String type;//1 ��ʾ ��¼ 0 ��ʾ�˳�
	private String ztId;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public Timestamp getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setZtId(String ztId) {
		this.ztId = ztId;
	}
	public String getZtId() {
		return ztId;
	}
	
}
