package com.imti.sysmgr.vo;

import java.io.Serializable;

import com.imti.framework.web.vo.BaseVO;

public class WarnVO extends BaseVO implements Serializable {
	private String id;
	private String createDate;
	private String isHandle;//是否处理0未处理1已处理
	private String content;//内容
	private String module;//模块
	private String operator;

	public static final String HANDLE_NO = "0";//未处理
	public static final String HANDLE_YES = "1";//已处理
	
	
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
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
