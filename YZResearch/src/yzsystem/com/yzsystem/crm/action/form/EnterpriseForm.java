package com.yzsystem.crm.action.form;

import com.imti.framework.web.action.BaseForm;
import com.yzsystem.crm.vo.EnterpriseVO;

public class EnterpriseForm extends BaseForm {

	/**
	 * ˵����������Ե�ҵ����
	 */
	private static final long serialVersionUID = -2401154849546604878L;
	private EnterpriseVO vo = new EnterpriseVO();

	public EnterpriseVO getVo() {
		return vo;
	}

	public void setVo(EnterpriseVO vo) {
		this.vo = vo;
	}
	
}
