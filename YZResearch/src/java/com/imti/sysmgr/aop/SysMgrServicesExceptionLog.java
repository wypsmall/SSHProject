/**
 * 
 */
package com.imti.sysmgr.aop;

import org.aspectj.lang.JoinPoint;

import com.imti.framework.web.log.ExtLog;

/**
 * 系统管理 服务层抛出的异常日志记录器
 *	
 * @version gsmwp 1.0
 */
public class SysMgrServicesExceptionLog {
	
	ExtLog log = new ExtLog(SysMgrServicesExceptionLog.class);

	public void afterThrowing(JoinPoint jp, Exception ex) throws Throwable {
//		log.error("通过AOP拦截到以下异常......");
		log.error(ex.getMessage(), ex);
		throw new RuntimeException(ex);
	}
}
