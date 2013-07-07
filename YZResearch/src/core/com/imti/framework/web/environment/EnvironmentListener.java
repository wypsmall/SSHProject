package com.imti.framework.web.environment;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.imti.framework.web.environment.utils.ServletUtil;
import com.imti.framework.web.log.ExtLog;

public class EnvironmentListener implements ServletContextListener {

	private static ServletContext context = null;
	private static ExtLog log = new ExtLog(EnvironmentListener.class);
	
	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("开始停止环境配置监听器");
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		log.info("开始启动环境配置监听器");
	    context = servletContextEvent.getServletContext();
	    //初始化ServletUtil
	    ServletUtil.init(context);
	}

}
