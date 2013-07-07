package com.imti.framework.component.httpclient.request;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.FilePartSource;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.imti.framework.component.httpclient.clickstream.constant.ClickConstant;
import com.imti.framework.component.httpclient.clickstream.module.HttpResult;

public class PostUploadFileRequest extends PostRequest {

	public PostUploadFileRequest(String _charset) {
		super(_charset);
	}
	public static PostUploadFileRequest newInstance(String charset){
		return new PostUploadFileRequest(charset);
	}
	
	public static PostUploadFileRequest newInstance(){
		return new PostUploadFileRequest(ClickConstant.DEFAULT_CHARSET);
	}
	/**
	 * files中的文件路径约定：fileContrlID#filePath
	 * @param client
	 * @param serverURL
	 * @param paramMap
	 * @param files
	 * @param headMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HttpResult doRequest(HttpClient client, String serverURL, Map paramMap, 
			String[] files, Map<String, String> headMap){
		HttpResult httpResult = new HttpResult();
		// 第一步：创建post方法
		PostMethod postMethod = new PostMethod(serverURL) {
			public String getRequestCharSet() {
				return getCharset();
			}
		};
		try {
			postMethod.setFollowRedirects(false);
			postMethod.addRequestHeader("Pragma", "no-cache");
			List partList = new ArrayList();;// 将此list转换成为Part数组
			//第二步：组建请求数据
			if (paramMap != null) {
				List dataList =buildPostMethodParam(paramMap);
				partList.addAll(dataList);
			}
			//第三步：组装上传文件
			for(int i = 0; files != null && i < files.length; i++){
				String fileContrlID = files[i].split("#")[0];
				String filePath = files[i].split("#")[1];
				File file = new File(filePath);
				FilePartSource source = new FilePartSource(file.getName(), file);
				FilePart filePart = new FilePart(fileContrlID, source);
				filePart.setCharSet(getCharset());
				filePart.setContentType("application/octet-stream");
				partList.add(filePart);
			}
			
			postMethod.getParams().setParameter(HttpMethodParams.MULTIPART_BOUNDARY, "-----------------------------7d92031030394");
			Part[] parts = (Part[])partList.toArray(new Part[0]);
			MultipartRequestEntity muti = new MultipartRequestEntity(parts, postMethod.getParams());
			postMethod.getParams().setParameter("Content-Type", muti.getContentType());
			postMethod.setRequestEntity(muti);
			
			// 第四步：请求头参数
			if (headMap != null) {
				addHttpHeader(postMethod, headMap);
			}
			client.executeMethod(postMethod);
			//第五步：处理响应结果
		    httpResult = getHttpResult(postMethod);
		} catch (HttpException e) {
			httpResult.setStatus("-1");//请求异常
		} catch (IOException e) {
			httpResult.setStatus("-1");//请求异常
		} finally {
			postMethod.releaseConnection();
		}
		return httpResult;
	}
}
