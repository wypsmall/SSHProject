package com.imti.framework.web.environment.utils;

import org.springframework.beans.factory.BeanFactory;

import com.imti.framework.web.log.ExtLog;

public class SpringBeanUtil {

	private static ExtLog log = new ExtLog(SpringBeanUtil.class);
	private static BeanFactory beanFactory;
	
	/**
	 * ��ʼֵ
	 * 
	 * @param servletContext
	 */
	public static void init(BeanFactory _beanFactory) {
		log.info(">>>>>>>>>>>��ʼ���س־û�����");
		beanFactory = _beanFactory;
	}

	public static Object getBean(String beanName) {
		return beanFactory.getBean(beanName);
	}
	public static BeanFactory getBeanFactory(){
		return beanFactory;
	}
	
}
