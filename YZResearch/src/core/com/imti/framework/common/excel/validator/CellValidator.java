package com.imti.framework.common.excel.validator;

import jxl.Cell;

/**
 * 单元格验证器
 * @author db2admin
 *
 */
public interface CellValidator {
	/**
	 * 验证单元格,验证通过返回null,否则返回相应的InvalidCell
	 * @param cell
	 * @return
	 */
	InvalidCell validate(Cell cell);
	int [] getColumnIndexes();
}
