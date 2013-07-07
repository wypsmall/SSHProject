package com.yzsystem.exhibitions.service;

import java.util.List;

import com.imti.framework.web.service.BaseService;
import com.yzsystem.exhibitions.common.search.ExhProSearch;
import com.yzsystem.exhibitions.vo.ExhibitionEnterpriseVO;

public interface ExhProjEnterpriseService extends BaseService {

	/**
	 * ���Ҳ�չ��ҵ����
	 * 
	 * @param search
	 * @return
	 * @author �ܸ� �������ڣ�2013-5-16����11:22:01
	 * @since 1.0
	 */
	public List<ExhibitionEnterpriseVO> getExhEnterpriseList(ExhProSearch search);

	/**
	 * ͳ�Ʋ�չ��ҵ����
	 * 
	 * @param search
	 * @return
	 * @author �ܸ� �������ڣ�2013-5-16����11:22:26
	 * @since 1.0
	 */
	public int countExhEnterprise(ExhProSearch search);

}
