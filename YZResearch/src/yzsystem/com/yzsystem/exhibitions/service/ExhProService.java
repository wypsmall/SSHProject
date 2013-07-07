package com.yzsystem.exhibitions.service;

import java.util.List;

import com.imti.framework.web.service.BaseService;
import com.yzsystem.exhibitions.common.search.ExhProSearch;
import com.yzsystem.exhibitions.vo.ExhProModuleVO;
import com.yzsystem.exhibitions.vo.ExhibitionProjectVO;

public interface ExhProService extends BaseService {

	/**
	 * ������չ��Ŀ���ṩ��ͬ��ģ����ã�
	 * @param exhibitionProject
	 */
	public void insertExhPro(ExhibitionProjectVO exhibitionProject);
	/**
	 * �޸Ļ�չ��Ŀ
	 * @param exhibitionProject
	 */
	public void updateExhPro(ExhibitionProjectVO exhibitionProject);
	/**
	 * չ���б�
	 * 
	 * @param search
	 * @return
	 * @author �ܸ� �������ڣ�2013-5-14����01:34:45
	 * @since 1.0
	 */
	public List<ExhibitionProjectVO> getExhProList(ExhProSearch search);
	/**
	 * չ��ͳ��
	 * 
	 * @param search
	 * @return
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2013-5-14����01:34:54
	 * @since 1.0
	 */
	public int countExhPro(ExhProSearch search);
	/**
	 * ���û�չ��Ŀģ��
	 * 
	 * @param hostunit
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2013-5-14����05:25:07
	 * @since 1.0
	 */
	public void saveExhProModule(ExhProModuleVO mod);
	/**
	 * ��ȡչ����Ŀ��Ӧ�ķ���ģ��
	 * @param search
	 * @return
	 */
	public List<ExhProModuleVO> getExhProTransList(ExhProSearch search);
	/**
	 * ��ȡ���ģ���б�
	 * @param search
	 * @return
	 */
	public List<ExhProModuleVO> getExhProAdvList(ExhProSearch search);
	/**
	 * ɾ��ģ��
	 * 
	 * @param exhProMododuleId
	 * @author �ܸ� �������ڣ�2013-5-15����10:51:37
	 * @since 1.0
	 */
	public void deleteExhProModule(String exhProModuleId);
	/**
	 * �ύģ��
	 * 
	 * @param exhProMododuleId
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2013-5-15����11:06:23
	 * @since 1.0
	 */
	public void submitExhProModule(String exhProModuleId);
}
