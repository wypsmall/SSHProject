package com.imti.framework.web.environment;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.imti.framework.web.environment.utils.SpringBeanUtil;

public class SpringBeanFactoryAware implements BeanFactoryAware {
	private BeanFactory beanFactory;
	
	public void setBeanFactory(BeanFactory _beanFactory) throws BeansException {
		this.beanFactory = _beanFactory;
		SpringBeanUtil.init(beanFactory);
	}

}
