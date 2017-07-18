package com.donson.utils;

public class IPConvertUtil {
	/**
	 * 由数组随机生成的IP再转为数组时调用，正方向 
	 * 100.64.0.0 :100*2^24+64*2^16+0*2^8+0
	 * @param ip
	 * @return
	 */
	public static int ip2IntForm(String ip) {
		String[] items = ip.split("\\.");
		Integer intIP = Integer.valueOf(items[0]) << 24
				| Integer.valueOf(items[1]) << 16
				| Integer.valueOf(items[2]) << 8 | Integer.valueOf(items[3]);
		return intIP;
	}

	/**
	 * 有数组生成IP地址 正方向
	 * 1681915904 
	 * @param intIp
	 * @return
	 */
	public static String int2IpForm(int ip) {
		return  ((ip >> 24& 0xFF)) + "." + ((ip>> 16 & 0xFF) ) + "."
				+ ((ip >> 8& 0xFF) ) + "." + (ip & 0xFF);
	}
	/**
	 * Android 系统中 ip转int  反方向
	 * 100.64.0.0 : 0*2^24+0*2^16+64*2^8+100
	 * @param ip
	 * @return
	 */
	public static int ip2IntAndroid(String ip) {
		String[] items = ip.split("\\.");
		Integer intIP = Integer.valueOf(items[0]) 
				| Integer.valueOf(items[1]) << 8
				| Integer.valueOf(items[2]) << 16 | Integer.valueOf(items[3])<< 24;
		return intIP;
	}
	/**
	 * Android 系统中 int转ip  反方向
	 * 
	 * @param ip
	 * @return
	 */
	public static String int2IpAndroid(int i) {
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}
	
}
