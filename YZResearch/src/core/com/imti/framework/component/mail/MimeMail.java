/**
 * 
 */
package com.imti.framework.component.mail;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * �ʼ�ʵ��
 * @version
 */
public class MimeMail {
	 
	/**
	 * �ռ����б�
	 */
	private HashSet<InternetAddress> to = new HashSet<InternetAddress>();
	/**
	 * ����
	 */
	private String subject;
 
	/**
	 * ����
	 */
	private String text;
 
	/**
	 * �����б�
	 */
	private HashSet attachments = new HashSet();

	/**
	 * ���س�Ա���� to
	 * @return to
	 */
	public HashSet<InternetAddress> getTo() {
		return to;
	}

	/**
	 * ����ռ���
	 * @param mailAddress	�ռ��˵�ַ
	 * @param name	�ռ�������
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
	 * ����ռ���
	 * @param mailAddress �ռ��˵�ַ
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
	 * ���س�Ա���� subject
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * ���������subject ������Ա���� subject
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * ���س�Ա���� text
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * ���������text ������Ա���� text
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * ���س�Ա���� attachments
	 * @return attachments
	 */
	public HashSet getAttachments() {
		return attachments;
	}

	/**
	 * ���������attachments ������Ա���� attachments
	 * @param attachments
	 */
	public void setAttachments(HashSet attachments) {
		this.attachments = attachments;
	}
}
