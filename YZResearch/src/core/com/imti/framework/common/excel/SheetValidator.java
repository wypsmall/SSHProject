package com.imti.framework.common.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;

import com.imti.framework.common.excel.validator.CellValidator;
import com.imti.framework.common.excel.validator.InvalidCell;
import com.imti.framework.web.exception.FileException;

/**
 * Excel��ҳ��֤�Լ����ݰ�װ���
 * 1.֧�ֻ����ĵ�Ԫ��������֤
 * 2.�ɶ�λ��ͷ��
 * 3.�ɶ�������������
 * 4.֧��ʹ�ñ߽�
 * ���ݰ�װͨ��callbackʵ��,һ��ʹ������������
 * �������ڰ�װ��ͬʱʵ���Զ����������֤
 * ��ȡ��Χ��startRow��endRow -1
 * ��ν�ǾӼ����αؾ���ҩ
 * @author 
 * 
 */
public class SheetValidator {

	/**
	 * ��Ԫ����֤���б�
	 */
	private List cellValidators = null;
	
	/**
	 * ��ͷ����
	 */
	private String[] heads = null;
	
	/**
	 * ��λ��ͷ��Ĭ�Ͼ���: 2
	 */
	public static final int DEFAULT_HEAD_PRECISION = 2;
	
	/**
	 * ��λ��ͷ�ľ���
	 */
	private int headPrecision = DEFAULT_HEAD_PRECISION;
	
	/**
	 * Ĭ���������������: 3
	 */
	public static final int DEFAULT_MAX_BLANK_ROW = 3;
	
	/**
	 * ���������������
	 */
	private int maxBlankRow = DEFAULT_MAX_BLANK_ROW;
	
	/**
	 * ����
	 */
	private int columnNumber = -1;
	
	/**
	 * ����߽����� 
	 */
	private boolean enabledBorder = false;
	
	/**
	 * ������������հ���������
	 */
	private boolean enabledMaxBlankRow = false;
	
	/**
	 * ��ʼ��
	 */
	private int startRow = -1;
	
	/**
	 * ������,�����ǲ���ȡ��
	 */
	private int endRow = -1;
	
	private Workbook workbook;
	
	private Sheet sheet;
	
	private List invalidCells;
	
	private List mappedRows;
	
	public List getMappedRows() {
		return mappedRows;
	}

	public void setMappedRows(List mappedRows) {
		this.mappedRows = mappedRows;
	}

	public List getInvalidCells() {
		return invalidCells;
	}

	public void setInvalidCells(List invalidCells) {
		this.invalidCells = invalidCells;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}
	
	public SheetValidator(){
	}
	
	public SheetValidator(InputStream stream){
		try {
			WorkbookSettings workbookSettings=new WorkbookSettings();
			workbookSettings.setInitialFileSize(1);
			workbookSettings.setArrayGrowSize(1);
//			Runtime runtime = Runtime.getRuntime();
//			System.out.println("������ҳ֮ǰ...");
//			System.out.println("�ڴ�����:" + runtime.totalMemory() / 1024 / 1024 + " M");
//			System.out.println("�����ڴ�:" + runtime.freeMemory() / 1024 / 1024 + " M");
			workbook = Workbook.getWorkbook(stream,workbookSettings);
//			System.out.println("������ҳ֮��...");
//			System.out.println("�ڴ�����:" + runtime.totalMemory() / 1024 / 1024 + " M");
//			System.out.println("�����ڴ�:" + runtime.freeMemory() / 1024 / 1024 + " M");
		} catch (BiffException e) {
			throw new FileException(e);
		} catch (IOException e) {
			throw new FileException(e);
		}
		
		Sheet [] sheets = workbook.getSheets();
		if (sheets != null){
			/*
			 * JExcelApi����ҳ��������1,���ұ�ҳ����Ϊ����ʱ,���������Խ�����
			 * ��ʱ���Ҳ�������ķ���,Ҳ�п�����JExcelApi��BUG
			 * �°汾��JExcelApi�뱾ϵͳʹ�õĲ�����,�ʲ���ͨ�����������
			 * ��������,����ֻ��ͨ�����Ʊ�ҳ���������ⷢ������Ԥ�ϵĴ���
			 * 							---  ������  2008-12-03
			 */
			if (sheets.length > 1)
				throw new FileException("������ֻ�ܰ���һ����ҳ,��ɾ�������ҳ!");
			sheet = workbook.getSheet(0);
		}
		if (sheet == null)
			throw new FileException("�����������κα�ҳ!");
	}

	
	/**
	 * ��֤������ҳ
	 * @param sheet
	 * @param startRow
	 * @return
	 * @throws DefaultException 
	 */
	public void validate(RowMapper rowMapper){
		if (cellValidators == null || cellValidators.size()==0)
			return ;

		invalidCells = new ArrayList();
		mappedRows = new ArrayList();
		
		int seriesBlankRows = 0;//������������
		
		for (int rowNumber = startRow ;rowNumber < sheet.getRows(); rowNumber++){
			/*
			 * �����߽�����ֹͣ����
			 */
			if (rowNumber > this.endRow - 1)
				break ;
			
			Cell [] row = sheet.getRow(rowNumber);
			boolean invalid = false;
			
			/*
			 * ����ǿ���������������
			 * �����Ŀ�������������maxBlankRow��ֹͣ����
			 */
			if (row.length == 0 || isBlankRow(row)){
				seriesBlankRows ++;
				if (enabledMaxBlankRow && seriesBlankRows > maxBlankRow){
					break ;
				} else 
					continue ;
			} else
				seriesBlankRows = 0;
			
			/*
			 * ʹ�����е���֤������Ӧ�ĵ�Ԫ�������֤
			 */
			for (int vIndex = 0; vIndex < cellValidators.size(); vIndex++){
				CellValidator validator = (CellValidator)cellValidators.get(vIndex);
				int [] columns = validator.getColumnIndexes();
				if (columns != null){
					for (int colIndex = 0; colIndex < columns.length; colIndex++){
						Cell cell = null;
						if (columns[colIndex] > row.length - 1)//�����ڵ�Ԫ��͵����ǿհ����ݵĵ�Ԫ��
							cell = new Label(columns[colIndex],rowNumber,"");
						else
							cell = row[columns[colIndex]];
						InvalidCell iCell = validator.validate(cell);
						if (iCell != null){
							invalidCells.add(iCell);
							invalid = true;
						}
					}
				}
			}
			/*
			 * ��װ��֤ͨ������
			 * ����յ�Ԫ��
			 */
			if (!invalid && rowMapper != null){
				Cell [] tempRow = row;
				if (columnNumber > 0 && columnNumber > row.length){
					List cells = new ArrayList();
					cells.addAll(Arrays.asList(row));
					while (cells.size() == columnNumber){
						cells.add(new Label(cells.size(),rowNumber,""));
					}
					tempRow = (Cell [])cells.toArray(new Cell[1]);
				}
				Object object = rowMapper.mapRow(tempRow, rowNumber, invalidCells);
				if (object != null)
					mappedRows.add(object);
			}
		}
		
		workbook.close();
	}

	public List getCellValidators() {
		return cellValidators;
	}

	public void setCellValidators(List cellValidators) {
		this.cellValidators = cellValidators;
	}
	
	/**
	 * �Ƿ�հ���
	 * @param row
	 * @return
	 */
	protected boolean isBlankRow(Cell[] row){
		boolean isBlank = true;
		for(int i = 0; i < row.length; i++){
			if(row[i].getContents() != null && !"".equals(row[i].getContents().trim())){
				isBlank = false;
				break ;
			}
		}
		return isBlank;
	}

	/**
	 * ��ʼ��,ȷ����ʼ���Լ��߽���
	 *
	 */
	public void initializtion(){
		/*
		 * �ҵ���ʼ���Լ�������
		 */
		if (this.heads == null || this.heads.length == 0 || this.headPrecision < 1){
			this.startRow = 0;
		} else {
			boolean read = false;
			for (int rowNumber = 0; rowNumber < sheet.getRows(); rowNumber++){
				Cell [] row = sheet.getRow(rowNumber);
				/*
				 * ��������
				 */
				if (row.length == 0 || isBlankRow(row))
					continue ;
				
				if (row.length < headPrecision)
					continue;
				
				for (int headIndex = 0; headIndex < headPrecision; headIndex++){
					String content = row[headIndex].getContents();
					if (content == null || !heads[headIndex].equals(content.trim())){
						break ;
					}
					if (headIndex == headPrecision - 1){
						this.startRow = rowNumber + 1;
						read = true;
					}
				}
				if (read)
					break ;
			}
			if (!read)
				this.startRow = 0;
		}
		
		
		if (enabledBorder){
			boolean read = false;
			for (int rowNumber = sheet.getRows() - 1; rowNumber > -1; rowNumber--){
				Cell [] row = sheet.getRow(rowNumber);

				if (row.length < this.columnNumber)
					continue;
				
				for (int headIndex = 0; headIndex < row.length; headIndex++){
					Cell cell = row[headIndex];
					if (cell == null || cell.getCellFormat() == null)
						break ;
					
					BorderLineStyle lineStyle = cell.getCellFormat().getBorder(Border.BOTTOM);
					if (BorderLineStyle.NONE.equals(lineStyle)){
						break ;
					}
					if (headIndex == row.length - 1){
						this.endRow = rowNumber + 1;
						read = true;
					}
				}
				if (read)
					break ;
			}
			if (!read)
				this.endRow = sheet.getRows();
		} else {
			this.endRow = sheet.getRows();
		}
	}
	
	/**
	 * ���ɼ򵥵���֤��ʾ��Ϣ
	 * @param invalidCells
	 * @return
	 */
	public String toSimpleMessage(List invalidCells){
		if (invalidCells == null)
			return null;
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i < invalidCells.size(); i++){
			InvalidCell cell = (InvalidCell)invalidCells.get(i);
			buffer.append("��")
					.append(cell.getRow() + 1)
					.append("�е�")
					.append(cell.getCol() + 1)
					.append("��");
			if (heads != null && heads.length > cell.getCol())
				buffer.append("��").append(heads[cell.getCol()]).append("��");
			buffer.append("��")
					.append(cell.getMessage());
			buffer.append("\r\n");
		}
		return buffer.toString();
	}

	public String[] getHeads() {
		return heads;
	}

	public void setHeads(String[] heads) {
		this.heads = heads;
	}

	public int getHeadPrecision() {
		return headPrecision;
	}

	public void setHeadPrecision(int headPrecision) {
		this.headPrecision = headPrecision;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}

	public int getMaxBlankRow() {
		return maxBlankRow;
	}

	public void setMaxBlankRow(int maxBlankRow) {
		this.maxBlankRow = maxBlankRow;
	}

	public boolean isEnabledBorder() {
		return enabledBorder;
	}

	public void setEnabledBorder(boolean enabledBorder) {
		this.enabledBorder = enabledBorder;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public void setEndRow(int borderRow) {
		this.endRow = borderRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public boolean isEnabledMaxBlankRow() {
		return enabledMaxBlankRow;
	}

	public void setEnabledMaxBlankRow(boolean enabledMaxBlankRow) {
		this.enabledMaxBlankRow = enabledMaxBlankRow;
	}
}
