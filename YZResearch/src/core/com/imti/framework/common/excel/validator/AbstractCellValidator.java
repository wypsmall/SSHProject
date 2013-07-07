package com.imti.framework.common.excel.validator;

import jxl.Cell;

/**
 * �������֤��
 * @author db2admin
 *
 */
public abstract class AbstractCellValidator implements CellValidator, Validator {
	
	/**
	 * Ӧ�õ�������
	 */
	private int [] columnIndexes ;
	
	private String invalidMessage = null;
	
	public InvalidCell validate(Cell cell) {
		if (this.validate(cell.getContents()))
			return null;
		else{
			InvalidCell invalidCell = new InvalidCell(cell.getRow(),cell.getColumn(),this.getInvalidMessage());
			return invalidCell;
		}
	}

	public int[] getColumnIndexes() {
		return columnIndexes;
	}

	public void setColumnIndexes(int[] columnIndexes) {
		this.columnIndexes = columnIndexes;
	}

	public String getInvalidMessage() {
		return invalidMessage;
	}

	public void setInvalidMessage(String invalidMessage) {
		this.invalidMessage = invalidMessage;
	}
}
