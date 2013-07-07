package com.yzsystem.hr.dao;

import com.imti.framework.web.dao.BaseDao;
import com.yzsystem.hr.vo.HrEmployeeVO;

public interface HrDao extends BaseDao {

	public void deleteHrOrgById(String orgId);

	public void groupEmployee(String employeeId, String orgId);

	public void updateHrEmployee(HrEmployeeVO hrEmployee);

	public void insertHrEmployee(HrEmployeeVO hrEmployee);

}
