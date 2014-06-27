package com.isoftframework.common.util;

import java.text.DateFormat;
import java.text.ParseException;   
import java.text.SimpleDateFormat;   
import java.util.Calendar;   
import java.util.Date;   
  
/**  
 * 时间帮助类  
 * @version $Id: DateUtil.java,v 1.1 2008/05/28 04:29:52 linan Exp $  
 * @author LiNan  
 */  
public class DateUtilold {   
  
    private  static Calendar calendar=Calendar.getInstance();   
       
    /**  
     * 得到当前的时间，时间格式yyyy-MM-dd  
     * @return  
     */  
    public static String getCurrentDate(){   
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");   
        return sdf.format(new Date());   
    }   
       
    /**  
     * 得到当前的时间,自定义时间格式  
     * y 年 M 月 d 日 H 时 m 分 s 秒  
     * @param dateFormat 输出显示的时间格式  
     * @return  
     */  
    public static String getCurrentDate(String dateFormat){   
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);   
        return sdf.format(new Date());   
    }   
       
    /**  
     * 日期格式化，默认日期格式yyyy-MM-dd  
     * @param date  
     * @return  
     */  
    public static String formatDateTime(Date date){ 
    	if(date==null)
    		return "";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");   
        return sdf.format(date);   
    }   
       
    /**  
     * 日期格式化，自定义输出日期格式  
     * @param date  
     * @return  
     */  
    public static String formatDateTime(Date date,String dateFormat){   
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);   
        return sdf.format(date);   
    }   
    /**  
     * 返回当前日期的前一个时间日期，amount为正数 当前时间后的时间 为负数 当前时间前的时间  
     * 默认日期格式yyyy-MM-dd  
     * @param field 日历字段  
     * y 年 M 月 d 日 H 时 m 分 s 秒  
     * @param amount 数量  
     * @return 一个日期  
     */  
    public static String getPreDate(String field,int amount){   
        calendar.setTime(new Date());   
        if(field!=null&&!field.equals("")){   
            if(field.equals("y")){   
                calendar.add(calendar.YEAR, amount);   
            }else if(field.equals("M")){   
                calendar.add(calendar.MONTH, amount);   
            }else if(field.equals("d")){   
                calendar.add(calendar.DAY_OF_MONTH, amount);   
            }else if(field.equals("H")){   
                calendar.add(calendar.HOUR, amount);   
            }   
        }else{   
            return null;   
        }          
        return formatDateTime(calendar.getTime());   
    }   
       
    /**  
     * 某一个日期的前一个日期  
     * @param d,某一个日期  
     * @param field 日历字段  
     * y 年 M 月 d 日 H 时 m 分 s 秒  
     * @param amount 数量  
     * @return 一个日期  
     */  
    public static String getPreDate(Date d,String field,int amount){   
        calendar.setTime(d);   
        if(field!=null&&!field.equals("")){   
            if(field.equals("y")){   
                calendar.add(calendar.YEAR, amount);   
            }else if(field.equals("M")){   
                calendar.add(calendar.MONTH, amount);   
            }else if(field.equals("d")){   
                calendar.add(calendar.DAY_OF_MONTH, amount);   
            }else if(field.equals("H")){   
                calendar.add(calendar.HOUR, amount);   
            }   
        }else{   
            return null;   
        }          
        return formatDateTime(calendar.getTime());   
    }   
       
    /**  
     * 某一个时间的前一个时间  
     * @param date  
     * @return  
     * @throws ParseException   
     */  
    public static String getPreDate(String date) throws ParseException{   
        Date d=new SimpleDateFormat().parse(date);   
        String preD=getPreDate(d,"d",1);   
        Date preDate=new SimpleDateFormat().parse(preD);   
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
        return sdf.format(preDate);   
    }   

	/**
	 * <p>
	 * <b>业务处理描述</b>
	 * <ul>
	 * <li>可见性原因：需要被其他应用调用</li>
	 * <li>目的：获取本年度的第一天</li>
	 * <li>适用的前提条件:</li>
	 * <li>后置条件：</li>
	 * <li>例外处理：无 </li>
	 * <li>已知问题：</li>
	 * <li>调用的例子： </li>
	 * </ul>
	 * </p>
	 * 
	 * @param format
	 * @return
	 */
	public static Date getFirstDayOfYear(String format)
	{
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_YEAR, 1);
		return ca.getTime();
	}

	/**
	 * <p>
	 * <b>业务处理描述</b>
	 * <ul>
	 * <li>可见性原因：需要被其他应用调用</li>
	 * <li>目的：获取一年的最后一个月</li>
	 * <li>适用的前提条件:</li>
	 * <li>后置条件：</li>
	 * <li>例外处理：无 </li>
	 * <li>已知问题：</li>
	 * <li>调用的例子： </li>
	 * </ul>
	 * </p>
	 * 
	 * @param format
	 * @return
	 */
	public static Date getLastMonth(String format)
	{
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
		return ca.getTime();
	}

	/**
	 * <p>
	 * <b>业务处理描述</b>
	 * <ul>
	 * <li>可见性原因：需要被其他应用调用</li>
	 * <li>目的：获取本年的最后一天</li>
	 * <li>适用的前提条件:</li>
	 * <li>后置条件：</li>
	 * <li>例外处理：无 </li>
	 * <li>已知问题：</li>
	 * <li>调用的例子： </li>
	 * </ul>
	 * </p>
	 * 
	 * @param format
	 * @return
	 */
	public static Date getLastDayOfYear()
	{
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.MONTH, Calendar.DECEMBER);
		ca.set(Calendar.DAY_OF_MONTH, 30);
		return ca.getTime();
	}

	/**
	 * <p>
	 * <b>业务处理描述</b>
	 * <ul>
	 * <li>可见性原因：需要被其他应用调用</li>
	 * <li>目的：Comments</li>
	 * <li>适用的前提条件:</li>
	 * <li>后置条件：</li>
	 * <li>例外处理：无 </li>
	 * <li>已知问题：</li>
	 * <li>调用的例子： </li>
	 * </ul>
	 * </p>
	 * 
	 * @param i
	 * @return
	 */
	public static Date subDays(Date source, int i)
	{

		return new Date(source.getTime() - 86400000 * i);
	}

	/**
	 * <p>
	 * <b>业务处理描述</b>
	 * <ul>
	 * <li>可见性原因：需要被其他应用调用</li>
	 * <li>目的：增加i小时</li>
	 * <li>适用的前提条件:</li>
	 * <li>后置条件：</li>
	 * <li>例外处理：无 </li>
	 * <li>已知问题：</li>
	 * <li>调用的例子：
	 * </li>
	 * </ul>
	 * </p>
	 * 
	 * @param source
	 * @param i
	 * @return
	 */
	public static Date subHours(Date source, int i)
	{
		return new Date(source.getTime() - 3600000 * i);
	}

	/**
	 * <p>
	 * <b>业务处理描述</b>
	 * <ul>
	 * <li>可见性原因：需要被其他应用调用</li>
	 * <li>目的：增加分钟</li>
	 * <li>适用的前提条件:</li>
	 * <li>后置条件：</li>
	 * <li>例外处理：无 </li>
	 * <li>已知问题：</li>
	 * <li>调用的例子：
	 * </li>
	 * </ul>
	 * </p>
	 * 
	 * @param source
	 * @param i
	 * @return
	 */
	public static Date subMinius(Date source, int i)
	{
		return new Date(source.getTime() - 60000 * i);
	}


	/**
	 * <p>
	 * <b>业务处理描述</b>
	 * <ul>
	 * <li>可见性原因：需要被其他应用调用</li>
	 * <li>目的：为时间增加I秒</li>
	 * <li>适用的前提条件:</li>
	 * <li>后置条件：</li>
	 * <li>例外处理：无 </li>
	 * <li>已知问题：</li>
	 * <li>调用的例子： </li>
	 * </ul>
	 * </p>
	 * 
	 * @param source
	 * @param i
	 * @return
	 */
	public static Date subSeconds(Date source, int i)
	{
		return new Date(source.getTime() - 1000 * i);
	}
	/**
	 * <p>
	 * <b>业务处理描述</b>
	 * <ul>
	 * <li>可见性原因：需要被其他应用调用</li>
	 * <li>目的：Comments</li>
	 * <li>适用的前提条件:</li>
	 * <li>后置条件：</li>
	 * <li>例外处理：无 </li>
	 * <li>已知问题：</li>
	 * <li>调用的例子： </li>
	 * </ul>
	 * </p>
	 * 
	 * @param i
	 * @return
	 */
	public static Date addDays(Date source, int i)
	{

		return new Date(source.getTime() + 86400000 * i);
	}

	/**
	 * <p>
	 * <b>业务处理描述</b>
	 * <ul>
	 * <li>可见性原因：需要被其他应用调用</li>
	 * <li>目的：增加i小时</li>
	 * <li>适用的前提条件:</li>
	 * <li>后置条件：</li>
	 * <li>例外处理：无 </li>
	 * <li>已知问题：</li>
	 * <li>调用的例子：
	 * </li>
	 * </ul>
	 * </p>
	 * 
	 * @param source
	 * @param i
	 * @return
	 */
	public static Date addHours(Date source, int i)
	{
		return new Date(source.getTime() + 3600000 * i);
	}

	/**
	 * <p>
	 * <b>业务处理描述</b>
	 * <ul>
	 * <li>可见性原因：需要被其他应用调用</li>
	 * <li>目的：增加分钟</li>
	 * <li>适用的前提条件:</li>
	 * <li>后置条件：</li>
	 * <li>例外处理：无 </li>
	 * <li>已知问题：</li>
	 * <li>调用的例子：
	 * </li>
	 * </ul>
	 * </p>
	 * 
	 * @param source
	 * @param i
	 * @return
	 */
	public static Date addMinius(Date source, int i)
	{
		return new Date(source.getTime() + 60000 * i);
	}


	/**
	 * <p>
	 * <b>业务处理描述</b>
	 * <ul>
	 * <li>可见性原因：需要被其他应用调用</li>
	 * <li>目的：为时间增加I秒</li>
	 * <li>适用的前提条件:</li>
	 * <li>后置条件：</li>
	 * <li>例外处理：无 </li>
	 * <li>已知问题：</li>
	 * <li>调用的例子： </li>
	 * </ul>
	 * </p>
	 * 
	 * @param source
	 * @param i
	 * @return
	 */
	public static Date addSeconds(Date source, int i)
	{
		return new Date(source.getTime() + 1000 * i);
	}

	/**
	 * <p>
	 * <b>业务处理描述</b>
	 * <ul>
	 * <li>可见性原因：需要被其他应用调用</li>
	 * <li>目的：Comments</li>
	 * <li>适用的前提条件:</li>
	 * <li>后置条件：</li>
	 * <li>例外处理：无 </li>
	 * <li>已知问题：</li>
	 * <li>调用的例子： </li>
	 * </ul>
	 * </p>
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Date d = new Date();
		//System.out.println(DateUtil.format(d, DateUtil.yyyyMMddHHmmssSpt));
		//System.out.println(DateUtil.format(DateUtil.addSeconds(d, 1),
				//DateUtil.yyyyMMddHHmmssSpt));
		//System.out.println(DateUtil.getDateByStrToFormat("2010-10-01".toString()));
		//System.out.println(DateUtil.parse("2010-10-01", "yyyy-MM-dd"));
		//System.out.println(DateUtil.parse("2010-10-01", "yyyy-MM-dd"));
		
		System.out.println(new Date());
		
	}

	/**
	 * <p>
	 * <b>业务处理描述</b>
	 * <ul>
	 * <li>可见性原因：需要被其他应用调用</li>
	 * <li>目的：返回指定格式的当前日期格式化字符串</li>
	 * <li>适用的前提条件:</li>
	 * <li>后置条件：</li>
	 * <li>例外处理：无 </li>
	 * <li>已知问题：</li>
	 * <li>调用的例子： </li>
	 * </ul>
	 * </p>
	 * 
	 * @param formart
	 * @return
	 */
	public static String format(String format)
	{
		return new SimpleDateFormat(format).format(Calendar.getInstance()
				.getTime());
	}

	/**
	 * <p>
	 * <b>业务处理描述</b>
	 * <ul>
	 * <li>可见性原因：需要被其他应用调用</li>
	 * <li>目的：返回指定格式的当前日期格式化字符串</li>
	 * <li>适用的前提条件:</li>
	 * <li>后置条件：</li>
	 * <li>例外处理：无 </li>
	 * <li>已知问题：</li>
	 * <li>调用的例子： </li>
	 * </ul>
	 * </p>
	 * 
	 * @param formart
	 * @return
	 */
	public static String format(Date date, String format)
	{
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * <p>
	 * <b>业务处理描述</b>
	 * <ul>
	 * <li>可见性原因：需要被其他应用调用</li>
	 * <li>目的：格式化字符串为日期</li>
	 * <li>适用的前提条件:</li>
	 * <li>后置条件：</li>
	 * <li>例外处理：无 </li>
	 * <li>已知问题：</li>
	 * <li>调用的例子： </li>
	 * </ul>
	 * </p>
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date parse(String date, String format)
	{
		try
		{
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}

		return null;
	}
	 public static String formatDateSQL(Date dt) {
	    String sqlString = format(dt,"yyyy-MM-dd HH:mm:ss");
	    sqlString = " to_date('" + sqlString + "', 'yyyy-mm-dd hh24:mi:ss') ";
	    return sqlString;
	  }
	  public static String formatDateSQL(String dtString, String sf)
	  {
	    Date dt = parse(dtString, "yyyy-MM-dd HH:mm:ss");
	    dtString = format(dt, "yyyy-MM-dd HH:mm:ss");
	    String sqlString = " to_date('" + dtString + "', '" + sf + "') ";
	    return sqlString;
	  }
	  public static String formatDateSQL(Date dt, String sf)
	  {
	    String dtString = format(dt, "yyyy-MM-dd HH:mm:ss");
	    String sqlString = " to_date('" + dtString + "', '" + sf + "') ";
	    return sqlString;
	  }
	  

	  /** 
	  * @see 把字符串类型的时间转换为自定义格式的时间 
	  * String format,
	  */ 
	  public static Date getDateByStrToFormat(String format, String str) { 
		  DateFormat dFormat = new SimpleDateFormat(format); 
		  Date date = null; 
		  try { 
			  if (str != null) { 
				  date = dFormat.parse(str); 
			  } 
		  } catch (ParseException e) { 
			  e.printStackTrace(); 
		  } 
		  return date; 
	  } 
	

	public static String yyyyMMddHHmmssSpt = "yyyy-MM-dd HH:mm:ss";
	public static String yyyyMMddHHmmSpt = "yyyy-MM-dd HH:mm";
	public static String yyyyMMddSpt = "yyyy-MM-dd";
	public static String yyyyMMSpt = "yyyy-MM";
	public static String HHmmssSpt = "HH:mm:ss";
	public static String HHmmSpt = "HH:mm";   
       
}