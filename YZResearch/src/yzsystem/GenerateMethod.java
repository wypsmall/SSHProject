

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class GenerateMethod {
	public static void main(String[] args) {
		String str = "";
		str=getPOToVO("com.yzsystem.tresearch.po.AbroadPersonPO");
//		str=getExtStore("com.yzsystem.hr.vo.HrEmployeeVO");
		System.out.println(str);
	}

	/**
	 * 生成po的vo配置文件
	 * @param str
	 * @return
	 */
	public static String getPOToVO(String PO) {
		String val = "";
		try {
			Class c = Class.forName(PO);
			Method m[] = c.getDeclaredMethods();
			Field Field[] = c.getDeclaredFields();
			for (int i = 0; i < Field.length; i++) {
				val = val + "<property voProperty=\"" + Field[i].getName()
						+ "\" poProperty=\"" + Field[i].getName()
						+ "\"  voType=\"String\" poType=\""
						+ replace(Field[i].getGenericType().toString())
						+ "\"  primary=\"false\"/>" + "\r\n";
			}
			String VO=PO.replaceAll("po", "vo").replaceAll("PO", "VO");
			val="<class voClass=\""+VO+"\" " +
					"poClass=\""+PO+"\"> "+"\r\n"
				+val+"</class>";
		} catch (Throwable e) {
			System.out.println(e);
		}
		return val;
	}

	/**
	 * 由po 获得前台store中的值
	 * @param str
	 * @return
	 */
	public static String getExtStore(String VO) {
		String val = "";
		try {
			Class c = Class.forName(VO);
			Method m[] = c.getDeclaredMethods();
			Field Field[] = c.getDeclaredFields();

			for (int i = 0; i < Field.length; i++) {
				val = val + "{name : '" + Field[i].getName() + "'},\r\n";
			}
		} catch (Throwable e) {
			System.out.println(e);
		}
		return val;
	}
	/**
	 * 寻找Vo中有Po中没有的属性
	 * @param str
	 * @return
	 */
	public static String getVOHavePONo(String VO,String PO) {
		String val = "";
		try {
			Class c = Class.forName(VO);
			Field Field[] = c.getDeclaredFields();

			Class cp = Class.forName(PO);
			Field Fieldp[] = cp.getDeclaredFields();
		
			for (int i = 0; i < Field.length; i++) {
				for (int j = 0; j < Fieldp.length; j++) {
					if(Fieldp[j].getName().equals(Field[i].getName())){
						break;
					}
					if(j==Fieldp.length-1){System.out.println("=="+Field[i]);}
				}
				
			}
		} catch (Throwable e) {
			System.out.println(e);
		}
		return val;
	}
	/**
	 * js 置数据
	 * @param str
	 * @return
	 */
	public static String setJsData(String VO) {
		String val = "";
		try {
			Class c = Class.forName(VO);
			Method m[] = c.getDeclaredMethods();
			Field Field[] = c.getDeclaredFields();

			for (int i = 0; i < Field.length; i++) {
				 
				val = val + " setValue(\""+Field[i].getName()+"\",containerFeeVo."+Field[i].getName()+"); \r\n";
			}
		} catch (Throwable e) {
			System.out.println(e);
		}
		return val;
	}
	/**
	 * vo变量的request
	 * @param str
	 * @return
	 */
	public static String getRequestStr(String VO) {
		String val = "";
		try {
			Class c = Class.forName(VO);
			Method m[] = c.getDeclaredMethods();
			Field Field[] = c.getDeclaredFields();

			for (int i = 0; i < Field.length; i++) {
				 
				val = val +"vo.set"+Field[i].getName().substring(0,1).toUpperCase()+Field[i].getName().substring(1)
+"(request.getParameter(\""+Field[i].getName()+"\")); \r\n";
			}
		} catch (Throwable e) {
			System.out.println(e);
		}
		return val;
	}
	/**
	 * vo变量的set
	 * @param str
	 * @return
	 */
	public static String getSet(String VO) {
		String val = "";
		try {
			Class c = Class.forName(VO);
			Method m[] = c.getDeclaredMethods();
			Field Field[] = c.getDeclaredFields();

			for (int i = 0; i < Field.length; i++) {
				 
				val = val +"vo.set"+Field[i].getName().substring(0,1).toUpperCase()+Field[i].getName().substring(1)+"();\r\n";
			}
		} catch (Throwable e) {
			System.out.println(e);
		}
		return val;
	}
	/**
	 * vo变量的get
	 * @param str
	 * @return
	 */
	public static String getGet(String VO) {
		String val = "";
		try {
			Class c = Class.forName(VO);
			Method m[] = c.getDeclaredMethods();
			Field Field[] = c.getDeclaredFields();

			for (int i = 0; i < Field.length; i++) {
				 
				val = val +"vo.get"+Field[i].getName().substring(0,1).toUpperCase()+Field[i].getName().substring(1)+"()\r\n";
			}
		} catch (Throwable e) {
			System.out.println(e);
		}
		return val;
	}
	/***
	 * 产生whereHql
	 */
	public static String getWhereHql(String VO) {
		String val = "";
		try {
			Class c = Class.forName(VO);
			Method m[] = c.getDeclaredMethods();
			Field Field[] = c.getDeclaredFields();

			for (int i = 0; i < Field.length; i++) {
			val = val +"if(StringUtils.isNotBlank(cvo.get"+Field[i].getName().substring(0,1).toUpperCase()+Field[i].getName().substring(1)+"())){ \r\n"+
				"whereHql.append(\" and po."+Field[i].getName() +"= '\"+cvo.get"+Field[i].getName().substring(0,1).toUpperCase()+Field[i].getName().substring(1)+"()+\"'\");"+
			"}\r\n";
			}
		} catch (Throwable e) {
			System.out.println(e);
		}
		return val;
	}
	
	/***
	 * 替换常用的
	 * @param str
	 * @return
	 */
	public static String replace(String str) {
		return str.replaceAll("class java.math.", "").replaceAll(
				"class java.util.", "").replaceAll("class java.lang.", "");
	}
}
