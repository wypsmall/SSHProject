package com.imti.sysmgr.vo;

import java.io.Serializable;

import com.imti.framework.web.vo.BaseVO;

public class WarnVO extends BaseVO implements Serializable {
	private String id;
	private String createDate;
	private String isHandle;//�Ƿ���0δ����1�Ѵ���
	private String content;//����
	private String module;//ģ��
	private String operator;

	public static final String HANDLE_NO = "0";//δ����
	public static final String HANDLE_YES = "1";//�Ѵ���
	
	
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
