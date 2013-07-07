package com.imti.framework.component.httpclient.clickstream.service;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;

import com.imti.framework.component.httpclient.clickstream.module.Click;
import com.imti.framework.component.httpclient.clickstream.module.HttpResult;

public interface ClickInvokeService {

	@SuppressWarnings("unchecked")
	public HttpResult invoke(HttpClient client, Click click, Map paramMap);
}
