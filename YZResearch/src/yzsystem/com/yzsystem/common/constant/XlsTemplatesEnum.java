package com.yzsystem.common.constant;

/**
 * @author �ܸ� �½����ڣ�2013-4-18����12:59:07
 * @version tde 1.0
 */
public enum XlsTemplatesEnum {

	/**
	 * �ͻ�֪ͨ��ģ��
	 */
	CUSTOMER_INFORM_TEMPLATES("/view/component/templates/customerInformTemplates.xls", "customerInformTemplates.xls");
	
	
	
	
	
	XlsTemplatesEnum(String temlpates, String fileName)	{
		this.temlpates = temlpates;
		this.fileName = fileName;
	}
	
	public String temlpates;
	public String fileName;
}
