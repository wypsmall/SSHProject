package com.imti.framework.component.httpclient.clickstream.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;

import com.imti.framework.component.httpclient.clickstream.constant.ClickConstant;
import com.imti.framework.component.httpclient.clickstream.module.Click;
import com.imti.framework.component.httpclient.clickstream.module.Header;
import com.imti.framework.component.httpclient.clickstream.module.HttpResult;
import com.imti.framework.component.httpclient.clickstream.parser.HeaderParser;
import com.imti.framework.component.httpclient.clickstream.service.ClickInvokeService;
import com.imti.framework.component.httpclient.request.GetRequest;
import com.imti.framework.component.httpclient.request.PostRequest;
import com.imti.framework.component.httpclient.util.HttpclientUtil;

public class DefaultClickInvokeServiceImpl implements ClickInvokeService{

	/**
	 * 1����ȡhttpclient����
	 * 2�������ϴε����õ���Ч���ݣ�input��textarea��
	 * 3���ϲ�����
	 * 4��ִ������
	 * 5���������õ���Ľ��
	 */
	@SuppressWarnings("unchecked")
	public HttpResult invoke(HttpClient client, Click click, Map paramMap) {
		HttpResult result = null;
		if(click == null){
			throw new RuntimeException("���õĵ����ʧ��");
		}
		try{
			//��һ������ȡhttpclient����
			Map headMap = new HashMap();
			Header header = HeaderParser.getHeader(click.getCurrentClick());
			if(header != null){
				headMap = header.getHeaderMap();
			}
			//�ڶ����������ϴε����õ���Ч���ݣ�input��textarea��
			String method = click.getMethod();
			Map inputMap = click.getResult().getResParaMap();
			//���������ϲ�����
			HttpclientUtil.mergeMap(inputMap, paramMap);
			//���Ĳ���ִ������
			if(ClickConstant.GET.equals(method)){
				result =  GetRequest.newInstance().doRequest(client, click.getUrl(), inputMap, headMap);
			}else if(ClickConstant.POST.equals(method)){
				result =  PostRequest.newInstance().doRequest(client, click.getUrl(), inputMap, headMap);
			}
			//���岽���������õ���Ľ��
			click.setResult(result);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return result;
	}

}
