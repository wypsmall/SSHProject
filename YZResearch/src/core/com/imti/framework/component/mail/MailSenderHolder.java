/**
 * 
 */
package com.imti.framework.component.mail;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.imti.framework.component.vopomapping.utils.PropertyUtils;

/**
 * JavaMailSender对象持有者
 * @see org.springframework.mail.javamail.JavaMailSenderImpl
 * @version 
 */
public class MailSenderHolder {

	private static JavaMailSenderImpl mailSenderImpl;
	
	private MailSenderHolder() {
	}
	
	public static void init() {
		
		mailSenderImpl.setHost(PropertyUtils.getProperty("imti.mail.host"));
		mailSenderImpl.setUsername(PropertyUtils.getProperty("imti.mail.username"));
		mailSenderImpl.setPassword(PropertyUtils.getProperty("imti.mail.password"));
		
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.auth", PropertyUtils.getProperty("imti.mail.smtp.auth"));
		prop.setProperty("mail.smtp.timeout", PropertyUtils.getProperty("imti.mail.smtp.timeout"));
		
		mailSenderImpl.setJavaMailProperties(prop);
	}
	
	/**
	 * 获取MailSender的单例
	 * @return {@link JavaMailSender}
	 * @version 1.0
	 */
	public static JavaMailSender getMailSender() {
		if(mailSenderImpl == null) {
			mailSenderImpl = new JavaMailSenderImpl();
			init();
		}
		return mailSenderImpl;
	}
}
