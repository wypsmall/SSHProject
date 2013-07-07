package com.imti.framework.component.httpclient.request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.imti.framework.component.httpclient.clickstream.constant.ClickConstant;
import com.imti.framework.component.httpclient.clickstream.module.HttpResult;

/**
 * @author Administrator
 *post���󣬲����ļ��ϴ�
 */
public class PostRequest extends Request{

	public PostRequest(String _charset) {
		super(_charset);
	}
	public static PostRequest newInstance(String charset){
		return new PostRequest(charset);
	}
	
	public static PostRequest newInstance(){
		return new PostRequest(ClickConstant.DEFAULT_CHARSET);
	}
	
	/**
	 * 1������httpPost����
	 * 2���齨��������
	 * 3������ͷ����
	 * 4��������Ӧ���
	 * @param client
	 * @param serverURL
	 * @param paramMap
	 * @param headMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HttpResult doRequest(HttpClient client, String serverURL, Map paramMap, Map<String, String> headMap){
		HttpResult httpResult = new HttpResult();
		
		//��һ��������httpPost����
		PostMethod postMethod = new PostMethod(serverURL) {
			public String getRequestCharSet() {
				return getCharset();
			}
		};
		
		//�ڶ������齨��������
		if (paramMap != null) {
			List dataList =buildPostMethodParam(paramMap);
			postMethod.setRequestBody((NameValuePair[]) dataList.toArray(new NameValuePair[0]));
		}

		postMethod.setFollowRedirects(false);
		// ������������
		postMethod.addRequestHeader("Pragma", "no-cache");
		try {
			// ������:����ͷ����
			if (headMap != null) {
				addHttpHeader(postMethod, headMap);
			}
			client.executeMethod(postMethod);
			//���Ĳ���������Ӧ���
		    httpResult = getHttpResult(postMethod);
		} catch (HttpException e) {
			httpResult.setStatus("-1");//�����쳣
		} catch (IOException e) {
			httpResult.setStatus("-1");//�����쳣
		} finally {
			postMethod.releaseConnection();
		}
		return httpResult;
	}
	
	@SuppressWarnings("unchecked")
	public List buildPostMethodParam(Map paramMap){
		if(paramMap == null || paramMap.isEmpty()){
			return new ArrayList();
		}
		Iterator keys = paramMap.keySet().iterator();
		List dataList = new ArrayList();
		while (keys.hasNext()) {
			String paramName = (String) keys.next();
			Object paramValue = paramMap.get(paramName);
			if (paramValue instanceof String) {
				dataList.add(new NameValuePair(paramName, paramValue.toString()));
			} else {// ֵ���ַ�������ʽ
				String[] values = (String[]) paramValue;
				if (values != null) {
					for (int j = 0; j < values.length; j++) {
						dataList.add(new NameValuePair(paramName, values[j].toString()));
					}
				}
			}
		}
		return dataList;
	}
}
