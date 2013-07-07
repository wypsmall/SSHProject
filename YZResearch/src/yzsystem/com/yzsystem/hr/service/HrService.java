package com.yzsystem.hr.service;

import com.imti.framework.web.service.BaseService;
import com.yzsystem.hr.vo.HrEmployeeVO;
import com.yzsystem.hr.vo.HrOrgVO;

public interface HrService extends BaseService {

	/**
	 * 新增部门资料
	 * 
	 * @param hrOrg
	 * @see (此处写相关联的一些类名，如果没有，请去掉此注释，如果多于一个，请换行新增一个@see）
	 * @author 曹刚 新增日期：2013-4-19下午04:10:48
	 * @since 1.0
	 */
	public void insertHrOrg(HrOrgVO hrOrg);

	/**
	 * 修改部门资料
	 * 
	 * @param hrOrg
	 * @see (此处写相关联的一些类名，如果没有，请去掉此注释，如果多于一个，请换行新增一个@see）
	 * @author 曹刚 新增日期：2013-4-19下午04:10:57
	 * @since 1.0
	 */
	public void updateHrOrg(HrOrgVO hrOrg);

	/**
	 * 删除部门资料
	 * 
	 * @param orgId
	 * @see (此处写相关联的一些类名，如果没有，请去掉此注释，如果多于一个，请换行新增一个@see）
	 * @author 曹刚 新增日期：2013-4-19下午04:11:05
	 * @since 1.0
	 */
	public void deleteHrOrgById(String orgId);

	/**
	 * 新增职员资料
	 * @param hrEmployee
	 */
	public void insertHrEmployee(HrEmployeeVO hrEmployee);

	/**
	 * 修改职员资料
	 * @param hrEmployee
	 */
	public void updateHrEmployee(HrEmployeeVO hrEmployee);

	/**
	 * 删除职员资料
	 * @param employeeId
	 */
	public void deleteHrEmployee(String employeeId);

	/**
	 * 调岗（形成了历史的履历表）
	 * @param employeeId
	 * @param destOrgId
	 * @param employeePostName
	 * @param memo
	 */
	public void transPosition(String employeeId, String destOrgId, String employeePostName, String memo);

	/**
	 * 职员分组
	 * 
	 * @param employeeId
	 * @param orgId
	 * @see (此处写相关联的一些类名，如果没有，请去掉此注释，如果多于一个，请换行新增一个@see）
	 * @author 曹刚 新增日期：2013-4-22下午04:58:54
	 * @since 1.0
	 */
	public void groupEmployee(String employeeId, String orgId);
}
