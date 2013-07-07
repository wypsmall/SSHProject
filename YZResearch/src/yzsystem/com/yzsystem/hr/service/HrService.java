package com.yzsystem.hr.service;

import com.imti.framework.web.service.BaseService;
import com.yzsystem.hr.vo.HrEmployeeVO;
import com.yzsystem.hr.vo.HrOrgVO;

public interface HrService extends BaseService {

	/**
	 * ������������
	 * 
	 * @param hrOrg
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2013-4-19����04:10:48
	 * @since 1.0
	 */
	public void insertHrOrg(HrOrgVO hrOrg);

	/**
	 * �޸Ĳ�������
	 * 
	 * @param hrOrg
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2013-4-19����04:10:57
	 * @since 1.0
	 */
	public void updateHrOrg(HrOrgVO hrOrg);

	/**
	 * ɾ����������
	 * 
	 * @param orgId
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2013-4-19����04:11:05
	 * @since 1.0
	 */
	public void deleteHrOrgById(String orgId);

	/**
	 * ����ְԱ����
	 * @param hrEmployee
	 */
	public void insertHrEmployee(HrEmployeeVO hrEmployee);

	/**
	 * �޸�ְԱ����
	 * @param hrEmployee
	 */
	public void updateHrEmployee(HrEmployeeVO hrEmployee);

	/**
	 * ɾ��ְԱ����
	 * @param employeeId
	 */
	public void deleteHrEmployee(String employeeId);

	/**
	 * ���ڣ��γ�����ʷ��������
	 * @param employeeId
	 * @param destOrgId
	 * @param employeePostName
	 * @param memo
	 */
	public void transPosition(String employeeId, String destOrgId, String employeePostName, String memo);

	/**
	 * ְԱ����
	 * 
	 * @param employeeId
	 * @param orgId
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2013-4-22����04:58:54
	 * @since 1.0
	 */
	public void groupEmployee(String employeeId, String orgId);
}
