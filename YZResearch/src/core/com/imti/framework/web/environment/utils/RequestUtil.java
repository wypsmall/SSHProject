package com.imti.framework.web.environment.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.imti.framework.common.BeanUtil;
import com.imti.framework.common.StringUtils;
import com.imti.framework.component.vopomapping.constant.VOMappingExceptionConstant;
import com.imti.framework.component.vopomapping.vomapping.SimpleProperty;
import com.imti.framework.component.vopomapping.vomapping.VOConfig;
import com.imti.framework.component.vopomapping.vomapping.VOMapping;
import com.imti.framework.web.exception.constant.ExceptionConstant;
import com.imti.framework.web.log.ExtLog;
import com.imti.framework.web.vo.BaseVO;

public class RequestUtil {

	private static ExtLog log  = new ExtLog(RequestUtil.class);
	private static HttpServletRequest request;
	private static HttpServletResponse response;
	
	public static void init(HttpServletRequest _request, HttpServletResponse _response){
		request = _request;
		response = _response;
	}
	public static String getPropertyValue(String property){
		String value = request.getParameter(property);
		if(StringUtils.isBlank(value)){
			return "";
		}
		return value;
	}
	
	public static BaseVO getBaseVO(String voClassName){
		BaseVO baseVO = newBaseVOInstance(voClassName);
		VOConfig voConfig = VOMapping.getVOConfigByVoName(voClassName);
		if(voConfig == null){
			throw new RuntimeException(voClassName + ":" + VOMappingExceptionConstant.VOMAPPING_PROPERTY_BEAN_NOT_CONFIGURATION);
		}else{
			//只对vo的简单数据类型进行请求
			Map propertyMap = voConfig.getProperties();
			Iterator it = propertyMap.values().iterator();
			try {
				while(it.hasNext()){
		    		SimpleProperty simpleProperty = (SimpleProperty)it.next();
		    		String voPropertyName = simpleProperty.getVoProperty();
		    		String voPropertyType = simpleProperty.getVoType();
		    		String obj = request.getParameter(voPropertyName);
		    		if(obj != null){
		    			//TODO 编码处理
		    			Object voValue = BeanUtil.toDestValue(obj, voPropertyType);
						BeanUtils.setProperty(baseVO, voPropertyName, voValue);
		    		}
				}	
			} catch (IllegalAccessException e) {
				log.error(e);
			} catch (InvocationTargetException e) {
				log.error(e);
			}
		}
		return baseVO;
	}
	private static BaseVO newBaseVOInstance(String className) {
		BaseVO instance = null;
		try {
			instance = (BaseVO)Class.forName(className).newInstance();
		} catch (Exception e) {
			log.error(ExceptionConstant.IMTI_VO_EXTENDS_ERROR);
		}
		return instance;
	}
	
	public static HttpServletRequest getRequest(){
		return request;
	}
	
	public static HttpServletResponse getResponse(){
		return response;
	}
}
