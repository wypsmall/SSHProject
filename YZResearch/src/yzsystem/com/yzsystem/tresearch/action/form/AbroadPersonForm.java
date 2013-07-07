package com.yzsystem.tresearch.action.form;

import com.imti.framework.web.action.BaseForm;
import com.yzsystem.tresearch.vo.AbroadPersonVO;

public class AbroadPersonForm extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6373203341810416725L;

	private AbroadPersonVO apVO = new AbroadPersonVO();

	public AbroadPersonVO getApVO() {
		return apVO;
	}

	public void setApVO(AbroadPersonVO apVO) {
		this.apVO = apVO;
	}


}
