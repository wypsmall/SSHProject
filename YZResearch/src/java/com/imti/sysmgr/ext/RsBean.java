package com.imti.sysmgr.ext;


public class RsBean {

	private String rootId;
	private String rootCode;
	private String rootName;
	private String rootImgUrl;//当获取顶级节点的子菜单时，这里是二级菜单
	private String rootRsUrl;
	private String secondId;
	private String secondCode;
	private String secondName;
	private String secondImgUrl;//当获取顶级节点的子菜单时，这里是三级菜单
	private String secondRsUrl;
	private String thirdId;
	private String thirdCode;
	private String thirdName;
	
	private String operatorId;
	private String operatorCode;
	private String operatorName;
	
	public String getRootImgUrl() {
		return rootImgUrl;
	}
	public void setRootImgUrl(String rootImgUrl) {
		this.rootImgUrl = rootImgUrl;
	}
	public String getRootRsUrl() {
		return rootRsUrl;
	}
	public void setRootRsUrl(String rootRsUrl) {
		this.rootRsUrl = rootRsUrl;
	}
	public String getSecondImgUrl() {
		return secondImgUrl;
	}
	public void setSecondImgUrl(String secondImgUrl) {
		this.secondImgUrl = secondImgUrl;
	}
	public String getSecondRsUrl() {
		return secondRsUrl;
	}
	public void setSecondRsUrl(String secondRsUrl) {
		this.secondRsUrl = secondRsUrl;
	}
	public String getRootId() {
		return rootId;
	}
	public void setRootId(String rootId) {
		this.rootId = rootId;
	}
	public String getRootCode() {
		return rootCode;
	}
	public void setRootCode(String rootCode) {
		this.rootCode = rootCode;
	}
	public String getRootName() {
		return rootName;
	}
	public void setRootName(String rootName) {
		this.rootName = rootName;
	}
	public String getSecondId() {
		return secondId;
	}
	public void setSecondId(String secondId) {
		this.secondId = secondId;
	}
	public String getSecondCode() {
		return secondCode;
	}
	public void setSecondCode(String secondCode) {
		this.secondCode = secondCode;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public String getThirdId() {
		return thirdId;
	}
	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}
	public String getThirdCode() {
		return thirdCode;
	}
	public void setThirdCode(String thirdCode) {
		this.thirdCode = thirdCode;
	}
	public String getThirdName() {
		return thirdName;
	}
	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
}
