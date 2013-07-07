package com.imti.framework.component.httpclient.clickstream.module;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang.StringUtils;

import com.imti.framework.component.httpclient.clickstream.parser.ClickStreamParser;
import com.imti.framework.component.httpclient.clickstream.service.ClickInvokeService;
import com.imti.framework.component.httpclient.clickstream.service.impl.DefaultClickInvokeServiceImpl;

public class Click {

	private String currentClick;
	private String nextClick;
	private String headerId;
	private String method;
	private String url;
	private String clickStreamId;
	private String startBtn;
	private HttpResult result;
	private String service;
	public String getCurrentClick() {
		return currentClick;
	}
	public void setCurrentClick(String currentClick) {
		this.currentClick = currentClick;
	}
	public Click getNextClick() {
		Click nextClickBean = null;
		if(StringUtils.isNotBlank(nextClick) && StringUtils.isNotBlank(clickStreamId)){
			ClickStream clickStream =  (ClickStream)ClickStreamParser.getClickStream(clickStreamId);
			if(clickStream != null){
				nextClickBean = (clickStream.getClickMap() == null ? null : (Click)clickStream.getClickMap().get(nextClick));
			}
		}
		return nextClickBean;
	}
	
	@SuppressWarnings("unchecked")
	public void invoke(HttpClient client, Map paramMap){
		ClickInvokeService service = new DefaultClickInvokeServiceImpl();
		String serviceImpl = getService();
		if(StringUtils.isNotBlank(serviceImpl)){
			service = getClickInvokeService(serviceImpl);
		}
		service.invoke(client, this, paramMap);
	}
	
	private ClickInvokeService getClickInvokeService(String clickInvokeServiceImplName){
		try {
			Object object = Class.forName(clickInvokeServiceImplName).newInstance();
			if(object != null){
				return (ClickInvokeService)object;
			}
    	} catch (InstantiationException e) {
    		throw new RuntimeException("实例化时出错，请检查配置的路径");
    	} catch (IllegalAccessException e) {
        } catch (ClassNotFoundException e) {
        	throw new RuntimeException("没有此对象：" + clickInvokeServiceImplName + "请检查配置的路径");
        }
		throw new RuntimeException("请实现com.imti.framework.compenent.httpclient.clickstream.service.ClickInvokeService接口");
	}
	public void setNextClick(String nextClick) {
		this.nextClick = nextClick;
	}
	public String getHeaderId() {
		return headerId;
	}
	public void setHeaderId(String headerId) {
		this.headerId = headerId;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public HttpResult getResult() {
		if(result == null){
			result = new HttpResult();
		}
		return result;
	}
	public void setResult(HttpResult result) {
		this.result = result;
	}
	public String getClickStreamId() {
		return clickStreamId;
	}
	public void setClickStreamId(String clickStreamId) {
		this.clickStreamId = clickStreamId;
	}
	public String getStartBtn() {
		return startBtn;
	}
	public void setStartBtn(String startBtn) {
		this.startBtn = startBtn;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	
}
