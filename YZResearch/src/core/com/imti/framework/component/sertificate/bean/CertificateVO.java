package com.imti.framework.component.sertificate.bean;

import java.io.Serializable;

public class CertificateVO implements Serializable {

	private String id;//数据库中的主键
	private String systemId;//系统标识（对生成证书的外围接入系统的唯一标识）
	private String keyAlias; //别名
	private String keyPass;//密码一
	private String storePass;//密码二
	private String keyStoreFile;//ks文件（私钥文件）
	private String csrFile;//.csr文件
	private String cerFile;//.cer文件（公钥文件）
	private String zipFile;//压缩文件（含有各种输入信息）
	private String paramFile;//提供下载时（keyAlias、keyPass、storePass等参数）
	
	//生成证书时，toolkey所需输入参数
	private String firstName;
	private String unitName;
	private String orgName;
	private String area;
	private String province;
	private String country;
	
	private String random;//增加storePass后的一个随机数保证每次生成的都不一样

	private String javaHome;//证书生成时所需的jdk
	
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
