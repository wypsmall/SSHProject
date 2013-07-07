package com.imti.framework.common.ext;

import java.util.Date;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
/**
 * ������������JSON-lib�������õ�����
 * @author Administrator
 *
 */
public class JsonConfigUtil {

	/**
	 * ����json-lib��Ҫ��excludes��datePattern.
	 * 
	 * @param excludes ����Ҫת������������
	 * @param datePattern ����ת��ģʽ
	 * @return JsonConfig ����excludes��dataPattern���ɵ�jsonConfig������write
	 */
	public static JsonConfig configJson(String[] excludes, String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor(datePattern));
		jsonConfig.registerJsonValueProcessor(java.sql.Clob.class,
				new ClobJsonValueProcessor());
		return jsonConfig;
	}
	/**
	 * ���ɶ����ڽ��д����JSON����
	 * @param datePattern ����ת��ģʽ
	 * @return
	 */
	public static JsonConfig configJson(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor(datePattern));
		jsonConfig.registerJsonValueProcessor(java.sql.Clob.class,
				new ClobJsonValueProcessor());
		return jsonConfig;
	}
	
}