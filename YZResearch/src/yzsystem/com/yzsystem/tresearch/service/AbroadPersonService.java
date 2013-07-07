package com.yzsystem.tresearch.service;

import java.util.List;

import com.imti.framework.web.service.BaseService;
import com.yzsystem.tresearch.common.search.AbroadPersonSearch;
import com.yzsystem.tresearch.vo.AbroadPersonVO;
import com.yzsystem.tresearch.vo.ExhComVO;

public interface AbroadPersonService extends BaseService {
	public List<ExhComVO> getExhComVOList(AbroadPersonSearch search);
	public int getExhComVOCount(AbroadPersonSearch search);
	
	public List<AbroadPersonVO> getPersonVOList(AbroadPersonSearch search);
	public List<AbroadPersonVO> exportPersonData();
	public int getPersonVOCount(AbroadPersonSearch search);
	public void insertAbroadPerson(AbroadPersonVO person);
	public void updateAbroadPerson(AbroadPersonVO person);
}
