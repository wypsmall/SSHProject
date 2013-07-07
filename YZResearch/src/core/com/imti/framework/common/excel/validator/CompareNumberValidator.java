package com.imti.framework.common.excel.validator;

import java.math.BigDecimal;

public abstract class CompareNumberValidator extends NumberValidator {

	private BigDecimal comparer = null;

	public BigDecimal getComparer() {
		return comparer;
	}

	public void setComparer(BigDecimal comparer) {
		this.comparer = comparer;
	}
	
	public boolean validate(String content) {
		if (!super.validate(content))
			return false;
		return this.compare(new BigDecimal(content.trim().replaceAll(",", "")));
	}
	
	public abstract boolean compare(BigDecimal numberContent);
}
