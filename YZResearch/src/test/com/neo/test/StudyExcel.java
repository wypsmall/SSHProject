package com.neo.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

public class StudyExcel {
	private static String filePath = "D:\\Code\\Code_J2ee_e34\\YZResearch\\src\\test\\";
	private static String readFile = "study01.xls";
	private static String writeFile = "study-w.xls";
	
	public static void main(String[] args) {
		try {
			//readFile();
			//setFont();
			//setBackground();
			//setBorder();
			getCombineCell();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void getCombineCell() throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath+readFile));
		HSSFSheet sheet = wb.getSheetAt(0);
		int sheetmergerCount = sheet.getNumMergedRegions();
		List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
		for (int i = 0; i < sheetmergerCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			list.add(ca);
		}
		int firstC = 0;
		int lastC = 0;
		int firstR = 0;
		int lastR = 0;
		String cellValue = null;
		for (Iterator<Row> iter = (Iterator<Row>) sheet.rowIterator(); iter.hasNext();) {
			Row row = iter.next();
			for (Iterator<Cell> iter2 = (Iterator<Cell>) row.cellIterator(); iter2.hasNext();) {
				Cell cell = iter2.next();
				cellValue = "";
				for (CellRangeAddress ca : list) {
					firstC = ca.getFirstColumn();
					lastC = ca.getLastColumn();
					firstR = ca.getFirstRow();
					lastR = ca.getLastRow();
					if (cell.getColumnIndex() <= lastC && cell.getColumnIndex() >= firstC) {
						if (cell.getRowIndex() <= lastR && cell.getRowIndex() >= firstR) {
							Row fRow = sheet.getRow(firstR);
							Cell fCell = fRow.getCell(firstC);
							cellValue = fCell.getStringCellValue();
							break;
						}
					} else {
						cellValue = "";
					}
				}
				System.out.println(cellValue);
			}
		}
	
	}
	private static void setFont() throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath+readFile));
		HSSFSheet sheet = wb.getSheetAt(0);
		
		HSSFRow row = sheet.getRow(0);
	    HSSFCell cell = row.getCell(0);
	    
	    HSSFFont font = wb.createFont();
	    font.setFontName("Verdana");
	    font.setBoldweight((short) 100);
	    font.setFontHeight((short) 300);
	    font.setColor(HSSFColor.BLUE.index);
	    HSSFCellStyle style = wb.createCellStyle();
	    style.setFont(font);
	    cell.setCellStyle(style);
	    
		FileOutputStream os = new FileOutputStream(filePath+writeFile);
		wb.write(os);
		os.close();
	}
	private static void setBorder() throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath+readFile));
		HSSFSheet sheet = wb.getSheetAt(0);
		
		HSSFRow row = sheet.getRow(0);
	    HSSFCell cell = row.getCell(2);
	    
	    
	    HSSFCellStyle style = wb.createCellStyle();
        
	    style.setBottomBorderColor(HSSFColor.GREEN.index);
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    
	    style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM_DASHED);
	    style.setLeftBorderColor(HSSFColor.ORANGE.index);
	    
	    style.setRightBorderColor(HSSFColor.YELLOW.index);
	    style.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
	    
	    style.setTopBorderColor(HSSFColor.PINK.index);
	    style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
	    if(null == cell)
	    	cell = row.createCell(2);
	    cell.setCellStyle(style);
	    
		FileOutputStream os = new FileOutputStream(filePath+writeFile);
		wb.write(os);
		os.close();		
	}
	private static void setBackground() throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath+readFile));
		HSSFSheet sheet = wb.getSheetAt(0);
		
		HSSFRow row = sheet.getRow(0);
	    HSSFCell cell = row.getCell(1);
	    
	    HSSFFont font = wb.createFont();
	    font.setFontName("Verdana");
	    font.setBoldweight((short) 100);
	    font.setFontHeight((short) 300);
	    font.setColor(HSSFColor.BLUE.index);
	    
	    CellStyle style = wb.createCellStyle();
	    style.setFillForegroundColor(HSSFColor.RED.index);
	    //这个一定要设置
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cell.setCellStyle(style);
	    
		FileOutputStream os = new FileOutputStream(filePath+writeFile);
		wb.write(os);
		os.close();		
	}
	private static void readFile() throws Exception {
//		URL url = StudyExcel.class.getClassLoader().getResource("study01.xls");
//		File file = new File(url.getPath());
//		if (!file.exists()) {
//			System.out.println("文件不存在！");
//			return;
//		}
		
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath+readFile));
/*		HSSFSheet sheet = wb.getSheetAt(0);
	    HSSFRow row = sheet.getRow(0);
	    HSSFCell cell = row.getCell(0);
	    String msg = cell.getStringCellValue();
	    System.out.println("=>"+msg);*/
		HSSFSheet sheet = wb.getSheetAt(1);
		int x = 0;
		
		for (Iterator<Row> iter = (Iterator<Row>) sheet.rowIterator(); iter.hasNext();) {
			Row row = iter.next();
			int y = 0;
			for (Iterator<Cell> iter2 = (Iterator<Cell>) row.cellIterator(); iter2.hasNext();) {
				Cell cell = iter2.next();
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				String content = cell.getStringCellValue();
				if(content.indexOf("A")!=-1)
					System.out.println("[x:" + x + "-y:" + y + "]=" + content);
//				if(content.indexOf("A")!=-1)
//					cell.setCellValue(content+"公司");
				y++;
			}
			x++;
		}
/*		
		FileOutputStream os = new FileOutputStream(filePath+writeFile);
		wb.write(os);
		os.close();*/
	}
}
