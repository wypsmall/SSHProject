/**
 * 
 */
package com.imti.framework.component.mail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.imti.framework.component.vopomapping.utils.PropertyUtils;

/**
 * 邮件发送类
 * @version 
 */
public class MailUtils {

	/**
	 * 使用多线程发送邮件
	 * @param to
	 * @param subject
	 * @param text
	 * @version 1.0
	 */
	public static void sending(List<String> to, String subject, String text) {
		//没有收件人，就不要发送邮件
		if(to == null || to.isEmpty()){
			return ;
		}
		
		//邮件发送开关
		String toggle = PropertyUtils.getProperty("mail.toggle");
		if (StringUtils.isNotBlank(toggle) && toggle.trim().equalsIgnoreCase("true")) {
			MimeMail mimeMail = new MimeMail();
			for (String mailTo : to) {
				if (StringUtils.isNotBlank(mailTo)) {
					mimeMail.addTo(mailTo);
				}
			}
			mimeMail.setSubject(subject);
			mimeMail.setText(text);
			sendingMail(mimeMail);
		}
	}
	
	private static void sendingMail(MimeMail mimeMail) {
		MailSendingThread mailThread = new MailSendingThread(mimeMail);
		mailThread.start();
	}
	public static void main(String[] args){
		List<String> to = new ArrayList();
		to.add("13662464397@139.com");
		sending(to, "测试数据", "d");
	}
}
