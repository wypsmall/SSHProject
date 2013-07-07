package com.imti.sysmgr.ext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class MenueBean implements Serializable {
	
	/**
	 * 说明清楚此属性的业务含义
	 */
	private static final long serialVersionUID = 1L;
	private String rsId;
	private String rsImgUrl;
	private String rsName;
	private String rsUrl;
	
	public MenueBean(String rsId, String rsName, String rsImgUrl, String rsUrl){
		this.rsId = rsId;
		this.rsName = rsName;
		this.rsImgUrl = rsImgUrl;
		this.rsUrl = rsUrl;
	}
	public MenueBean addChild(MenueBean menueBean){
		if(CollectionUtils.isEmpty(children)){
			children = new ArrayList<MenueBean>(); 
		}
		children.add(menueBean);
		return this;
	}
	
	private List<MenueBean> children = new ArrayList<MenueBean>();

	public String getRsId() {
		return rsId;
	}

	public void setRsId(String rsId) {
		this.rsId = rsId;
	}

	public String getRsImgUrl() {
		return rsImgUrl;
	}

	public void setRsImgUrl(String rsImgUrl) {
		this.rsImgUrl = rsImgUrl;
	}

	public String getRsName() {
		return rsName;
	}

	public void setRsName(String rsName) {
		this.rsName = rsName;
	}

	public String getRsUrl() {
		return rsUrl;
	}

	public void setRsUrl(String rsUrl) {
		this.rsUrl = rsUrl;
	}

	public List<MenueBean> getChildren() {
		return children;
	}

	public void setChildren(List<MenueBean> children) {
		this.children = children;
	}
}
