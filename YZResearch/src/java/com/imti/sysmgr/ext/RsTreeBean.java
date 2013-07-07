package com.imti.sysmgr.ext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class RsTreeBean implements Serializable {

	/**
	 * 说明清楚此属性的业务含义
	 */
	private static final long serialVersionUID = 9154448989171628229L;

	private String rsId;
	private String rsCode;
	private String rsName;
	private boolean leaf;
	
	private List<RsTreeBean> children = new ArrayList<RsTreeBean>();

	public RsTreeBean(String rsId, String rsCode, String rsName, boolean leaf){
		this.rsId = rsId;
		this.rsCode = rsCode;
		this.rsName = rsName;
		this.leaf = leaf;
	}
	public RsTreeBean addChild(RsTreeBean rsTreeBean){
		if(CollectionUtils.isEmpty(children)){
			children = new ArrayList<RsTreeBean>(); 
		}
		children.add(rsTreeBean);
		return this;
	}
	public String getRsId() {
		return rsId;
	}

	public void setRsId(String rsId) {
		this.rsId = rsId;
	}

	public String getRsCode() {
		return rsCode;
	}

	public void setRsCode(String rsCode) {
		this.rsCode = rsCode;
	}

	public String getRsName() {
		return rsName;
	}

	public void setRsName(String rsName) {
		this.rsName = rsName;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public List<RsTreeBean> getChildren() {
		return children;
	}

	public void setChildren(List<RsTreeBean> children) {
		this.children = children;
	}
}
