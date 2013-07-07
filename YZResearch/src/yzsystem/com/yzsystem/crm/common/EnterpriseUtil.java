package com.yzsystem.crm.common;

import javax.servlet.http.HttpServletRequest;

import com.yzsystem.crm.common.search.EnterpriseSearch;

public class EnterpriseUtil {

	public static final EnterpriseSearch getSearch(HttpServletRequest request) {
		EnterpriseSearch search = new EnterpriseSearch();
		return search;
	}

	public static String getCondition(EnterpriseSearch search) {
		// TODO Auto-generated method stub
		return null;
	}

}
