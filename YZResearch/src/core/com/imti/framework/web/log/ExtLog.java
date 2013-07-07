package com.imti.framework.web.log;

import java.io.PrintStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.imti.framework.component.vopomapping.utils.PropertyUtils;


public class ExtLog {

private Log log;
	
	public ExtLog(Class cla){
		log  = LogFactory.getLog(cla);
	}
	
	/**
	 * 是否采用日志
	 * @return
	 */
	private boolean useLog(){
		String logConfig = PropertyUtils.getProperty("imti.log.useLog", "true");
		if("true".equals(logConfig)){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否采用系统的打印
	 * @return
	 */
	private boolean usePrint(){
		String usePrint = PropertyUtils.getProperty("imti.use.system.print", "falses");
		if("true".equals(usePrint)){
			return true;
		}
		return false;
	}
	
	public void error(Exception ex){
		if(useLog()){
			log.error(ex);
		}
		if(usePrint()){
			System.out.println(ex.getMessage());
		}
	}
	
	public void error(String message){
		if(useLog()){
			log.error(message);
		}
		if(usePrint()){
			System.out.println(message);
		}
	}
	
	public void error(Object object, Throwable throwable) {
		if (useLog()) {
			log.error(object, throwable);
		} 
		println(throwable);
	}
	
	public void debug(Object object) {
		if (useLog()) {
			log.debug(object);
		} 
	}

	public void debug(Object object, Throwable throwable) {
		if (useLog()) {
			log.debug(object, throwable);
		} 
		println(throwable);
	}
	
	public void info(Object object) {
		if (useLog()) {
			log.info(object);
		} 
	}
	public void println(Object object){
		if(usePrint()) {
			System.out.println(object.toString());
		}
	}
	public void info(Object object, Throwable throwable) {
		if (useLog()) {
			log.info(object, throwable);
		} 
		println(throwable);
	}
	
	public void warn(Object object) {
		if (useLog()) {
			log.warn(object);
		} 
		if(usePrint()) {
			System.out.println(object.toString());
		}
	}
	
	public void warn(Object object, Throwable throwable) {
		if (useLog()) {
			log.warn(object, throwable);
		} 
		println(throwable);
	}
	
	public void fatal(Object object) {
		if (useLog()) {
			log.fatal(object);
		} 
		if(usePrint()) {
			System.out.println(object.toString());
		}
	}
	
	public void fatal(Object object, Throwable throwable) {
		if (useLog()) {
			log.fatal(object, throwable);
		} 
		println(throwable);
	}
	
	/**
	 * 打印异常
	 * @param throwable  异常对象
	 */
	private void println(Throwable throwable) {
		if (!usePrint()){
			return;
		}
		PrintStream s = System.err;
		synchronized (s) {
			s.println(throwable);
			StackTraceElement[] trace = throwable.getStackTrace();
			for (int i = 0; i < trace.length; i++)
				s.println("\tat " + trace[i]);
			s.println(throwable.getMessage());
		}
	}
}
