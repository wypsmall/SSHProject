package com.imti.framework.common.excel.validator;

import java.math.BigDecimal;

public class GreaterThanValidator extends CompareNumberValidator {
	
	public boolean compare(BigDecimal numberContent) {
		boolean result = numberContent.compareTo(this.getComparer()) == 1;
		if (!result)
			this.setInvalidMessage("������� " + this.getComparer().toString() + " ");
		return result;
	}
}
