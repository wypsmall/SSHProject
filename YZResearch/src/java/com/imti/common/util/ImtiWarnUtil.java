package com.imti.common.util;

import com.imti.framework.common.DateUtil;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.sysmgr.service.WarnService;
import com.imti.sysmgr.vo.WarnVO;


public class ImtiWarnUtil {

	/**
	 * <p>保存系统错误信息：为及时维护提供依据
	 * 
	 * @param module  错误发生模块
	 * @param content 错误内容描述
	 * @param operator 操作人
	 * @author 曹刚 新增日期：2010-7-28下午06:01:22
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
