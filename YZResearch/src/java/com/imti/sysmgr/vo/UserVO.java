package com.imti.sysmgr.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.imti.framework.common.StringUtils;
import com.imti.framework.web.vo.BaseVO;

public class UserVO extends BaseVO implements Serializable {
	private String id;//����
	private String loginId;//�˺�
	private String password;//����
	private String userNick;//�ǳ�
	private String userName;//�û�����
	private String valid;//�˺��Ƿ�������Ч 0 ��Ч 1 ��Ч
	private String orgId;//��֯����
	private String loginType;//��¼���ͣ���չ�ֶΣ�
	private String userType;//�����û����� 0 ��ͨ�ڲ��û� 1 һ��ͻ��û�
	private String companyCode;//�ֹ�˾����
	private String finCode;//�������
	private String ztId;
	private int isCreater;
	//����������ֶ�ֵ������ʾ
	private String orgName;
	private Set roleSet = new HashSet();
	private Set orgSet = new HashSet();
	
	//�û����Բ����Ĳ�����Դ��map(code,url)
	private Map rsOpeCodeMap;

	public static final String USER_TYPE_NORMAL = "0";
	public static final String USER_TYPE_CUSTOMER = "1";
	
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
	
	public Map getRsOpeCodeMap() {
		return rsOpeCodeMap;
	}
	public void setRsOpeCodeMap(Map rsOpeCodeMap) {
		this.rsOpeCodeMap = rsOpeCodeMap;
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
		return StringUtils.isEmpty(userType) ? "0" : userType;
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
