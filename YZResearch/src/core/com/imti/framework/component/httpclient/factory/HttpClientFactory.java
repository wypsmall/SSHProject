package com.imti.framework.component.httpclient.factory;

import org.apache.commons.httpclient.HttpClient;

public class HttpClientFactory {

	private HttpClientFactory(){
		
	}

	public HttpClient getHttpClient(){
		HttpClient client = new HttpClient();
		return client;
	}
	public static HttpClientFactory getInstance(){
    	return new HttpClientFactory();
    }
}
