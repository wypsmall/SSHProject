package com.imti.framework.component.sertificate.bean;

import java.io.Serializable;

public class CertificateVO implements Serializable {

	private String id;//���ݿ��е�����
	private String systemId;//ϵͳ��ʶ��������֤�����Χ����ϵͳ��Ψһ��ʶ��
	private String keyAlias; //����
	private String keyPass;//����һ
	private String storePass;//�����
	private String keyStoreFile;//ks�ļ���˽Կ�ļ���
	private String csrFile;//.csr�ļ�
	private String cerFile;//.cer�ļ�����Կ�ļ���
	private String zipFile;//ѹ���ļ������и���������Ϣ��
	private String paramFile;//�ṩ����ʱ��keyAlias��keyPass��storePass�Ȳ�����
	
	//����֤��ʱ��toolkey�����������
	private String firstName;
	private String unitName;
	private String orgName;
	private String area;
	private String province;
	private String country;
	
	private String random;//����storePass���һ���������֤ÿ�����ɵĶ���һ��

	private String javaHome;//֤������ʱ�����jdk
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getKeyAlias() {
		return keyAlias;
	}

	public void setKeyAlias(String keyAlias) {
		this.keyAlias = keyAlias;
	}

	public String getKeyPass() {
		return keyPass;
	}

	public void setKeyPass(String keyPass) {
		this.keyPass = keyPass;
	}

	public String getStorePass() {
		return storePass;
	}

	public void setStorePass(String storePass) {
		this.storePass = storePass;
	}

	public String getKeyStoreFile() {
		return keyStoreFile;
	}

	public void setKeyStoreFile(String keyStoreFile) {
		this.keyStoreFile = keyStoreFile;
	}

	public String getCsrFile() {
		return csrFile;
	}

	public void setCsrFile(String csrFile) {
		this.csrFile = csrFile;
	}

	public String getCerFile() {
		return cerFile;
	}

	public void setCerFile(String cerFile) {
		this.cerFile = cerFile;
	}

	public String getZipFile() {
		return zipFile;
	}

	public void setZipFile(String zipFile) {
		this.zipFile = zipFile;
	}

	public String getParamFile() {
		return paramFile;
	}

	public void setParamFile(String paramFile) {
		this.paramFile = paramFile;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public String getJavaHome() {
		return javaHome;
	}

	public void setJavaHome(String javaHome) {
		this.javaHome = javaHome;
	}
	
	
}
