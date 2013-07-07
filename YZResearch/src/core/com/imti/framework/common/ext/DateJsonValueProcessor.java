package com.imti.framework.common.ext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;


/**
 * json-lib�����ڵ�Ĭ�Ͻ����������JSONʱ�������⡣ 
 * 
 * json-lib-2.2����Ϊ��Ӧ��classע������࣬һ��Ҫ2.2��2.1��ͬ������
 * ����ʵ�ֶ�Date�������ԵĽ���
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

