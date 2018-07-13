package com.fq.modules.was.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatesUtils {

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM");

	/**
     * 获取前一天日期yyyyMMdd
     * @ 经测试，针对闰年02月份或跨年等情况，该代码仍有效。测试代码如下
     * @ calendar.set(Calendar.YEAR, 2013);
     * @ calendar.set(Calendar.MONTH, 0);
     * @ calendar.set(Calendar.DATE, 1);
     * @ 测试时，将其放到<code>calendar.add(Calendar.DATE, -1);</code>前面即可
     * @return 返回的日期格式为yyyy-MM-dd
     */
    public static String getYestoday(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return sdf.format(calendar.getTime());
    }
    /**
     * 获取当前的日期yyyyMMdd
     */
    public static String getToday(){

		return sdf.format(new Date());
    }
	public static String time(){

		long times = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(times);
		String time = sdf2.format(date);

		return time;
	}

    /**
     * 获取明天
     * @return
     */
    public static String getTomorrow(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +1);
        return sdf.format(calendar.getTime());
    }

	/**
	 * 获取当前时间days天的日期
	 * @param days
	 * @return
	 */
	public static String getDateByDay(int days){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -days);
		return sdf.format(calendar.getTime());
	}
    /**
     * 获取最后一天/月
     * @return
     * @throws ParseException 
     */
    public static String lastDayOfMonth(String d) throws ParseException{

		int m = Integer.parseInt(d.split("-")[1]);
		 Date date = new Date(sdf.parse(d.split("-")[0]+"-"+(m+1)+"-01").getTime()-24*60*60*1000);
        
        String lastday = sdf.format(date);  
        
		return lastday;
		
	}

	/**
	 * 获取当月第一天
	 * @return
     */
	public static String getFirstDayOfThisMonth(){
		String time = sdf3.format(new Date())+"-01";
		return time;
	}

	/**
	 * 获取下个月第一天
	 * @return
     */
	public static String getFirstDayOfNextMonth(){

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, +1);
		String time = sdf3.format(calendar.getTime())+"-01";
		return time;
	}

	/**
	 * 获取上个月最后一天
	 * @return
	 * @throws ParseException
	 */
	public static String getLastDayOfLastMonth() throws ParseException {

		String time = sdf.format(sdf.parse(getFirstDayOfThisMonth()).getTime()-24*60*60*1000);
		return time;

	}

	/**
	 * 获得本周一
	 * @return
	 */
    public static String getMondayOfThisWeek() {
    	Calendar c = Calendar.getInstance();
    	  int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
    	  if (day_of_week == 0){
    		  day_of_week = 7;
    	  }
    	  c.add(Calendar.DATE, -day_of_week + 1);
    	  return sdf.format(c.getTime());
    }
	/**
	 * 得到本周日
	 *
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 7);
		return sdf.format(c.getTime());
	}

	/**
	 * 获取上周一
	 * @return
	 * @throws ParseException
	 */
	public static String getMondayOfLastWeek() throws ParseException {

		return sdf.format(sdf.parse(getMondayOfThisWeek()).getTime()-7*24*60*60*1000);
	}

	/**
	 * 获取上周日
	 * @return
	 * @throws ParseException
	 */
	public static String getSundayOfLastWeek() throws ParseException {

		return sdf.format(sdf.parse(getMondayOfThisWeek()).getTime()-24*60*60*1000);
	}

	public static String getDateByTime(long time){

		return sdf2.format(new Date(time));
	}

	/**
	 * 格式化指定日期为指定格式
	 * @param time
	 * @return
	 */
	public static Date formatDate(String time,String type) throws ParseException {

		return new SimpleDateFormat(type).parse(time);
	}
    
    public static void main(String[] args) throws ParseException {
		
    	/*System.out.println(getLastDayOfLastMonth());
    	
    	Double money = 0.00;
    	double m = 0.01;
    	for(int i=1;i<=30;i++){
    		if(i != 1){
    			m = m*2;
    			money += m;
    		}else{
    			money = m;
    		}
    		System.out.println(i+"  "+m+"   "+money);
    	}
    	System.out.println("总金额："+money);*/
		System.out.println(getMondayOfThisWeek());
		System.out.println(getSundayOfThisWeek());
		System.out.println(getMondayOfLastWeek());
		System.out.println(getSundayOfLastWeek());
		System.out.println(getDateByTime(1515371796000L));
	}

	/**
	 * 获取毫秒
	 * @param getTime
	 * @return
	 */
	public static long getTimesByString(String getTime) throws ParseException {

		if (CommonUtil.isNotEmpty(getTime)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf2.parse(getTime));
			return calendar.getTimeInMillis();
		} else {
			return 0L;
		}
	}
}
