package com.imti.framework.common;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

public class ImtiListUtil {
	/**
	 * <li>�ж��б��Ƿ�Ϊ��
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List list){
		if(list == null || list.isEmpty()){
			return true;
		}
		return false;
	}
	
	/**
	 * <li>�ж��б��Ƿ�Ϊ��
	 * @param list
	 * @return
	 */
	public static boolean isNotEmpty(List list){
		if(list != null && !list.isEmpty()){
			return true;
		}
		return false;
	}
	
	
	/**
	  * ��һ��list����ת����set����
	  * @param list
	  * @return
	  * @throws RuntimeException
	  */
	public static Set listToSet(List list){
		Set set = new LinkedHashSet();
		if(isNotEmpty(list)){
			for(int i = 0; i < list.size(); i++){
				set.add(list.get(i));
			}
		}
		return set;
	}
	/**
	  * ��ȡ�б�ĳ��������Ϊ�������鷵��
	  * @param list
	  * @param property
	  * @return
	  * @throws RuntimeException
	  */
	 public static Object [] getPropertyArray(List list,String property) {
		 Object [] ary = new Object[list.size()];
		 for (int i = 0; i < list.size(); i++){
			 try {
				 ary[i] = BeanUtils.getProperty(list.get(i), property);
			 } catch (IllegalAccessException e) {
				 throw new RuntimeException(e);
			 } catch (InvocationTargetException e) {
				 throw new RuntimeException(e);
			 } catch (NoSuchMethodException e) {
				 throw new RuntimeException(e);
			 }
		 }
	  return ary;
	 }
	 
	 /**
	  * ��ȡ�б�ĳ��������Ϊ�ַ������鷵��
	  * @param list
	  * @param property
	  * @return
	  * @throws RuntimeException
	  */
	 public static String [] getPropertyStringArray(List list,String property) {
		  Object [] objectAry = getPropertyArray(list, property);
		  String [] stringAry = new String[objectAry.length];
		  System.arraycopy(objectAry, 0, stringAry, 0, objectAry.length);
		  return stringAry;
	 }
	 /**
	  * ���ָ��BEANĳ������ֵ
	  * @param bean
	  * @param property
	  * @return
	  * @throws RuntimeException
	  */
	 public static Object getProperty(Object bean, String property){
		 Object value = null;
		 try {
			 value = BeanUtils.getProperty(bean, property);
		 } catch (IllegalAccessException e) {
			 e.printStackTrace();
			 throw new RuntimeException(e);
		 } catch (InvocationTargetException e) {
			 e.printStackTrace();
			 throw new RuntimeException(e);
		 } catch (NoSuchMethodException e) {
			 e.printStackTrace();
			 throw new RuntimeException(e);
		 }
		 return value;
	 }
	 /**
	  * ����bean������ֵ
	  * @param list
	  * @param property
	  * @param value
	  * @throws RuntimeException
	  */
	 public static void setProperty(Object bean, String property, Object value) throws RuntimeException{
		 try {
			 BeanUtils.setProperty(bean, property, value);
		 } catch (IllegalAccessException e) {
			 throw new RuntimeException(e);
		 } catch (InvocationTargetException e) {
			 throw new RuntimeException(e);
		 } 
	 }
}
