package com.yzsystem.common.extgrid;

public interface DynamicFieldInterface {
	/**
	 * ��ʾ����
	 * 
	 * @return
	 * @author �ܸ� �������ڣ�2013-5-15����04:55:49
	 * @since 1.0
	 */
	public String getFieldName();
	/**
	 * ��ʾ�ֶ�
	 * 
	 * @return
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2013-5-15����04:56:41
	 * @since 1.0
	 */
	public String getFieldValue();
	/**
	 * ��չ
	 * 
	 * @return
	 * @author �ܸ� �������ڣ�2013-5-15����04:57:07
	 * @since 1.0
	 */
	public String getFieldArg();
	
}
