package com.imti.sysmgr.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RsTree implements Serializable {

	private String nodeId;
	private String nodeName;
	private String imgUrl;
	private String qtip;
	private String code;
	private String type; //1：表示资源  2表示操作
	private List<RsTree> children = new ArrayList<RsTree>();
	public List<RsTree> getChildren() {
		return children;
	}
	public void setChildren(List<RsTree> children) {
		this.children = children;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public String getQtip() {
		return qtip;
	}
	public void setQtip(String qtip) {
		this.qtip = qtip;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
