package com.imti.framework.common.excel.validator;

import jxl.Cell;

/**
 * ��Ԫ����֤��
 * @author db2admin
 *
 */
public interface CellValidator {
	/**
	 * ��֤��Ԫ��,��֤ͨ������null,���򷵻���Ӧ��InvalidCell
	 * @param cell
	 * @return
	 */
	InvalidCell validate(Cell cell);
	int [] getColumnIndexes();
}
