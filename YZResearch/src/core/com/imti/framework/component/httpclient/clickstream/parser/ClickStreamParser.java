package com.imti.framework.component.httpclient.clickstream.parser;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;

import com.imti.framework.component.httpclient.clickstream.module.Click;
import com.imti.framework.component.httpclient.clickstream.module.ClickStream;
import com.imti.framework.component.vopomapping.utils.PropertyUtils;

public class ClickStreamParser {
	private static Map clickStreamMappings = new HashMap();
	static{
		String mappingNames = PropertyUtils.getProperty("imti.httpclient.clickstream.config");
		String [] names = mappingNames.split(",");		
		for(int i=0;i<names.length;i++)	{	
			initConfig(names[i]);
		}
	}
	public  void addClickStream(ClickStream clickStream) {
		clickStreamMappings.put(clickStream.getClickStreamId(), clickStream);
	}
	
	public Map getClickStreams(){
		return clickStreamMappings;
	}
	public static ClickStream getClickStream(String id){
		if(StringUtils.isEmpty(id)){
			return null;
		}
		return (ClickStream)clickStreamMappings.get(id);
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
		digester.addObjectCreate("httpclient/clickstream", ClickStream.class);
		digester.addSetProperties("httpclient/clickstream");
		digester.addObjectCreate("httpclient/clickstream/click",Click.class);
		digester.addSetProperties("httpclient/clickstream/click");
		digester.addSetNext("httpclient/clickstream/click", "addClickStream");
		digester.addSetNext("httpclient/clickstream", "addHeader");

	}
}
