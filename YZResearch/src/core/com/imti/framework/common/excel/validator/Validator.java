package com.imti.framework.common.excel.validator;

/**
 * 验证器
 */
public interface Validator {
	/**
	 * 验证内容
	 * @param content
	 * @return
	 */
	boolean validate(String content);
	/**
	 * 验证不通过时的提示信息
	 * @return
	 */
	String getInvalidMessage();
}
