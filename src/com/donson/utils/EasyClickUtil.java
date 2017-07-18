package com.donson.utils;

import android.content.Context;
import android.provider.Settings.System;
import android.text.TextUtils;

public class EasyClickUtil {
	
	
	public static final String KEY_USE_WHICH_VPN = "key_donson_use_which_vpn";
	public static final int USE_WJ_VPN = 1;
	public static final int USE_SYSTEM_VPN = 2;
	public static final int USE_WUJI_DULI_VPN = 3;
	
	public static boolean isuseSystemVpn(Context context){
		return System.getInt(context.getContentResolver(), KEY_USE_WHICH_VPN, USE_WJ_VPN)==USE_SYSTEM_VPN;
	}
	public static int getUsedVpnType(Context context){
		return System.getInt(context.getContentResolver(), KEY_USE_WHICH_VPN, USE_WJ_VPN);
	}
	public static void setUseWhichVpnFlag(Context context,int value){
		System.putInt(context.getContentResolver(), KEY_USE_WHICH_VPN, value);
	}
	public static boolean isVpnTypeSame(Context context,int value){
		if(getUsedVpnType(context)==value)return true;
		else {
			return false;
		}
//		if(isuseWjVpn(context)&&value==USE_WJ_VPN) return true;
//		else if (!isuseWjVpn(context)&&value==USE_SYSTEM_VPN) {
//			return true;
//		}else {
//			return false;
//		}
	}
	
	/**
	 * 无极VPN 连接
	 */
	public static final String KEY_CONNECT_WJ_VPN = "key_donson_connect_wj_vpn";
	public static final int CONNECT_WJ_VPN = 0;
	public static final int NOT_CONNECT_WJ_VPN = 1;
	public static int getwjVpnConnectFlag(Context context){
		return System.getInt(context.getContentResolver(), KEY_CONNECT_WJ_VPN, NOT_CONNECT_WJ_VPN);
	}
	public static void setwjVpnConnectFlag(Context context,int value){
		System.putInt(context.getContentResolver(), KEY_CONNECT_WJ_VPN, value);
	}
	/**
	 * 无极VPN断开
	 */
	public static final String KEY_DISCONNECT_WJ_VPN = "key_donson_disconnect_wj_vpn";
	public static final int DIS_CONNECT_WJ_VPN = 0;
	public static final int NOT_DIS_CONNECT_WJ_VPN = 1;
	public static boolean getwjVpnDisConnectFlag(Context context){
		return System.getInt(context.getContentResolver(), KEY_DISCONNECT_WJ_VPN, NOT_DIS_CONNECT_WJ_VPN)==DIS_CONNECT_WJ_VPN;
	}
	public static void setwjVpnDisConnectFlag(Context context,boolean value){
		System.putInt(context.getContentResolver(), KEY_DISCONNECT_WJ_VPN, value?DIS_CONNECT_WJ_VPN:NOT_DIS_CONNECT_WJ_VPN);
	}
	
	/**
	 * 系统VPN连接
	 */
	public static final String KEY_CONNECT_VPN = "key_donson_connect_vpn";
	public static final int CONNECT_VPN = 0;
	public static final int NOT_CONNECT_VPN = 1;
	public static int getvpnConnectFlag(Context context){
		return System.getInt(context.getContentResolver(), KEY_CONNECT_VPN, NOT_CONNECT_VPN);
	}
	public static void setvpnConnectFlag(Context context,int value){
		System.putInt(context.getContentResolver(), KEY_CONNECT_VPN, value);
	}
	
	/**
	 * 系统VPN断开
	 */
	public static final String KEY_DISCONNECT_VPN = "key_donson_disconnect_vpn";
	public static final int DIS_CONNECT_VPN = 0;
	public static final int NOT_DIS_CONNECT_VPN = 1;
	public static boolean getvpnDisConnectFlag(Context context){
		return System.getInt(context.getContentResolver(), KEY_DISCONNECT_VPN, NOT_DIS_CONNECT_VPN)==DIS_CONNECT_VPN;
	}
	public static void setvpnDisConnectFlag(Context context,boolean value){
		System.putInt(context.getContentResolver(), KEY_DISCONNECT_VPN, value?DIS_CONNECT_VPN:NOT_DIS_CONNECT_VPN);
	}
	
	public static final String KEY_VPN_OPT_WHERE = "key_donson_vpn_opt_where";
	public static final int AUTO_OPT = 0;
	public static final int VPN_SET_OPT = 1;
	public static final int MAIN_DISCONNECT = 2;
	public static int getvpnOptWhere(Context context){
		return System.getInt(context.getContentResolver(), KEY_VPN_OPT_WHERE, MAIN_DISCONNECT);
	}

	public static void setvpnOptWhere(Context context,int where){
		System.putInt(context.getContentResolver(), KEY_VPN_OPT_WHERE, where);
	}
	public static final String KEY_IS_XPOSED_USED = "key_donson_is_xposed";
	public static final String XPOSED_USED = "xposed_used";
	
	public static String getXposedUsedFlag(Context context){
		return System.getString(context.getContentResolver(), KEY_IS_XPOSED_USED);
	}
	
	public static final String KEY_SCRIPT_IS_RUNNING = "key_donson_script_is_running";
	public static final int SCRIPT_RUNNING = 0;
	public static final int SCRIPT_NOT_RUNNING = 1;
	public static void setScriptIsRunning(Context context,int value){
		System.putInt(context.getContentResolver(), KEY_SCRIPT_IS_RUNNING, value);
	}
	public static boolean getScriptIsRunning(Context context){
		boolean is = System.getInt(context.getContentResolver(), KEY_SCRIPT_IS_RUNNING, SCRIPT_NOT_RUNNING)==SCRIPT_RUNNING?true:false;
		return is;
	}
	public static final String KEY_IS_HAS_TASK = "key_donson_is_has_task";
	public static final int HAS_TASK = 0;
	public static final int NOT_HAS_TASK = 1;
	
	public static boolean isHasTask(Context context){
		boolean is = System.getInt(context.getContentResolver(), KEY_IS_HAS_TASK,NOT_HAS_TASK)==HAS_TASK?true:false;
		return is;
	}
	public static void setIsHasTask(Context context,int isTask){
		System.putInt(context.getContentResolver(), KEY_IS_HAS_TASK, isTask);
	}
	public static final String KEY_IS_TASK_RUNNING = "key_donson_is_task_running";
	public static final int TASK_NOT_RUNNING = 0;
	public static final int TASK_RUNNING = 1;
	public static boolean getIsTaskRunning(Context context){
		boolean is = System.getInt(context.getContentResolver(), KEY_IS_TASK_RUNNING,TASK_NOT_RUNNING)==TASK_RUNNING?true:false;
		return is;
	}
	public static void setIsTaskRunning(Context context,int isTask){
		System.putInt(context.getContentResolver(), KEY_IS_TASK_RUNNING, isTask);
	}
	
	
	
	public static final String KEY_ADD_VPN = "key_add_vpn";
	public static final int ADD_VPN = 0;
	public static final int NOT_ADD_VPN = 1;
	public static boolean getIsAddingVpn(Context context){
		boolean is = System.getInt(context.getContentResolver(), KEY_ADD_VPN,NOT_ADD_VPN)==ADD_VPN?true:false;
		return is;
	}
	public static void setIsAddingVpn(Context context,int addVpn){
		System.putInt(context.getContentResolver(), KEY_ADD_VPN, addVpn);
	}
	
	
	public static final String KEY_CHANGE_VPN = "key_change_vpn";
	public static final int CHANGE_VPN = 0;
	public static final int NOT_CHANGE_VPN = 1;
	public static boolean getIsChangingVpn(Context context){
		boolean is = System.getInt(context.getContentResolver(), KEY_CHANGE_VPN,NOT_CHANGE_VPN)==CHANGE_VPN?true:false;
		return is;
	}
	public static void setIsChangingVpn(Context context,int addVpn){
		System.putInt(context.getContentResolver(), KEY_CHANGE_VPN, addVpn);
	}
	
	public static final String KEY_DELETE_VPN = "key_delete_vpn";
	public static final int DELETE_VPN = 0;
	public static final int NOT_DELETE_VPN = 1;
	public static boolean getIsDeletingVpn(Context context){
		boolean is = System.getInt(context.getContentResolver(), KEY_DELETE_VPN,NOT_DELETE_VPN)==DELETE_VPN?true:false;
		return is;
	}
	public static void setIsDeletingVpn(Context context,int deleteVpn){
		System.putInt(context.getContentResolver(), KEY_DELETE_VPN, deleteVpn);
	}
	
	/**
	 * 无极VPN
	 */
	public static final String KEY_WUJI_VPN_USERNAME = "key_wuji_vpn_username";
	public static String getWJVpnUserName(Context context){
		String is = System.getString(context.getContentResolver(), KEY_WUJI_VPN_USERNAME);
		if(TextUtils.isEmpty(is)){
			is = "gzw";
			setWJVpnUserName(context, is);
		}
		return is;
	}
	public static void setWJVpnUserName(Context context,String username){
		System.putString(context.getContentResolver(), KEY_WUJI_VPN_USERNAME, username);
	}
	
	public static final String KEY_WUJI_VPN_PASSWORD = "key_wuji_vpn_password";
	public static String getWJVpnPassword(Context context){
		String is = System.getString(context.getContentResolver(), KEY_WUJI_VPN_PASSWORD);
		if(TextUtils.isEmpty(is)){
			is = "522964";
			setWJVpnPassword(context, is);
		}
		return is;
	}
	public static void setWJVpnPassword(Context context,String password){
		System.putString(context.getContentResolver(), KEY_WUJI_VPN_PASSWORD, password);
	}
	
	
	
	/**
	 * 系统VPN
	 */
	public static final String KEY_VPN_USERNAME = "key_vpn_username";
	public static String getVpnUserName(Context context){
		String is = System.getString(context.getContentResolver(), KEY_VPN_USERNAME);
		return is;
	}
	public static void setVpnUserName(Context context,String username){
		System.putString(context.getContentResolver(), KEY_VPN_USERNAME, username);
	}
	
	public static final String KEY_VPN_PASSWORD = "key_vpn_password";
	public static String getVpnPassword(Context context){
		String is = System.getString(context.getContentResolver(), KEY_VPN_PASSWORD);
		return is;
	}
	public static void setVpnPassword(Context context,String password){
		System.putString(context.getContentResolver(), KEY_VPN_PASSWORD, password);
	}
	
	
	public static final String KEY_VPN_SERVER = "key_vpn_server";
	public static String getVpnServer(Context context){
		String is = System.getString(context.getContentResolver(), KEY_VPN_SERVER);
		return is;
	}
	public static void setVpnServer(Context context,String server){
		System.putString(context.getContentResolver(), KEY_VPN_SERVER, server);
	}
	
	public static final String XPOSED_HOOK = "xposed_hook";
	public static final int DO_XPOSED_HOOK = 0;
	public static final int DO_NOT_XPOSED_HOOK = 1;
	public static  boolean getXposedHook(Context context){
		boolean xopsed = System.getInt(context.getContentResolver(), XPOSED_HOOK,DO_NOT_XPOSED_HOOK)==DO_XPOSED_HOOK?true:false;
		return xopsed;
	}
	public static void setXposedHook(Context context,boolean xposed){
		System.putInt(context.getContentResolver(), XPOSED_HOOK, xposed?DO_XPOSED_HOOK:DO_NOT_XPOSED_HOOK);
	}
	public static final String REBOOT_FLAG = "xx_reboot";
	public static final int REBOOTED = 0;
	public static final int NOT_REBOOTED = 1;
	public static void setRebootFlag(Context context, boolean reboot) {
		System.putInt(context.getContentResolver(), REBOOT_FLAG, reboot?REBOOTED:NOT_REBOOTED);
	}
	public static boolean getRebootFlag(Context context) {
		return System.getInt(context.getContentResolver(), REBOOT_FLAG,NOT_REBOOTED)==REBOOTED?true:false;
	}
	
	public static final String FILE_OPT_FLAG = "xx_file_opt";
	public static final int FILE_OPT = 0;
	public static final int NOT_FILE_OPT = 1;
	public static void setFileOptFlag(Context context, boolean fileOpted) {
		System.putInt(context.getContentResolver(), FILE_OPT_FLAG, fileOpted?FILE_OPT:NOT_FILE_OPT);
	}
	public static boolean getFileOptFlag(Context context) {
		return System.getInt(context.getContentResolver(), FILE_OPT_FLAG,NOT_FILE_OPT)==FILE_OPT?true:false;
	}
	public static final String MARKET_HOOK_FLAG = "xx_market_hook";
	public static final int MARKET_HOOK = 0;
	public static final int NOT_MARKET_HOOK = 1;
	
	public static void setMarketHookFlag(Context context, boolean market) {
		System.putInt(context.getContentResolver(), MARKET_HOOK_FLAG, market?MARKET_HOOK:NOT_MARKET_HOOK);
	}
	public static boolean getMarketHookFlag(Context context) {
		return System.getInt(context.getContentResolver(), MARKET_HOOK_FLAG,NOT_MARKET_HOOK)==MARKET_HOOK?true:false;
	}
	
	public static final String IS_LOGINED_FLAG = "xx_is_logined";
	public static final int LOGINED = 0;
	public static final int NOT_LOGINED = 1;
	public static void setIsLogined(Context context, boolean islogined) {
		System.putInt(context.getContentResolver(), IS_LOGINED_FLAG,islogined?LOGINED:NOT_LOGINED);
	}
	public static boolean isLogined(Context context){
		return System.getInt(context.getContentResolver(), IS_LOGINED_FLAG,NOT_LOGINED)==LOGINED?true:false;
	}
	
	public static final String IS_OPT_PERMISSION = "xx_is_opt_permission";
	public static final int OPT_PERMISSION = 0;
	public static final int NOT_OPT_PERMISSION = 1;
	public static void setIsOptPermission(Context context,boolean isOpt) {
		System.putInt(context.getContentResolver(), IS_OPT_PERMISSION,isOpt?OPT_PERMISSION:NOT_OPT_PERMISSION );
	}
	
	public static boolean IsOptPermission(Context context) {
		return System.getInt(context.getContentResolver(), IS_OPT_PERMISSION,NOT_OPT_PERMISSION)==OPT_PERMISSION?true:false;
	}
	public static final String IS_OPT_PERMISSION2 = "xx_is_opt_permission2";
	public static final int OPT_PERMISSION2 = 0;
	public static final int NOT_OPT_PERMISSION2 = 1;
	public static void setIsOptPermission2(Context context,boolean isOpt) {
		System.putInt(context.getContentResolver(), IS_OPT_PERMISSION2,isOpt?OPT_PERMISSION2:NOT_OPT_PERMISSION2 );
	}
	
	public static boolean IsOptPermission2(Context context) {
		return System.getInt(context.getContentResolver(), IS_OPT_PERMISSION2,NOT_OPT_PERMISSION2)==OPT_PERMISSION2?true:false;
	}
	
}
