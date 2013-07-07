package com.yzsystem.tresearch.action;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;

import com.imti.framework.common.ext.ExtJSONUtil;
import com.imti.framework.web.action.BaseExtAction;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.yzsystem.tresearch.action.form.AbroadPersonForm;
import com.yzsystem.tresearch.common.AbroadPersonUtil;
import com.yzsystem.tresearch.common.search.AbroadPersonSearch;
import com.yzsystem.tresearch.service.AbroadPersonService;
import com.yzsystem.tresearch.vo.AbroadPersonVO;
import com.yzsystem.tresearch.vo.ExhComVO;

public class AbroadPersonAction extends BaseExtAction {

	@Override
	public AbroadPersonService getService() {
		return (AbroadPersonService)SpringBeanUtil.getBean("abroadPersonService");
	}

	
	public ActionForward listExhCom(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AbroadPersonSearch search = AbroadPersonUtil.getSearch(request);
		List<ExhComVO> dataList = getService().getExhComVOList(search);
		int totalCnt = getService().getExhComVOCount(search);
		ExtJSONUtil.gridWithPaged(dataList, totalCnt, response, null);
		return null;
	}
	
	public ActionForward listAbroadPersons(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AbroadPersonSearch search = AbroadPersonUtil.getSearch(request);
		List<AbroadPersonVO> dataList = getService().getPersonVOList(search);
		int totalCnt = getService().getPersonVOCount(search);
		ExtJSONUtil.gridWithPaged(dataList, totalCnt, response, null);
		return null;
	}
	

	public ActionForward insertAP(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AbroadPersonVO apVO = ((AbroadPersonForm)form).getApVO();
		try{
			getService().insertAbroadPerson(apVO);
			ExtJSONUtil.writeSuccessInfo(true, response, "保存成功！");
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage().replaceAll("java.lang.RuntimeException:", "").
					replaceAll("java.lang.NullPointerException:", ""));
		}
		return null;
	}

	public ActionForward updateAP(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AbroadPersonVO apVO = ((AbroadPersonForm)form).getApVO();
		try{
			getService().updateAbroadPerson(apVO);
			ExtJSONUtil.writeSuccessInfo(true, response, "保存成功！");
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage().replaceAll("java.lang.RuntimeException:", "").
					replaceAll("java.lang.NullPointerException:", ""));
		}
		return null;
	}
	
	public ActionForward exportAP(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean exp_sex = ServletRequestUtils.getBooleanParameter(request, "exp_sex", false);
		boolean exp_mobile = ServletRequestUtils.getBooleanParameter(request, "exp_mobile", false);
		boolean exp_tel = ServletRequestUtils.getBooleanParameter(request, "exp_tel", false);
		List<AbroadPersonVO> dataList = getService().exportPersonData();
		try{
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("sheet1");
			for (int i = 0; i < dataList.size(); i++) {
				int index = 0;
				AbroadPersonVO personVO = dataList.get(i);
				HSSFRow row = sheet.createRow(i);
				HSSFCell cell = row.createCell(index);
				cell.setCellValue(personVO.getApname());
				if(exp_sex) {
					index += 1;
					cell = row.createCell(index);
					cell.setCellValue(personVO.getApsex().toString());
				}
				if(exp_mobile) {
					index += 1;
					cell = row.createCell(index);
					cell.setCellValue(personVO.getApmobile());
				}
				if(exp_tel) {
					index += 1;
					cell = row.createCell(index);
					cell.setCellValue(personVO.getAptel());
				}
			}
			OutputStream os = response.getOutputStream();
			response.reset();
            response.setContentType("application/msexcel");// 定义输出类型 
            response.setHeader("Content-Disposition", "attachment; filename=export.xls");
            wb.write(os);
            os.flush();
    		os.close();
		}catch (Exception ex){
			log.info(ex.getMessage());
		}
		return null;
	}
}
