package com.imti.framework.component.httpclient.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

import com.imti.framework.component.httpclient.clickstream.constant.ClickConstant;
import com.imti.framework.component.httpclient.clickstream.module.HttpResult;

public class GetRequest extends Request{

	private GetRequest(String _charset){
		super(_charset);
	}
	
	/**
	 * 1����װ�������
	 * 2������get����
	 * 3����װhttp����ͷ
	 * 4��ִ��http����
	 * 5��������Ӧ���
	 * @param client
	 * @param serverURL
	 * @param paramMap
	 * @param headMap
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public HttpResult doRequest(HttpClient client,String serverURL,
			Map paramMap, Map<String,String> headMap) throws UnsupportedEncodingException, IOException{
		HttpResult httpResult = new HttpResult();  
		//��װget���������Ĵ��䷽ʽ
		String getMethodParam = "";
		
		//������װ
		if(paramMap != null && !paramMap.isEmpty()){
			getMethodParam = buildGetMethodParam(paramMap);
		}
		if(serverURL.indexOf("?") == -1){//�����û�в���
			serverURL += "?";
	    }else if(StringUtils.isNotBlank(getMethodParam.trim())){//�Ѿ��в���
	    	serverURL += "&";
	    }
		serverURL += getMethodParam;
		
		//�ڶ���������get����
		HttpMethod method = new GetMethod(serverURL){
	    	public String  getRequestCharSet() {
	    		return getCharset();
	    	}
	    };
	    method.setFollowRedirects(false);
	    //������������
		method.addRequestHeader("Pragma","no-cache");
		
		try{
			//����������װhttp����ͷ
		    if(headMap != null){
		    	addHttpHeader(method, headMap);
		    }
		    
		    //���Ĳ���ִ��http����
		    client.executeMethod(method);	
		    
		    //���岽��������Ӧ���
		    httpResult = getHttpResult(method);
		} catch (HttpException e) {
			httpResult.setStatus("-1");//�����쳣
		} catch (IOException e) {
			httpResult.setStatus("-2");//IO�쳣
		} finally {
			method.releaseConnection();
		}
		return httpResult ;
	}
	/**
	 * �齨get����������
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String buildGetMethodParam(Map paramMap){
		StringBuffer paramStr = new StringBuffer();
		//�����ж�
		if(paramMap == null || paramMap.isEmpty()){
			return "";
		}
		Iterator keys = paramMap.keySet().iterator();
	    int paramIndex=0;
	    while(keys.hasNext()){
    		String paramName = (String)keys.next();
    		Object paramValue = paramMap.get(paramName);
    		if(paramIndex > 0){
    			paramStr.append("&");
    		}
    		if(paramValue instanceof String){
    			paramStr.append(paramName + "=" + paramValue.toString());
    			paramIndex++;
    		}else{//ֵ����ʽ��һ���ַ�������
    			String[] values = (String[])paramValue;
    			if(values != null){
    				for(int i = 0; i < values.length; i++){
    					if(i == 0){
    						paramStr.append(paramName + "=" + values[i].toString());
    					}else{
    						paramStr.append("&" + paramName + "=" + values[i].toString());
    					}
    					paramIndex++;
    				}
    			}
    		}
    	}
		return paramStr.toString();
	}
	public static GetRequest newInstance(String charset){
		return new GetRequest(charset);
	}
	
	public static GetRequest newInstance(){
		return new GetRequest(ClickConstant.DEFAULT_CHARSET);
	}
}
