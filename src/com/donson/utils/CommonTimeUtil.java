package com.donson.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;

public class CommonTimeUtil {
	/**
	 * 1天的毫秒数
	 */
	public static final long ONE_DAY = 24 * 60 * 60 * 1000;

	/**
	 * n 点的时间戳
	 * @param n
	 * @return
	 */
	public static long getDayNClock(int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, n);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 001);
		return new Timestamp(calendar.getTimeInMillis()).getTime();
	}

	/**
	 * 3点的时间戳
	 * @return
	 */
	public static long getDay3Clock() {
		return getDayNClock(3);
	}

	/**
	 * 1点的时间戳
	 * @return
	 */
	public static long getDay1Clock() {
		return getDayNClock(1);
	}

	/**
	 * 0点的时间戳
	 * @return
	 */
	public static long getDayStart() {
		return getDayNClock(0);
	}

	/**
	 * 24点的时间戳
	 * @return
	 */
	public static long getDayEnd() {
		// return getDayStart()+ONE_DAY;
		return getDayNClock(24);
	}

	/**
	 * 获得 n天前的凌晨时间
	 * @param n
	 * @return
	 */
	public static long getNDayStart(int n) {
		return getDayStart() - ONE_DAY * n;
	}

	/**
	 * 获得 n天前的结束时间
	 * @param n
	 * @return
	 */
	public static long getNDayEnd(int n) {
		return getDayEnd() - ONE_DAY * n;
	}
}
