package com.imti.framework.component.condition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.imti.framework.component.condition.constant.ConditionConstant;
import com.imti.framework.component.condition.parser.ConditionParse;
import com.imti.framework.component.condition.parser.ConditionParseFactory;
import com.imti.framework.component.vopomapping.utils.PropertyUtils;
import com.imti.framework.web.log.ExtLog;

public class Condition implements Serializable {

	private static ExtLog log = new ExtLog(Condition.class);
	
	private String orderType = ConditionConstant.ASC;  //��������
	private String orderFld ="";                       //�����ֶ�
	private int rowsPerPage = PropertyUtils.getIntProperty("imti.condition.page.rows", 20);      //ÿ�ж���ҳ
	private long currentPage =1;                 //��ǰҳ
	private List generalList = new ArrayList();  //��ͨ����
	private String sqlWhere ;
	
	public List getGeneralList() {            
		return this.generalList;
	}
	

	public void or(FieldCondition fc) {
		this.sqlWhere = null;
		if(fc != null){
			generalList.add(ConditionConstant.OR);
			generalList.add(fc);
		}
	}

	public void add(FieldCondition fc) {
		this.sqlWhere = null;
		generalList.add(fc);
	}

	public void and(FieldCondition fc) {
		this.sqlWhere = null;
		if(fc != null){
			generalList.add(ConditionConstant.AND);
			generalList.add(fc);
		}
	}

	
	public void add(String value) {
		generalList.add(value);
	}

	public void and(String value) {
		this.sqlWhere = null;
		generalList.add(ConditionConstant.AND);
		generalList.add(value);
	}

	public void or(String value) {
		this.sqlWhere = null;
		generalList.add(ConditionConstant.OR);
		generalList.add(value);
	}

	/**
	 * �����ͨ�����Ķ���
	 */
	public void clear() {
		this.sqlWhere = null;
		this.generalList.clear();
	}
	
	/**
	 * ������еĶ���
	 */
	public void clearAll() {
		this.clear();
		this.currentPage = 1;
		this.orderFld = "";
		this.orderType = "";
		this.rowsPerPage = 20;
		this.sqlWhere = null;
	}

	/**
	 * ��StringTokenizerת����string����
	 * @param stk StringTokenizer����
	 * @return ֵ
	 */
	private  String stkToString(StringTokenizer stk){
		String value = null;
		if(stk.hasMoreTokens()){
			value = stk.nextToken();
		}
		return value;
	}
	
	/**
	 * ͨ��Expression��������������������
	 * @param name ����
	 * @param dbType ��������
	 * @param opt ������
	 * @param value ��ʼֵ
	 * @param endValue ����ֵ
	 * @return ��������
	 */
	private  FieldCondition express(String name, String dbType, String opt,String value,String endValue){
		FieldCondition fc = null;
		
		if(ConditionConstant.LE.equalsIgnoreCase(opt)){
			fc =Expression.le(name,value,dbType);
		} else if (ConditionConstant.LT.equalsIgnoreCase(opt)){
			fc =Expression.lt(name,value,dbType);
		} else if (ConditionConstant.GE.equalsIgnoreCase(opt)){
			fc =Expression.ge(name,value,dbType);
		} else if (ConditionConstant.GT.equalsIgnoreCase(opt)){
			fc =Expression.gt(name,value,dbType);
		} else if (ConditionConstant.EQ.equalsIgnoreCase(opt)){
			fc =Expression.eq(name,value,dbType);
		} else if (ConditionConstant.NE.equalsIgnoreCase(opt)){
			fc =Expression.ne(name,value,dbType);
		} else if (ConditionConstant.LIKE.equalsIgnoreCase(opt)){
			fc =Expression.like(name,value);
		} else if (ConditionConstant.BETWEEN.equalsIgnoreCase(opt)){
			fc =Expression.between(name,value,endValue,dbType);
		} else if(ConditionConstant.IN.equalsIgnoreCase(opt)){
			if(value!=null){
				String[] values = value.split(",");
				fc =Expression.in(name, values, dbType);
			}
		} else if(ConditionConstant.NOTIN.equals(opt)){
			if(value!=null){
				String[] values = value.split(",");
				fc =Expression.notIn(name, values, dbType);
			}
		}
		return fc;
	}
	

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
        if (currentPage<=0) currentPage=1;
		this.currentPage = currentPage;
	}

	public String getOrderFld() {
		return orderFld;
	}

	public void setOrderFld(String orderFld) {
		this.orderFld = orderFld;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getSQLWhere(){
		if(this.sqlWhere == null){
			ConditionParse parse = ConditionParseFactory.createParse();
			this.sqlWhere = parse.parse(this);
		}
		return this.sqlWhere;
	}
}
