package test.com.imti.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelTest {

	/**
	 * 
	 * @param args
	 * @author �ܸ� �������ڣ�2011-5-16����09:47:43
	 * @since 1.0
	 */
	public static void main(String[] args) {
		ExcelTest excel = new ExcelTest();
		try {
			excel.createExcel(new FileOutputStream("E://hello.xls"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * (�˴�д����������������<br />��������<p/>��
	 * 
	 * @param os
	 * @throws IOException
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2011-5-16����05:12:27
	 * @since 1.0
	 */
	public void createExcel(OutputStream os) throws IOException {
		// create Excel
		WritableWorkbook wb = Workbook.createWorkbook(os);
		// create sheet 1 total stock
		WritableSheet sheet = wb.createSheet("���̴�", 0);
		WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.GREEN);
		WritableCellFormat wcfFc = new WritableCellFormat(wfc);
		WritableFont wfcTotal = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.RED);
		WritableCellFormat wcfFcTotal = new WritableCellFormat(wfcTotal);
		WritableFont wfcContent = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat wcfFcContent = new WritableCellFormat(wfcContent);
		
		try {
			WritableCellFormat wrappedText = new WritableCellFormat(WritableWorkbook.ARIAL_10_PT);
			wrappedText.setWrap(true);// �ɻ��е�label��ʽ 
			//wrappedText.setBackground(Colour.PALE_BLUE);
			wrappedText.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
			Label labelC = new Label(1, 1, "        ��Ʒ\012���", wrappedText);
			
			wcfFcContent.setAlignment(Alignment.RIGHT);
			wcfFcTotal.setAlignment(Alignment.RIGHT);
			wcfFc.setAlignment(Alignment.CENTRE);
			wcfFc.setShrinkToFit(true);
			wcfFc.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
			wcfFcContent.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
			wcfFcTotal.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
			sheet.addCell(labelC);
			// create head
			List<Merchandise> headList = DataFactory.getMerchandise();
			for (int i = 0; i < headList.size(); i++) {
				Merchandise merchaise = (Merchandise) headList.get(i);
				
				Label label = new Label(1 + (i + 1), 1, merchaise.getMerchandiseName(), wcfFc);
				sheet.addCell(label);
			}
			// ������һ���ϼ���
			Label label = new Label(1 + (headList.size() + 1), 1, "�ϼ�", wcfFcTotal);
			sheet.addCell(label);

			// fill data
			Map<String, String> stockMap = DataFactory.getStock();

			List<Warehouse> warehouseList = DataFactory.getWarehouse();
			for (int j = 0; j < warehouseList.size(); j++) {
				Warehouse warehouse = (Warehouse) warehouseList.get(j);
				Label rowHead = new Label(1, 1 + (j + 1), warehouse.getWarehouseName(), wcfFc);
				sheet.addCell(rowHead);
				long total = 0;
				
				for (int i = 0; i < headList.size(); i++) {
					Merchandise merchaise = (Merchandise) headList.get(i);
					String num = "0";
					if (stockMap.get(merchaise.getMerchandiseId() + "_" + warehouse.getWarehouseId()) != null) {
						num = String.valueOf(stockMap.get(merchaise.getMerchandiseId() + "_" + warehouse.getWarehouseId()));
					}
					
					total = Long.valueOf(total) + Long.valueOf(num);
					Label warehouseLabel = new Label(1 + (i + 1), 1 + (j + 1),num, wcfFcContent);
					sheet.addCell(warehouseLabel);
				}
				//����ϼ�
				Label columnTotal = new Label(1 + (headList.size() + 1), 1 + (j + 1),String.valueOf(total), wcfFcTotal);
				sheet.addCell(columnTotal);
			}

			//����ϼ�
			Label rowTotalLabel = new Label(1, 1 + (warehouseList.size() + 1),"�ܼ�", wcfFcTotal);
			sheet.addCell(rowTotalLabel);
			for (int i = 0; i <= headList.size(); i++) {
				insertFormula(sheet, 1 + (i + 1), 1 + (warehouseList.size() + 1), 
						"sum("+DataFactory.getCharacters(i)+"3:" + DataFactory.getCharacters(i)+ (1 + (warehouseList.size() + 1))+")", wcfFcTotal);
			}
			
			Map<String, SheetContentObject> sheetContentObjectMap = DataFactory.getSheetContent();
			
			//ѭ������ÿ��sheet
			for (int i = 0; i < headList.size(); i++) {
				Merchandise merchaise = headList.get(i);
				WritableSheet sheetChild = wb.createSheet(merchaise.getMerchandiseName(), i + 1);
				//�ϲ���Ԫ�� head
				WritableFont headFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
				WritableCellFormat headFormat = new WritableCellFormat(headFont);
				headFormat.setAlignment(Alignment.CENTRE);
				headFormat.setBackground(Colour.PINK);
				headFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
				sheetChild.addCell(new Label(1, 1, merchaise.getMerchandiseName()+"�����ϸ", headFormat)); 
				sheetChild.mergeCells(1, 1, 5, 1);
				
				//дÿ��sheet������
//				SheetContentObject contentObject = sheetContentObjectMap.get(merchaise.getMerchandiseId());
//				List list = contentObject.getDetailList();
			}
			
			//���ó�����
			for (int i = 1; i <= headList.size(); i++) {
				Merchandise merchaise = headList.get(i-1);
				sheet.addHyperlink(new WritableHyperlink(1+i,1,merchaise.getMerchandiseName(),wb.getSheet(merchaise.getMerchandiseName()),0,0));
			}
			
			
			wb.write();
			wb.close();
			os.flush();
			os.close();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}

		
	}
	public void insertFormula(WritableSheet sheet, Integer col, Integer row,
			String formula, WritableCellFormat format) {
		try {
			Formula f = new Formula(col, row, formula, format);
			sheet.addCell(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
