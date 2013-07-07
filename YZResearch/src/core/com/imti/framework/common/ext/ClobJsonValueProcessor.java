package com.imti.framework.common.ext;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import com.imti.framework.web.log.ExtLog;

public class ClobJsonValueProcessor implements JsonValueProcessor{
	private ExtLog logger = new ExtLog(ClobJsonValueProcessor.class);
	
	public Object processArrayValue(Object value, JsonConfig arg1) {
		return process(value);
	}

	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		return process(value);
	}
	
	
	private Object process(Object value) {
		Clob clob=(Clob)value;
		if(clob==null){
			return "";
		}
		StringBuilder sb=new StringBuilder();
		Reader reader;
		try {
			reader = clob.getCharacterStream();
			BufferedReader br=new BufferedReader(reader);
			String line=br.readLine();
			while(line!=null){
				sb.append(line);
				line=br.readLine();
			}
		} catch (Exception e) {
			 logger.error("获取Clob数据出错"+e.getMessage());
		}  
		return sb.toString();
	}
}
