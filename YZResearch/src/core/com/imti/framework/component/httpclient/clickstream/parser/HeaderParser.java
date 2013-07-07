package com.imti.framework.component.httpclient.clickstream.parser;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;

import com.imti.framework.component.httpclient.clickstream.module.BaseModel;
import com.imti.framework.component.httpclient.clickstream.module.Header;
import com.imti.framework.component.vopomapping.utils.PropertyUtils;

public class HeaderParser {
	@SuppressWarnings("unchecked")
	private static Map headerMappings = new HashMap();
	static{
		String mappingNames = PropertyUtils.getProperty("imti.httpclient.config.files");
		String [] names = mappingNames.split(",");		
		for(int i=0;i<names.length;i++)	{	
			initConfig(names[i]);
		}
	}
	@SuppressWarnings("unchecked")
	public  void addHeader(Header header) {
		headerMappings.put(header.getHeaderId(), header.getHeaderMap());
	}
	
	@SuppressWarnings("unchecked")
	public static Map getHeaders(){
		return headerMappings;
	}
	public static Header getHeader(String id){
		if(StringUtils.isEmpty(id)){
			return null;
		}
		Object object = headerMappings.get(id);
		if(object != null){
			return (Header)object;
		}
		return null;
	}
	/**
	 * 初始化
	 */
	public static void initConfig(String name) {
		Digester digester = new Digester();
		digester.push(new HeaderParser());
		addRules(digester);
		try {
			digester.parse(HeaderParser.class.getResourceAsStream(name));
		} catch (java.io.IOException ioe) {
			ioe.printStackTrace();
		} catch (org.xml.sax.SAXException se) {
			se.printStackTrace();
		}
		digester.clear();
	}
	
	
	/**
	 * 加入验证规则 
	 * @param digester 解析器对象
	 */
	private static void addRules(Digester digester) {
		digester.addObjectCreate("httpclient/header", Header.class);
		digester.addSetProperties("httpclient/header");
		digester.addObjectCreate("httpclient/header/model",BaseModel.class);
		digester.addSetProperties("httpclient/header/model");
		digester.addSetNext("httpclient/header/model", "addBaseModel");
		digester.addSetNext("httpclient/header", "addHeader");

	}
}
