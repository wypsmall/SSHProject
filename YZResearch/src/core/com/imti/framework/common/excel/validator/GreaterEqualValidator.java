package com.imti.framework.common.excel.validator;

import java.math.BigDecimal;


public class GreaterEqualValidator extends CompareNumberValidator {
	
	public boolean compare(BigDecimal numberContent) {
		boolean result = numberContent.compareTo(this.getComparer()) >= 0;
		if (!result)
			this.setInvalidMessage("必需大于或等于 " + this.getComparer().toString() + " ");
		return result;
	}
	
}
