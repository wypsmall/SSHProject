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
	 * ������������
	 * @param cond ��������
	 * @return ������
	 */
	public String parse(Condition cond){
		if(cond == null)
			return "";
		String strRet = parseList(cond.getGeneralList()); 
		log.info(">>>>������ѯ������"+strRet);
		return strRet;
	}
	
	/**
	 * ������������
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
	 * ���������ֶ�
	 * @param fc �ֶ�����
	 * @return ��֯�õ��ֶ�����
	 */
	private  static String parseField(FieldCondition fc ){
		StringBuffer sbWhere = new StringBuffer(""); 
		if(ConditionConstant.BETWEEN.equalsIgnoreCase(fc.getOperation())){ //��Χ֮��
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
			 
		} else if (ConditionConstant.ISNOTNULL.equalsIgnoreCase(fc.getOperation())){ //��Ϊ��
			
			sbWhere.append(fc.getName());
			sbWhere.append(" ");
			sbWhere.append(fc.getOperation());
			
		} else if (ConditionConstant.ISNULL.equalsIgnoreCase(fc.getOperation())){ //Ϊ��
			
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
	 * ��֯�����ֶε�ֵ
	 * @param value ֵ
	 * @param dbType ��������
	 * @return ��֯�õ��ֶ�
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
	 * Wengnb Add 2003-09-09 ��ǰ��ո��ַ�����ȥ��,��"'"�滻��Ϊ"''"
	 * @param strSource
	 *            String:�ַ���
	 * @return String:�����滻����ִ����
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
