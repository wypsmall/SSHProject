package com.imti.framework.component.httpclient.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.TextareaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.imti.framework.common.FileUtils;
import com.imti.framework.component.httpclient.clickstream.constant.ClickConstant;
import com.imti.framework.component.httpclient.clickstream.module.HttpResult;
import com.imti.framework.component.vopomapping.utils.PropertyUtils;
import com.imti.framework.web.log.ExtLog;

public final class HttpclientUtil {
	private static ExtLog log  = new ExtLog(HttpclientUtil.class);

	/**
	 * �����ص��ļ����й��ˣ���ȡtext��textarea��hiddenԪ��
	 * ����checkgroup�ĸ�ʽ��name, values�ַ������飩
	 * @param filePath �����ļ�·��
	 * @return map(name,value)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> httpParser(String responseFile) throws ParserException, Exception {
		// ��Map���͵�ret�����ȡhtml�ڵ����Ե�ֵ
		Map ret = new HashMap<String, String>();
		if (StringUtils.isEmpty(responseFile)) {
			return ret;
		}
		Parser myParser = new Parser(responseFile);
		NodeList nodeList = new NodeList();
		NodeFilter inputFilter = new NodeClassFilter(InputTag.class);
		NodeFilter TextareaFilter = new NodeClassFilter(TextareaTag.class);
		OrFilter lastFilter = new OrFilter();
		lastFilter.setPredicates(new NodeFilter[] { TextareaFilter, inputFilter });
		try {
			nodeList = myParser.parse(lastFilter);
		} catch (ParserException e) {
			throw new Exception("����DOMԪ�ش���");
		}
		Node[] nodes = nodeList.toNodeArray();
		for (int i = 0; i < nodes.length; i++) {
			Node anode = (Node) nodes[i];
			if (anode instanceof TextareaTag) {
				TextareaTag textareanode = (TextareaTag) anode;
				ret.put(textareanode.getAttribute("name"), textareanode.getAttribute("value"));
			} else if (anode instanceof InputTag) {
				InputTag inputnode = (InputTag) anode;
				Vector v = inputnode.getAttributesEx();
				if (v.toString().indexOf("type=\"hidden\"") != -1 || v.toString().indexOf("type=\"text\"") != -1
						|| (v.toString().indexOf("type=\"radio\"") != -1 && v.toString().indexOf("checked") != -1)) {
					ret.put(inputnode.getAttribute("name"), inputnode.getAttribute("value"));
				} else if (v.toString().indexOf("type=\"checkbox\"") != -1 && v.toString().indexOf("checked") != -1) {
					String checkboxName = inputnode.getAttribute("name");
					String checkboxValue = inputnode.getAttribute("value");
					Object object = ret.get(checkboxName);
					if (object == null) {
						ret.put(checkboxName, checkboxValue);
					} else if (object instanceof String) {
						Object[] tempObject = new Object[2];
						tempObject[0] = object;
						tempObject[1] = checkboxValue;
						ret.put(checkboxName, tempObject);
					} else {// �Ѿ�������
						int length = ((Object[]) object).length;
						Object[] tempObject = new Object[length + 1];
						tempObject = mergeArray(tempObject, (Object[]) object);
						tempObject[length] = checkboxValue;
						ret.put(checkboxName, tempObject);
					}
				}
			}
		}
		String deleteFlag = PropertyUtils.getProperty("imti.httpclient.response.file.delete", "false");
		File file = new File(responseFile);
		if("true".equals(deleteFlag)){
			FileUtils.deleteDirectory(file);
		}
		return ret;
	}
	public static Object[] mergeArray(Object[] src, Object[] dest) {
		if (dest != null) {
			for (int i = 0; i < dest.length; i++) {
				src[i] = dest[i];
			}
		}
		return src;
	}
	/**
	 * �ϲ�map,���ڶ���map�ϲ�����һ����
	 * @param mapOne
	 * @param mapTwo
	 */
	@SuppressWarnings("unchecked")
	public static void mergeMap(Map mapOne, Map mapTwo) {
		if (mapTwo != null) {
			Iterator it = mapTwo.keySet().iterator();
			if (mapOne == null) {
				mapOne = new HashMap();
			}
			while (it.hasNext()) {
				Object key = it.next();
				mapOne.put(key, mapTwo.get(key));
			}
		}
	}
	public static boolean canNextClick(HttpResult result){
		String status = result.getStatus();
		if(ClickConstant.STATUS_200.equals(status) || ClickConstant.STATUS_302.equals(status)){
			if(ClickConstant.STATUS_302.equals(status)){
				log.info("httpclient:�ͻ���ת��");
			}
			log.info("httpclient:����ɹ�");
		}else if(ClickConstant.STATUS_500.equals(status)){
			log.info("httpclient:�������ڲ�����,���ܵ�ԭ��\n\r1����������\r\n2������ͷ��Ϣ");
			return false;
		}else if(ClickConstant.STATUS_ERROR.equals(status)){
			log.info("httpclient:��������ʱ���������쳣����Ͻ�����");
			return false;
		}
		return true;
	}
}
