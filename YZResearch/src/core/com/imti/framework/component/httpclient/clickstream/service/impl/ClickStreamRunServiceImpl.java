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
	 * 执行点击流
	 * 1、找到点击流的 点击开关
	 * 2、执行点击开关
	 * 3、遍历执行点击流
	 * (non-Javadoc)
	 * @see com.imti.framework.compenent.httpclient.clickstream.service.ClickStreamRunService#run(com.imti.framework.compenent.httpclient.clickstream.model.ClickStream, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public void run(ClickStream clickStream, Map inputMap) {
		//第一步：找到点击流的 点击开关
		Click click = clickStream.getStartClick();
		HttpClient client = HttpClientFactory.getInstance().getHttpClient();
		//第二步：启动点击流开关
		click.invoke(client, inputMap);
		HttpResult result = click.getResult();
		//第三步：遍历点击流
		while(HttpclientUtil.canNextClick(result) &&
				null != click.getNextClick()){
			click = click.getNextClick();
			click.invoke(client, inputMap);
			result = click.getResult();
		}
		log.info("点击流成功执行");
	}
}
