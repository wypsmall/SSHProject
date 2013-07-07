package com.imti.framework.common;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

public class MoneyUtil {
	/**
	  * ��ʽ�������һǧԪ�ɸ�ʽΪ:1000.00
     * @author �ܸ�
     * @param moneyList �б�
     * @param property ����������
     * @param type �б�VO������
     * @return double
     * @throws RuntimeException
     */
	 public static String formatMoney(String money){
		  /* ��һ��ʵ�ַ���(���ο�)
		   * DecimalFormat df = new DecimalFormat("0.00"); 
		   * return df.format(Double.parseDouble(money));
		   */
		 int point=money.indexOf('.');
		 int len=money.length();
		 if (point>-1){
			 int decNum=len-point-1; //С������
			 if (decNum<2){
				 //��С��
				 for (int i=0;i<2-decNum;i++){
					 money=money+"0";
				 }
			 }else if (decNum>2){
				 //��С��
				 money=money.substring(0,point+3);
			 }
		  }else{
		   money=money+".00";
		  }
		 return money;
	 }
	 
	 /**
	  * ͨ�õĺϼƽ��ķ���
     * @author �ܸ�
     * @param moneyList �б�
     * @param property ����������
     * @param type �б�VO������
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
	  * �ϼƽ��ķ���,�ܹ���Զ������ͬʱ����,�����غϼ�����
     * @author �ܸ�
     * @param moneyList �б�
     * @param property ��������������
     * @param type �б�VO������
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
	  * �õ����Ե�get������
     * @author �ܸ�
     * @param property ����
     * @return ������
     * @throws RuntimeException
     */
	 private static String  getGetterMethodName(String property) throws RuntimeException{
		 return getBeanMethodName("get", property);
	 }
	 
	 /**
	  * �õ����Ե�get/set������
     * @author �ܸ�
     * @param prefix ǰ׺��(get,set)
     * @param property ����
     * @return ������
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
