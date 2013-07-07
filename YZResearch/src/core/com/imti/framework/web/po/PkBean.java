package com.imti.framework.web.po;

import com.imti.framework.component.vopomapping.constant.DataType;
import com.imti.framework.component.vopomapping.vomapping.SimpleProperty;

public class PkBean {
	private Class poClass;
	private String[] values;
	
	public PkBean(Class poClass, String[] values){
		this.poClass = poClass;
		this.values = values;
	}
	public String getPoClass(){
		return poClass.getName();
	}
	public String getPkValues(){
		BasePO po = getInstance(poClass.getName());
		SimpleProperty sp = po.getPkFields();
		StringBuffer sb = new  StringBuffer("");
		if(values != null){
			for(int i = 0; i < values.length; i++){
				if(sp.getPoType().endsWith(DataType.STRING) ||
						sp.getPoType().endsWith(DataType.CSTRING)){
					sb.append("'").
					append(values[i]).
					append("'").
					append(",");
				}else {
					sb.append(values[i]).
					append(",");
				}
			}
		}else {
			if(sp.getPoType().endsWith(DataType.STRING) ||
					sp.getPoType().endsWith(DataType.CSTRING)){
				sb.append("''");
			} 
		}
		if(sb.toString().endsWith(",")){
			return sb.toString().substring(0, sb.toString().length() - 1);
		}
		return sb.toString();
	}
	
	public static PkBean newInstance(Class poClass, String[] values){
		return new PkBean(poClass, values);
	}
	
	private static BasePO getInstance(String className){
    	try {
			return (BasePO)Class.forName(className).newInstance();
    	} catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (ClassNotFoundException e) {
        }
		return null;
    }
}
