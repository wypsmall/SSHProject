/**
 * 
 */
package com.imti.framework.component.mail;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * 邮件实体
 * @version
 */
public class MimeMail {
	 
	/**
	 * 收件人列表
	 */
	private HashSet<InternetAddress> to = new HashSet<InternetAddress>();
	/**
	 * 标题
	 */
	private String subject;
 
	/**
	 * 正文
	 */
	private String text;
 
	/**
	 * 附件列表
	 */
	private HashSet attachments = new HashSet();

	/**
	 * 返回成员属性 to
	 * @return to
	 */
	public HashSet<InternetAddress> getTo() {
		return to;
	}

	/**
	 * 添加收件人
	 * @param mailAddress	收件人地址
	 * @param name	收件人姓名
	 * @version 1.0
	 */
	public void addTo(String mailAddress, String name) {
		InternetAddress recipient;
		try {
			recipient = new InternetAddress(mailAddress, name);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		to.add(recipient);
	}
	
	/**
	 * 添加收件人
	 * @param mailAddress 收件人地址
	 * @version 1.0
	 */
	public void addTo(String mailAddress) {
		InternetAddress recipient;
		try {
			recipient = new InternetAddress(mailAddress);
		} catch (AddressException e) {
			throw new RuntimeException(e);
		}
		to.add(recipient);
	}

	/**
	 * 返回成员属性 subject
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * 将传入参数subject 赋给成员属性 subject
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 返回成员属性 text
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * 将传入参数text 赋给成员属性 text
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 返回成员属性 attachments
	 * @return attachments
	 */
	public HashSet getAttachments() {
		return attachments;
	}

	/**
	 * 将传入参数attachments 赋给成员属性 attachments
	 * @param attachments
	 */
	public void setAttachments(HashSet attachments) {
		this.attachments = attachments;
	}
}
