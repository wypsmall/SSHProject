package com.imti.framework.component.condition;

import org.apache.commons.lang.StringUtils;

import com.imti.framework.component.condition.constant.ConditionConstant;

public class Expression {

	/**
	 * like��ѯ
	 * @param name �ֶ�����
	 * @param value ֵ
	 * @return �ֶ�����
	 */
	public static FieldCondition like(String name, String value){
		if(StringUtils.isEmpty(value) || StringUtils.isEmpty(name)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.LIKE);
		fc.setName(name);
		fc.setValue(value);
		return fc;
	}
	
	/**
	 * ��������
	 * @param name �ֶ�����
	 * @param value ֵ
	 * @param dbType ���ݿ����� 
	 * @return �ֶ�����
	 */
	public static FieldCondition eq( String name, String value,String dbType){
		if(StringUtils.isEmpty(value)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.EQ);
		fc.setDbType(dbType);
		fc.setName(name);
		fc.setValue(value);
		return fc;
	}

	/**
	 * ������
	 * @param name �ֶ�����
	 * @param value ֵ
	 * @param dbType ���ݿ����� 
	 * @return �ֶ�����
	 */
	public static FieldCondition ne(String name, String value,String dbType){
		if(StringUtils.isEmpty(value)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.NE);
		fc.setDbType(dbType);
		fc.setName(name);
		fc.setValue(value);
		return fc;
	}
	
	/**
	 * ��������
	 * @param name �ֶ�����
	 * @param value ֵ
	 * @param dbType 
	 * @return �ֶ�����
	 */
	public static FieldCondition gt(String name, String value,String dbType){
		if(StringUtils.isEmpty(value)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.GT);
		fc.setDbType(dbType);
		fc.setName(name);
		fc.setValue(value);
		return fc;
	}

	/**
	 * ���ڵ�������
	 * @param name   �ֶ�����
	 * @param value  ֵ
	 * @param dbType ���ݿ����� 
	 * @return �ֶ�����
	 */
	public static FieldCondition ge( String name, String value,String dbType){
		if(StringUtils.isEmpty(value)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.GE);
		fc.setDbType(dbType);
		fc.setName(name);
		fc.setValue(value);
		return fc;
	}
	/**
	 * С������
	 * @param name   �ֶ�����
	 * @param value  ֵ
	 * @param dbType ���ݿ����� 
	 * @return ��������
	 */
	public static FieldCondition lt( String name, String value,String dbType){
		if(StringUtils.isEmpty(value)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.LT);
		fc.setName(name);
		fc.setDbType(dbType);
		fc.setValue(value);
		return fc;
	}
	
	
	/**
	 * С�ڵ�������
	 * @param name   �ֶ�����
	 * @param value  ֵ
	 * @param dbType ���ݿ����� 
	 * @return ��������
	 */
	public static FieldCondition le(String name, String value,String dbType){
		if(StringUtils.isEmpty(value)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.LE);
		fc.setName(name);
		fc.setDbType(dbType);
		fc.setValue(value);
		return fc;
	}
	
	
	/**
	 * Ϊ��ʱ
	 * @param name   �ֶ�����
	 * @return ��������
	 */
	public static FieldCondition isNull(String name){
		if(StringUtils.isEmpty(name)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.ISNULL);
		fc.setName(name);
		return fc;
	}
	
	/**
	 * ��Ϊ�յ�����
	 * @param name   �ֶ�����
	 * @return ��������
	 */
	public static FieldCondition isNotNull(String name){
		if(StringUtils.isEmpty(name)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.ISNOTNULL);
		fc.setName(name);
		return fc;
	}
	
	/**
	 * ��Ϊ�յ�����
	 * @param name   �ֶ�����
	 * @return ��������
	 */
	public static FieldCondition in(String name,String[]values, String dbType){
		if(values  == null || values.length<=0){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.IN);
		fc.setName(name);
		fc.setValueMany(values);
		fc.setDbType(dbType);
		return fc;
	}
	
	public static FieldCondition notIn(String name,String[]values, String dbType){
		if(values  == null || values.length<=0){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.NOTIN);
		fc.setName(name);
		fc.setValueMany(values);
		fc.setDbType(dbType);
		return fc;
	}
	
	/**
	 * ��Χ��ѯ
	 * @param name   �ֶ�����
	 * @param startValue ��ʼֵ
	 * @param endValue   ����ֵ
	 * @param dbType ���ݿ�����
	 * @return ��������
	 */
	public static FieldCondition between(String name, String startValue, String endValue,String dbType){
		if(StringUtils.isEmpty(startValue) && (StringUtils.isEmpty( endValue))){
			return null;
		}
		
		FieldCondition fc = new FieldCondition();
		fc.setDbType(dbType);
		fc.setName(name);
		if (StringUtils.isEmpty(startValue) && !StringUtils.isEmpty(endValue) ){
			fc.setOperation(ConditionConstant.LE);	
			fc.setValue(endValue);
		} else if(!StringUtils.isEmpty(startValue) && StringUtils.isEmpty(endValue)) {
			fc.setOperation(ConditionConstant.GE);
			fc.setValue(startValue);
		} else {
			fc.setOperation(ConditionConstant.BETWEEN);
			fc.setValue(startValue);
			fc.setEndValue(endValue);
		}
		
		return fc;
	}
}
