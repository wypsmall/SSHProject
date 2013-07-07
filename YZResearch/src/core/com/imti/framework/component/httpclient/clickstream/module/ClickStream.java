package com.imti.framework.component.httpclient.clickstream.module;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.imti.framework.component.httpclient.clickstream.service.ClickStreamRunService;
import com.imti.framework.component.httpclient.clickstream.service.impl.ClickStreamRunServiceImpl;
import com.imti.framework.component.vopomapping.utils.PropertyUtils;

public class ClickStream {

	private String clickStreamId;
	@SuppressWarnings("unchecked")
	private Map clickMap = new HashMap() ; //�¼�����(map(clickId, Click))
	
	private ClickStream(String _clickStreamId){
		this.clickStreamId = _clickStreamId;
	}
	public String getClickStreamId() {
		return clickStreamId;
	}
	public void setClickStreamId(String clickStreamId) {
		this.clickStreamId = clickStreamId;
	}
	@SuppressWarnings("unchecked")
	public Map getClickMap() {
		return clickMap;
	}
	@SuppressWarnings("unchecked")
	public void setClickMap(Map clickMap) {
		this.clickMap = clickMap;
	}
	@SuppressWarnings("unchecked")
	public void addClick(Click click){
		clickMap.put(click.getCurrentClick(), click);
	}
	
	@SuppressWarnings("unchecked")
	public void run(Map inputMap){
		ClickStreamRunService service = new ClickStreamRunServiceImpl();
		String serviceImpl = PropertyUtils.getProperty("imti.httpclient.clickStream" + clickStreamId);
		if(StringUtils.isNotBlank(serviceImpl)){
			service = getClickStreamRunService(serviceImpl);
		}
		service.run(this, inputMap);
	}
	private ClickStreamRunService getClickStreamRunService(String clickInvokeServiceImplName){
		try {
			Object object = Class.forName(clickInvokeServiceImplName).newInstance();
			if(object != null){
				return (ClickStreamRunService)object;
			}
    	} catch (InstantiationException e) {
    		throw new RuntimeException("ʵ����ʱ�����������õ�·��");
    	} catch (IllegalAccessException e) {
        } catch (ClassNotFoundException e) {
        	throw new RuntimeException("û�д˶���" + clickInvokeServiceImplName + "�������õ�·��");
        }
		throw new RuntimeException("��ʵ��com.imti.framework.compenent.httpclient.clickstream.service.ClickInvokeService�ӿ�");
	}
	
	public static ClickStream newInstance(String _clickStream){
		return new ClickStream(_clickStream);
	}
	
	/**
	 * ��ȡ��������������
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Click getStartClick(){
		Click click = null;
		Iterator it = clickMap.keySet().iterator();
		while(it.hasNext()){
			Click curClick = (Click)it.next();
			if(StringUtils.isNotBlank(curClick.getStartBtn()) && "true".equals(curClick.getStartBtn())){
				if(click != null){
					throw new RuntimeException("�������������ļ�����ֻ֤��һ���������");
				}
				click = curClick;
			}
		}
		if(click == null){
			throw new RuntimeException("û������������������������ļ����磺startBtn=��true����");
		}
		return click;
	}
}
