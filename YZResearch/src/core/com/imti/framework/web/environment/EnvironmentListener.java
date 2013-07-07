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
		log.info("��ʼֹͣ�������ü�����");
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		log.info("��ʼ�����������ü�����");
	    context = servletContextEvent.getServletContext();
	    //��ʼ��ServletUtil
	    ServletUtil.init(context);
	}

}
