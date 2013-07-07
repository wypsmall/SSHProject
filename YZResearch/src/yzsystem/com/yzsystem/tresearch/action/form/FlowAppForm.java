package com.yzsystem.tresearch.action.form;

import com.imti.framework.web.action.BaseForm;
import com.yzsystem.tresearch.po.UserDO;
import com.yzsystem.tresearch.vo.FlowAppVO;

public class FlowAppForm extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7090046989946648306L;

	private UserDO userDO = new UserDO();
	private FlowAppVO flowAppVO = new FlowAppVO();
	public UserDO getUserDO() {
		return userDO;
	}
	public void setUserDO(UserDO userDO) {
		this.userDO = userDO;
	}
	public FlowAppVO getFlowAppVO() {
		return flowAppVO;
	}
	public void setFlowAppVO(FlowAppVO flowAppVO) {
		this.flowAppVO = flowAppVO;
	}
	
	
}
