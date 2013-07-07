package com.imti.framework.component.httpclient.clickstream.service;

import java.util.Map;

import com.imti.framework.component.httpclient.clickstream.module.ClickStream;

public interface ClickStreamRunService {

	/**
	 * Æô¶¯µã»÷Á÷
	 */
	@SuppressWarnings("unchecked")
	public void run(ClickStream clickStream, Map inputMap);
}
