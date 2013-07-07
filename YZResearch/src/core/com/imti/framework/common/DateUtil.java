package com.imti.framework.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class DateUtil {
	
	// ��ʮ��Сʱ��
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	private static SimpleDateFormat dfOnly = new SimpleDateFormat("yyyy-MM-dd");
	// ���Գ�������
	private static final String ENGLISH = "english";
	private static final String CHINESE = "chinese";
	// ���ڳ�������
	private static final String ESUNDAY = "Sunday";
	private static final String CSUNDAY = "������";
	private static final String EMONDAY = "Monday";
	private static final String CMONDAY = "����һ";
	private static final String ETUESDAY = "Tuesday";
	private static final String CTUESDAY = "���ڶ�";
	private static final String EWEDNESDAY = "Wednesday";
	private static final String CWEDNESDAY = "������";
	private static final String ETHURSDAY = "Thursday";
	private static final String CTHURSDAY = "������";
	private static final String EFRIDAY = "Friday";
	private static final String CFRIDAY = "������";
	private static final String ESATURDAY = "Saturday";
	private static final String CSATURDAY = "������";
	private static final String EDEFAULT = "Dimness";
	private static final String CDEFAULT = "����";
	public static String DateTOStr(Date date) {
		return df.format(date);
	}

	public static Date StrToDate(String str) throws ParseException {
		if(str==null || str.trim().equals("")){
			return null;
		}else{
			return df.parse(str);
		}
	}
	public static Date StrToDateOnly(String str) throws ParseException {
		if(str==null || str.trim().equals("")){
			return null;
		}else{
			return dfOnly.parse(str);
		}
	}
	public static String getNowDate() {
		Date d1 = new Date();
		String str = df.format(d1);
		return str;
	}

	public static String addMinute(String timeSrc, int addminute)
			throws ParseException {
		Date d1 = StrToDate(timeSrc);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d1);
		cal.add(Calendar.MINUTE, addminute);
		return DateTOStr(cal.getTime());
	}

	/* �Ƚϴ�С ��һ���ȵȶ�С����True */
	public static boolean isLess(String src, String dest) throws ParseException {
		Date d1 = StrToDate(src);
		Date d2 = StrToDate(dest);
		if (d1.getTime() < d2.getTime())
			return true;
		else
			return false;
	}

	/**
	 * �õ���ǰ�������ڵ�����»���
	 * @param strDate
	 *            �ַ��������ڣ���ʽ��2004-05-20��
	 * @param intFlag
	 *            int ����(0������,1������,2������)
	 * @return int �ɹ��������յ�����ֵ ʧ�ܣ�-1
	 */
	public static int getNumberOfDate(String strDate, int intFlag) {
		StringTokenizer st = new StringTokenizer(strDate, "-");
		// �ݴ���
		if (st.countTokens() != 3) {
			return 0;
		}
		int year = Integer.parseInt(st.nextToken());
		int month = Integer.parseInt(st.nextToken());
		int day = Integer.parseInt(st.nextToken());
		if (intFlag == 0) {
			return year;
		} else if (intFlag == 1) {
			return month;
		} else if (intFlag == 2) {
			return day;
		} else {
			return -1;
		}
	}

	/**
	 * ��õ�ǰ�ַ������͵�����ʱ��ֵ����ʽ��2004-05-20 00:00:00��
	 * 
	 * @return ����ʱ��ֵ
	 */
	public static String getCurrentDateTime() {
		Date d = new Date();
		Timestamp t = new Timestamp(d.getTime());
		return t.toString().substring(0, 19);
	}

	/**
	 * ��õ�ǰ�ַ������͵�����ֵ����ʽ��2004-05-20��
	 * 
	 * @return ����ֵ
	 */
	public static String getCurrentDate() {
		Date d = new Date();
		Timestamp t = new Timestamp(d.getTime());
		return t.toString().substring(0, 10);
	}

	/**
	 * ��õ�ǰ�ַ������͵�ʱ��ֵ
	 * 
	 * @return ʱ��ֵ����ʽΪ"00:00:00"��
	 */
	public static String getCurrentTime() {
		Date d = new Date();
		Timestamp t = new Timestamp(d.getTime());
		return t.toString().substring(11, 19);
	}

	/**
	 * �õ�ָ��������
	 * 
	 * @param calendar
	 * @return
	 */
	public static String getCurrentDate(Calendar calendar) {
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String date = String.valueOf(calendar.get(Calendar.DATE));
		String retDate = year + "-"
				+ (month.length() > 1 ? month : "0" + month) + "-"
				+ (date.length() > 1 ? date : "0" + date);
		return retDate;
	}

	/**
	 * �����ĳ����������������
	 * 
	 * @param intWeek
	 *            ��������ֵ
	 * @param language
	 *            �������ͣ�Ŀǰֻ֧�����ĺ�Ӣ�ģ�
	 * @return ��ĳ����������������
	 */
	private static String selectWeek(int intWeek, String language) {
		String week = CDEFAULT;

		switch (intWeek) {
		case 1:
			if (language.equals(ENGLISH)) {
				week = EMONDAY;
			} else if (language.equals(CHINESE)) {
				week = CMONDAY;
			}
			break;
		case 2:
			if (language.equals(ENGLISH)) {
				week = ETUESDAY;
			} else if (language.equals(CHINESE)) {
				week = CTUESDAY;
			}
			break;
		case 3:
			if (language.equals(ENGLISH)) {
				week = EWEDNESDAY;
			} else if (language.equals(CHINESE)) {
				week = CWEDNESDAY;
			}
			break;
		case 4:
			if (language.equals(ENGLISH)) {
				week = ETHURSDAY;
			} else if (language.equals(CHINESE)) {
				week = CTHURSDAY;

			}
			break;
		case 5:
			if (language.equals(ENGLISH)) {
				week = EFRIDAY;
			} else if (language.equals(CHINESE)) {
				week = CFRIDAY;

			}
			break;
		case 6:
			if (language.equals(ENGLISH)) {
				week = ESATURDAY;
			} else if (language.equals(CHINESE)) {
				week = CSATURDAY;
			}
			break;
		case 0:
			if (language.equals(ENGLISH)) {
				week = ESUNDAY;
			} else if (language.equals(CHINESE)) {
				week = CSUNDAY;
				// System.out.println("-----------7 chinese
				// week----------"+week);
			}
			break;
		default:
			if (language.equals(ENGLISH)) {
				week = EDEFAULT;
			} else if (language.equals(CHINESE)) {
				week = CDEFAULT;
			}
		}

		return week;
	}

	/**
	 * ��ø������ڵ�����
	 * 
	 * @param calendar
	 *            ���и������ڵ�Calendar����
	 * @return �ɹ����������� ʧ�ܣ�-1
	 */
	public static int getWeek(Calendar calendar) {
		if (calendar == null) {
			return -1;
		}

		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * ��ø������ڵ���������
	 * 
	 * @param calendar
	 *            ���и������ڵ�Calendar����
	 * @return �ɹ����������� ʧ�ܣ�null
	 */
	public static String getChineseWeek(Calendar calendar) {
		if (calendar == null) {
			return null;
		}

		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		// System.out.println("---------------intWeek--------------"+intWeek);
		return selectWeek(intWeek, CHINESE);
	}

	/**
	 * ��ø������ڵ�Ӣ������
	 * 
	 * @param calendar
	 *            ���и������ڵ�Calendar����
	 * @return Ӣ������
	 */
	public static String getEnglishWeek(Calendar calendar) {
		if (calendar == null) {
			return null;
		}

		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return selectWeek(intWeek, ENGLISH);
	}

	/**
	 * ��õ�ǰ����
	 * 
	 * @return ��������
	 */
	public static String getCurrentWeek() {
		int week = -1;
		Calendar c = SumUtils.stringToCalendar(getCurrentDate());
		week = c.get(Calendar.WEEK_OF_MONTH);
		System.out.println("The current week is:***" + week);
		return (String.valueOf(week));
	}

	/**
	 * ��õ�ǰ��������
	 * 
	 * @return ��������
	 */
	public static String getCurrentChineseWeek() {
		Calendar c = SumUtils.stringToCalendar(getCurrentDate());
		int intWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		return selectWeek(intWeek, CHINESE);
	}

	/**
	 * ��õ�ǰӢ������
	 * 
	 * @return Ӣ������
	 */
	public static String getCurrentEnglishWeek() {
		Calendar c = SumUtils.stringToCalendar(getCurrentDate());
		int intWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		return selectWeek(intWeek, ENGLISH);
	}

	/**
	 * �õ���ǰ����(1��ʾ����һ,2Ϊ���ڶ�,�Դ�����) Wengnb Add 2003-09-09'
	 * 
	 * @param calendar
	 *            Calendar
	 * @return int
	 */
	public static int getCurWeek(Calendar calendar) {
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * Wengnb Add 2003-09-09'
	 * 
	 * @return
	 */
	public static int getCurWeek() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * Wengnb Add 2003-09-09'
	 * 
	 * @return
	 */
	public static String getCurWeek(boolean toChinese) {
		Calendar calendar = Calendar.getInstance();
		return getCurWeek(calendar, true);
	}

	/**
	 * �õ���ǰ����(������һ,���ڶ�,�Դ����� Wengnb Add 2003-09-09
	 * 
	 * @param calendar
	 *            Calendar
	 * @param toChinese
	 *            boolean
	 * @return String
	 */
	public static String getCurWeek(Calendar calendar, boolean toChinese) {

		String strRet = "";
		int intWeek = 0;
		// System.out.println("-------calendar.date----------" + calendar.DATE);
		// System.out.println("-------calendar.DayOfWeek-----" +
		// calendar.DAY_OF_WEEK);
		// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // ��ȡ���ܵĵڼ���
		// �����������Ϊÿ�ܵ�һ�죬���Լ�һ
		// System.out.println("-----------intWeek----------------" + intWeek);
		calendar = null; // ����
		if (!toChinese) {
			return (String.valueOf(intWeek));
		}

		switch (intWeek) {
		case 1:
			strRet = "����һ";
			break;
		case 2:
			strRet = "���ڶ�";
			break;
		case 3:
			strRet = "������";
			break;
		case 4:
			strRet = "������";
			break;
		case 5:
			strRet = "������";
			break;
		case 6:
			strRet = "������";
			break;
		case 0:
			strRet = "������";
			break;
		default:
			strRet = "����";
			break;
		}
		return strRet;
	}

	/**
	 * ȡ�����Minutes�������������ڸ�ʽ��2004-8-2 07:20:00��
	 * 
	 * @param strDateTime
	 *            String
	 * @param addMinutes
	 *            int
	 * @return String
	 */
	public static String getDateTimeByAddMinute(String strDateTime,
			int addMinutes) {

		try {
			SimpleDateFormat sdf = null;
			if (strDateTime.length() < 17)
				sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
			else
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			StringBuffer sumDate = new StringBuffer();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(strDateTime, new ParsePosition(0)));
			calendar.add(Calendar.MINUTE, addMinutes);
			sdf.format(calendar.getTime(), sumDate, new FieldPosition(0));
			return sumDate.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * �õ���Ӧ���¼�������� , ��:getDate("2003-03-01",2)�������"2003-03-03"
	 * 
	 * @param strDate
	 *            ָ��������
	 * @param intDays
	 *            ����
	 * @return String ��������
	 */
	public String getDate(String strDate, int intDays) {
		StringTokenizer st = new java.util.StringTokenizer(strDate, "-");
		String strNewYear = new String("");
		String strNewMonth = new String("");
		String strNewDay = new String("");

		if (st.countTokens() != 3) { // �ݴ���-����ָ�����Ϊ3��˵����������
			return "";
		}
		Calendar calendar = Calendar.getInstance();
		int year = Integer.parseInt(st.nextToken());
		int month = Integer.parseInt(st.nextToken());
		int day = Integer.parseInt(st.nextToken());
		calendar.set(year, month - 1, day);
		calendar.add(Calendar.DAY_OF_MONTH, intDays); // ��һ������
		// �õ���
		strNewYear = String.valueOf(calendar.get(Calendar.YEAR));
		// �õ���
		strNewMonth = (calendar.get(Calendar.MONTH) + 1 < 10) ? "0"
				+ String.valueOf(calendar.get(Calendar.MONTH) + 1) : String
				.valueOf(calendar.get(Calendar.MONTH) + 1);
		// �õ���
		strNewDay = (calendar.get(Calendar.DAY_OF_MONTH) < 10) ? "0"
				+ String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) : String
				.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		strDate = strNewYear + "-" + strNewMonth + "-" + strNewDay;
		// �õ���ǰ����Ϊ���ڼ�
		return strDate;
	}

	/**
	 * ʵ���������ӹ���
	 * 
	 * @param strDate
	 *            �������ڣ���ʽ��2004-05-20��
	 * @param dateNumber
	 *            ��������ֵ
	 * @return �ɹ������Ӻ������ֵ����ʽ��2004-05-20�� ʧ�ܣ�null
	 */
	public static String getDateSum(String strDate, int dateNumber) {
		Calendar c = null;
		try {
			c = SumUtils.stringToCalendar(strDate);
			if (c == null) {
				return null;
			}
			c.add(Calendar.DATE, dateNumber);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			StringBuffer sumDate = new StringBuffer();
			sdf.format(c.getTime(), sumDate, new FieldPosition(0));
			return sumDate.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ʵ��ʱ�����
	 * 
	 * @param beginDateTime
	 *            2005-07-12 17:56:00
	 * @param time
	 * @return
	 */
	public static String getDateTimeSum(String beginDateTime, String time) {
		System.out.println("");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(beginDateTime.substring(0,
				4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(beginDateTime.substring(
				5, 7)) - 1);
		calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(beginDateTime
				.substring(8, 10)));
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(beginDateTime
				.substring(11, 13)));
		calendar.set(Calendar.MINUTE, Integer.parseInt(beginDateTime.substring(
				14, 16)));
		calendar.set(Calendar.SECOND, Integer.parseInt(beginDateTime.substring(
				17, 19)));
		calendar.add(Calendar.MINUTE, Integer.parseInt(time));
		return calendar.getTime().toLocaleString();
	}

	/**
	 * ʵ�����ڼ��ٹ���
	 * 
	 * @param strDate
	 *            �������ڣ���ʽ��2004-05-20��
	 * @param dateNumber
	 *            ���ڼ���ֵ
	 * @return �ɹ������ٺ������ֵ����ʽ��2004-05-20�� ʧ�ܣ�null
	 */
	public static String getDateSubtract(String strDate, int dateNumber) {
		try {
			dateNumber = Integer.parseInt("-" + String.valueOf(dateNumber));
			return getDateSum(strDate, dateNumber);
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * ʵ�������������
	 * 
	 * @param strBeginDate
	 *            ��Ϊ�����������ڣ���ʽ��2004-05-20��
	 * @param strEndDate
	 *            ��Ϊ���������ڣ���ʽ��2004-05-20��
	 * @return �ɹ������ڲ�ֵ ʧ�ܣ�0
	 */
	public static long getDateSubtract(String strBeginDate, String strEndDate) {
		String strTime = "00:00:00";
		strBeginDate += " " + strTime;
		strEndDate += " " + strTime;
		Date beginDate = null;
		Date endDate = null;
		long subtractDate = 0;

		try {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			ParsePosition pp1 = new ParsePosition(0);
			ParsePosition pp2 = new ParsePosition(0);
			beginDate = formatter.parse(strBeginDate, pp1);
			endDate = formatter.parse(strEndDate, pp2);
			subtractDate = (beginDate.getTime() - endDate.getTime())
					/ (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			// e.printStackTrace();
			return 0;
		}

		return subtractDate;
	}

	/**
	 * �õ����ڲ� Wengnb Add 2003-09-09 ��getDateDiff("2004-02-01"
	 * ,"2004-03-01")��������29��(��Ϊ����)
	 * 
	 * @param beginDate
	 *            String: "2003-09-01"
	 * @param endDate
	 *            String :'2003-09-01'
	 * @return int
	 */
	public static int getDateDiff(String strBeginDate, String strEndDate) {
		int intDays = 0;
		Calendar now = Calendar.getInstance();
		int iBeginYear = getDateNumber(strBeginDate, 0);
		int iEndYear = getDateNumber(strEndDate, 0);
		int intTmp = 0;
		// �ݴ���
		if (iBeginYear > iEndYear) {
			return 0;
		}
		for (int i = iBeginYear; i < iEndYear; i++) {
			now.set(i, 11, 31);
			intTmp += now.get(Calendar.DAY_OF_YEAR);
		}
		now.set(iBeginYear, getDateNumber(strBeginDate, 1) - 1, getDateNumber(
				strBeginDate, 2));
		int iStartDays = now.get(Calendar.DAY_OF_YEAR); // �õ���ʼ�������xxxx-01-01�����ڵ����ڵ�������
		now.set(iEndYear, getDateNumber(strEndDate, 1) - 1, getDateNumber(
				strEndDate, 2));
		int iEndDays = now.get(Calendar.DAY_OF_YEAR);
		intDays = intTmp + iEndDays - iStartDays;
		return intDays;
	}

	/**
	 * �õ���ǰ�������ڵ�����»���(�ú����Ǹ��Ǹ������õ�) Wengnb Add 2003-09-09
	 * 
	 * @param strDate
	 *            String :"2003-08-01"
	 * @param intFlag
	 *            int: ����(0������,1������,2������)
	 * @return int
	 */
	private static int getDateNumber(String strDate, int intFlag) {
		StringTokenizer st = new StringTokenizer(strDate, "-");
		// �ݴ���
		if (st.countTokens() != 3) {
			return 0;
		}
		int year = Integer.parseInt(st.nextToken());
		int month = Integer.parseInt(st.nextToken());
		int day = Integer.parseInt(st.nextToken());
		if (intFlag == 0) { // �õ���ǰ��
			return year;
		} else if (intFlag == 1) { // �õ���ǰ��
			return month;
		} else { // �õ���ǰ��
			return day;
		}
	}

	/**
	 * ͨ����ǰ���ڻ�ȡ�ĵ�ǰ���,���������ݿ�����Ϊ����ʹ�� ��Ȼֻ������ÿ���Ӧһ����¼������� eg: 2003-09-09 �õ�20031009
	 * 
	 * @return String
	 */

	public static String getNowBh() {
		Calendar cal = Calendar.getInstance();
		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH) + 1;
		int d = cal.get(Calendar.DATE);
		int h = cal.get(Calendar.HOUR_OF_DAY);
		int mi = cal.get(Calendar.MINUTE);
		return y + toStr(m) + toStr(d) + toStr(h) + toStr(mi);
	}

	private static String toStr(int i) {
		return i < 10 ? "0" + i : String.valueOf(i);
	}

	/**
	 * �õ������ǵ�ǰ�µĵڼ��� CY Add 2004-08-04'
	 * 
	 * @return String
	 */
	public static String getWeekNumOfThisMonth() {
		String weekOfMonth = "";
		Calendar calendar = Calendar.getInstance();
		String curDate = getCurrentDate(); // ȡ�õ�ǰ����--��Ӧ��������
		// System.out.println("-------------curDate-----------" + curDate);
		int year = Integer.parseInt(curDate.substring(0, 4));
		int month = Integer.parseInt(curDate.substring(5, 7));
		int day = Integer.parseInt(curDate.substring(8, 10));
		// System.out.println("----+++++----" + String.valueOf(year) + "-" +
		// String.valueOf(month) + "-" +
		// String.valueOf(day));
		calendar.set(year, month, day);
		weekOfMonth = String.valueOf(Calendar.WEEK_OF_MONTH);
		// ͨ��WEEK_OF_MONTH��ȡ�ĵ�ǰ��ӵ�м������ڵ���ֵ�����Ի���Ҫת��ȡ�õ�ǰ�ǵڼ��ܲ���
		// System.out.println("----------weekOfMonth-----------" + weekOfMonth);
		return weekOfMonth;
	}

	/**
	 * �õ���ǰ���������ڼ� CY Add 2004-08-28'
	 * 
	 * @return String
	 */

	public static String getWeek(String strDate, int intDays) {
		java.util.StringTokenizer st = new java.util.StringTokenizer(strDate,
				"-");
		String strNewYear = new String("");
		String strNewMonth = new String("");
		String strNewDay = new String("");

		if (st.countTokens() != 3) { // �ݴ���
			return "";
		}
		java.util.Calendar calendar = null;
		calendar = java.util.Calendar.getInstance();
		int year = Integer.parseInt(st.nextToken());
		int month = Integer.parseInt(st.nextToken());
		int day = Integer.parseInt(st.nextToken());
		calendar.set(year, month - 1, day);
		calendar.add(Calendar.DAY_OF_MONTH, intDays); // ��һ������
		// �õ���
		strNewYear = String.valueOf(calendar.get(Calendar.YEAR));
		// �õ���
		strNewMonth = (calendar.get(Calendar.MONTH) + 1 < 10) ? "0"
				+ String.valueOf(calendar.get(Calendar.MONTH) + 1) : String
				.valueOf(calendar.get(Calendar.MONTH) + 1);
		// �õ���
		strNewDay = (calendar.get(Calendar.DAY_OF_MONTH) < 10) ? "0"
				+ String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) : String
				.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		strDate = strNewYear + "-" + strNewMonth + "-" + strNewDay;
		// �õ���ǰ����Ϊ���ڼ�
		String myWeek = getCurWeek(calendar, true);

		return myWeek;
	}

	/**
	 * �õ��·ݵĵ�һ������ݼ� �磺2005-03-01���򷵻� 2
	 * 
	 * @param date
	 *            String ��ʽΪ2005-03-01
	 * @return int add by lhd
	 */
	public static int getFirstDayOfWeekInMonth(String date) {
		StringTokenizer st = new StringTokenizer(date, "-");
		if (st.countTokens() != 3) { // �ݴ���
			return -1;
		}
		Calendar calendar = Calendar.getInstance();
		int year = Integer.parseInt(st.nextToken());
		int month = Integer.parseInt(st.nextToken());
		calendar.set(year, month - 1, 1);
		int iWeek = getCurWeek(calendar);
		if (iWeek == 0) {
			iWeek = 7;
		}
		return iWeek;
	}

	/**
	 * �õ����������·ݵ�����
	 * 
	 * @param date
	 *            String
	 * @return int add by lhd
	 */
	public static int getDayNumInMonth(String date) {
		int month[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		StringTokenizer st = new StringTokenizer(date, "-");
		if (st.countTokens() != 3) { // �ݴ���
			return -1;
		}
		int year = Integer.parseInt(st.nextToken());
		int tmpMonth = Integer.parseInt(st.nextToken());
		int dayNum = month[tmpMonth - 1];
		if (tmpMonth == 2 && year % 4 == 0) {// ���Ϊ����ģ����µ�����Ϊ29��
			++dayNum;
		}
		return dayNum;
	}

	/**
	 * �����������ڵõ��������������ܵ�����һ�������������
	 * 
	 * @param date
	 *            String
	 * @return String[]
	 */
	public static String[] getDateByWeek(String date) {
		StringTokenizer st = new StringTokenizer(date, "-");
		Calendar calendar = Calendar.getInstance();
		String[] dateOfWeek = new String[7];
		int year = Integer.parseInt(st.nextToken());
		int month = Integer.parseInt(st.nextToken());
		int day = Integer.parseInt(st.nextToken());
		calendar.set(year, month - 1, day);
		int dayOfWeek = getCurWeek(calendar);
		if (dayOfWeek == 0) {// ���������쿪ʼ
			dayOfWeek = 7;
		}
		System.out.println("The dayOfWeek is:-->" + dayOfWeek);
		String tmpDate = getDateSum(date, -(dayOfWeek - 1));
		System.out.println("The tmpDate is:" + tmpDate);
		for (int i = 0; i < dateOfWeek.length; i++) {
			dateOfWeek[i] = getDateSum(tmpDate, i);
		}
		return dateOfWeek;
	}

	/**
	 * �����ڸ�ʽ��Ϊ���ĵ�������
	 * 
	 * @param sDate
	 *            String
	 * @return String author: lun
	 */
	public static String toChineseDate(String sDate) {
		String[] arrStr = sDate.split("-");
		String str = sDate;
		if (arrStr.length == 3) {
			str = arrStr[0] + "��" + arrStr[1] + "��" + arrStr[2] + "��";
		}
		return str;
	}

	/**
	 * �����ڸ�ʽ��Ϊ���ĵ�������
	 * 
	 * @param date
	 *            Date
	 * @return String author: lun
	 */
	public static String toChineseDate(Date date) {
		String str;
		Format ft = new SimpleDateFormat("yyyy��MM��dd��");
		str = ft.format(date);
		return str;
	}

	/**
	 * �����ڸ�ʽ��Ϊ���ĵ�����
	 * 
	 * @param sDate
	 *            String
	 * @return String author: lun
	 */
	public static String toChineseMonth(String sDate) {
		String[] arrStr = sDate.split("-");
		String str = sDate;
		if (arrStr.length >= 2) {
			str = arrStr[0] + "��" + arrStr[1] + "��";
		}
		return str;
	}

	/**
	 * �����ڸ�ʽ��Ϊ���ĵ���
	 * 
	 * @param sDate
	 *            String
	 * @return String author: lun
	 */
	public static String toChineseYear(String sDate) {
		String[] arrStr = sDate.split("-");
		String str = arrStr[0] + "��";

		return str;
	}

	/**
	 * ���ڲ�ѯ�ڼ�Ĵ���������֮��� �����ѯ2005-2��2005-3����� ��Ҫ˼·��between '2005-2-1' and
	 * '2005-4-1' ��1
	 * 
	 * @param sDate1
	 *            String
	 * @param sDate2
	 *            String
	 * @return String author: lun
	 */
	public static String getBetweenPeriodSQL(String sDate1, String sDate2) {
		String sSql = " between Date('" + sDate1 + "' and Date('" + sDate2
				+ "') ";
		String[] arrStr;
		
		try {
			arrStr = sDate1.split("-");
			sDate1 = " Date('" + arrStr[0] + "-" + arrStr[1] + "-01') ";

			arrStr = sDate2.split("-");
			int ii = Integer.parseInt(arrStr[1]);
			if (ii == 12) {
				sDate2 = arrStr[0] + "-" + arrStr[1] + "-31";
			} else {// ���ڼ�1
				sDate2 = " Date( Days('" + arrStr[0] + "-" + (ii + 1)
						+ "-01') - 1 ) ";
			}

			sSql = " between " + sDate1 + " and " + sDate2 + " ";
		} catch (Exception ex) {
			System.out
					.println("com.gever.goa.util.DateTimeUtils.getBetweenPeriodSQL() error : "
							+ ex.getMessage());
		}
		return sSql;
	}

	/**
	 * ���Է���
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		System.out.println(DateUtil.getNextMonth(DateUtil.getCurrentDate(), -1));
		System.out.println(DateUtil.getNextMonth(DateUtil.getCurrentDate(), 1));
	}

	public static String getCurrentHourMin(){
		return (String) DateUtil.getNowDate().subSequence(11, 16);
	}
	// public static String toDate(String strDate) {
	// ActiveUsers au = ActiveUsers.getInstance();
	// String strRet = strDate;
	// if (au.isOracle()) {
	// strRet = "to_date('" + strRet + "','yyyy-mm-dd')";
	// }else {
	// strRet = "'" + strDate + "'";
	// }
	//
	// return strRet;
	// }
	//
	// public static String toDateTime(String strDate) {
	// ActiveUsers au = ActiveUsers.getInstance();
	// String strRet = strDate;
	// if (au.isOracle()) {
	// strRet = "to_date('" + strRet + "','yyyy-mm-dd hh24:mi:ss')";
	// } else {
	// strRet = "'" + strDate + "'";
	// }
	// return strRet;
	// }

	public static String[] getCurrentMonthBound() {
		Calendar cal = Calendar.getInstance();
		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH) + 1;
		String strMonth = y + "-" + toStr(m);
		String beginDate = strMonth + "-01";

		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);

		String endDate = strMonth + "-" + cal.get(Calendar.DATE);

		return new String[] { beginDate, endDate };
	}
	/**
	 * monthNum 
	 * (�˴�д����������������<br />��������<p/>��
	 * 
	 * @param curDate
	 * @param monthNum
	 * @return
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2010-8-31����08:53:05
	 * @since 1.0
	 */
	public static String getNextMonth(String curDate, int monthNum) {
		Calendar calendar = Calendar.getInstance();
		int year = getDateNumber(curDate, 0);
		int month = getDateNumber(curDate, 1);
		int day = getDateNumber(curDate, 2);
		calendar.set(year, month - 1, day);
		calendar.add(Calendar.MONTH, monthNum);
		return getCurrentDate(calendar);
	}

	private static String strCh[] = { "��", "һ", "��", "��", "��", "��", "��", "��",
			"��", "��" };

	private static String strUnit[] = { "", "ʮ", "��", "ǧ", "��", "��", "��", "��" };

	public static String getChineseDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = format.format(date);
		StringBuffer retStr = new StringBuffer();
		String tempStr = dateStr.substring(0, 4);
		for (int i = 0; i < tempStr.length(); i++) {
			retStr.append(strCh[Integer.parseInt(tempStr.charAt(i) + "")]);
		}
		retStr.append(strUnit[5]);

		if (dateStr.charAt(5) == '0') {

		} else if (dateStr.charAt(5) == '1') {
			retStr.append(strUnit[1]);
		} else {
			retStr.append(strCh[Integer.parseInt(dateStr.charAt(5) + "")]);
			retStr.append(strUnit[1]);
		}
		if (dateStr.charAt(6) == '0') {

		} else {
			retStr.append(strCh[Integer.parseInt(dateStr.charAt(6) + "")]);
		}
		retStr.append(strUnit[6]);

		if (dateStr.charAt(8) == '0') {

		} else if (dateStr.charAt(8) == '1') {
			retStr.append(strUnit[1]);
		} else {
			retStr.append(strCh[Integer.parseInt(dateStr.charAt(8) + "")]);
			retStr.append(strUnit[1]);
		}
		if (dateStr.charAt(9) == '0') {

		} else {
			retStr.append(strCh[Integer.parseInt(dateStr.charAt(9) + "")]);
		}
		retStr.append(strUnit[7]);
		return retStr.toString();
	}

	public static String convertDigToCh(int intDig) {
		String str0 = "";
		int temp;
		String strdig = String.valueOf(intDig);
		strdig = new StringBuffer(strdig).reverse().toString();
		if (intDig < 10000) {
			for (int i = 0; i < strdig.trim().length(); i++) {
				if (strdig.charAt(i) == '0') {
					str0 += "0";
				} else {
					if (i == 1 && strdig.charAt(i) == '1'
							&& strdig.trim().length() == 2) {
						str0 += strUnit[i];
					} else {
						str0 += strUnit[i] + strCh[strdig.charAt(i) - 48];
					}
				}
			}
			strdig = new StringBuffer(str0).reverse().toString();
			int index = strdig.indexOf("00");
			while (index != -1) {
				strdig = strdig.substring(0, index) + "0"
						+ strdig.substring(index + 2, strdig.length());
				index = strdig.indexOf("00");
			}
			if (strdig.endsWith("0")) {
				strdig = strdig.substring(0, strdig.length() - 1);
			}
			strdig = strdig.replace('0', '��');
		}
		return strdig;
	}
	public static String utcFormat(String utcPattrn){
		DateFormat df=new SimpleDateFormat("EEE MMM dd HH:mm:ss 'UTC+0800' yyyy",Locale.ENGLISH);//CST��ʽ  
	    Date date = null;  
	    try {  
	    	if(StringUtils.isNotBlank(utcPattrn) && utcPattrn.indexOf("UTC") != -1){
	    		date = (Date) df.parse(utcPattrn);//parse��������ת��  
	    		utcPattrn = DateUtil.DateTOStr(date);
			}
	     } catch (ParseException e) {  
	         e.printStackTrace();  
	     } 
	     return utcPattrn;
	}
	public static String utcSimpleFormat(String utcPattrn){
		DateFormat df=new SimpleDateFormat("EEE MMM dd HH:mm:ss 'UTC+0800' yyyy",Locale.ENGLISH);//CST��ʽ  
	    Date date = null;  
	    try {  
	    	if(StringUtils.isNotBlank(utcPattrn) && utcPattrn.indexOf("UTC") != -1){
	    		date = (Date) df.parse(utcPattrn);//parse��������ת��  
	    		utcPattrn = dfOnly.format(date);
	    		utcPattrn = utcPattrn.substring(0, 10);
			}
	     } catch (ParseException e) {  
	         e.printStackTrace();  
	     } 
	     if(StringUtils.isNotBlank(utcPattrn) ){
	    		utcPattrn = utcPattrn.substring(0, 10);
			}
	     return utcPattrn;
	}
	public static Date simpleDateFormat(String dateStr){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = null;  
	    try {  
	    		date = (Date) df.parse(dateStr);//parse��������ת��  
	     } catch (ParseException e) {  
	         e.printStackTrace();  
	     } 
	     return date;
	}
}
