package com.yzsystem.exhibitions.service;

import java.util.List;

import com.imti.framework.web.service.BaseService;
import com.yzsystem.exhibitions.common.search.HostunitSearch;
import com.yzsystem.exhibitions.vo.HostunitVO;

public interface HostunitService extends BaseService {

	/**
	 * �������쵥λ
	 * 
	 * @param hostunit
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2013-4-17����09:28:03
	 * @since 1.0
	 */
	public void insetHostunit(HostunitVO hostunit);

	/**
	 * �޸����쵥λ����
	 * 
	 * @param hostunit
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2013-4-17����09:28:23
	 * @since 1.0
	 */
	public void updateHostunit(HostunitVO hostunit);

	/**
	 * ɾ�����쵥λ
	 * 
	 * @param hostUnitId
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2013-4-17����09:50:55
	 * @since 1.0
	 */
	public void deleteHostunit(String hostUnitId);

	/**
	 * ����������ȡ��δɾ���������쵥λ
	 * 
	 * @param search
	 * @return
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2013-4-17����09:53:43
	 * @since 1.0
	 */
	public List<HostunitVO> getValidHostunit(HostunitSearch search);

	/**
	 * ��������ͳ�ơ�δɾ���������쵥λ
	 * 
	 * @param search
	 * @return
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2013-4-17����09:54:27
	 * @since 1.0
	 */
	public int countValidHostunit(HostunitSearch search);

}
