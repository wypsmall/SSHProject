package com.yzsystem.sysparam.service;

import java.util.List;

import com.imti.framework.web.service.BaseService;
import com.yzsystem.sysparam.vo.SysParamVO;

public interface SysParamService extends BaseService {

	/**
	 * ��ȡ��������
	 * @return
	 */
	public List<SysParamVO> getTransParam();
	/**
	 * ��ȡ���ģ��
	 * @return
	 */
	public List<SysParamVO> getAdvertisementJournal();
	/**
	 * ��ȡ�ι�ָ��
	 * @return
	 */
	public List<SysParamVO> getAdvertisementVisit();
}
