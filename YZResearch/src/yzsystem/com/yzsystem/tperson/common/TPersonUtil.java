package com.yzsystem.tperson.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestUtils;

import com.imti.framework.common.StringUtil;
import com.yzsystem.tperson.common.search.TPersonSearch;

public class TPersonUtil {
	
	public static final TPersonSearch getTPersonSearch(HttpServletRequest request){
		TPersonSearch search = new TPersonSearch();
		search.setPersonId(ServletRequestUtils.getIntParameter(request, "personId", 0));
		search.setSr_personName(ServletRequestUtils.getStringParameter(request, "sr_personName", ""));
		return search;
	}
	public static final String getTPersonCondition(TPersonSearch search){
		StringBuffer whereHQL = new StringBuffer();

		if (!Integer.valueOf(0).equals(search.getPersonId())) {
			whereHQL.append(" and po.personId=" + search.getPersonId() + "");
		}
		if(StringUtil.isNotBlank(search.getSr_personName())){
			whereHQL.append(" and po.personName like '%" + search.getSr_personName() + "%'");
		}
		return whereHQL.toString();
	}
}
