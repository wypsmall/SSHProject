package com.imti.framework.common;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;

import com.imti.framework.component.vopomapping.constant.DataType;
import com.imti.framework.component.vopomapping.constant.VOMappingExceptionConstant;
import com.imti.framework.component.vopomapping.vomapping.SimpleProperty;
import com.imti.framework.component.vopomapping.vomapping.VOConfig;
import com.imti.framework.component.vopomapping.vomapping.VOMapping;
import com.imti.framework.web.exception.BeanException;
import com.imti.framework.web.exception.ConException;
import com.imti.framework.web.exception.constant.ExceptionConstant;
import com.imti.framework.web.vo.BaseVO;

public class BeanUtil {
	
	public static Object toDestValue(Object value,String type){   
    	if(value == null){
    		return null;
    	}
    	if(DataType.STRING.toLowerCase().equals(type.toLowerCase())){
    		return value;
    	}else if( DataType.INT.toLowerCase().equals(type.toLowerCase()) || 
    			DataType.INTEGER.toLowerCase().equals(type.toLowerCase())){
        	if(StringUtils.isEmpty(value.toString())){
        		throw new RuntimeException(VOMappingExceptionConstant.VOMAPPING_DATA_INVALID_NULL_TO_INT);
        	}
        	return Integer.valueOf(value.toString()); 
        }else if(DataType.LONG.toLowerCase().equals(type.toLowerCase())){
        	if(StringUtils.isEmpty(value.toString())){
        		throw new RuntimeException(VOMappingExceptionConstant.VOMAPPING_DATA_INVALID_NULL_TO_INT);
        	}
        	return Long.valueOf(value.toString());
        }else if(DataType.SHORT.toLowerCase().equals(type.toLowerCase())){
        	if(StringUtils.isEmpty(value.toString())){
        		throw new RuntimeException(VOMappingExceptionConstant.VOMAPPING_DATA_INVALID_NULL_TO_INT);
        	}
        	return Short.valueOf(value.toString());
        }else if(DataType.BOOLEAN.toLowerCase().equals(type.toLowerCase())){        	
        	return Boolean.valueOf("true".equals(value));
        }else if(DataType.FLOAT.toLowerCase().equals(type.toLowerCase())){
        	if(StringUtils.isEmpty(value.toString())){
        		throw new RuntimeException(VOMappingExceptionConstant.VOMAPPING_DATA_INVALID_NULL_TO_FLOAT);
        	}
        	return Float.valueOf(value.toString());
        }else if(DataType.DOUBLE.toLowerCase().equals(type.toLowerCase())){
        	if(StringUtils.isEmpty(value.toString())){
        		throw new RuntimeException(VOMappingExceptionConstant.VOMAPPING_DATA_INVALID_NULL_TO_FLOAT);
        	}
        	return Double.valueOf(value.toString());
        }else if(DataType.DECIMAL.toLowerCase().equals(type.toLowerCase()) || 
        		DataType.BIGDECIMAL.toLowerCase().equals(type.toLowerCase())){
        	if(StringUtils.isEmpty(value.toString())){
        		throw new RuntimeException(VOMappingExceptionConstant.VOMAPPING_DATA_INVALID_NULL_TO_FLOAT);
        	}
        	return new BigDecimal(value.toString());
        }else if(DataType.DATE.toLowerCase().equals(type.toLowerCase())){
        	if(StringUtils.isEmpty(value.toString())){
        		throw new RuntimeException(VOMappingExceptionConstant.VOMAPPING_DATA_INVALID_NULL_TO_DATE);
        	}
         	return Date.valueOf(value.toString());
        }else if(DataType.TIME.toLowerCase().equals(type.toLowerCase())){
        	if(StringUtils.isEmpty(value.toString())){
        		throw new RuntimeException(VOMappingExceptionConstant.VOMAPPING_DATA_INVALID_NULL_TO_TIME);
        	}
        	String str = value.toString(); 
        	/**
        	 * hh:mm:ss
        	 */
        	if(str.length()==5){
        		str += ":00";
        	}
        	return Time.valueOf(str);
        }else if(DataType.TIMESTMAP.toLowerCase().equals(type.toLowerCase())){
        	if(StringUtils.isEmpty(value.toString())){
        		throw new RuntimeException(VOMappingExceptionConstant.VOMAPPING_DATA_INVALID_NULL_TO_TIME);
        	}
        	String str = value.toString();
        	/**
        	 * yyyy-mm-dd hh:mm:ss
        	 */
        	if(str.length()==16){
        		str += ":00";
        	}
        	return Timestamp.valueOf(str);
        }else if(DataType.CLOB.toLowerCase().equals(type.toLowerCase())){
        	return Hibernate.createClob(String.valueOf(value));
        }else if(DataType.BLOB.toLowerCase().equals(type.toLowerCase())){
        	return Hibernate.createBlob((byte[])value);
        }else if(DataType.BYTES.toLowerCase().equals(type.toLowerCase())){
        	return (byte[])value;
        }else{
        	return value;
        }
    }
	/**
	  * 将每行数据组装成为指定的对象
	  * @param row 每行的数据(通过分隔符分割每列)
	  * @param cla 转换的类
	  * @param properties 属性与属性类型数组
	  * @param seperator 每行数据的列分隔符
	  */
	 public static Object createObject(String row, Class cla, String[][] properties, String seperator) throws Exception{
		 Object object = newInstance(cla.getName());
		 if(StringUtils.isBlank(seperator)){
			 seperator = "\\|";
		 }
		 if(!StringUtils.isBlank(row)){
			 String[] proArray = row.split(seperator);
			 if(proArray.length != properties.length){
					throw new RuntimeException("列数不正确！");
			 }else{
				 for(int i = 0; i < proArray.length; i++){
					 createObjectInfo(object, properties[i][0], proArray[i], properties[i][1]);
				 }
			 }
		 }
		 return object;
	 }
	 
	 public static void createObjectInfo(Object object, String proName, String proValue, String proType) throws Exception{
		 
		 try {
			if(proName.indexOf(".") == -1){//普通属性
				Method setMethod = getMethodByName(object, getSetMethodName(proName));
				//得到set方法的参数数据类型
				Object destValue = toDestValue(proValue, proType);
				setMethod.invoke(object, new Object[]{destValue});
			}else{//针对属性是对象类型的情况
				Method getMethod = getMethodByName(object, getGetMethodName(proName.substring(0, proName.indexOf("."))));//获得get方法
				Method setMethod = getMethodByName(object, getSetMethodName(proName.substring(0, proName.indexOf("."))));//获得set方法
				Class cla = getMethod.getReturnType();//方法的返回类型（也即对象类型）
				Object obj = newInstance(cla.getName());//获得对象类型的一份实例
				createObjectInfo(obj, proName.substring(proName.indexOf(".") + 1, proName.length()), proValue, proType);
				setMethod.invoke(object, new Object[]{obj});
			}
			
		} catch (Exception e) {
			throw new BeanException(e);
		}
	 }
	 /**
	 * 根据字符串得到该字符串构成的set方法名
	 * @param propertyName属性名
	 */
	public static String getSetMethodName(String propertyName){
		return "set" + upperFirst(propertyName);
	}
	/**
	 * 根据字符串得到该字符串构成的get方法名
	 * @param propertyName属性名
	 */
	public static String getGetMethodName(String propertyName){
		return "get" + upperFirst(propertyName);
	}
	
	 /**
	 * 找出object对象的指定方法(在公有方法中)
	 * @param Object，String,isGetMethod
	 * @return Method
	 * @throws Exception 
	 * @throws DefaultException 
	 */
	public static Method getMethodByName(Object object, String methodName) throws Exception{
		
		try {
			Method[] methods = object.getClass().getDeclaredMethods();
			if(methods != null){
				for(int i = 0; i < methods.length; i++){
					if(methods[i].getName().equals(methodName)){
						return methods[i];
					}
				}
			}
		} catch (SecurityException e) {
			throw new BeanException("该方法不存在或者该方法为私有的，不可访问！");
		} 
		throw new BeanException("该方法不存在！");
	}
	 
	 /**
     * 将首字母换为置为大写
     */
    public static String upperFirst(String str){
    	if (str == null || str.length() == 0 || Character.isUpperCase(str.charAt(0))) {
    	    return str;
    	}
    	char chars[] = str.toCharArray();
    	chars[0] = Character.toUpperCase(chars[0]);
    	return new String(chars);
    }
    
	 /**
	 * 获得给定全名的类的一份实例
	 * @param className
	 *    给定全名的类的名称
	 */
	public static Object newInstance(String className) {
		Object instance = null;
		try {
			instance = Class.forName(className).newInstance();
		} catch (Exception e) {
			throw new BeanException(e);
		}
		return instance;
	}
	public static Class getPOClass(BaseVO baseVO){
		Class poClass = null;
		try {
			VOConfig voCfg = VOMapping.getVOConfigByPoName(baseVO.getClass().getName());
			if (voCfg == null){
				return null;
			}
			poClass = Class.forName(voCfg.getPoClass());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return poClass;
	}
	public static String getPkField(BaseVO baseVO) {
		VOConfig voCfg = VOMapping.getVOConfigByVoName(baseVO.getClass().getName());
		if (voCfg == null)
			return null;
		SimpleProperty sp = new SimpleProperty();
		Map propertyMap = voCfg.getProperties();
		Iterator it = propertyMap.values().iterator();
		int primaryConut = 0;
		while(it.hasNext()){
			SimpleProperty simpleProperty = (SimpleProperty)it.next();
			String primary = simpleProperty.getPrimary();
			if ("true".equals(primary)) {
				sp = simpleProperty;
				primaryConut ++ ;
			}
			if(primaryConut > 2){
				throw new ConException(ExceptionConstant.IMTI_CONDITION_PRIMARY_MORE_ERROR);
			}
		}
    	if(primaryConut == 0){
    		throw new ConException(ExceptionConstant.IMTI_CONDITION_PRIMARY_NONO_ERROR);
    	}
		return sp.getVoProperty();
	}
}
