package com.imti.framework.common.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.beanutils.BeanUtils;

import com.imti.framework.web.exception.FileException;

public class ExcelUtil {
	public static String DATA_TYPE_NUM = "0";
	public static String DATA_TYPE_STRING = "1";
	public static String DATA_TYPE_MONEY = "2";
	
	private static WritableCellFormat defaultFormat = new WritableCellFormat(new WritableFont(WritableFont.COURIER, 15, WritableFont.BOLD, false));
	private static WritableCellFormat stringCellFormat = new WritableCellFormat(new WritableFont(WritableFont.COURIER, 15, WritableFont.NO_BOLD, false));
	private static NumberFormat moneyFormat = new NumberFormat("#,##0.00");//金额格式 
	private static WritableCellFormat ltZeroMoneyCellFormat = new WritableCellFormat(
			new WritableFont(WritableFont.ARIAL,
					10, WritableFont.NO_BOLD,
					false,
					UnderlineStyle.NO_UNDERLINE,
					Colour.RED), moneyFormat);
	
	private static WritableCellFormat geZeroMoneyCellFormat = new WritableCellFormat(
			new WritableFont(WritableFont.ARIAL,
					10, WritableFont.NO_BOLD,
					false,
					UnderlineStyle.NO_UNDERLINE,
					Colour.BLUE), moneyFormat);
	
	private static WritableCellFormat numberCellFormat = new WritableCellFormat(
			new WritableFont(WritableFont.ARIAL,
					10, WritableFont.NO_BOLD,
					false,
					UnderlineStyle.NO_UNDERLINE,
					Colour.BLUE), new NumberFormat("0.00"));
	/**
	 * 导出Excel
	 * @param os
	 * @param objects
	 * @param heads 数据格式{属性名,列头显示名,数据类型}
	 * @throws DefaultException
	 */
	public static void writeExcel(OutputStream os, List objects, String[][] heads) {
		WritableWorkbook rwb = null;
		try {
			rwb = Workbook.createWorkbook(os);
			WritableSheet ws = rwb.createSheet("sheet1", 0);//创建sheet
			for (int i = 0; i < heads.length; i++){
				WritableCell cell = new Label(i, 0, heads[i][1], defaultFormat);
				try {
					ws.addCell(cell);
				} catch (RowsExceededException e) {
					throw new FileException(e);
				} catch (WriteException e) {
					throw new FileException(e);
				}
			}
			Iterator itObj = objects.iterator();
			int j = 1;
			while(itObj.hasNext()){
				Object object = itObj.next();
				for (int i = 0; i < heads.length; i++){
					Object value = BeanUtils.getProperty(object, heads[i][0]);
					WritableCell cell = null;
					String val = (value==null)?"":value.toString();
					if (DATA_TYPE_STRING.equals(heads[i][2])){
						cell = new Label(i, j, val, stringCellFormat);
					} else if (DATA_TYPE_MONEY.equals(heads[i][2])){
						double doubleValue = 0;
						try{
							doubleValue = ("".equals(val))?0:Double.parseDouble(val);
							if (doubleValue < 0) {// 金额小于零则用红色显示
								cell = new jxl.write.Number(i, j, doubleValue,ltZeroMoneyCellFormat);;
							} else {// 金额大于等于零则用蓝色显示
								cell = new jxl.write.Number(i, j, doubleValue,geZeroMoneyCellFormat);;
							}
						} catch(Exception ex){
							cell = new Label(i, j, "#VALUE", stringCellFormat);
						}
					} else if (DATA_TYPE_NUM.equals(heads[i][2])){
						double doubleValue = 0;
						try{
							doubleValue = ("".equals(val))?0:Double.parseDouble(val);
							cell = new jxl.write.Number(i, j, doubleValue, numberCellFormat);;
						} catch(Exception ex){
							cell = new Label(i, j, "#VALUE", stringCellFormat);
						}
					}
					try {
						ws.addCell(cell);
					} catch (RowsExceededException e) {
						throw new FileException(e);
					} catch (WriteException e) {
						throw new FileException(e);
					}
				}
				j++;
			}
			rwb.write();
		} catch (IOException e) {
			throw new FileException(e);
		} catch (IllegalAccessException e) {
			throw new FileException(e);
		} catch (InvocationTargetException e) {
			throw new FileException(e);
		} catch (NoSuchMethodException e) {
			throw new FileException(e);
		} finally{
			if (rwb != null){
				try {
					rwb.close();
				} catch (WriteException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
