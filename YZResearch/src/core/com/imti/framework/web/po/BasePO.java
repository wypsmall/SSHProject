package com.imti.framework.web.po;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import com.imti.framework.component.vopomapping.vomapping.SimpleProperty;
import com.imti.framework.component.vopomapping.vomapping.VOConfig;
import com.imti.framework.component.vopomapping.vomapping.VOMapping;
import com.imti.framework.web.exception.ConException;
import com.imti.framework.web.exception.constant.ExceptionConstant;

@SuppressWarnings("serial")
public class BasePO implements Serializable {
	/**
	 * 得到主键名字
	 * @return 主键名字
	 */
	@SuppressWarnings("unchecked")
	public SimpleProperty getPkFields() {
		VOConfig voCfg = VOMapping.getVOConfigByPoName(this.getClass().getName());
		if (voCfg == null)
			return null;
		SimpleProperty sp = new SimpleProperty();
		Map propertyMap = voCfg.getProperties();
		Iterator it = propertyMap.values().iterator();
		int primaryConut = 0;
		while(it.hasNext()){
			SimpleProperty simpleProperty = (SimpleProperty)it.next();
			String primary = simpleProperty.getPrimary();
			if ("true".equals(primary)) {
				sp = simpleProperty;
				primaryConut ++ ;
			}
			if(primaryConut > 2){
				throw new ConException(ExceptionConstant.IMTI_CONDITION_PRIMARY_MORE_ERROR);
			}
		}
    	if(primaryConut == 0){
    		throw new ConException(ExceptionConstant.IMTI_CONDITION_PRIMARY_NONO_ERROR);
    	}
		return sp;
	}
}
