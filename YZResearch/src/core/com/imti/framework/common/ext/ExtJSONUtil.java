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
 * ������������ʵ�����ΪExtJs�涨��JSON��ʽ ���ٴ��븴�������Ժ�����ǿ������
 * 
 * @author 
 * 
 */
public class ExtJSONUtil {
	private static ExtLog logger = new ExtLog(ExtJSONUtil.class);

	/**
	 * û�з�ҳ��GridPanel����Ҫ��JSON����
	 */
	public static void gridNoPaged(List data, HttpServletResponse response,JsonConfig jsonConfig) {
		int totalCnt = data.size();
		gridWithPaged(data, totalCnt, response,jsonConfig);
	}

	/**
	 * �з�ҳ��GridPanel����Ҫ��JSON����
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
	 * �������ɹ�ʧ����Ϣ
	 * @param success ������
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
			logger.error("�޷��������" + e.getMessage());
		}
	}

	public static void writeObject(Object object, HttpServletResponse response){
		JSONArray json = JSONArray.fromObject(object);
		write(json.toString(), response);
	}
	
	/**
	 * ֱ�����JSON
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
			logger.error("�޷��������" + e.getMessage());
		}
	}
	/**
	 * �������ɹ�ʧ����Ϣ
	 * @param success  ������
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	public static void writeSuccessInfo(boolean success,
			HttpServletResponse response, String content) {
		response.setContentType("text/json; charset=utf-8");
		StringBuffer jsonSb = new StringBuffer();
		if (success) {
			jsonSb.append("{success:true,data:'" + "�����ɹ�" + "'}");
		} else {
			jsonSb.append("{success:false,data:'" + content + "'}");
		}
		try {
			PrintWriter writer = response.getWriter();
			logger.println(jsonSb.toString());
			writer.write(jsonSb.toString());
		} catch (IOException e) {
			logger.error("�޷��������" + e.getMessage());
		}
	}
	/**
	 * �������ɹ�ʧ����Ϣ
	 * @param success  ������
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
			logger.error("�޷��������" + e.getMessage());
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
			logger.error("�޷��������" + e.getMessage());
		}
	}
	/**
	 * ���ajax����Ľ��
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
			logger.error("�޷��������" + e.getMessage());
		}
	}
}
