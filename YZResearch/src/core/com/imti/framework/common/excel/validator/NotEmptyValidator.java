package com.imti.framework.common.excel.validator;

public class NotEmptyValidator extends AbstractCellValidator {
	
	static final String EMPTY_MESSAGE = "û������";
	
	public boolean validate(String content) {
		if (content == null || "".equals(content)){
			this.setInvalidMessage(EMPTY_MESSAGE);
			return false;
		}
		return true;
	}
}
