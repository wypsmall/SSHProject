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
	//��Ӧ����ģ��ı�ţ�����Ψһȷ��һ������ģ��
	public String rootModule() default "";
	//��Ӧ����ģ������ƣ�����Ψһȷ��һ������ģ��
	public String secondModule() default "";
	//��Ӧ����ģ���ģ�����ƣ�
	public String thirdModule() default "";
	//��������������޸ġ�ɾ���ȵȣ�
	public String operateName() default "";
	
}
