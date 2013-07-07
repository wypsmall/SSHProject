package com.imti.framework.common.excel.validator;

/**
 * 
 * @author db2admin
 *
 */
public class InvalidCell {
	
	/**
	 * 构造器
	 * @param row 行
	 * @param col 列
	 * @param message 信息
	 */
	public InvalidCell(int row,int col,String message){
		this.row = row;
		this.col = col;
		this.message = message;
	}
	/**
	 * 无参构造器
	 *
	 */
	public InvalidCell(){
	}
	
	//行数
	private int row;
	
	//列数
	private int col;
	
	//信息
	private String message;

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}
