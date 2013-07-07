package com.imti.framework.common.excel.validator;

import java.math.BigDecimal;

public class NumberValidator extends NotBlankValidator {
	
	static final String NOT_NUMBER_MESSAGE = "²»ÊÇÊý×Ö";
	
	public boolean validate(String content) {
		if (!super.validate(content)){
			return false;
		}
		try{
			new BigDecimal(content.trim().replaceAll(",", ""));
		}catch (NumberFormatException e){
			this.setInvalidMessage(NOT_NUMBER_MESSAGE);
			return false;
		}
		return true;
	}
	
}
