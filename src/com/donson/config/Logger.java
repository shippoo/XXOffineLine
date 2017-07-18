package com.donson.config;

import android.util.Log;

public class Logger {
	static boolean ISLOG = true;
	static String TAG = "TEST_HOOK";
	static String TAG_LISTEN = "HOOK_LISTEN";
	static String TAG_FILE = "FILE_RE";
	static String TAG_FILE2 = "FILE_RE2";
	static String TAG_HOOK = "HOOK_X2";
	static String TAG_HOOKT = "HOOK_XPARAM";
	static String TAG_HOOKTEST = "HOOK_TEST";
	static String TAG_HOOKTEST2 = "HOOK_TEST2";
	static String TAG_HOOKMARKET = "HOOK_MARKET";
	static String TAG_VPN = "HOOK_VPN";
	static String TAG_DEBUG = "HOOK_DEBUG";
	static String TAG_FILEDEBUG = "TAG_FILEDEBUG";
	public static void i(String tips) {
		if (ISLOG)
			Log.i(TAG, tips);
	}
	public static void d(String tips) {
		if (ISLOG)
			Log.i(TAG_DEBUG, tips);
	}
	public static void l(String tips) {
		if (ISLOG)
			Log.i(TAG_LISTEN, tips);
	}
	public static void i(String key,String tips) {
		if (ISLOG)
			Log.i(key, tips);
	}
	public static void f(String tips) {
		if (ISLOG)
			Log.i(TAG_FILE, tips);
	}
	public static void f2(String tips) {
		if (ISLOG)
			Log.i(TAG_FILE2, tips);
	}
	public static void fd(String tips) {
		if (ISLOG)
			Log.i(TAG_FILEDEBUG, tips);
	}
	public static void h(String tips) {
		if (ISLOG)
			Log.i(TAG_HOOK, tips);
	}
	public static void ht(String tips) {
		if (ISLOG)
			Log.i(TAG_HOOKT, tips);
	}
	public static void t(String tips) {
		if (ISLOG)
			Log.i(TAG_HOOKTEST, tips);
	}
	public static void t2(String tips) {
		if (ISLOG)
			Log.i(TAG_HOOKTEST2, tips);
	}
	public static void m(String tips) {
		if (ISLOG)
			Log.i(TAG_HOOKMARKET, tips);
	}
	public static void v(String tips) {
		if (ISLOG)
			Log.i(TAG_VPN, tips);
	}
	public static void v2(String tips) {
		if (ISLOG)
			Log.i("HOOK_2VPN", tips);
	}
}
