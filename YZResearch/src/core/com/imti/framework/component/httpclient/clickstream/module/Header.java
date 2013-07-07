package com.imti.framework.component.httpclient.clickstream.module;

import java.util.HashMap;
import java.util.Map;


public class Header {
	
	private String headerId;
	private Map headerMap  = new HashMap();
	public String getHeaderId() {
		return headerId;
	}
	public void setHeaderId(String headerId) {
		this.headerId = headerId;
	}
	public Map getHeaderMap() {
		return headerMap;
	}
	public void setHeaderMap(Map headerMap) {
		this.headerMap = headerMap;
	}
	public void addBaseModel(BaseModel baseModel){
		headerMap.put(baseModel.getName(), baseModel.getValue());
	}
}
