package com.imti.sysmgr.po;

import java.io.Serializable;

import com.imti.framework.web.po.BasePO;

public class SysOperatorPO extends BasePO implements Serializable {
	
	private String id;//操作主键
	private String name;//操作名称
	private String code;//操作编码
	private String rsId;//资源ID
	private int displayIndex;//顺序号
	private String url;//资源Url
	private String memo;//备注
	private String ztId;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getDisplayIndex() {
		return displayIndex;
	}
	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRsId() {
		return rsId;
	}
	public void setRsId(String rsId) {
		this.rsId = rsId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getZtId() {
		return ztId;
	}
	public void setZtId(String ztId) {
		this.ztId = ztId;
	}
	
}
