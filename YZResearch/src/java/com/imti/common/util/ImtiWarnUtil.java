package com.imti.common.util;

import com.imti.framework.common.DateUtil;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.sysmgr.service.WarnService;
import com.imti.sysmgr.vo.WarnVO;


public class ImtiWarnUtil {

	/**
	 * <p>����ϵͳ������Ϣ��Ϊ��ʱά���ṩ����
	 * 
	 * @param module  ������ģ��
	 * @param content ������������
	 * @param operator ������
	 * @author �ܸ� �������ڣ�2010-7-28����06:01:22
	 * @since 1.0
	 */
	@Deprecated
	public static void saveWarn(String module, String content, String operator){
		WarnService warnService = (WarnService)SpringBeanUtil.getBean("warnService");
		 WarnVO warn = new  WarnVO();
		 warn.setContent(content);
		 warn.setModule(module);
		 warn.setCreateDate(DateUtil.getCurrentDate());
		 warn.setIsHandle(WarnVO.HANDLE_NO);
		 warn.setOperator(operator);
		 warnService.insert(warn);
	}
}
