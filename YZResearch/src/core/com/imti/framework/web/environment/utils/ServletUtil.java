package com.imti.framework.web.environment.utils;

import javax.servlet.ServletContext;

import com.imti.framework.web.environment.EnvironmentListener;
import com.imti.framework.web.log.ExtLog;

public class ServletUtil {

	public static String SERVLET_CONTEXT_REAL_PATH;
	private static ServletContext servletContext;
	private static ExtLog log = new ExtLog(EnvironmentListener.class);
	
	public static void init(ServletContext _servletContext){
		log.info(">>>>>>>>>>>初始化全局变量:SERVLET_CONTEXT_REAL_PATH");
		servletContext = _servletContext;
		SERVLET_CONTEXT_REAL_PATH = servletContext.getRealPath("/");
	}
	
	public static ServletContext getServletContext(){
		return servletContext;
	}
}
