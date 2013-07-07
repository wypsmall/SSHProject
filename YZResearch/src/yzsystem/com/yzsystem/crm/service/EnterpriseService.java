package com.yzsystem.crm.service;

import java.util.List;

import com.imti.framework.web.service.BaseService;
import com.yzsystem.crm.common.search.EnterpriseSearch;
import com.yzsystem.crm.vo.EnterpriseVO;

public interface EnterpriseService extends BaseService {

	/**
	 * ��ѯ��ҵ����
	 * 
	 * @param search
	 * @return
	 * @author �ܸ� �������ڣ�2013-5-16����10:34:07
	 * @since 1.0
	 */
	public List<EnterpriseVO> getEnterpriseList(EnterpriseSearch search);

	/**
	 * ͳ����ҵ��������
	 * 
	 * @param search
	 * @return
	 * @author �ܸ� �������ڣ�2013-5-16����10:34:30
	 * @since 1.0
	 */
	public int countEnterprise(EnterpriseSearch search);

}
