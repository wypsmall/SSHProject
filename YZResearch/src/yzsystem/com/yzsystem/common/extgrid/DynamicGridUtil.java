package com.yzsystem.common.extgrid;

import java.util.List;

import com.imti.framework.common.ImtiListUtil;
import com.imti.framework.common.StringUtil;

public class DynamicGridUtil {

	public static final StringBuffer createFields(int siteLength){
		StringBuffer jsonSb = new StringBuffer("");
		for(int index = 1; index <= siteLength; index++){
			jsonSb.append("{name :'fieldName" + index + "'}," );
		}
		if(StringUtil.isNotBlank(jsonSb.toString())){
			jsonSb.substring(0, jsonSb.length()-1);
		}
		return jsonSb;
	}
	public static final StringBuffer createFieldsWithTotal(int siteLength){
		StringBuffer jsonSb = new StringBuffer("");
		if(siteLength == 0){
			return jsonSb;
		}
		for(int index = 1; index <= siteLength; index++){
			jsonSb.append("{name :'fieldName" + index + "'}," );
		}
		jsonSb.append("{name :'fieldTotal'}" );//合计
		return jsonSb;
	}
	public static final StringBuffer createColumn(List<DynamicFieldInterface> srcList, DynamicFieldInterface total){
		StringBuffer jsonSb = new StringBuffer(" ");
		if(ImtiListUtil.isEmpty(srcList)){
			return jsonSb;
		}
		for(int index = 1; index <= srcList.size(); index++){
			DynamicFieldInterface dynamicField = srcList.get(index - 1);
			jsonSb.append("{").append("header :'<center>" + dynamicField.getFieldName() + "</center>',")
			  .append("autoWidth :true,")
			  .append("dataIndex :'fieldName" + index + "'")
			  .append("},");
		}
		if(null != total){
			jsonSb.append("{header :'<center><font color=\"red\">合计</font></center>',")
			  .append("autoWidth :true,")
			  .append("dataIndex :'fieldTotal',")
			  .append("}" );
		}
		return jsonSb;
	}
}
