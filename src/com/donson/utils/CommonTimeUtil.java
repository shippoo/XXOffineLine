package com.donson.utils;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;


public class CommonTimeUtil {
	public static final long ONE_DAY = 24*60*60*1000;
	 public static long getDay3Clock(){
		 Calendar calendar = Calendar.getInstance();
		 calendar.set(Calendar.HOUR_OF_DAY, 3);
		 calendar.set(Calendar.MINUTE, 0);
		 calendar.set(Calendar.SECOND, 0);
		 calendar.set(Calendar.MILLISECOND, 001);
		 return new Timestamp(calendar.getTimeInMillis()).getTime();
	 }
	 public static long getDay1Clock(){
		 Calendar calendar = Calendar.getInstance();
		 calendar.set(Calendar.HOUR_OF_DAY, 1);
		 calendar.set(Calendar.MINUTE, 0);
		 calendar.set(Calendar.SECOND, 0);
		 calendar.set(Calendar.MILLISECOND, 001);
		 return new Timestamp(calendar.getTimeInMillis()).getTime();
	 }
}
