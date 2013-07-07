package com.imti.sysmgr.vo;

import java.io.Serializable;
import java.util.List;

import com.imti.framework.common.ImtiListUtil;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.framework.web.vo.BaseVO;
import com.imti.sysmgr.service.SysResourceService;

public class SysResourceVO extends BaseVO implements Serializable {
	
	private String id;//��Դ����
	private String parentId;//����Դ��������Դ�ĸ���ԴIDΪ��root����
	private String name;//��Դ����
	private String code;//��Դ����
	private int level = 0;//��Դ���
	private int displayIndex = 0;//˳���
	private String imgUrl;//��ԴͼƬ
	private String url;//��ԴUrl
	private String validFlag;//��Դ��Ч��
	private String memo;//��ע
	private String ztId;
	private String rsPath;
	public static final String RS_VALID_FLAG_YES = "1";//��Ч
	public static final String RS_VALID_FLAG_NO = "0";//��Ч
	
	public String getRsPath() {
		return rsPath;
	}
	public void setRsPath(String rsPath) {
		this.rsPath = rsPath;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getDisplayIndex() {
		return displayIndex;
	}
	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	public boolean hasChildren(){
		boolean hasChildren = false;
		SysResourceService service = (SysResourceService)SpringBeanUtil.getBean("sysResourceService");
		List list = service.getChildrenByRsId(id);
		if(ImtiListUtil.isNotEmpty(list)){
			hasChildren = true;
		}
		return hasChildren;
	}
	public String getZtId() {
		return ztId;
	}
	public void setZtId(String ztId) {
		this.ztId = ztId;
	}
	
}
