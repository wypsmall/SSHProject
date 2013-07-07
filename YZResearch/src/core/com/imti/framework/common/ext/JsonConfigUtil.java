package com.imti.framework.common.ext;

import java.util.Date;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
/**
 * 本类用于生成JSON-lib解析配置的内容
 * @author Administrator
 *
 */
public class JsonConfigUtil {

	/**
	 * 配置json-lib需要的excludes和datePattern.
	 * 
	 * @param excludes 不需要转换的属性数组
	 * @param datePattern 日期转换模式
	 * @return JsonConfig 根据excludes和dataPattern生成的jsonConfig，用于write
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
	 * 生成对日期进行处理的JSON配置
	 * @param datePattern 日期转换模式
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