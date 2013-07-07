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
	 * 1、组装请求参数
	 * 2、创建get方法
	 * 3、组装http请求头
	 * 4、执行http请求
	 * 5、处理响应结果
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
		//组装get方法参数的传输方式
		String getMethodParam = "";
		
		//参数组装
		if(paramMap != null && !paramMap.isEmpty()){
			getMethodParam = buildGetMethodParam(paramMap);
		}
		if(serverURL.indexOf("?") == -1){//如果还没有参数
			serverURL += "?";
	    }else if(StringUtils.isNotBlank(getMethodParam.trim())){//已经有参数
	    	serverURL += "&";
	    }
		serverURL += getMethodParam;
		
		//第二步：创建get方法
		HttpMethod method = new GetMethod(serverURL){
	    	public String  getRequestCharSet() {
	    		return getCharset();
	    	}
	    };
	    method.setFollowRedirects(false);
	    //不允许缓存数据
		method.addRequestHeader("Pragma","no-cache");
		
		try{
			//第三步：组装http请求头
		    if(headMap != null){
		    	addHttpHeader(method, headMap);
		    }
		    
		    //第四步：执行http请求
		    client.executeMethod(method);	
		    
		    //第五步：处理响应结果
		    httpResult = getHttpResult(method);
		} catch (HttpException e) {
			httpResult.setStatus("-1");//请求异常
		} catch (IOException e) {
			httpResult.setStatus("-2");//IO异常
		} finally {
			method.releaseConnection();
		}
		return httpResult ;
	}
	/**
	 * 组建get方法的数据
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String buildGetMethodParam(Map paramMap){
		StringBuffer paramStr = new StringBuffer();
		//参数判断
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
    		}else{//值的形式是一个字符串数组
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
