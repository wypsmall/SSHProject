package com.yzsystem.hr.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.imti.framework.common.StringUtil;
import com.imti.framework.component.vopomapping.utils.POUtil;
import com.imti.framework.web.dao.impl.BaseDaoImpl;
import com.yzsystem.hr.dao.HrDao;
import com.yzsystem.hr.po.HrEmployeePO;
import com.yzsystem.hr.vo.HrEmployeeVO;

public class HrDaoImpl extends BaseDaoImpl implements HrDao {

	/**
	 * 说明清楚此属性的业务含义
	 */
	private static final long serialVersionUID = -4402614761608301251L;

	@Override
	public void deleteHrOrgById(final String orgId) {
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = " update com.yzsystem.hr.po.HrOrgPO  po  set po.delFlag=0 where po.orgId=?";
				Query query = session.createQuery(hql);
				query.setString(0, orgId);
				query.executeUpdate();
				return null;
			}
		});
	}

	@Override
	public void groupEmployee(final String employeeId, final String orgId) {
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = " update com.yzsystem.hr.po.HrEmployeePO  po  set po.org.orgId=? where po.employeeId=?";
				Query query = session.createQuery(hql);
				query.setString(0, orgId);
				query.setString(1, employeeId);
				query.executeUpdate();
				return null;
			}
		});
	}

	@Override
	public void insertHrEmployee(HrEmployeeVO hrEmployee) {
		HrEmployeePO po = (HrEmployeePO)POUtil.copyVoToPo(hrEmployee);
		if(StringUtil.isEmpty(hrEmployee.getOrgId())){
			po.setOrg(null);
			insert(po);
		}else {
			insert(po);
		}
	}

	@Override
	public void updateHrEmployee(HrEmployeeVO hrEmployee) {
		HrEmployeePO po = (HrEmployeePO)POUtil.copyVoToPo(hrEmployee);
		if(StringUtil.isBlank(hrEmployee.getOrgId())){
			po.setOrg(null);
			update(po);
		}else {
			update(po);
		}
	}

}
