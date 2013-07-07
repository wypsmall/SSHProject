package com.imti.framework.common.ext;
/**
 * 该类是用来与ExtJS中的树结构相对应的，方便转化成JSON
 * @author Administrator
 *
 */
public class ExtJsTreeNode {
	private String id;
	private String text="";// 节点文本
	private boolean leaf;// 是否叶子节点,看情况设置
	private String qtip="";// 提示信息
	private boolean expanded = false;// 展开,默认不展开

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

}
