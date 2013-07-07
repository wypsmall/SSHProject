/**
 * 
 */
package com.imti.framework.component.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.imti.framework.common.StringUtils;
import com.imti.framework.component.vopomapping.utils.PropertyUtils;

/**
 * 邮件发送线程
 * @version 
 */
public class MailSendingThread extends Thread {
	
	private MimeMail mimeMail;
	
	public MailSendingThread(MimeMail mimeMail) {
		this.mimeMail = mimeMail;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		MailSenderHolder.getMailSender().send(createMimeMessage());
	}
	
	private MimeMessage createMimeMessage() {
		
		String fromMail = PropertyUtils.getProperty("mail.from");
		String fromName = PropertyUtils.getProperty("mail.from.name");
		fromName = StringUtils.iso88591ToGbkEncoding(fromName);
		InternetAddress internetAddress = null;
		try {
			internetAddress = new InternetAddress(fromMail, fromName);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		MimeMessage mimeMessage = MailSenderHolder.getMailSender().createMimeMessage();
		try {
			mimeMessage.setFrom(internetAddress);
			InternetAddress[] address = new InternetAddress[mimeMail.getTo().size()];
			mimeMessage.setRecipients(Message.RecipientType.TO, mimeMail.getTo().toArray(address));
			mimeMessage.setSubject(mimeMail.getSubject());
			
			Multipart multipart = new MimeMultipart();
			MimeBodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(mimeMail.getText(), "text/html;charset=utf-8");
			multipart.addBodyPart(bodyPart);
			
			mimeMessage.setContent(multipart);
			mimeMessage.setSentDate(new Date());
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return mimeMessage;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#start()
	 */
	@Override
	public synchronized void start() {
		super.start();
	}

	/**
	 * 返回成员属性 mimeMail
	 * @return mimeMail
	 */
	public MimeMail getMimeMail() {
		return mimeMail;
	}

	/**
	 * 将传入参数mimeMail 赋给成员属性 mimeMail
	 * @param mimeMail
	 */
	public void setMimeMail(MimeMail mimeMail) {
		this.mimeMail = mimeMail;
	}
}
