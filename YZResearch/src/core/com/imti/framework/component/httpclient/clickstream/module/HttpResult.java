package com.imti.framework.component.httpclient.clickstream.module;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * 响应信息结果类
 */
public class HttpResult {
	private String status ;
	private String resHeader ;
	private String sessionid ;
	private String resBody;
	private Map resParaMap;
	private String resFilePath; 
	
	public String getResBody() {
		return resBody;
	}
	public void setResBody(String resBody) {
		this.resBody = resBody;
	}
	public String getResHeader() {
		return resHeader;
	}
	public void setResHeader(String resHeader) {
		this.resHeader = resHeader;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Map getResParaMap() {
		if(resParaMap == null){
			resParaMap = new HashMap();
		}
		return resParaMap;
	}
	public void addResParaMap(String name, String value){
		this.resParaMap.put(name, value);
	}
	public void setResParaMap(Map resParaMap) {
		this.resParaMap = resParaMap;
	}
	public String getResFilePath() {
		return resFilePath;
	}
	public void setResFilePath(String resFilePath) {
		this.resFilePath = resFilePath;
	}
	
}
