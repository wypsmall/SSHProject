package com.yzsystem.tresearch.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.neo.common.dao.impl.AirBaseDaoImpl;
import com.neo.common.exception.BaseException;
import com.neo.common.model.IDomainObject;
import com.yzsystem.tresearch.dao.IFlowAppDao;
import com.yzsystem.tresearch.po.FlowAppDO;
import com.yzsystem.tresearch.vo.FlowAppVO;

public class FlowAppDaoImpl extends AirBaseDaoImpl implements IFlowAppDao {

	@Override
	public List<IDomainObject> getFlowPage(FlowAppVO flowAppVO, Integer start, Integer pagesize) {
		try {
			StringBuffer buf = new StringBuffer("FROM FlowAppDO fd where ");
			buf.append(getWhereHql(flowAppVO));
			buf.append(" ORDER BY fd.flwId desc");
			Query queryObject = getSession().createQuery(buf.toString());
			queryObject.setFirstResult(start);
			queryObject.setMaxResults(pagesize);
			queryObject.setCacheable(false);
			return queryObject.list();
		} catch (Exception e) {
			BaseException.ThrowException(e.getMessage());
		}
		return null;
	}
	private String getWhereHql(FlowAppVO flowAppVO) {
		StringBuffer buf = new StringBuffer();
		if(flowAppVO.getFlwProposerId()!=null)
			buf.append(" fd.flwProposer.fid ='"+flowAppVO.getFlwProposerId()+"' and");
		if(flowAppVO.getFlwAuditorId()!=null)
			buf.append(" fd.flwAuditor.fid ='"+flowAppVO.getFlwAuditorId()+"' and");
		buf.append(" 1=1");
		
		return buf.toString();
	}
	@Override
	public void replyFlow(FlowAppVO flowAppVO) {
		
		try {
			StringBuffer buf = new StringBuffer("UPDATE FlowAppDO fd SET ");
			buf.append(" fd.flwAuditor.fid = " + flowAppVO.getFlwAuditorId());
			buf.append(" fd.flwReply = '" + flowAppVO.getFlwReply() + "'");
			buf.append(" fd.modTime = '" + flowAppVO.getModTime() + "'");
			buf.append(" fd.flwStatus = " + flowAppVO.getFlwStatus());
			buf.append(" where fd.flwId = " + flowAppVO.getFlwId());
			Query queryObject = getSession().createQuery(buf.toString());
			queryObject.executeUpdate();
		} catch (Exception e) {
			BaseException.ThrowException(e.getMessage());
		}
	}

	@Override
	public void submitFlow(FlowAppDO flowAppDO) {
		this.add(flowAppDO);
	}
	@Override
	public Integer getFlowPageCount(FlowAppVO flowAppVO) {
		List res = null;
		try {
			StringBuffer buf = new StringBuffer("SELECT COUNT(*) FROM FlowAppDO fd where ");
			buf.append(getWhereHql(flowAppVO));
			Query queryObject = getSession().createQuery(buf.toString());
			res = queryObject.list();
		} catch (Exception e) {
			BaseException.ThrowException(e.getMessage());
		}
		if(res == null || res.size()==0)
			return 0;
		else
			return Integer.valueOf(res.get(0).toString());
	}

}
