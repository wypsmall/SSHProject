package com.imti.framework.component.sertificate.window.service;

import com.imti.framework.component.sertificate.bean.CertificateVO;

public interface WindowCertificateService {
	/**
	 * ����֤��
	 * @param vo ֤�������Ϣ�ķ�װ
	 * @param realPath ����֤����ص��ļ��ľ���·��
	 */
	public void generateCertificate(CertificateVO vo, String realPath);
}
