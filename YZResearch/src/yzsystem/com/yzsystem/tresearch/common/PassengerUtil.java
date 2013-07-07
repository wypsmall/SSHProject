package com.yzsystem.tresearch.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestUtils;

import com.yzsystem.tresearch.common.search.PassengerSearch;

public class PassengerUtil {
	
	public static final PassengerSearch getPassengerSearch(HttpServletRequest request){
		PassengerSearch search = new PassengerSearch();
		search.setId(ServletRequestUtils.getIntParameter(request, "id", 0));
		return search;
	}
	public static final String getPassengerCondition(PassengerSearch search){
		StringBuffer whereHQL = new StringBuffer();

		if (!Integer.valueOf(0).equals(search.getId())) {
			whereHQL.append(" and po.id=" + search.getId() + "");
		}

		return whereHQL.toString();
	}
}
