package com.imti.framework.common.excel.validator;

public class NotBlankValidator extends NotEmptyValidator {
	
	static final String BLANK_MESSAGE = "¿Õ°×ÄÚÈÝ";
	
	public boolean validate(String content) {
		if (!super.validate(content)){
			return false;
		}
		if (content.trim().equals("")){
			this.setInvalidMessage(BLANK_MESSAGE);
			return false;
		}
		return true;
	}
}
