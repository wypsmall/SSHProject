package com.yzsystem.sysparam.service;

import java.util.List;

import com.imti.framework.web.service.BaseService;
import com.yzsystem.sysparam.vo.SysParamVO;

public interface SysParamService extends BaseService {

	/**
	 * 获取翻译种类
	 * @return
	 */
	public List<SysParamVO> getTransParam();
	/**
	 * 获取广告模板
	 * @return
	 */
	public List<SysParamVO> getAdvertisementJournal();
	/**
	 * 获取参观指南
	 * @return
	 */
	public List<SysParamVO> getAdvertisementVisit();
}
