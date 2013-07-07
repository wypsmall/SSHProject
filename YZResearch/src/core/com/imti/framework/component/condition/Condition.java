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
	
	private String orderType = ConditionConstant.ASC;  //排序类型
	private String orderFld ="";                       //排序字段
	private int rowsPerPage = PropertyUtils.getIntProperty("imti.condition.page.rows", 20);      //每行多少页
	private long currentPage =1;                 //当前页
	private List generalList = new ArrayList();  //普通条件
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
	 * 清除普通条件的对象
	 */
	public void clear() {
		this.sqlWhere = null;
		this.generalList.clear();
	}
	
	/**
	 * 清除所有的对象
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
	 * 将StringTokenizer转换成string对象
	 * @param stk StringTokenizer对象
	 * @return 值
	 */
	private  String stkToString(StringTokenizer stk){
		String value = null;
		if(stk.hasMoreTokens()){
			value = stk.nextToken();
		}
		return value;
	}
	
	/**
	 * 通过Expression构造器来构造条件对象
	 * @param name 名字
	 * @param dbType 数据类型
	 * @param opt 操作符
	 * @param value 开始值
	 * @param endValue 结束值
	 * @return 条件对象
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
