package com.imti.framework.common.ext;
/**
 * ������������ExtJS�е����ṹ���Ӧ�ģ�����ת����JSON
 * @author Administrator
 *
 */
public class ExtJsTreeNode {
	private String id;
	private String text="";// �ڵ��ı�
	private boolean leaf;// �Ƿ�Ҷ�ӽڵ�,���������
	private String qtip="";// ��ʾ��Ϣ
	private boolean expanded = false;// չ��,Ĭ�ϲ�չ��

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
