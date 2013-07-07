package com.imti.framework.component.condition.parser;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.imti.framework.component.vopomapping.utils.PropertyUtils;


public class ConditionParseFactory {

	private static Log log  = LogFactory.getLog(ConditionParseFactory.class);
	private static final String DEFAULT_PARSER = "com.imti.framework.compenent.condition.parser.DefaultConditionParse";
	public static ConditionParse createParse(){
		ConditionParse parseImpl = null;
		String className = PropertyUtils.getProperty("mti.condition.parser", DEFAULT_PARSER);
		if(StringUtils.isEmpty(className)){
			return new DefaultConditionParse();
		}
		try{
			parseImpl = (ConditionParse) Class.forName(className).newInstance();
		}catch(InstantiationException e){
			log.error("createParse",e);
		}catch(IllegalAccessException e){
			log.error("createParse",e);
		}catch(ClassNotFoundException e){
			log.error("createParse",e);
		}
		return parseImpl;
	}
}
