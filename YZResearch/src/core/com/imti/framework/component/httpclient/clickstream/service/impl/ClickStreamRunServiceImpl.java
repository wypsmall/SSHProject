package com.imti.framework.component.httpclient.clickstream.service.impl;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;

import com.imti.framework.component.httpclient.clickstream.module.Click;
import com.imti.framework.component.httpclient.clickstream.module.ClickStream;
import com.imti.framework.component.httpclient.clickstream.module.HttpResult;
import com.imti.framework.component.httpclient.clickstream.service.ClickStreamRunService;
import com.imti.framework.component.httpclient.factory.HttpClientFactory;
import com.imti.framework.component.httpclient.util.HttpclientUtil;
import com.imti.framework.web.log.ExtLog;

public class ClickStreamRunServiceImpl implements ClickStreamRunService {

	private static ExtLog log  = new ExtLog(ClickStreamRunServiceImpl.class);
	
	/**
	 * ִ�е����
	 * 1���ҵ�������� �������
	 * 2��ִ�е������
	 * 3������ִ�е����
	 * (non-Javadoc)
	 * @see com.imti.framework.compenent.httpclient.clickstream.service.ClickStreamRunService#run(com.imti.framework.compenent.httpclient.clickstream.model.ClickStream, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public void run(ClickStream clickStream, Map inputMap) {
		//��һ�����ҵ�������� �������
		Click click = clickStream.getStartClick();
		HttpClient client = HttpClientFactory.getInstance().getHttpClient();
		//�ڶ������������������
		click.invoke(client, inputMap);
		HttpResult result = click.getResult();
		//�����������������
		while(HttpclientUtil.canNextClick(result) &&
				null != click.getNextClick()){
			click = click.getNextClick();
			click.invoke(client, inputMap);
			result = click.getResult();
		}
		log.info("������ɹ�ִ��");
	}
}
