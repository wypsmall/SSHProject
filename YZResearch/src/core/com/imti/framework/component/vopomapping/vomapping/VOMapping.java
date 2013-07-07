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
	 * ��vo���ȫ�޶���Ϊkey����VOConfig����
	 */
	private static Map voMappings = new HashMap();
	/**
	 * ��po���ȫ�޶���Ϊkey����VOConfig����
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
	 * ��ʼ��
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
	 * ������֤���� 
	 * @param digester ����������
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
	 * ���ֲ����㷨(�����������)
	 * @param voClassName vo������
	 * @return vo������Ϣ����
	 */
	public static VOConfig getVOConfigByVoName(String voClassName){
		return (VOConfig) voMappings.get(voClassName);
	}

	
	/**
	 * ���ֲ����㷨(�����������)
	 * @param poClassName vo������
	 * @return vo������Ϣ����
	 */
	public static VOConfig getVOConfigByPoName(String poClassName){
		return (VOConfig) poMappings.get(poClassName);
	}
	
	
	/**
	 * �ж��Ƿ��������������ں��ڵĲ�ѯ���õ�
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
