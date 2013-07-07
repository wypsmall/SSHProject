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
	 * 1、获取httpclient对象
	 * 2、接收上次点击获得的有效数据（input、textarea）
	 * 3、合并参数
	 * 4、执行请求
	 * 5、反向设置点击的结果
	 */
	@SuppressWarnings("unchecked")
	public HttpResult invoke(HttpClient client, Click click, Map paramMap) {
		HttpResult result = null;
		if(click == null){
			throw new RuntimeException("配置的点击丢失！");
		}
		try{
			//第一步：获取httpclient对象
			Map headMap = new HashMap();
			Header header = HeaderParser.getHeader(click.getCurrentClick());
			if(header != null){
				headMap = header.getHeaderMap();
			}
			//第二步：接收上次点击获得的有效数据（input、textarea）
			String method = click.getMethod();
			Map inputMap = click.getResult().getResParaMap();
			//第三步：合并参数
			HttpclientUtil.mergeMap(inputMap, paramMap);
			//第四步：执行请求
			if(ClickConstant.GET.equals(method)){
				result =  GetRequest.newInstance().doRequest(client, click.getUrl(), inputMap, headMap);
			}else if(ClickConstant.POST.equals(method)){
				result =  PostRequest.newInstance().doRequest(client, click.getUrl(), inputMap, headMap);
			}
			//第五步：反向设置点击的结果
			click.setResult(result);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return result;
	}

}
