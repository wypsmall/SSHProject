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
		hostunit.setHostUnitName(CodeUtil.decode(request.getParameter("hostUnitName")));//��λ����
		hostunit.setPostalcode(CodeUtil.decode(request.getParameter("postalcode"))) ;//�ʱ�
		hostunit.setAddress(CodeUtil.decode(request.getParameter("address")));//ͨѶ��ַ
		hostunit.setContact(CodeUtil.decode(request.getParameter("contact")));//��ϵ��
		hostunit.setPostName(CodeUtil.decode(request.getParameter("postName")));//ְ��
		hostunit.setTelephone(CodeUtil.decode(request.getParameter("telephone")));//��ϵ�ֻ�
		hostunit.setContactEmail(CodeUtil.decode(request.getParameter("contactEmail")));//��ϵ������
		hostunit.setOfficeTel(CodeUtil.decode(request.getParameter("officeTel")));//�칫�绰
		hostunit.setOfficeFax(CodeUtil.decode(request.getParameter("officeFax")));//�칫����
		hostunit.setWebSite(CodeUtil.decode(request.getParameter("webSite")));//��˾��ַ
		hostunit.setRemark(CodeUtil.decode(request.getParameter("remark")));//��ע
		hostunit.setEnterUser(StringUtil.isNotBlank(enterUser) ? enterUser : ImtiUtil.getUserName(request)) ;//¼��Ա
		hostunit.setEnterDate(StringUtil.isNotBlank(enterDate) ? enterDate : DateUtil.getCurrentDateTime()) ;//¼��ʱ��
		hostunit.setLastModifyUser(ImtiUtil.getUserName(request));//���һ���޸���
		hostunit.setLastModifyDate(DateUtil.getCurrentDateTime()) ;//���һ���޸�ʱ��
		hostunit.setDelFlag(StringUtil.isEmpty(delFlag) ? "1" : delFlag) ;//1��ʾ��δɾ����
		return hostunit;
	}

	public static final HostunitSearch getHostunitSearch(HttpServletRequest request) {
		HostunitSearch search = new HostunitSearch();
		search.setHostUnitName(CodeUtil.decode(request.getParameter("hostUnitName")));//��λ����
		search.setEnterUser(CodeUtil.decode(request.getParameter("enterUser"))) ;//¼��Ա
		search.setLastModifyUser(CodeUtil.decode(request.getParameter("lastModifyUser")));//���һ���޸���
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
	 * У�����쵥λ�еĲ���Ϊ�շ�
	 * 
	 * @param hostunit
	 * @author �ܸ� �������ڣ�2013-4-17����09:59:02
	 * @since 1.0
	 */
	public static void validParams(HostunitVO hostunit) {
		if(StringUtil.isEmpty(hostunit.getHostUnitName())){
			throw new RuntimeException("���쵥λ���Ʋ���Ϊ�գ�");
		}
	}
}
