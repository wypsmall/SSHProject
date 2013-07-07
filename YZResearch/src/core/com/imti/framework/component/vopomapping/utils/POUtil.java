package com.imti.framework.component.vopomapping.utils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.imti.framework.common.DateUtil;
import com.imti.framework.component.vopomapping.constant.DataType;
import com.imti.framework.component.vopomapping.constant.VOMappingExceptionConstant;
import com.imti.framework.component.vopomapping.vomapping.ManyToMany;
import com.imti.framework.component.vopomapping.vomapping.OneToOne;
import com.imti.framework.component.vopomapping.vomapping.SimpleProperty;
import com.imti.framework.component.vopomapping.vomapping.VOConfig;
import com.imti.framework.component.vopomapping.vomapping.VOMapping;

public class POUtil {
	private static Log log  = LogFactory.getLog(POUtil.class);
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	 public static Object copyVoToPo(Object voBean) {
		 return copyVoToPo(voBean, true);
	 }
	 
    /**
     * 将vo对象的值拷贝到持久对象PO中
     * 1、简单的属性类型 
     * 2、复杂类型（多对多等）
     * @param vo 值对象
     * @return 持久层对象
     */
    public static Object copyVoToPo(Object voBean, boolean includeDetail) {
    	/*
    	 * 1、根据vo实例与mapping文件,实例化po
    	 * 2、根据vo实例与mapping文件,得到vo与po映射的实体voConfig
    	 * 3、对简单的属性进行拷贝
    	 * 4、对复杂的类型进行拷贝
    	 */
    	if(voBean == null ){
        	return null;
        }
    	String voClassName = voBean.getClass().getName();
    	VOConfig voConfig = VOMapping.getVOConfigByVoName(voClassName);
    	if(voConfig == null){
    		throw new RuntimeException(voClassName + ":" + VOMappingExceptionConstant.VOMAPPING_PROPERTY_BEAN_NOT_CONFIGURATION);
    	}
    	Object poBean = getInstance(voConfig.getPoClass());
    	
    	try{
    		//3、简单类型进行拷贝 propertyMap是SimpleProperty的集合
        	Map propertyMap = voConfig.getProperties();
        	Iterator it = propertyMap.values().iterator();
    		while(it.hasNext()){
        		SimpleProperty simpleProperty = (SimpleProperty)it.next();
        		String voPropertyName = simpleProperty.getVoProperty();
        		String poPropertyName = simpleProperty.getPoProperty();
        		String poPropertyType = simpleProperty.getPoType();
        		Object voValue = MethodUtils.invokeMethod(voBean, "get" + upperFirst(voPropertyName), null);
        		
        		int idx = poPropertyName.indexOf('.');
        		if(idx != -1){//po中的属性是一个po对象,如：user.address
        			if(voValue != null && !"".equals(voValue.toString())){
        				Object po = poBean;
						do {
							String propertyName = upperFirst(poPropertyName.substring(0, idx));
							Class poClass = po.getClass();
							Method propertyGetter = poClass.getMethod("get" + propertyName, null);
							Object property = propertyGetter.invoke(po, null);//得到po属性对象的get方法返回值
							if (property == null) {
								Class propertyClass = propertyGetter.getReturnType();//po中属性的类型（对象）
								if(propertyClass.getSimpleName().equals("Long")){
									property = null;
								}else{
									property = propertyClass.newInstance();
								}
								Method propertySetter = poClass.getMethod("set" + propertyName,new Class[] { propertyClass });
								propertySetter.invoke(po,new Object[] { property });
							}
							po = property;
							poPropertyName = poPropertyName.substring(idx + 1);
							idx = poPropertyName.indexOf('.');
						} while (idx != -1);
						Object poValue = toDestValue(voValue, poPropertyType);
						BeanUtils.setProperty(po, poPropertyName,poValue);
					}
        		}else{//po是简单的属性
        			Object poValue = toDestValue(voValue, poPropertyType);
            		BeanUtils.setProperty(poBean, poPropertyName, poValue);
        		}
        	}
    		
    		if(includeDetail){
    			//4 one-to-one 拷贝
    			List oneToOneList = voConfig.getOneList();
    			Iterator itOneToOne = oneToOneList.iterator();
    			while(itOneToOne.hasNext()){
    				OneToOne oneToOne = (OneToOne)itOneToOne.next();
    				String voPropertyName = oneToOne.getVoProperty();
    				String poProertyName = oneToOne.getPoProperty();
    				Object voProperty = MethodUtils.invokeMethod(voBean, "get" + upperFirst(voPropertyName), null);
        			if(voProperty != null){
        				Object poProperty = copyVoToPo(voProperty);//po对象
        				Method propertySetter = poBean.getClass().getMethod("set" + upperFirst(poProertyName),new Class[] { poProperty.getClass() });
						propertySetter.invoke(poBean,new Object[] { poProperty });
        			}
        		}
    			
    			//5、many-to-many 复杂类型的值拷贝
    			List manyToMany = voConfig.getManyManys();
    			Iterator itManyToMany = manyToMany.iterator();
        		while(itManyToMany.hasNext()){
        			Collection voList = null;
        			Collection poList = null;
        			ManyToMany many = (ManyToMany)itManyToMany.next();
    				voList = (Collection) MethodUtils.invokeMethod(voBean, "get" + upperFirst(many.getVoProperty()), null);
    				poList = (Collection) MethodUtils.invokeMethod(poBean, "get" + upperFirst(many.getPoProperty()), null);
        			if(voList != null && !voList.isEmpty()){
        				Iterator iter = voList.iterator();
        				while (iter.hasNext()) {
        					poList.add(copyVoToPo(iter.next(), true));
        				}
        			}
        		}
    		}
    	}catch(Exception ex){
    		log.error(ex);
    		throw new RuntimeException(ex);
    	}
    	return poBean;
    }

   

    /**
     * 将实体层对象copy至值对象
     * @param po 实体层对象
     * @param vo vo对象
     * @return 转换后的值对象
     */

    public static Object copyPoToVO(Object poBean){
    	return copyPoToVO(poBean, true);
    }

    /**
     * 将实体层对象copy至值对象
     * @param po 实体层对象
     * @param vo vo对象
     * @return 转换后的值对象
     */
    public static Object copyPoToVO(Object poBean, boolean includeDetail) {
        if(poBean == null ){
        	return null;
        }
    	
    	String poClassName = poBean.getClass().getName();
    	VOConfig voConfig = VOMapping.getVOConfigByPoName(poClassName);
    	if(voConfig == null){
    		throw new RuntimeException(poClassName + ":" + VOMappingExceptionConstant.VOMAPPING_PROPERTY_BEAN_NOT_CONFIGURATION);
    	}
    	Object voBean = getInstance(voConfig.getVoClass());

    	try{
    		//3、简单类型进行拷贝 propertyMap是SimpleProperty的集合
        	Map propertyMap = voConfig.getProperties();
        	Iterator it = propertyMap.values().iterator();
    		while(it.hasNext()){
        		SimpleProperty simpleProperty = (SimpleProperty)it.next();
        		String voPropertyName = simpleProperty.getVoProperty();
        		String voPropertyType = simpleProperty.getVoType();
        		String poPropertyName = simpleProperty.getPoProperty();
        		if(poPropertyName.indexOf(".")!=-1){
        			Object parent = BeanUtils.getProperty(poBean, poPropertyName.substring(0, poPropertyName.indexOf(".")));
        			if(parent == null){
        				continue;
        			}
        		}
        		Object poValue = BeanUtils.getProperty(poBean, poPropertyName);
        		Object voValue = toDestValue(poValue, voPropertyType);
        		BeanUtils.setProperty(voBean, voPropertyName, voValue);
        	}
    		
    		if(includeDetail){
    			//4 one-to-one 拷贝
    			List oneToOneList = voConfig.getOneList();
    			Iterator itOneToOne = oneToOneList.iterator();
    			while(itOneToOne.hasNext()){
    				OneToOne oneToOne = (OneToOne)itOneToOne.next();
    				String voPropertyName = oneToOne.getVoProperty();
    				String poProertyName = oneToOne.getPoProperty();
    				Object poProperty = MethodUtils.invokeMethod(poBean, "get" + upperFirst(poProertyName), null);
        			if(poProperty != null){
        				Object voProperty = copyPoToVO(poProperty);//po对象
        				Method propertySetter = voBean.getClass().getMethod("set" + upperFirst(voPropertyName),new Class[] { voProperty.getClass() });
						propertySetter.invoke(voBean,new Object[] { voProperty });
        			}
        		}
    			
    			
    			
    			//5、many-to-many 复杂类型的值拷贝
    			List manyToMany = voConfig.getManyManys();
    			Iterator itManyToMany = manyToMany.iterator();
        		while(itManyToMany.hasNext()){
        			Collection voList = null;
        			Collection poList = null;
        			ManyToMany many = (ManyToMany)itManyToMany.next();
    				voList = (Collection) MethodUtils.invokeMethod(voBean, "get" + upperFirst(many.getVoProperty()), null);
    				poList = (Collection) MethodUtils.invokeMethod(poBean, "get" + upperFirst(many.getPoProperty()), null);
        			if(poList != null && !poList.isEmpty()){
        				Iterator iter = poList.iterator();
        				while (iter.hasNext()) {
        					voList.add(copyPoToVO(iter.next(), true));
        				}
        			}
        		}
    		}
    		
    		
    	}catch(Exception ex){
    		log.error(ex);
    		throw new RuntimeException(ex);
    	}
    	return voBean;
    }
    public static List copyPoListToVoList(List poList) {
    	List voList = new ArrayList();
    	if(poList == null || poList.isEmpty()){
    		return voList;
    	}
    	Object poBean = poList.get(0);
    	String poClassName = poBean.getClass().getName();
    	VOConfig voConfig = VOMapping.getVOConfigByPoName(poClassName);
    	
        for (int idx =0; idx< poList.size(); idx++){
        	Object voBean = copyPoToVO(poList.get(idx), true);
            voList.add(voBean);
        }
        return voList;
    }
    public static List copyVoListToPoList(List voList) {
    	List poList = new ArrayList();
    	if (voList == null || voList.isEmpty()){
    		return poList;
    	}
        for (int idx =0; idx< voList.size(); idx++){
        	poList.add(copyVoToPo(voList.get(idx), true));
        }
        return poList;
    }
    
    private static Object getInstance(String className){
    	try {
			return Class.forName(className).newInstance();
    	} catch (InstantiationException e) {
         	log.error(e.getMessage());
         	throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
         	log.error(e.getMessage());
         	throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
         	log.error(e.getMessage());
         	throw new RuntimeException(e);
        }
    }
    /**
     * 将首字母换为置为大写
     */
    private static String upperFirst(String str){
    	if (str == null || str.length() == 0 || Character.isUpperCase(str.charAt(0))) {
    	    return str;
    	}
    	char chars[] = str.toCharArray();
    	chars[0] = Character.toUpperCase(chars[0]);
    	return new String(chars);
    }
    /**
     * 将VO属性的值转化为PO属性的值
     */

    private static Object toDestValue(Object value,String type){
    	/*BigDecimal类型数据容错 2010-06-30 曹刚*/
    	if(null == value && (DataType.DECIMAL.equals(type) || DataType.CDECIMAL.equals(type) || 
        		DataType.BIGDECIMAL.equals(type) || DataType.CBIGDECIMAL.equals(type))){
    		return new BigDecimal("0");
    	}
    	
    	if(value == null){
    		return null;
    	}
    	if(DataType.STRING.equals(type) || DataType.CSTRING.equals(type)){
    		if(value.toString().indexOf(" CST ")!=-1){
    			return DateUtil.DateTOStr(new Date(value.toString()));
    		}
    		return value.toString();
    	}else if( DataType.INT.equals(type) || DataType.INTEGER.equals(type) || DataType.CINTEGER.equals(type)){
        	if(StringUtils.isEmpty(value.toString())){
        		throw new RuntimeException(VOMappingExceptionConstant.VOMAPPING_DATA_INVALID_NULL_TO_INT);
        	}
        	return Integer.valueOf(value.toString()); 
        }else if(DataType.LONG.equals(type) || DataType.CLONG.equals(type)){
        	if(StringUtils.isEmpty(value.toString())){
        		throw new RuntimeException(VOMappingExceptionConstant.VOMAPPING_DATA_INVALID_NULL_TO_INT);
        	}
        	return Long.valueOf(value.toString());
        }else if(DataType.SHORT.equals(type) || DataType.CSHORT.equals(type)){
        	if(StringUtils.isEmpty(value.toString())){
        		throw new RuntimeException(VOMappingExceptionConstant.VOMAPPING_DATA_INVALID_NULL_TO_INT);
        	}
        	return Short.valueOf(value.toString());
        }else if(DataType.BOOLEAN.equals(type)){        	
        	return Boolean.valueOf("true".equals(value));
        }else if(DataType.FLOAT.equals(type) || DataType.CFLOAT.equals(type)){
        	if(StringUtils.isEmpty(value.toString())){
        		return Float.valueOf("0");
        	}
        	return Float.valueOf(value.toString());
        }else if(DataType.DOUBLE.equals(type) || DataType.CDOUBLE.equals(type)){
        	if(StringUtils.isEmpty(value.toString())){
        		return Double.valueOf("0");
        	}
        	return Double.valueOf(value.toString());
        }else if(DataType.DECIMAL.equals(type) || DataType.CDECIMAL.equals(type) || 
        		DataType.BIGDECIMAL.equals(type) || DataType.CBIGDECIMAL.equals(type)){
        	if(StringUtils.isEmpty(value.toString())){
        		return new BigDecimal("0");
        	}
        	return new BigDecimal(value.toString());
        }else if(DataType.DATE.equals(type)	|| DataType.CDATE.equals(type)){
        	if(StringUtils.isEmpty(value.toString())){
        		return null;
//        		throw new RuntimeException(VOMappingExceptionConstant.VOMAPPING_DATA_INVALID_NULL_TO_DATE);
        	}
        	
        	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			try {
				Date date = simpleDateFormat.parse(String.valueOf(value));
				return date;
			} catch (ParseException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}    
        }else if(DataType.TIME.equals(type) || DataType.CTIME.equals(type)){
        	if(StringUtils.isEmpty(value.toString())){
        		return null;
//        		throw new RuntimeException(VOMappingExceptionConstant.VOMAPPING_DATA_INVALID_NULL_TO_TIME);
        	}
        	String str = value.toString(); 
        	/**
        	 * hh:mm:ss
        	 */
        	if(str.length()==5){
        		str += ":00";
        	}
        	return Time.valueOf(str);
        }else if(DataType.TIMESTMAP.equals(type) || DataType.CTIMESTMAP.equals(type)){
        	if(StringUtils.isEmpty(value.toString())){
        		return null;
//        		throw new RuntimeException(VOMappingExceptionConstant.VOMAPPING_DATA_INVALID_NULL_TO_TIME);
        	}
        	String str = value.toString();
        	/**
        	 * yyyy-mm-dd hh:mm:ss
        	 */
        	if(str.length()==16){
        		str += ":00";
        	}
        	return Timestamp.valueOf(str);
        }else if(DataType.CLOB.equals(type)){
        	return Hibernate.createClob(String.valueOf(value));
        }else if(DataType.BLOB.equals(type)){
        	return Hibernate.createBlob((byte[])value);
        }else if(DataType.BYTES.equals(type) || DataType.CBYTES.equals(type)){
        	return (byte[])value;
        }else{
        	return value;
        }
    }
    public static void main(String[] args){
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		try {
			Date date = simpleDateFormat.parse("2010-06-23");
			System.out.println(DateUtil.DateTOStr(date));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}   
    }
}
