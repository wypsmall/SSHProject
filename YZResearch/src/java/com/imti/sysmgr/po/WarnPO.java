package com.imti.sysmgr.po;

import java.io.Serializable;
import java.util.Date;

import com.imti.framework.web.po.BasePO;

public class WarnPO extends BasePO implements Serializable {

	private String id;
	private Date createDate;
	private String content;//ÄÚÈÝ
	private String isHandle;
	private String operator;
	private String module;//Ä£¿é
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsHandle() {
		return isHandle;
	}
	public void setIsHandle(String isHandle) {
		this.isHandle = isHandle;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
}
