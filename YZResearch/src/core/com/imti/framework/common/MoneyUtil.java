package com.imti.framework.common;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

public class MoneyUtil {
	/**
	  * 格式化金额如一千元可格式为:1000.00
     * @author 曹刚
     * @param moneyList 列表
     * @param property 金额的属性名
     * @param type 列表VO的类型
     * @return double
     * @throws RuntimeException
     */
	 public static String formatMoney(String money){
		  /* 另一种实现方法(供参考)
		   * DecimalFormat df = new DecimalFormat("0.00"); 
		   * return df.format(Double.parseDouble(money));
		   */
		 int point=money.indexOf('.');
		 int len=money.length();
		 if (point>-1){
			 int decNum=len-point-1; //小数个数
			 if (decNum<2){
				 //补小数
				 for (int i=0;i<2-decNum;i++){
					 money=money+"0";
				 }
			 }else if (decNum>2){
				 //减小数
				 money=money.substring(0,point+3);
			 }
		  }else{
		   money=money+".00";
		  }
		 return money;
	 }
	 
	 /**
	  * 通用的合计金额的方法
     * @author 曹刚
     * @param moneyList 列表
     * @param property 金额的属性名
     * @param type 列表VO的类型
     * @return BigDecimal
     * @throws RuntimeException
     */
	 public static BigDecimal sumMoney(List moneyList,String property,Class type) throws RuntimeException {
		 BigDecimal sum=new BigDecimal("0");
		 String getMethodName=getGetterMethodName(property);
		 Class [] paramtype =null;
		 Method md=null;
		  try{
			  md=type.getMethod(getMethodName,paramtype);
		  }catch(NoSuchMethodException ne){
			  throw new RuntimeException(ne);
		  }
		  try{
			   for (int i=0;i<moneyList.size();i++){
				    Object money=moneyList.get(i);
				    Object [] param=null;
				    BigDecimal result=null;
				    if(md.invoke(money, param)!=null&&!md.invoke(money, param).equals("")){
				    	result=new BigDecimal((String)md.invoke(money, param));
				    }else{
				    	result=new BigDecimal("0.00");
				    }     
				    sum=sum.add(result);
			   }
		  }catch(Exception ie){
			  throw new RuntimeException(ie);
		  }
		  return sum;
	 }
	 
	 /**
	  * 合计金额的方法,能够针对多个属性同时计算,并返回合计数组
     * @author 曹刚
     * @param moneyList 列表
     * @param property 金额的属性名数组
     * @param type 列表VO的类型
     * @return BigDecimal []
     * @throws RuntimeException
     */
	 public static BigDecimal [] sumMoney(List moneyList,String [] property,Class type) throws RuntimeException {
		 BigDecimal [] sumList=new BigDecimal[property.length];
		 String [] getMethodName=new String[property.length];
		 Class [] paramtype =null;
		 Method [] md=new Method[property.length];
	  
		 try{
			 for (int i=0;i<property.length;i++){
				 getMethodName[i]=getGetterMethodName(property[i]);
				 md[i]=type.getMethod(getMethodName[i],paramtype);
				 BigDecimal sum=new BigDecimal("0");
				 for (int j=0;j<moneyList.size();j++){
					 Object money=moneyList.get(j);
					 Object [] param=null;
					 String result=String.valueOf(md[i].invoke(money, param));
					 if (result!=null&&!"".equals(result)&&!"null".equals(result))
						 sum=sum.add(new BigDecimal(result));
				 }
				 sumList[i]=sum;
			 }
		 }catch(Exception ne){
			 ne.printStackTrace();
			 throw new RuntimeException(ne);
		 }
		 return sumList;
	 }
	 /**
	  * 得到属性的get方法名
     * @author 曹刚
     * @param property 属性
     * @return 方法名
     * @throws RuntimeException
     */
	 private static String  getGetterMethodName(String property) throws RuntimeException{
		 return getBeanMethodName("get", property);
	 }
	 
	 /**
	  * 得到属性的get/set方法名
     * @author 曹刚
     * @param prefix 前缀如(get,set)
     * @param property 属性
     * @return 方法名
     * @throws RuntimeException
     */
	 private static String getBeanMethodName(String prefix,String property) throws RuntimeException{
		 if(!"get".equals(prefix) && !"set".equals(prefix))
			 throw new RuntimeException("must be get and set!");
		 if (property == null || "".equals(property))
			 throw new RuntimeException("property is not found!");
		 StringBuffer methodName = new StringBuffer(prefix).append(property.trim());
		 methodName.replace(prefix.length(), prefix.length() + 1, methodName.substring(prefix.length(), prefix.length()+1).toUpperCase());
		 return methodName.toString();
	}
}
