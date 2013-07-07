package com.imti.framework.common;

import org.apache.commons.lang.StringUtils;

public class StringUtil extends org.apache.commons.lang.StringUtils{

	public final static String SQL_STRING_NO_BRACKETS = "1";
    public final static String SQL_STRING_BRACKETS = "2";
    public final static String SEPERATOR__COMMA = ",";
    
    /**  
     * 将String[]转换为形如"…，…，…，…"的字符串，用来在删除时使用  
     * @return  
     */  
    public static String arrayToString(String[] fid) {   
        String serperater = ",";   
        return arrayToString(fid, serperater);   
    }    
    
    /**  
     * 将int的数据转换成String  
     * @return  
     */  
    public static String intToString(int num) {   
        return String.valueOf(num);   
    } 
    /**  
     * 将null 转为 空字符串 
     * @return  
     */  
    public static String nullToString(String  str) {   
        return  str == null ? "" : str;
    } 
    
    /**  
     * 用指定的分隔符来将一个字符串数组组合成一个字符串(对于fid是int、或者long类型)  
     */  
    public static String arrayToString(String[] fids, String serperater) {   
        String strfids = "";   
        for (int i = 0; fids != null && i < fids.length; i++) { // 将所有选择的记录删除   
            if (i == 0){   
                strfids = fids[0];   
            } else{   
                strfids += serperater + fids[i];   
            }   
        }   
        return strfids;   
    }   
    /**  
     * 将字符数组编程sql的形式（对于字符串而言）,不带括号 
     * @param arrayPks 逗号连接主键的字符串
     */
    public static String stringToSqlStringNoBrakets(String pkString){
    	String sqlString = "";
    	if(StringUtils.isNotEmpty(pkString)){   
    		String[] arrayPks = pkString.split(",");
            for(int i=0;i<arrayPks.length;i++){
            	sqlString += "'"+arrayPks[i]+"'"+",";
            }  
        } 
    	return sqlString.substring(0, sqlString.length()-1);
    }
    /**  
     * 将字符数组编程sql的形式（对于字符串而言）,带括号 
     * @param arrayPks 逗号连接主键的字符串
     */
    public static String stringToSqlStringBrakets(String pkString){
    	String sqlString = "(";
    	if(StringUtils.isNotEmpty(pkString)){   
    		String[] arrayPks = pkString.split(",");
            for(int i=0;i<arrayPks.length;i++){
            	sqlString += "'"+arrayPks[i]+"'"+",";
            }  
            sqlString = sqlString.substring(0, sqlString.length()-1);
        } else {
        	sqlString += "''";
        }
    	sqlString += ")";
    	return sqlString;
    }
    /**  
     * 将String[]转换为sql形式的字符串  （int或long类型）
     * @param fid 要转换的字符串数组
     * @param type 转换类型1没有括号 2有括号
     * @return  
     */  
    
    public static String arrayToSQLString(String[] fid, String type) { 
    	if(StringUtil.SQL_STRING_NO_BRACKETS.equals(type)){
    		return stringToSqlStringNoBrakets(arrayToString(fid, ","));
    	}else if(StringUtil.SQL_STRING_BRACKETS.equals(type)){
    		return stringToSqlStringBrakets(arrayToString(fid, ","));
    	}
        return "";   
    }
    public static String getSqlQueRemark(int num, String type){
    	
    	String questionRemark = "";
    	for(int i = 0; i < num; i++){
    		if(i == 0){
    			questionRemark = "?";
    		}else {
    			questionRemark = "," + questionRemark;
    		}
    	}
    	
    	if(StringUtil.SQL_STRING_NO_BRACKETS.equals(type)){
    		return questionRemark;
    	}else if(StringUtil.SQL_STRING_BRACKETS.equals(type)){
    		return "(" + questionRemark + ")";
    	}
        return "";   
    }
}
