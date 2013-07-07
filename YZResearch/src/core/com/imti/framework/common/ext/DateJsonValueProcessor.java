package com.imti.framework.common.ext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;


/**
 * json-lib对日期的默认解析在输出成JSON时会有问题。 
 * 
 * json-lib-2.2可以为对应的class注册解析类，一定要2.2，2.1有同步问题
 * 本类实现对Date类型属性的解析
 * 
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	private DateFormat dateFormat;

	public DateJsonValueProcessor(String datePattern) {
		try {
			dateFormat = new SimpleDateFormat(datePattern);
		} catch (Exception ex) {
			dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		}
	}

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return process(value);
	}

	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		return process(value);
	}

	private Object process(Object value) {
		if(value==null){
			return "";
		}
		return dateFormat.format((Date) value);
	}

}

