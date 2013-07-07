package com.imti.framework.component.httpclient.request;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.lang.StringUtils;

import com.imti.framework.common.DateUtil;
import com.imti.framework.common.FileUtils;
import com.imti.framework.component.httpclient.clickstream.constant.ClickConstant;
import com.imti.framework.component.httpclient.clickstream.module.HttpResult;
import com.imti.framework.component.httpclient.util.HttpclientUtil;
import com.imti.framework.component.vopomapping.utils.PropertyUtils;


public class Request {
	private String charset;
	
	private static final String DEFAULT_DIR = "C:/LOG/";
	private static Pattern p2 = Pattern.compile("JSESSIONID=[0-9a-zA-Z\\-!]*");
	
	public Request(String _charset){
		this.charset = _charset;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	
	/**
	 * 组建http请求头
	 * @param method
	 * @param headMap
	 */
	@SuppressWarnings("unchecked")
	public void addHttpHeader(HttpMethod method, Map<String, String> headMap){
		if(headMap != null){
			Iterator keys = headMap.keySet().iterator();
		   	 while(keys.hasNext()){
		   		String headName=(String)keys.next();
		   		String headValue=(String)headMap.get(headName);
		   		method.addRequestHeader(headName, headValue);
		   	 }
		}
	}
	
	/**
	 * 处理请求的响应结果
	 * @param method
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HttpResult getHttpResult(HttpMethod method) {
		HttpResult result = new HttpResult();
		String sessionid = "";
		Header[] headers = method.getResponseHeaders();
		String headerStr = "";
		for (Header h : headers) {
			if (h.getName().equals("Set-Cookie")) {
				String value = h.getValue();
				if(!(value == null || value.equals(""))){
					sessionid = getSessionId(h.getValue());
				}
			}
			headerStr += h.getName()+ ":" + h.getValue() + "\n";
		}
		result.setResHeader(headerStr);//响应头
		result.setStatus(method.getStatusCode() + "");//响应码
		result.setSessionid(sessionid);//session
		try {
			String content = new String(method.getResponseBodyAsString().getBytes(ClickConstant.DEFAULT_CHARSET));
			result.setResBody(content);//响应体的字符串表示
			String responseDir = PropertyUtils.getProperty("imti.httpclient.response.dir", DEFAULT_DIR);
			String dateDir = DateUtil.getCurrentDate();
			String file = DateUtil.getCurrentDateTime() + ".txt";
			String responseFile = responseDir + dateDir + "/" + file;
			result.setResFilePath(responseFile);//相应文件的本地路径
			setHttpResultBody(result, method, responseFile);
			Map responMap = HttpclientUtil.httpParser(responseFile);
			result.setResParaMap(responMap);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	private String getSessionId(String str) {
		Matcher m = p2.matcher(str);
		String temp = null;
		if (m.find()) {
			temp = m.group();
		}
		temp = temp.substring(11,temp.length());
		return temp;
	}
	/**
	 * 设置响应内容（非文件形式的字符串） 1.可以将响应信息存放到本地 2.可以将响应信息存放到字符串中
	 * @throws Exception
	 */
	private void setHttpResultBody(HttpResult httpResult, HttpMethod method, String localPath) throws Exception {
		try {
			if (Integer.parseInt(httpResult.getStatus()) == 200) {
				if (StringUtils.isNotEmpty(localPath)) {
					InputStream fileInpuStream = method.getResponseBodyAsStream();
					File tmpfile = new File(localPath);
					File parent = tmpfile.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}
					if (tmpfile.exists()) {
						tmpfile.delete();
					}
					FileUtils.writer(localPath, fileInpuStream);
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
}
