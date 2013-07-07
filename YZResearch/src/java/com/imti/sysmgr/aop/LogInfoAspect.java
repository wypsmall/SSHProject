package com.imti.sysmgr.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface LogInfoAspect {
	//对应功能模块的编号，可以唯一确定一个功能模块
	public String rootModule() default "";
	//对应功能模块的名称，可以唯一确定一个功能模块
	public String secondModule() default "";
	//对应功能模块的模块名称，
	public String thirdModule() default "";
	//操作类别（新增、修改、删除等等）
	public String operateName() default "";
	
}
