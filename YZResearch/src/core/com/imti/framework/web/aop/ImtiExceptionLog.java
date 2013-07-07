package com.imti.framework.web.aop;

import org.aspectj.lang.JoinPoint;

import com.imti.framework.web.log.ExtLog;

public class ImtiExceptionLog {

	private ExtLog log;
	public void afterThrowing(JoinPoint jp, Exception ex) throws Throwable {
		log = new ExtLog(jp.getTarget().getClass());
		log.error("通过AOP拦截到以下异常......");
		log.error(ex.getMessage(), ex);
	}
}
