package com.yzsystem.exhibitions.action.form;

import com.imti.framework.web.action.BaseForm;
import com.yzsystem.exhibitions.vo.ExhProModuleVO;

public class ExhibitionProjectForm extends BaseForm {

	/**
	 * 说明清楚此属性的业务含义
	 */
	private static final long serialVersionUID = 3716994420495189520L;

	private ExhProModuleVO mod = new ExhProModuleVO();

	public ExhProModuleVO getMod() {
		return mod;
	}

	public void setMod(ExhProModuleVO mod) {
		this.mod = mod;
	}
	
}
