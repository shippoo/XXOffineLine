package com.donson.utils;

import android.content.Context;

import com.param.config.SPrefUtil;
import com.param.dao.DBHelper;

public class CommonSprUtil {
	/**
	 * 当前监听的apk 包名
	 * @param context
	 * @return
	 */
	public static String getListenPackageName(Context context){
		return SPrefHookUtil.getSettingStr(context,SPrefHookUtil.KEY_HHOOK_PACKAGE_NAME);
	}
	/**
	 * 当前监听的apk包的渠道号
	 * @param context
	 * @return
	 */
	public static String getCurChannel(Context context){
		return SPrefUtil.getString(context,SPrefUtil.C_CHANNEL,SPrefUtil.D_CHANNEL);
	}
	
//	public static String getCurRetain(Context context){
//		return SPrefUtil.getString(context, SPrefUtil.c, defaultV)
//	}
	
	
	public static String getRetainTableName(Context context){
		return DBHelper.liucunTableName+"_"+getParamTableName(context);
	}
	
	public static String getParamTableName(Context context){
		return getListenPackageName(context).replace(".", "_")+"_"+getCurChannel(context);
	}

}
