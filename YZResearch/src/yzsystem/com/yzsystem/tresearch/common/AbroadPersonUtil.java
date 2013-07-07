package com.yzsystem.tresearch.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestUtils;

import com.imti.framework.common.StringUtil;
import com.yzsystem.tresearch.common.search.AbroadPersonSearch;

public class AbroadPersonUtil {
	public static final AbroadPersonSearch getSearch(HttpServletRequest request){
		AbroadPersonSearch search = new AbroadPersonSearch();
		search.setSr_apid(ServletRequestUtils.getIntParameter(request, "sr_apid", -1));
		search.setSr_cid(ServletRequestUtils.getIntParameter(request, "sr_cid", -1));
		search.setSr_cname(ServletRequestUtils.getStringParameter(request, "sr_cname", ""));
		search.setSr_ecid(ServletRequestUtils.getIntParameter(request, "sr_ecid", -1));
		search.setSr_eid(ServletRequestUtils.getIntParameter(request, "sr_eid", -1));
		search.setSr_ename(ServletRequestUtils.getStringParameter(request, "sr_ename", ""));
		search.setSr_eno(ServletRequestUtils.getStringParameter(request, "sr_eno", ""));
		return search;
	}
	
	public static String getExhComCondition(AbroadPersonSearch search) {
		StringBuffer sb = new StringBuffer();
		if(StringUtil.isNotBlank(search.getSr_eno())){
			sb.append(" and ex.eno='" + search.getSr_eno() + "'");
		}
		if(StringUtil.isNotBlank(search.getSr_ename())){
			sb.append(" and ex.ename like '%" + search.getSr_ename() + "%'");
		}
		if(StringUtil.isNotBlank(search.getSr_cname())){
			sb.append(" and cp.cname like '%" + search.getSr_cname() + "%'");
		}
		return sb.toString();
	}
	
	public static String getPersonsCondition(AbroadPersonSearch search) {
		StringBuffer sb = new StringBuffer();
		if(null != search.getSr_eid()){
			sb.append(" and ex.eid=" + search.getSr_eid());
		}
		if(null != search.getSr_cid()){
			sb.append(" and cp.cid=" + search.getSr_cid());
		}
		if(StringUtil.isNotBlank(search.getSr_apname())){
			sb.append(" and po.apname like '%" + search.getSr_apname() + "%'");
		}
		return sb.toString();
	}
}
