package com.imti.framework.common;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Stack;

public class SumUtils {
	private SumUtils() {
    }

    /**
     * null����ת��Ϊ�ն���
     * @param value ����
     * @return String����
     */
    public static String nullToString(Object value) {
        String strRet = (String) value;
        if (null == strRet) {
            return strRet = "";
        }
        return strRet;
    }

    //Ĭ�ϳ������㾫��
    private static final int DEF_DIV_SCALE = 10;

    /**
     * �ṩ��ȷ�ļӷ����㡣
     * @param v1 ������
     * @param v2 ����
     * @return ���������ĺ�
     */
    public static double add(double v1, double v2) {
    	
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * �ṩ��ȷ�ļӷ����㡣
     * @param v1 ������
     * @param v2 ����
     * @return ���������ĺ�
     */
    public static BigDecimal add(String v1, String v2) {
        try {
            v1=(v1==null||v1.length()==0)?"0":v1;
            v2=(v2==null||v2.length()==0)?"0":v2;

            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.add(b2);
        } catch (NumberFormatException e) {
            return new BigDecimal("0");
        }
    }

    /**
     * �ṩ��ȷ�ļ������㡣
     * @param v1 ������
     * @param v2 ����
     * @return ���������Ĳ�
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * �ṩ��ȷ�ļ������㡣
     * @param v1 ������
     * @param v2 ����
     * @return ���������Ĳ�
     */
    public static BigDecimal sub(String v1, String v2) {
        try {
            v1=(v1==null||v1.length()==0)?"0":v1;
            v2=(v2==null||v2.length()==0)?"0":v2;
        	
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.subtract(b2);
        } catch (NumberFormatException e) {
            return new BigDecimal("0");
        }
    }

    /**
     * �ṩ��ȷ�ĳ˷����㡣
     * @param v1 ������
     * @param v2 ����
     * @return ���������ĳ�
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * �ṩ��ȷ�ĳ˷����㡣
     * @param v1 ������
     * @param v2 ����
     * @return ���������ĳ�
     */
    public static BigDecimal mul(String v1, String v2) {
        try {
            v1=(v1==null||v1.length()==0)?"0":v1;
            v2=(v2==null||v2.length()==0)?"0":v2;
        	BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.multiply(b2);
        } catch (NumberFormatException e) {
            return new BigDecimal("0");
        }
    }

    /** add by tbb
     * �ṩ��ȷ�ĳ˷����㡣
     * @param v1 ������
     * @param v2 ����
     * @return ���������ĳ�
     */
    public static BigDecimal mul(String v1, int v2) {
        try {
            v1=(v1==null||v1.length()==0)?"0":v1;
        	
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.multiply(b2);
        } catch (NumberFormatException e) {
            return new BigDecimal("0");
        }
    }

    /**
     * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ��
     * С�����Ժ�10λ���Ժ�������������롣
     *@param v1 ������
     *@param v2 ����
     *@return ������������
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ��
     * С�����Ժ�10λ���Ժ�������������롣
     *@param v1 ������
     *@param v2 ����
     *@return ������������
     */
    public static BigDecimal div(String v1, String v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /** Add by tbb
     * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ��
     * С�����Ժ�10λ���Ժ�������������롣
     *@param v1 ������
     *@param v2 ����
     *@return ������������
     */
    public static BigDecimal div(String v1, int v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ
     * �����ȣ��Ժ�������������롣
     *@param v1 ������
     *@param v2 ����
     *@param scale ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
     *@return ������������
     */
    public static BigDecimal div(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        try {
            v1=(v1==null||v1.length()==0)?"0":v1;
            v2=(v2==null||v2.length()==0)?"1":v2;
        	
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
        } catch (NumberFormatException e) {
            return new BigDecimal("0");
        }
    }

    /** add by tbb
     * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ
     * �����ȣ��Ժ�������������롣
     *@param v1 ������
     *@param v2 ����
     *@param scale ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
     *@return ������������
     */
    public static BigDecimal div(String v1, int v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        //�ݴ�
        if(v2 == 0){
        	v2 = 1;
        }
        try {
            v1=(v1==null||v1.length()==0)?"0":v1;
        	
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
        } catch (NumberFormatException e) {
            return new BigDecimal("0");
        }
    }

    /**
     * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ
     * �����ȣ��Ժ�������������롣
     *@param v1 ������
     *@param v2 ����
     *@param scale ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
     *@return ������������
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * �ṩ��ȷ��С��λ�������봦��
     *@param v ��Ҫ�������������
     *@param scale С���������λ
     *@return ���������Ľ��
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * �ṩ��ȷ��С��λ�������봦��
     *@param v ��Ҫ�������������
     *@param scale С���������λ
     *@return ���������Ľ��
     */
    public static BigDecimal round(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        v=(v==null||v.length()==0)?"0":v;
        
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * nullת��Ϊ���ִ�
     * @param strValue �ִ�ֵ
     * @return �ִ�
     */
    public static String toString(String strValue) {
        if (strValue == null)
            return "";
        else
            return strValue;
    }

    /**
     * �ַ�ת����double
     * @param  str �ַ���ֵ
     * @return doubleֵ
     */
    public static double strToDouble(String str) {
        String loghead = new String("TypeConver:str2float:");
        double ret = 0.00;
        try {
            boolean flag = (null == str);
            flag = flag || (str.trim().length() < 1);
            if (flag) {
                ret = 0.00;
            } else {
                ret = Double.parseDouble(str);
            }
        } catch (NumberFormatException e) {
            ret = 0.00;
            //   System.out.println(loghead + e.toString());
        }
        return ret;
    }

    /**
     * �ַ�ת����Long
     * @param  str �ַ���ֵ
     * @return longֵ
     */
    public static long strToLong(String str) {
        String loghead = new String("TypeConver:str2float:");
        long ret = 0l;
        try {
            boolean flag = (null == str);
            flag = flag || (str.trim().length() < 1);
            if (flag) {
                ret = 0l;
            } else {
                ret = Long.parseLong(str);
            }
        } catch (NumberFormatException e) {
            ret = 0l;
            //   System.out.println(loghead + e.toString());
        }
        return ret;
    }

    /**
     * �ַ�ת����Int
     * @param  str �ַ���ֵ
     * @return Intֵ
     */
    public static int strToInt(String str) {
        String loghead = new String("TypeConver:str2float:");
        int ret = 0;
        try {
            boolean flag = (null == str);
            flag = flag || (str.trim().length() < 1);
            if (flag) {
                ret = 0;
            } else {
                ret = Integer.parseInt(str);
            }
        } catch (NumberFormatException e) {
            ret = 0;
            //  System.out.println(loghead + e.toString());
        }
        return ret;
    }

    /**
     * �ַ�ת����Int
     * @param  str �ַ���ֵ
     * @return Intֵ
     */
    public static float strToFloat(String str) {
        String loghead = new String("TypeConver:str2float:");
        float ret = 0;
        try {
            boolean flag = (null == str);
            flag = flag || (str.trim().length() < 1);
            if (flag) {
                ret = 0;
            } else {
                ret = Float.parseFloat(str);
            }
        } catch (NumberFormatException e) {
            ret = 0;
            //  System.out.println(loghead + e.toString());
        }
        return ret;
    }

    /**
     * �ִ���ת����BigDecimal ��
     * @param str str��ֵ
     * @return BigDecimal ��
     */
    public static BigDecimal strToBigDecimal(String str) {
        try {
            BigDecimal bd = new BigDecimal(str);
            return bd;
        } catch (NumberFormatException e) {
            return new BigDecimal("0");
        }
    }

    /**
     * BigDecimalת�����ַ�����
     * @param bdNum BigDecimal
     * @return �ִ�����
     */
    public static String BigDecimalToStr(BigDecimal bdNum) {
        return bdNum.toString();
    }

    /**
     * ��ʽ��doubleֵ,
     * @param dblValue  doubleֵ
     * @param strFormat ��ʽ��("00000.000")
     * @return ���ظ�ʽ��
     */
    public static String format(double dblValue, String strFormat) {
        java.text.DecimalFormat df = new java.text.DecimalFormat(strFormat);
        return df.format(dblValue);
    }

    /**
     * ��ʽ��Stringֵ,
     * @param dblValue  doubleֵ
     * @param strFormat ��ʽ��("00000.000")
     * @return ���ظ�ʽ��
     */
    public static String format(String strValue, String strFormat) {
        java.text.DecimalFormat df = new java.text.DecimalFormat(strFormat);
        return df.format(strToDouble(strValue));
    }

    public static String toFinish(String strValue) {
        return toFinish(strValue, true);
    }

    /**
     * �ж��Ƿ�Ϊ����
     * @param strValue �ִ�
     * @return ����
     */
    public static boolean isNumeric(String strValue) {
        boolean bRet = false;
        double dbl = 0.0;
        try {
            dbl = Double.parseDouble(strValue);
            bRet = true;
        } catch (NumberFormatException e) {
            bRet = false;
        }
        return bRet;
    }

    /**
     * �õ��ȷ��С��λ��
     * @param strValue �ִ�
     * @param isMoney �Ƿ�����
     * @return �������ִ�
     */
    public static String toFinish(String strValue, boolean isMoney) {
        int iPos = 0;
        int iLens = 0;
        int iScale = 0;
        String strInt = new String("");
        String strScale = new String("");
        iPos = strValue.indexOf(".");
        //�ݴ���
        if (isNumeric(strValue) == false)
            return strValue;
        if (iPos < 0) {
            if (isMoney == true)
                strValue += ".00";
            return strValue;
        }
        strInt = strValue.substring(0, iPos);
        strScale = strValue.substring(iPos + 1);
        iScale = strToInt(strScale);
        if (iScale == 0) { //С��λ��Ϊ0,��ʵ��δ���ֻ���ݴ���
            if (isMoney == true)
                strInt += ".00";
            else
                return strInt;
            return strInt;
        }
        iLens = strScale.length() - 1;
        for (int idx = iLens; idx >= 0; idx--) {
            if (strScale.charAt(idx) != '0') {
                iLens = idx + 1;
                break;
            }
        }
        if (isMoney == true && iLens < 2) {
            strScale = strScale.substring(0, iLens);
            for (int idx = 0; idx < 2 - iLens; idx++) {
                strScale += "0";
            }
        } else {
            strScale = strScale.substring(0, iLens);
        }
        return strInt + "." + strScale;
    }

    /**
     * �ж��Ƿ�Ϊ������
     * @param operator ������
     * @return trueΪ�� ,falseΪ��
     */
    private static boolean isOperator(char operator) {
        if (operator == '-' || operator == '+' || operator == '*'
                || operator == '/' || operator == '%')
            return true;
        else
            return false;
    }

    /**
     * �õ���������ȼ�
     * @param operator ������(+,-,*,/)
     * @return ���ȼ���
     */
    private static int priority(char operator) {
        int iPriority = 0;
        switch (operator) {
        case '-':
        case '+':
            iPriority = 1;
            break;
        case '/':
        case '*':
        case '%':
            iPriority = 2;
            break;
        default:
            iPriority = 0;
        }
        return iPriority;
    }

    /**
     * �����ʽ�Ƿ���ȷ
     * @param strExpression ���ʽ
     * @return trueΪ�� ,falseΪ��
     */
    private static boolean checkExpression(String strExpression) {
        int rightCount = 0;
        int leftCount = 0;
        int pos = -1;
        int iChar = 0;
        char c;
        char preChar = '0';
        for (int idx = 0; idx < strExpression.length(); idx++) {
            c = strExpression.charAt(idx);
            iChar = (int) strExpression.charAt(idx);
            if (c == ')') {
                ++rightCount;
            } else if (c == '(') {
                ++leftCount;
            } else {
                if (!(isOperator(c) || (iChar >= 48 && iChar <= 57) || (c == '.'))) {
                    // System.out.println("--false-1-");
                    return false;
                }
                /*
                 if (isOperator(preChar) && isOperator(c) || (preChar=='.' && c=='.')){ //��ֹ�������ص�
                 System.out.println("--false-2-");
                 return false;
                 }
                 if (isOperator(preChar) && (c=='(' || c==')')){ //��ֹ�������ص�
                 System.out.println("--false-2-");
                 return false;
                 }*/
                preChar = c;
            }
        }
        if (rightCount != leftCount)
            return false;
        return true;
    }

    /**
     * �������
     * @param operator ������
     * @param sOpertand1 ������1
     * @param sOpertand2 ������2
     * @return
     */
    private static String toResult(char operator, String sOpertand1,
            String sOpertand2) {
        String strValue = new String("");
        switch (operator) {
        case '-':
            strValue = sub(sOpertand1, sOpertand2).toString();
            break;
        case '+':
            strValue = add(sOpertand1, sOpertand2).toString();
            break;
        case '*':
            strValue = mul(sOpertand1, sOpertand2).toString();
            break;
        case '/': //��
            strValue = div(sOpertand1, sOpertand2).toString();
            break;
        case '%': //�������
            double dblOpertand1,
            dblOpertand2;
            dblOpertand1 = strToDouble(sOpertand1);
            dblOpertand2 = strToDouble(sOpertand2);
            if (dblOpertand2 == 0.0) {
                return "0";
            }
            dblOpertand1 = dblOpertand1 % dblOpertand2;
            strValue = format(dblOpertand1, "0");
            break;
        default:
            strValue = "";
        }
        return strValue;
    }

    /**
     * �ҵ���ƥ������ŵ�:��Ϊ���ſ��������Ƶ�Ƕ��
     * @param strExpression ���ʽ
     * @return �ұ����ŵĹ��λ��
     */
    private static int findRightPos(String strExpression) {
        int rightCount = 0;
        int leftCount = 1;
        int pos = -1;
        for (int idx = 0; idx < strExpression.length(); idx++) {
            if (strExpression.charAt(idx) == ')') {
                ++rightCount;
            } else if (strExpression.charAt(idx) == '(') {
                ++leftCount;
            }
            if (leftCount == rightCount) {
                pos = idx;
                break;
            }
        }
        return pos;
    }

    /**
     * ������ʽ(����Ӽ��˳�,����Ƕ������)
     * �÷�����Ҫ���ö�ջ���ݹ��㷨��ʵ��
     * ע:��Ҫ�������������������ȷ,��һ���õ���ȷ�Ľ��
     *
     * @param strExpression �ַ������ʽ
     * @return ��������Ľ��
     */
    private static String procSum(String strExpression) {
        Stack operator = new Stack();
        Stack operation = new Stack();
        int pos = 0;
        int rightPos = 0;
        char curOper;
        String strValue = "";
        String strOpertand1 = "";
        String strOpertand2 = "";
        boolean bNegative = false;
        try {
            while (true) {
                bNegative = false;
                //������
                if (strExpression.charAt(pos) == '-') {
                    if (pos > 0) {
                        if (isOperator(strExpression.charAt(pos - 1))) {
                            if (strExpression.charAt(pos - 1) == '-')
                                throw new RuntimeException("");
                            bNegative = true;
                        }
                    } else {
                        bNegative = true;
                    }
                }
                //���������
                if (isOperator(strExpression.charAt(pos)) && bNegative == false) {
                    operation.push(strValue); //��������ѹ��ջ
                    strValue = "";
                    if (!operator.isEmpty()) {
                        if (priority(strExpression.charAt(pos)) <= priority(((String) operator
                                .lastElement()).charAt(0))) {
                            strOpertand1 = (String) operation.pop();
                            strOpertand2 = (String) operation.pop();
                            curOper = ((String) operator.pop()).charAt(0);
                            operation.push(toResult(curOper, strOpertand2,
                                    strOpertand1));
                            if (!operator.isEmpty()
                                    && priority(strExpression.charAt(pos)) == 1) {
                                strOpertand1 = (String) operation.pop();
                                strOpertand2 = (String) operation.pop();
                                curOper = ((String) operator.pop()).charAt(0);
                                operation.push(toResult(curOper, strOpertand2,
                                        strOpertand1));
                            }
                        }
                        operator.push(strExpression.substring(pos, pos + 1)); //��������ѹ��ջ
                    } else {
                        operator.push(strExpression.substring(pos, pos + 1)); //��������ѹ��ջ
                    }
                } else if (strExpression.charAt(pos) == '(') {
                    rightPos = findRightPos(strExpression.substring(pos + 1)); //����ƥ����ұ��к�
                    if ((rightPos + pos - 1) <= pos || rightPos == -1) { //�ݴ���
                        ++pos;
                        continue;
                    }
                    //�ݹ���ñ�����,���������ڵı��ʽ
                    strValue = procSum(strExpression.substring(pos + 1, pos
                            + rightPos + 1));
                    pos += rightPos + 2;
                    if (pos >= strExpression.length()) {
                        operation.push(strValue); //������������ֵѹ���ջ
                        break;
                    }
                    continue;
                } else {
                    //����Ƿ��ַ�
                    if (strExpression.charAt(pos) != ')'
                            && (!strExpression.substring(pos, pos + 1).equals(
                                    " ")))
                        strValue += strExpression.substring(pos, pos + 1);
                }
                ++pos;
                if (pos >= strExpression.length()) {
                    operation.push(strValue); //��������ѹ��ջ
                    break;
                }
            }
            //�����ջ�����յ�����!!
            while (!operator.isEmpty()) {
                strOpertand1 = (String) operation.pop();
                strOpertand2 = (String) operation.pop();
                curOper = ((String) operator.pop()).charAt(0);
                operation.push(toResult(curOper, strOpertand2, strOpertand1));
            }
        } catch (Exception e) {
            throw new RuntimeException("���ʽ����");
        }
        return (String) operation.pop();
    }

    /**
     * �ȼ���Ƿ���ȷ,�������ȷ�����쳣
     * @param strExpression ���ʽ
     * @return ����Ľ��
     */
    public static String sum(String strExpression) {
        if (checkExpression(strExpression))
            return procSum(strExpression);
        else
            throw new RuntimeException("���ʽ����");
    }

    /**
     * ��"2003-08-08"��ʽ������ת����Calandar
     * @param s String���͵�����
     * @return �ɹ���ת�����Calendar����ʧ�ܣ�null
     */
    public static Calendar stringToCalendar(String strDate) {
        if (strDate == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(strDate, new ParsePosition(0)));
            return c;
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
}
