package com.imti.framework.web.exception.constant;

import java.io.Serializable;

public class ExceptionConstant implements Serializable {

	public static final String IMTI_ACTION_PARAM = "路径没有配置请求参数";
	public static final String IMTI_FORM_EXTENDS_ERROR = "你定义的form没有继承BaseForm";
	public static final String IMTI_VO_EXTENDS_ERROR = "你定义的VO没有继承BaseVO";
	
	public static final String IMTI_CONDITION_PRIMARY_MORE_ERROR = "目前查询组件还不支持复合主键";
	public static final String IMTI_CONDITION_PRIMARY_NONO_ERROR = "您还没有配置主键";
}
