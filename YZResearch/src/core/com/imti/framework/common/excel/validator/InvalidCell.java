package com.imti.framework.common.excel.validator;

/**
 * 
 * @author db2admin
 *
 */
public class InvalidCell {
	
	/**
	 * ������
	 * @param row ��
	 * @param col ��
	 * @param message ��Ϣ
	 */
	public InvalidCell(int row,int col,String message){
		this.row = row;
		this.col = col;
		this.message = message;
	}
	/**
	 * �޲ι�����
	 *
	 */
	public InvalidCell(){
	}
	
	//����
	private int row;
	
	//����
	private int col;
	
	//��Ϣ
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
