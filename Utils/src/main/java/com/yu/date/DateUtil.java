package com.yu.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.Assert;

import com.yu.utils.ObjectUtils;

/**
 * 日期工具
 * 
 * @author 张超
 * @date 2015�?�?�?上午11:08:00
 * @since 2.1
 *
 */
public class DateUtil {

	public static final ThreadLocal<SimpleDateFormat> UNDERLINE_DF = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMddHHmmss");
		};
	};
	public static final ThreadLocal<SimpleDateFormat> DERLINE_DF = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		};
	};
	public static final ThreadLocal<SimpleDateFormat> SORT_DERLINE_DF = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		};
	};
	public static final ThreadLocal<SimpleDateFormat> SORT_DERLINE_TIME = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("HH:mm:ss");
		};
	};
	public static final ThreadLocal<SimpleDateFormat> DERLINE_TIME_DF = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm");
		};
	};

	/**
	 * 比较date2减去date1的分钟差是否大于给定的minute
	 * 
	 * @param date1
	 * @param date2
	 * @param minute
	 * @return
	 */
	public static boolean compare(Date date1, Date date2, int minute) {
		long between = (date2.getTime() - date1.getTime()) / 1000;// 除以1000是为了转换成秒
		long day1 = between / (24 * 3600);
		long hour1 = between % (24 * 3600) / 3600;
		long minute1 = between % 3600 / 60;
		long second1 = between % 60 / 60;
		if (minute1 > minute) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 计算当前时间与凌晨的时间差(毫秒)
	 * 
	 * @return
	 */
	public static long getRemainingTime() {
		Date now = new Date();
		Date last = new Date();
		last = DateUtils.setHours(last, 23);
		last = DateUtils.setMinutes(last, 59);
		last = DateUtils.setMilliseconds(last, 59);
		return last.getTime() - now.getTime();
	}

	/**
	 * 比较date减去当前系统时间的分钟差是否大于给定的minute
	 * 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static boolean compare(Date date, int minute) {
		long between = (new Date().getTime() - date.getTime()) / 1000;// 除以1000是为了转换成�?
		long minute1 = between % 3600 / 60;
		if (minute1 > minute) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 解析yyyyMMddHHmmss样式日期
	 * 
	 * @param time
	 * @return
	 */
	public static Date parse(String time) {
		try {
			return UNDERLINE_DF.get().parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析yyyyMMddHHmmss样式日期
	 * 
	 * @param time
	 * @return yyyy-MM-dd
	 */
	public static Date parse2(String time) {
		try {
			return parseDate(UNDERLINE_DF.get().parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析Date型日期
	 * 
	 * @param time
	 * @return yyyy-MM-dd
	 */
	public static Date parseDate(Date date) {
		try {
			return SORT_DERLINE_DF.get().parse(format2(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析yyyy-MM-dd样式日期
	 * 
	 * @param time
	 * @return
	 */
	public static Date SortStringparseDerline(String time) {
		try {
			return SORT_DERLINE_DF.get().parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析yyyy-MM-dd HH:mm:ss样式日期
	 * 
	 * @param time
	 * @return
	 */
	public static Date parseDerline(String time) {
		try {
			return DERLINE_DF.get().parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String format(Date time) {
		try {
			return UNDERLINE_DF.get().format(time);
		} catch (Exception e) {
			return null;
		}
	}

	public static String format2(Date time) {
		try {
			return DERLINE_DF.get().format(time);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String dateFromatToString(Date time) {
		return dateFromatToString(time, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 将date型日期转换成string型
	 * @author ksh 康胜虎
	 * @date 2016年10月31日下午5:25:12
	 * @since 3.7
	 *
	 * @param time
	 * @param pattern 转换后的日期格式，例如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String dateFromatToString(Date time, String pattern) {
		return new SimpleDateFormat(pattern).format(time);
	}

	/**
	 * 获取当天或指定日期的开始时间
	 * 
	 * @return
	 */
	public static Date getDayStartTime(Date... day) {
		Date startTime = handlerVariableParam(day);
		startTime = DateUtils.setHours(startTime, 0);
		startTime = DateUtils.setMinutes(startTime, 0);
		startTime = DateUtils.setSeconds(startTime, 0);
		return startTime;
	}

	/**
	 * 获取当天或指定日期的结束时间
	 * 
	 * @return
	 */
	public static Date getDayEndTime(Date... day) {
		Date endTime = handlerVariableParam(day);
		endTime = DateUtils.setHours(endTime, 23);
		endTime = DateUtils.setMinutes(endTime, 59);
		endTime = DateUtils.setSeconds(endTime, 59);
		return endTime;
	}

	private static Date handlerVariableParam(Date... day) {
		Date tempTime = null;
		if (!ObjectUtils.isEmpty(day)) {
			if (ObjectUtils.isEmpty(day[0])) {
				tempTime = new Date();
			} else {
				tempTime = day[0];
			}
		} else {
			tempTime = new Date();
		}
		return tempTime;
	}

	/**
	 * 获取当月或指定月份的第一天
	 * 
	 * @param day
	 * @return
	 */
	public static Date getMonthFirstDay(Date... day) {
		Date firstDay = handlerVariableParam(day);
		firstDay = DateUtils.setDays(firstDay, 1); // 月的第一天
		firstDay = getDayStartTime(firstDay);
		return firstDay;
	}

	/**
	 * 获取当月或指定月份的最后一天
	 * 
	 * @param day
	 * @return
	 */
	public static Date getMonthEndDay(Date... day) {
		Date endDay = handlerVariableParam(day);
		// 先取得下个月的第一天再减一天，得到当前月份的最后一天。
		endDay = DateUtils.addMonths(endDay, 1);
		endDay = DateUtils.setDays(endDay, 1);
		endDay = DateUtils.addDays(endDay, -1);
		endDay = getDayEndTime(endDay);
		return endDay;
	}

	/**
	 * 获取指定日期加上指定天数（负数代表减）后的零点日期。 如：2015-11-17 11:30:00 加上 -10 后 得到：2015-11-07
	 * 00:00:00
	 *
	 * @author derek 蔡海佳
	 * @date 2015年11月17日 上午11:11:15
	 *
	 * @param date
	 *            日期
	 * @param day
	 *            相加的天数
	 * @return 相加后的零点的日期。
	 */
	public static Date getZeroDateAfterAddDays(Date date, int day) {
		Date zeroDate = convertToZeroDate(date);
		return DateUtils.addDays(zeroDate, day);
	}

	/**
	 * 将日期转化为零点的日期。 比如：2015-11-16 11:30:00 转化为 2015-11-16 00:00:00
	 *
	 * @author derek 蔡海佳
	 * @date 2015年11月17日 上午11:40:54
	 *
	 * @param date 转化前的日期
	 * @return 转化后的日期
	 */
	public static Date convertToZeroDate(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		try {
			date = format.parse(format.format(date));
		} catch (Exception e) {
			// 字符串写死，解析永远不会失败。
		}

		return date;
	}
	
	/**
	 * 获取零点的日期。 比如：2015-11-16 11:30:00 转化为 2015-11-16 00:00:00
	 *
	 * @author derek 蔡海佳
	 * @date 2017年01月11日 
	 * @since 3.9
	 *
	 * @param date 输入日期
	 * @return 零点日期
	 */
	public static Date getZeroDate(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		try {
			return format.parse(format.format(date));
		} catch (Exception e) {
			// 字符串写死，解析永远不会失败。
			return null;
		}
	}

	/**
	 * 将日期转化为当天最后日期。 比如：2015-11-16 11:30:00 转化为 2015-11-16 23:59:59
	 *
	 * @author derek 蔡海佳
	 * @date 2016年7月26日 上午9:44:45
	 * @since 3.4.2
	 *
	 * @param date 转化前的日期
	 * @return 转化后日期
	 */
	public static Date convertToMaxDate(Date date) {
		if (date == null) {
			return null;
		}
		// 先加一天再减一秒
		Date addOneDay = DateUtils.addDays(convertToZeroDate(date), 1);
		date = DateUtils.addSeconds(addOneDay, -1);
		
		return date;
	}
	
	/**
	 * 获取当天最大日期。 比如：2015-11-16 11:30:00 转化为 2015-11-16 23:59:59
	 *
	 * @author derek 蔡海佳
	 * @date 2017年01月11日
	 * @since 3.9
	 *
	 * @param date 日期
	 * @return 当天最大日期
	 */
	public static Date getMaxDate(Date date) {
		if (date == null) {
			return null;
		}
		
		// 先加一天再减一秒
		Date addOneDay = DateUtils.addDays(convertToZeroDate(date), 1);
		return DateUtils.addSeconds(addOneDay, -1);		
	}

	/**
	 * 将日期列表转化为零点的日期列表。 比如：2015-11-16 11:30:00 转化为 2015-11-16 00:00:00
	 *
	 * @author derek 蔡海佳
	 * @date 2015年11月17日 上午11:40:54
	 *
	 * @param date
	 *            转化前的日期列表
	 * @return 转化后的日期列表
	 */
	public static List<Date> convertToZeroDate(List<Date> dateList) {
		List<Date> zeroDates = new ArrayList<Date>();
		for (Date date : dateList) {
			zeroDates.add(convertToZeroDate(date));
		}

		return zeroDates;
	}

	/**
	 * 获取从指定日期开始算，最近的连续天数。
	 * 
	 * 测试代码： List<Date> dates = new ArrayList<Date>();
	 * dates.add(DateUtils.parseDate("20151115134700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151115134700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151113124700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151113114700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151112124700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151109124700", "yyyyMMddHHmmss"));
	 * 
	 * @author derek 蔡海佳
	 * @date 2015年11月17日 上午11:37:02
	 *
	 * @param 从指定天数开始
	 * @param dateList
	 *            日期列表
	 * @return 连续的天数
	 */
	public static int getContinuousDayNum(Date date, List<Date> dateList) {
		List<Date> zeroDates = convertToZeroDate(dateList);
		int num = 0; // 0表示比较当天日期 1表示不比较当天日期（当天总是会算上一次）
		boolean isToday = true;
		// 倒序排列
		Collections.sort(zeroDates);
		Collections.reverse(zeroDates);
		for (Date zeroDate : zeroDates) {
			// 日期为空时，过滤
			if (zeroDate == null) {
				continue;
			}
			// 如果满足连续提前num天，则连续天数加1
			if (getZeroDateAfterAddDays(date, -num).compareTo(zeroDate) == 0) {
				isToday = false;
				num++;
			}
		}
		// 解决list最大日期比参考date少1一天（list不包含当天）的问题，num不能自增的问题
		if (isToday && zeroDates.size() > 0 && num == 0) {
			num++;
			for (Date zeroDate : zeroDates) {
				// 日期为空时，过滤
				if (zeroDate == null) {
					continue;
				}
				// 如果满足连续提前num天，则连续天数加1
				if (getZeroDateAfterAddDays(date, -num).compareTo(zeroDate) == 0) {
					isToday = false;
					num++;
				}
			}
			// 参考日期和list最大日期有间隔
			if (isToday) {
				num = 0;
			} else {
				num--; // 减掉辅助自增的
			}
		}

		return num;
	}

	/**
	 * 获取指定日期列表中最大的连续天数。
	 * 
	 * 测试代码： List<Date> dates = new ArrayList<Date>();
	 * dates.add(DateUtils.parseDate("20160101134700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151231134700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151230134700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151229134700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151228134700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151227134700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151226134700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151120134700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151119134700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151118134700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151117134700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151116134700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151115134700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151115134700", "yyyyMMddHHmmss"));
	 * dates.add(DateUtils.parseDate("20151113124700", "yyyyMMddHHmmss"));
	 *
	 * @author derek 蔡海佳
	 * @date 2015年11月17日 下午5:27:15
	 *
	 * @param dateList
	 *            按照日期倒序排列的日期列表，最新的日期在最前面
	 * @return 连续的天数
	 */
	public static int getContinuousDayNum(List<Date> dateList) {
		int resultNum = 0;
		for (Date date : dateList) {
			int num = getContinuousDayNum(date, dateList);
			if (resultNum < num) {
				resultNum = num;
			}
		}
		return resultNum;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 算法：先获取两个日期的零点日期（时分秒去掉），再比较天数
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * 
	 * @return 相差天数 
	 */
	public static int daysBetween(Date startDate, Date endDate) {
		Assert.notNull(startDate);
		Assert.notNull(endDate);

		Date tmpStartDate = getZeroDate(startDate);
		Date tmpEndDate = getZeroDate(endDate);
		
		// 相差天数
		long betweenDays = (tmpEndDate.getTime() - tmpStartDate.getTime()) / (1000 * 3600 * 24);
		
		return (int) betweenDays;
	}
	
	/**
	 * 计算两个日期之间相差的天数
	 * 算法：获取两个日期的毫秒数后再相减，asOneDay参数控制不足24小时的部分是否算一天。
	 * 
	 * @author derek 蔡海佳
	 * @date 2017年1月11日
	 * @since 3.9
	 *
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param asOneDay true，不足24小时部分算一天；否则，false
	 * @return 相差天数
	 */
	public static int daysBetween(Date startDate, Date endDate, boolean  asOneDay) {
		Assert.notNull(startDate);
		Assert.notNull(endDate);

		double diff = (endDate.getTime() - startDate.getTime());
		double day = (1000 * 3600 * 24);
		double dbBetweenDays = diff/day;
		
		if (dbBetweenDays < 0) {
			return -(int) (asOneDay ? Math.ceil(-dbBetweenDays) : Math.floor(-dbBetweenDays));
		}
		
		return (int) (asOneDay ? Math.ceil(dbBetweenDays) : Math.floor(dbBetweenDays));	
	}

	/**
	 * 返回当前或指定日期修改后的日期
	 * 
	 * @author Cassie He
	 * @date 2016年2月20日 下午3:58:09
	 * @since 2.12
	 * @param beginDate
	 * @param calenderFeild
	 *            Calender类日历字段
	 * @param amount
	 *            要添加到该字段的日期或时间的量。
	 * @return
	 */
	public static Date addCalender(Date beginDate, int calenderFeild, int amount) {
		if (ObjectUtils.isEmpty(beginDate)) {
			beginDate = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		calendar.add(calenderFeild, amount);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 根据开始时间和结束时间返回时间段内的时间集合
	 * 
	 * @author Cassie He
	 * @date 2016年2月19日 下午3:54:57
	 * @since 2.12
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List<Date>
	 */
	public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
		if (ObjectUtils.isEmpty(beginDate))
			return new ArrayList<Date>();
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date设置此 Calendar的时间
		cal.setTime(beginDate);
		int between_days = daysBetween(beginDate, endDate);
		if (between_days > 0) {
			boolean bContinue = true;
			while (bContinue) {
				// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
				cal.add(Calendar.DAY_OF_MONTH, 1);
				// 测试此日期是否在指定日期之后
				if (endDate.after(cal.getTime())) {
					lDate.add(cal.getTime());
				} else {
					break;
				}
			}
			
			// 如果集合中没有值或最后一天和结束时间不是同一天时，加上最后一天的值
			if (lDate.size() == 0 || !convertToZeroDate(lDate.get(lDate.size() - 1)).equals(convertToZeroDate(endDate))) {
				lDate.add(endDate);// 把结束时间加入集合
			}
		}

		return lDate;
	}

	/**
	 * 获取当天早上8点整的日期时间
	 * 
	 * @return
	 */
	public static Date getMorningAt8ByToday() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 8);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取两个日期之间间隔的分钟数
	 * 
	 * @author ksh 康胜虎
	 * @date 2016年8月22日上午11:17:35
	 * @since 3.5
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static long getMInutesBetweenTwoDate(Date beginDate, Date endDate) {
		Calendar dateOne = Calendar.getInstance();
		Calendar dateTwo = Calendar.getInstance();
		dateOne.setTime(beginDate);
		dateTwo.setTime(endDate);
		long timeOne = dateOne.getTimeInMillis();
		long timeTwo = dateTwo.getTimeInMillis();
		return Math.abs((timeOne - timeTwo) / (1000 * 60));
	}

	/**
	 * 合并两个日期，提取yearDate的年月日，hourDate的时分秒，合并成一个日期
	 * 
	 * @author ksh 康胜虎
	 * @date 2016年9月8日上午11:29:25
	 * @since 3.6
	 *
	 * @param yearDate
	 *            提取年月日的日期
	 * @param hourDate
	 *            提取时分秒的日期
	 * @return
	 */
	public static Date mergeTwoDate(Date yearDate, Date hourDate) {
		Calendar dateOne = Calendar.getInstance();
		Calendar dateTwo = Calendar.getInstance();
		dateOne.setTime(yearDate);
		dateTwo.setTime(hourDate);
		dateOne.set(Calendar.HOUR_OF_DAY, dateTwo.get(Calendar.HOUR_OF_DAY));
		dateOne.set(Calendar.MINUTE, dateTwo.get(Calendar.MINUTE));
		dateOne.set(Calendar.SECOND, dateTwo.get(Calendar.SECOND));
		dateOne.set(Calendar.MILLISECOND, 0);
		return dateOne.getTime();
	}
}
