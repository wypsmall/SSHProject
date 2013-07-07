package com.yzsystem.common.constant;

/**
 * @author 曹刚 新建日期：2013-4-18下午12:59:07
 * @version tde 1.0
 */
public enum XlsTemplatesEnum {

	/**
	 * 客户通知书模板
	 */
	CUSTOMER_INFORM_TEMPLATES("/view/component/templates/customerInformTemplates.xls", "customerInformTemplates.xls");
	
	
	
	
	
	XlsTemplatesEnum(String temlpates, String fileName)	{
		this.temlpates = temlpates;
		this.fileName = fileName;
	}
	
	public String temlpates;
	public String fileName;
}
