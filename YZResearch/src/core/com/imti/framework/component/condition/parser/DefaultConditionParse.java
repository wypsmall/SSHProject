package com.imti.framework.component.condition.parser;

import java.util.List;

import com.imti.framework.component.condition.Condition;
import com.imti.framework.component.condition.FieldCondition;
import com.imti.framework.component.condition.constant.ConditionConstant;
import com.imti.framework.component.vopomapping.constant.DataType;
import com.imti.framework.web.log.ExtLog;

public class DefaultConditionParse implements ConditionParse {

	private static ExtLog log = new ExtLog(DefaultConditionParse.class);
	
	/**
	 * 解释条件对象
	 * @param cond 条件对象
	 * @return 条件语
	 */
	public String parse(Condition cond){
		if(cond == null)
			return "";
		String strRet = parseList(cond.getGeneralList()); 
		log.info(">>>>解析查询条件："+strRet);
		return strRet;
	}
	
	/**
	 * 解析条件对象
	 * @param conditionList 
	 * @return
	 */
	private static String parseList(List conditionList){
		StringBuffer sb = new StringBuffer("");
		for(int idx = 0 ; idx < conditionList.size(); idx++){
			Object object = conditionList.get(idx);
			
			if(object instanceof String){
				sb.append(object);
				sb.append(" ");
			} else if (object instanceof FieldCondition){
				sb.append(parseField((FieldCondition)object));
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 解析单个字段
	 * @param fc 字段条件
	 * @return 组织好的字段条件
	 */
	private  static String parseField(FieldCondition fc ){
		StringBuffer sbWhere = new StringBuffer(""); 
		if(ConditionConstant.BETWEEN.equalsIgnoreCase(fc.getOperation())){ //范围之间
			String startValue = toValue(fc.getValue(),fc.getDbType());
			String endValue = toValue(fc.getEndValue(),fc.getDbType());
			sbWhere.append(fc.getName());
			sbWhere.append(" ");
			sbWhere.append(fc.getOperation());
			sbWhere.append(" (");
			sbWhere.append(startValue);
			sbWhere.append(" ");
			sbWhere.append(ConditionConstant.AND);
			sbWhere.append(" ");
			sbWhere.append(endValue);
			sbWhere.append(")");
			 
		} else if (ConditionConstant.ISNOTNULL.equalsIgnoreCase(fc.getOperation())){ //不为空
			
			sbWhere.append(fc.getName());
			sbWhere.append(" ");
			sbWhere.append(fc.getOperation());
			
		} else if (ConditionConstant.ISNULL.equalsIgnoreCase(fc.getOperation())){ //为空
			
			sbWhere.append(fc.getName());
			sbWhere.append(" ");
			sbWhere.append(fc.getOperation());
			
		} else if (ConditionConstant.IN.equalsIgnoreCase(fc.getOperation()) ||
				ConditionConstant.NOTIN.equalsIgnoreCase(fc.getOperation())){  
			
			sbWhere.append(fc.getName());
			sbWhere.append(" ");
			sbWhere.append(fc.getOperation());
			sbWhere.append(" (");
			for(int idx =0; idx< fc.getValueMany().length; idx++){
				sbWhere.append(toValue(fc.getValueMany()[idx], fc.getDbType()));
				if(idx < (fc.getValueMany().length-1)){
					sbWhere.append(",");
				}
			}
			sbWhere.append(" )");
			
		}	else if (ConditionConstant.LIKE.equalsIgnoreCase(fc.getOperation())){
			sbWhere.append(fc.getName());
			sbWhere.append(" ");
			sbWhere.append(fc.getOperation());
			sbWhere.append(" ");
			sbWhere.append("'%" + replaceText(fc.getValue())+"%'");
		}else {
			sbWhere.append(fc.getName());
			sbWhere.append(fc.getOperation());
			sbWhere.append(toValue(fc.getValue(),fc.getDbType()));
		}
		return sbWhere.toString();
	}
	
	/**
	 * 组织单个字段的值
	 * @param value 值
	 * @param dbType 数据类型
	 * @return 组织好的字段
	 */
	private static String toValue(String value,String dbType){
		String strRet = value;
		if(DataType.STRING.equalsIgnoreCase(dbType)){
			strRet = "'" + replace(strRet,"'","''") + "'";
		} else if (DataType.DATE.equalsIgnoreCase(dbType )){
			strRet = ConditionConstant.DATE + "('" + strRet + "')";
		} else if (DataType.TIMESTMAP.equalsIgnoreCase(dbType )){
			strRet = ConditionConstant.TIMESTAMP + "('" + strRet + "')";
		} else if (DataType.TIME.equalsIgnoreCase(dbType)){
			strRet = ConditionConstant.TIME + "('" + strRet + "')";
		}
		return strRet;
	}
	/**
	 * Wengnb Add 2003-09-09 将前后空格字符串截去后,将"'"替换成为"''"
	 * @param strSource
	 *            String:字符串
	 * @return String:最终替换后的字串结果
	 */
	private static String replaceText(String strSource) {

		if (strSource == null) {
			return "";
		}
		return replace(strSource.trim(), "'", "''");
	}
	private static String replace(String strSource, String strFrom, String strTo) {
		if (strSource == null)
			return "";
		String strDest = "";
		int intFromLen = strFrom.length();
		int intPos = 0;
		while ((intPos = strSource.indexOf(strFrom)) != -1) {
			strDest = strDest + strSource.substring(0, intPos);
			strDest = strDest + strTo;
			strSource = strSource.substring(intPos + intFromLen);
		}
		strDest = strDest + strSource;
		return strDest;
	}
}
