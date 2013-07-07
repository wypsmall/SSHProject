package com.yzsystem.exhibitions.common;

import javax.servlet.http.HttpServletRequest;

import com.imti.common.util.CodeUtil;
import com.imti.common.util.ImtiUtil;
import com.imti.framework.common.DateUtil;
import com.imti.framework.common.StringUtil;
import com.yzsystem.exhibitions.common.search.HostunitSearch;
import com.yzsystem.exhibitions.vo.HostunitVO;

public final class HostunitUtil {

	public static final HostunitVO getHostunit(HttpServletRequest request){
		HostunitVO hostunit = new HostunitVO();
		String enterUser = CodeUtil.decode(request.getParameter("enterUser"));
		String enterDate = request.getParameter("enterDate");
		String delFlag = request.getParameter("delFlag");
		hostunit.setHostUnitId(CodeUtil.decode(request.getParameter("hostUnitId")));
		hostunit.setHostUnitName(CodeUtil.decode(request.getParameter("hostUnitName")));//单位名称
		hostunit.setPostalcode(CodeUtil.decode(request.getParameter("postalcode"))) ;//邮编
		hostunit.setAddress(CodeUtil.decode(request.getParameter("address")));//通讯地址
		hostunit.setContact(CodeUtil.decode(request.getParameter("contact")));//联系人
		hostunit.setPostName(CodeUtil.decode(request.getParameter("postName")));//职务
		hostunit.setTelephone(CodeUtil.decode(request.getParameter("telephone")));//联系手机
		hostunit.setContactEmail(CodeUtil.decode(request.getParameter("contactEmail")));//联系人邮箱
		hostunit.setOfficeTel(CodeUtil.decode(request.getParameter("officeTel")));//办公电话
		hostunit.setOfficeFax(CodeUtil.decode(request.getParameter("officeFax")));//办公传真
		hostunit.setWebSite(CodeUtil.decode(request.getParameter("webSite")));//公司网址
		hostunit.setRemark(CodeUtil.decode(request.getParameter("remark")));//备注
		hostunit.setEnterUser(StringUtil.isNotBlank(enterUser) ? enterUser : ImtiUtil.getUserName(request)) ;//录入员
		hostunit.setEnterDate(StringUtil.isNotBlank(enterDate) ? enterDate : DateUtil.getCurrentDateTime()) ;//录入时间
		hostunit.setLastModifyUser(ImtiUtil.getUserName(request));//最后一次修改人
		hostunit.setLastModifyDate(DateUtil.getCurrentDateTime()) ;//最后一次修改时间
		hostunit.setDelFlag(StringUtil.isEmpty(delFlag) ? "1" : delFlag) ;//1表示“未删除”
		return hostunit;
	}

	public static final HostunitSearch getHostunitSearch(HttpServletRequest request) {
		HostunitSearch search = new HostunitSearch();
		search.setHostUnitName(CodeUtil.decode(request.getParameter("hostUnitName")));//单位名称
		search.setEnterUser(CodeUtil.decode(request.getParameter("enterUser"))) ;//录入员
		search.setLastModifyUser(CodeUtil.decode(request.getParameter("lastModifyUser")));//最后一次修改人
		search.setStart(request.getParameter("start"));
		search.setLimit(request.getParameter("limit"));
		return search;
	}

	public static final String getHostunitCondition(HostunitSearch search) {
		StringBuffer whereHQL = new StringBuffer("");
		if(StringUtil.isNotBlank(search.getHostUnitName())){
			whereHQL.append(" and po.hostUnitName like '%"+search.getHostUnitName()+"%'");
		}
		if(StringUtil.isNotBlank(search.getEnterUser())){
			whereHQL.append(" and po.enterUser like '%"+search.getEnterUser()+"%'");
		}
		if(StringUtil.isNotBlank(search.getLastModifyUser())){
			whereHQL.append(" and po.lastModifyUser like '%"+search.getLastModifyUser()+"%'");
		}
		return whereHQL.toString();
	}

	/**
	 * 校验主办单位中的参数为空否
	 * 
	 * @param hostunit
	 * @author 曹刚 新增日期：2013-4-17上午09:59:02
	 * @since 1.0
	 */
	public static void validParams(HostunitVO hostunit) {
		if(StringUtil.isEmpty(hostunit.getHostUnitName())){
			throw new RuntimeException("主办单位名称不能为空！");
		}
	}
}
