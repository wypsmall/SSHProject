package com.imti.framework.component.sertificate.linux.service;

import com.imti.framework.component.sertificate.bean.CertificateVO;

public interface LinuxCertificateService {
	/**
	 * ����֤��
	 * @param vo ֤�������Ϣ�ķ�װ
	 * @param realPath ����֤����ص��ļ��ľ���·��
	 */
	public void generateCertificate(CertificateVO vo, String realPath);
}
