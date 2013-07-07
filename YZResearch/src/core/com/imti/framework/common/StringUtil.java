package com.imti.framework.common;

import org.apache.commons.lang.StringUtils;

public class StringUtil extends org.apache.commons.lang.StringUtils{

	public final static String SQL_STRING_NO_BRACKETS = "1";
    public final static String SQL_STRING_BRACKETS = "2";
    public final static String SEPERATOR__COMMA = ",";
    
    /**  
     * ��String[]ת��Ϊ����"��������������"���ַ�����������ɾ��ʱʹ��  
     * @return  
     */  
    public static String arrayToString(String[] fid) {   
        String serperater = ",";   
        return arrayToString(fid, serperater);   
    }    
    
    /**  
     * ��int������ת����String  
     * @return  
     */  
    public static String intToString(int num) {   
        return String.valueOf(num);   
    } 
    /**  
     * ��null תΪ ���ַ��� 
     * @return  
     */  
    public static String nullToString(String  str) {   
        return  str == null ? "" : str;
    } 
    
    /**  
     * ��ָ���ķָ�������һ���ַ���������ϳ�һ���ַ���(����fid��int������long����)  
     */  
    public static String arrayToString(String[] fids, String serperater) {   
        String strfids = "";   
        for (int i = 0; fids != null && i < fids.length; i++) { // ������ѡ��ļ�¼ɾ��   
            if (i == 0){   
                strfids = fids[0];   
            } else{   
                strfids += serperater + fids[i];   
            }   
        }   
        return strfids;   
    }   
    /**  
     * ���ַ�������sql����ʽ�������ַ������ԣ�,�������� 
     * @param arrayPks ���������������ַ���
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
     * ���ַ�������sql����ʽ�������ַ������ԣ�,������ 
     * @param arrayPks ���������������ַ���
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
     * ��String[]ת��Ϊsql��ʽ���ַ���  ��int��long���ͣ�
     * @param fid Ҫת�����ַ�������
     * @param type ת������1û������ 2������
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
