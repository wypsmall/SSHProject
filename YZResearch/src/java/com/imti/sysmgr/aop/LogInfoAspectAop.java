package com.imti.sysmgr.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.imti.common.util.CurrentUserThread;
import com.imti.framework.common.DateUtil;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.framework.web.log.ExtLog;
import com.imti.sysmgr.service.LoginLogService;
import com.imti.sysmgr.vo.BusiLogVO;

@Aspect
public class LogInfoAspectAop {
	
	ExtLog log = new ExtLog(LogInfoAspectAop.class);
	//��־����
	private LoginLogService getLogService(){
		return (LoginLogService)SpringBeanUtil.getBean("loginLogService");
	}
	@Pointcut("@annotation(com.imti.sysmgr.aop.LogInfoAspect)") 
	public void annotationMethod(){}   
	
	/*����ִ�к���� */ 
	@AfterReturning(value = "annotationMethod() && @annotation(logInfoAspect)")   
	public void logAfter(JoinPoint joinPoint,LogInfoAspect logInfoAspect) {  
		BusiLogVO busiVo = new BusiLogVO(logInfoAspect.rootModule(), logInfoAspect.secondModule(),
						logInfoAspect.thirdModule(),logInfoAspect.operateName(),"�ɹ�","",CurrentUserThread.getUserName(),DateUtil.getCurrentDateTime());
		getLogService().insert(busiVo);
		
	}   
  
	//�������г����쳣ʱ����   
	@AfterThrowing(pointcut = "annotationMethod() && @annotation(logInfoAspect)",throwing = "ex")   
	public void logAfterThrowing(JoinPoint joinPoint,LogInfoAspect logInfoAspect,Exception ex){
		String msg = ex.getMessage();
		msg = msg.length() > 999 ? msg.substring(0, 1000) : msg;
		BusiLogVO busiVo = new BusiLogVO(logInfoAspect.rootModule(), logInfoAspect.secondModule(),
				logInfoAspect.thirdModule(),logInfoAspect.operateName(),"ʧ��",msg,CurrentUserThread.getUserName(),DateUtil.getCurrentDateTime());
		getLogService().insert(busiVo);
	} 
}
