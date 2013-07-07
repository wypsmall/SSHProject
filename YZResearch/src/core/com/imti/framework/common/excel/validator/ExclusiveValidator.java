package com.imti.framework.common.excel.validator;

import jxl.Cell;
import jxl.Sheet;

/**
 * �ڱ�ҳ��Ψһ����֤��
 * @author db2admin
 *
 */
public class ExclusiveValidator implements CellValidator {
	
	private Sheet sheet = null;
	private int startRow = 0;
	
	private int [] columnIndexes ;
	
	public ExclusiveValidator(){
	}
			
	public ExclusiveValidator(Sheet sheet, int startRow){
		this.sheet = sheet;
		this.startRow = startRow;
	}
	
	public InvalidCell validate(Cell cell) {
		for (int i = startRow;i < cell.getRow();i++){
			Cell [] row = sheet.getRow(i);
			if ( row.length > cell.getColumn()
					&& cell.getContents()!=null 
					&& cell.getContents().equals(row[cell.getColumn()].getContents()) ){
				return new InvalidCell(cell.getRow(),cell.getColumn(),"�ڹ����������" + (i + 1) + "�С���" + (cell.getColumn() + 1) + "���ظ�");
			}
		}
		return null;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	
	public int[] getColumnIndexes() {
		return columnIndexes;
	}

	public void setColumnIndexes(int[] columnIndexes) {
		this.columnIndexes = columnIndexes;
	}
}
