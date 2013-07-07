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
 * Excel表页验证以及数据包装组件
 * 1.支持基本的单元格数据验证
 * 2.可定位表头行
 * 3.可定义最大空行数量
 * 4.支持使用边界
 * 数据包装通过callback实现,一般使用匿名内置类
 * 还可以在包装的同时实现自定义的数据验证
 * 读取范围从startRow到endRow -1
 * 可谓是居家旅游必具良药
 * @author 
 * 
 */
public class SheetValidator {

	/**
	 * 单元格验证器列表
	 */
	private List cellValidators = null;
	
	/**
	 * 表头名称
	 */
	private String[] heads = null;
	
	/**
	 * 定位表头的默认精度: 2
	 */
	public static final int DEFAULT_HEAD_PRECISION = 2;
	
	/**
	 * 定位表头的精度
	 */
	private int headPrecision = DEFAULT_HEAD_PRECISION;
	
	/**
	 * 默认最大连续空行数: 3
	 */
	public static final int DEFAULT_MAX_BLANK_ROW = 3;
	
	/**
	 * 最大连续空行数量
	 */
	private int maxBlankRow = DEFAULT_MAX_BLANK_ROW;
	
	/**
	 * 列数
	 */
	private int columnNumber = -1;
	
	/**
	 * 激活边界限制 
	 */
	private boolean enabledBorder = false;
	
	/**
	 * 激活最大连续空白行数限制
	 */
	private boolean enabledMaxBlankRow = false;
	
	/**
	 * 起始行
	 */
	private int startRow = -1;
	
	/**
	 * 结束行,此行是不读取的
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
//			System.out.println("创建表页之前...");
//			System.out.println("内存总量:" + runtime.totalMemory() / 1024 / 1024 + " M");
//			System.out.println("可用内存:" + runtime.freeMemory() / 1024 / 1024 + " M");
			workbook = Workbook.getWorkbook(stream,workbookSettings);
//			System.out.println("创建表页之后...");
//			System.out.println("内存总量:" + runtime.totalMemory() / 1024 / 1024 + " M");
//			System.out.println("可用内存:" + runtime.freeMemory() / 1024 / 1024 + " M");
		} catch (BiffException e) {
			throw new FileException(e);
		} catch (IOException e) {
			throw new FileException(e);
		}
		
		Sheet [] sheets = workbook.getSheets();
		if (sheets != null){
			/*
			 * JExcelApi当表页个数大于1,而且表页名称为中文时,会出现数据越界错误
			 * 暂时还找不到解决的方法,也有可能有JExcelApi的BUG
			 * 新版本的JExcelApi与本系统使用的不兼容,故不能通过升级来解决
			 * 综上所述,现在只能通过限制表页个数来避免发生不可预料的错误
			 * 							---  刘晓阳  2008-12-03
			 */
			if (sheets.length > 1)
				throw new FileException("工作表只能包含一个表页,请删除多余表页!");
			sheet = workbook.getSheet(0);
		}
		if (sheet == null)
			throw new FileException("工作表不包含任何表页!");
	}

	
	/**
	 * 验证工作表页
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
		
		int seriesBlankRows = 0;//连续空行数量
		
		for (int rowNumber = startRow ;rowNumber < sheet.getRows(); rowNumber++){
			/*
			 * 超过边界行则停止运行
			 */
			if (rowNumber > this.endRow - 1)
				break ;
			
			Cell [] row = sheet.getRow(rowNumber);
			boolean invalid = false;
			
			/*
			 * 如果是空行则跳过不处理
			 * 连续的空行数量超过于maxBlankRow就停止处理
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
			 * 使用所有的验证器对相应的单元格进行验证
			 */
			for (int vIndex = 0; vIndex < cellValidators.size(); vIndex++){
				CellValidator validator = (CellValidator)cellValidators.get(vIndex);
				int [] columns = validator.getColumnIndexes();
				if (columns != null){
					for (int colIndex = 0; colIndex < columns.length; colIndex++){
						Cell cell = null;
						if (columns[colIndex] > row.length - 1)//不存在单元格就当成是空白内容的单元格
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
			 * 包装验证通过的行
			 * 补充空单元格
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
	 * 是否空白行
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
	 * 初始化,确定起始行以及边界行
	 *
	 */
	public void initializtion(){
		/*
		 * 找到起始行以及结束行
		 */
		if (this.heads == null || this.heads.length == 0 || this.headPrecision < 1){
			this.startRow = 0;
		} else {
			boolean read = false;
			for (int rowNumber = 0; rowNumber < sheet.getRows(); rowNumber++){
				Cell [] row = sheet.getRow(rowNumber);
				/*
				 * 跳过空行
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
	 * 生成简单的验证提示信息
	 * @param invalidCells
	 * @return
	 */
	public String toSimpleMessage(List invalidCells){
		if (invalidCells == null)
			return null;
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i < invalidCells.size(); i++){
			InvalidCell cell = (InvalidCell)invalidCells.get(i);
			buffer.append("第")
					.append(cell.getRow() + 1)
					.append("行第")
					.append(cell.getCol() + 1)
					.append("列");
			if (heads != null && heads.length > cell.getCol())
				buffer.append("（").append(heads[cell.getCol()]).append("）");
			buffer.append("：")
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
