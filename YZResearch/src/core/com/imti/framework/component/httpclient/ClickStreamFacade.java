package com.imti.framework.component.httpclient;

import java.util.Map;

import com.imti.framework.component.httpclient.clickstream.module.ClickStream;


public class ClickStreamFacade {
	private String clickStream;
	@SuppressWarnings("unchecked")
	private Map inputMap;
	
	@SuppressWarnings("unchecked")
	private ClickStreamFacade(String _clickStream, Map _inputMap){
		this.clickStream = _clickStream;
		this.inputMap = _inputMap;
	}
	@SuppressWarnings("unchecked")
	public static ClickStreamFacade newInstance(String _clickStream, Map _inputMap){
		return new ClickStreamFacade(_clickStream, _inputMap);
	}
	public void startClickStream(){
		ClickStream.newInstance(clickStream).run(inputMap);
	}
}
