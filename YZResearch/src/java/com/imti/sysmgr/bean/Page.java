package com.imti.sysmgr.bean;

public class Page {
	private String start = "0";//��ҳ
	private String limit = "20";//��ҳ
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
}
