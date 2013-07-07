package com.imti.framework.common.ext;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import com.imti.framework.web.log.ExtLog;


/**
 * 本工具类用来实现输出为ExtJs规定的JSON格式 减少代码复制量，以后逐步增强其内容
 * 
 * @author 
 * 
 */
public class ExtJSONUtil {
	private static ExtLog logger = new ExtLog(ExtJSONUtil.class);

	/**
	 * 没有分页的GridPanel所需要的JSON内容
	 */
	public static void gridNoPaged(List data, HttpServletResponse response,JsonConfig jsonConfig) {
		int totalCnt = data.size();
		gridWithPaged(data, totalCnt, response,jsonConfig);
	}

	/**
	 * 有分页的GridPanel所需要的JSON内容
	 */
	public static void gridWithPaged(List data, int totalCnt,
			HttpServletResponse response,JsonConfig jsonConfig) {
		StringBuffer jsonSb = new StringBuffer();
		jsonSb.append("{total:");
		jsonSb.append(totalCnt);
		jsonSb.append(",rows:");
		JSONArray json=null;
		if(jsonConfig==null){
			json= JSONArray.fromObject(data );
		}else{
			json= JSONArray.fromObject(data, jsonConfig);
		}
		jsonSb.append(json.toString());
		jsonSb.append("}");
		write(jsonSb.toString(), response);
	}

	/**
	 * 输出处理成功失败信息
	 * @param success 处理结果
	 * @param response
	 */
	public static void writeSuccessInfo(boolean success,
			HttpServletResponse response) {
		StringBuffer jsonSb = new StringBuffer();
		if (success) {
			jsonSb.append("{success:true}");
		} else {
			jsonSb.append("{success:false}");
		}
		try {
			PrintWriter writer = response.getWriter();
			logger.println(jsonSb.toString());
			writer.write(jsonSb.toString());
		} catch (IOException e) {
			logger.error("无法处理输出" + e.getMessage());
		}
	}

	public static void writeObject(Object object, HttpServletResponse response){
		JSONArray json = JSONArray.fromObject(object);
		write(json.toString(), response);
	}
	
	/**
	 * 直接输出JSON
	 * @param json
	 * @param response
	 */
	public static void write(String json, HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=GBK");
		try {
			PrintWriter writer = response.getWriter();
			writer.write(json);
			logger.println(json);
		} catch (IOException e) {
			logger.error("无法处理输出" + e.getMessage());
		}
	}
	/**
	 * 输出处理成功失败信息
	 * @param success  处理结果
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	public static void writeSuccessInfo(boolean success,
			HttpServletResponse response, String content) {
		response.setContentType("text/json; charset=utf-8");
		StringBuffer jsonSb = new StringBuffer();
		if (success) {
			jsonSb.append("{success:true,data:'" + "操作成功" + "'}");
		} else {
			jsonSb.append("{success:false,data:'" + content + "'}");
		}
		try {
			PrintWriter writer = response.getWriter();
			logger.println(jsonSb.toString());
			writer.write(jsonSb.toString());
		} catch (IOException e) {
			logger.error("无法处理输出" + e.getMessage());
		}
	}
	/**
	 * 输出处理成功失败信息
	 * @param success  处理结果
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	public static void writeInfo(boolean success,
			HttpServletResponse response, String content) {
		response.setContentType("text/json; charset=utf-8");
		StringBuffer jsonSb = new StringBuffer();
		if (success) {
			jsonSb.append("{success:true,data:'" + content + "'}");
		} else {
			jsonSb.append("{success:false,data:'" + content + "'}");
		}
		try {
			PrintWriter writer = response.getWriter();
			logger.println(jsonSb.toString());
			writer.write(jsonSb.toString());
		} catch (IOException e) {
			logger.error("无法处理输出" + e.getMessage());
		}
	}
	public static void write(boolean success,
			HttpServletResponse response, String content) {
		response.setContentType("text/html; charset=utf-8");
		StringBuffer jsonSb = new StringBuffer();
		if (success) {
			jsonSb.append("{success:true,data:'" + content + "'}");
		} else {
			jsonSb.append("{success:false,data:'" + content + "'}");
		}
		try {
			PrintWriter writer = response.getWriter();
			logger.println(jsonSb.toString());
			writer.write(jsonSb.toString());
		} catch (IOException e) {
			logger.error("无法处理输出" + e.getMessage());
		}
	}
	/**
	 * 输出ajax请求的结果
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	public static void writeInfo(HttpServletResponse response, String content) {
		response.setContentType("text/json; charset=utf-8");
		StringBuffer jsonSb = new StringBuffer();
		jsonSb.append("{data:" + content + "}");
		try {
			PrintWriter writer = response.getWriter();
			logger.println(jsonSb.toString());
			writer.write(jsonSb.toString());
		} catch (IOException e) {
			logger.error("无法处理输出" + e.getMessage());
		}
	}
}
