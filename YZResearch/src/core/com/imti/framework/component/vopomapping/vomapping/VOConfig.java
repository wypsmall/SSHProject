package com.imti.framework.component.vopomapping.vomapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VOConfig {
	private String poClass = "";
	private String voClass = "";
	
	//properties 格式：map("vo的属性名",SimpleProperty实体)
	private Map properties  = new HashMap();
	private List manyManys;
	private List oneList;
	
	public VOConfig(){
	}
	
	public List getManyManys() {
		if(manyManys==null){
			manyManys = new ArrayList();
		}
		return manyManys;
	}

	public void setManyManys(List manyManys) {
		this.manyManys = manyManys;
	}

	public List getOneList() {
		if(oneList==null){
			oneList = new ArrayList();
		}
		return oneList;
	}

	public void setOneList(List oneList) {
		this.oneList = oneList;
	}

	public Map getProperties() {
		return properties;
	}
	
	public SimpleProperty getSimpleProperty(String name) {
		return (SimpleProperty) properties.get(name);
	}
	public void addProperty(SimpleProperty voProperty){
		properties.put(voProperty.getVoProperty(),voProperty);
	}
	public void addManyToMany (ManyToMany manyToMany){
		getManyManys().add(manyToMany);
	}
	public void addOneToOne(OneToOne oneToOne){
		getOneList().add(oneToOne);
	}
	public String getPoClass() {
		return poClass;
	}
	public void setPoClass(String poClass) {
		this.poClass = poClass;
	}
	public String getVoClass() {
		return voClass;
	}
	public void setVoClass(String voClass) {
		this.voClass = voClass;
	}
	
}
