package com.imti.framework.common.excel.validator;

/**
 * ��֤��
 */
public interface Validator {
	/**
	 * ��֤����
	 * @param content
	 * @return
	 */
	boolean validate(String content);
	/**
	 * ��֤��ͨ��ʱ����ʾ��Ϣ
	 * @return
	 */
	String getInvalidMessage();
}
