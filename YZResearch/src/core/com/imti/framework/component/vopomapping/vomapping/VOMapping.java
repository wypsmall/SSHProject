package com.imti.framework.component.vopomapping.vomapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.imti.framework.component.vopomapping.constant.VOMappingExceptionConstant;
import com.imti.framework.component.vopomapping.utils.PropertyUtils;

public class VOMapping {
	private static Log log  = LogFactory.getLog(VOMapping.class);
	/**
	 * 以vo类的全限定名为key保存VOConfig对象
	 */
	private static Map voMappings = new HashMap();
	/**
	 * 以po类的全限定名为key保存VOConfig对象
	 */
	private static Map poMappings = new HashMap();
	
	static{
		String vomappingNames=PropertyUtils.getProperty("vomapping.config.names");
		String [] names=vomappingNames.split(",");		
		
		for(int i=0;i<names.length;i++)	{	
			initConfig(names[i]);
		}
	}
	public  void addMapping(VOConfig voClass) {
		voMappings.put(voClass.getVoClass(),voClass);
		poMappings.put(voClass.getPoClass(), voClass);
	}
	
	public static Map getVoMappings(){
		return Collections.unmodifiableMap(voMappings);
	}
	
	public static List getVOConfigList(){
		return new ArrayList(voMappings.values());
	}
	
	/**
	 * 初始化
	 */
	public static void initConfig(String name) {
		Digester digester = new Digester();
		digester.push(new VOMapping());
		addRules(digester);
		try {
			digester.parse(VOMapping.class.getResourceAsStream(name));
		} catch (java.io.IOException ioe) {
			log.error(ioe);
			throw new RuntimeException(name + ":" + VOMappingExceptionConstant.VOMAPPING_XML_CONFIGURATION_FILE_NOT_FOUND);
		} catch (org.xml.sax.SAXException se) {
			log.error(se);
			throw new RuntimeException(name + ":" + VOMappingExceptionConstant.VOMAPPING_XML_CONFIGURATION_FILE_INVALID);
		}
		digester.clear();

	}
	
	
	/**
	 * 加入验证规则 
	 * @param digester 解析器对象
	 */
	private static void addRules(Digester digester) {
		digester.addObjectCreate("vo-po-mapping/class", VOConfig.class);
		digester.addSetProperties("vo-po-mapping/class");
		digester.addObjectCreate("vo-po-mapping/class/property",SimpleProperty.class);
		digester.addSetProperties("vo-po-mapping/class/property");
		digester.addSetNext("vo-po-mapping/class/property", "addProperty");
		
		digester.addObjectCreate("vo-po-mapping/class/one-one",OneToOne.class);
		digester.addSetProperties("vo-po-mapping/class/one-one");
		digester.addSetNext("vo-po-mapping/class/one-one", "addOneToOne");

		digester.addObjectCreate("vo-po-mapping/class/many-many",ManyToMany.class);
		digester.addSetProperties("vo-po-mapping/class/many-many");
		digester.addSetNext("vo-po-mapping/class/many-many", "addManyToMany");
		
		digester.addSetNext("vo-po-mapping/class", "addMapping");

	}
	
	/**
	 * 二分查找算法(解决性能问题)
	 * @param voClassName vo的名字
	 * @return vo配置信息对象
	 */
	public static VOConfig getVOConfigByVoName(String voClassName){
		return (VOConfig) voMappings.get(voClassName);
	}

	
	/**
	 * 二分查找算法(解决性能问题)
	 * @param poClassName vo的名字
	 * @return vo配置信息对象
	 */
	public static VOConfig getVOConfigByPoName(String poClassName){
		return (VOConfig) poMappings.get(poClassName);
	}
	
	
	/**
	 * 判断是否是主键，可能在后期的查询中用到
	 * @param cfg
	 * @param name
	 * @return
	 */
	public static boolean isPrimary(VOConfig cfg, String name){
		SimpleProperty vop = cfg.getSimpleProperty(name);
		return vop!=null && "true".equals(vop.getPrimary());
	}
	
	public static SimpleProperty getSimpleProperty(VOConfig cfg,String name){
		return cfg.getSimpleProperty(name);
	}
}
