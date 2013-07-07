package com.yzsystem.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import com.imti.framework.common.StringUtil;
import com.imti.framework.common.StringUtils;
import com.imti.framework.web.environment.utils.ServletUtil;
import com.yzsystem.common.constant.XlsTemplatesEnum;

public class ExtXLSTransformer extends XLSTransformer {

	private HttpServletResponse response;
	@SuppressWarnings("unchecked")
	private Map beanParams;
	private XlsTemplatesEnum xlsTemplatesEnum;
	private String fileName;
	
	@SuppressWarnings("unchecked")
	public ExtXLSTransformer(XlsTemplatesEnum xlsTemplatesEnum, Map beanParams, HttpServletResponse response, String fileName){
		this.xlsTemplatesEnum = xlsTemplatesEnum;
		this.beanParams = beanParams;
		this.response = response;
		this.fileName = fileName;
	}
	public void transformXLS() throws ParsePropertyException, IOException{
		String realPath = ServletUtil.SERVLET_CONTEXT_REAL_PATH;
        realPath = realPath.replaceAll("\\\\", "/");
		OutputStream os = response.getOutputStream();
		fileName = StringUtil.isEmpty(fileName)? xlsTemplatesEnum.fileName: fileName;
		try{
            response.reset();
            response.setContentType("application/msexcel");// 定义输出类型 
            response.setHeader("Content-Disposition", "attachment; filename=" + StringUtils.gbkToIso88591Encoding(fileName));
            InputStream is = new BufferedInputStream(new FileInputStream(realPath + xlsTemplatesEnum.temlpates));
    		Workbook workbook = transformXLS(is, beanParams);
    		workbook.write(os);
    		is.close();
    		os.flush();
    		os.close();
		}catch (Exception ex){
			log.info(ex.getMessage());
		}
	}
}
