/**
 * 
 */
package com.imti.sysmgr.aop;

import org.aspectj.lang.JoinPoint;

import com.imti.framework.web.log.ExtLog;

/**
 * ϵͳ���� ������׳����쳣��־��¼��
 *	
 * @version gsmwp 1.0
 */
public class SysMgrServicesExceptionLog {
	
	ExtLog log = new ExtLog(SysMgrServicesExceptionLog.class);

	public void afterThrowing(JoinPoint jp, Exception ex) throws Throwable {
//		log.error("ͨ��AOP���ص������쳣......");
		log.error(ex.getMessage(), ex);
		throw new RuntimeException(ex);
	}
}
