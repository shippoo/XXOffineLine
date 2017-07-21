package com.donson.utils;

import android.content.Context;

import com.param.bean.LiuCunMode;
import com.param.dao.DBHelper;
import com.param.dao.DbDao;

public class UtilRetain {
	public static boolean formRetainData(Context context, String packageName,
			String channel) {
		DbDao dao = DbDao.getInstance(context);
		dao.deleteRetainTableByPackageNameChannel(packageName,channel);
		LiuCunMode mode = dao.getLiuCunSetByPackageName(packageName, channel);
//		String tableName = DBHelper.liucunTableName+"_"
//				+ packageName.replace(".", "_") + "_" + channel;
		if (mode != null) {
			for (int i = 0; i <28; i++) {
				if(!getNDayLiuCunData(dao, packageName,channel, mode, i)){
					return false;
				}
			}
			return true;
		}
		else {
			return false;
		}
	}
	/*
	 * 第n天的留存数据生成
	 */
	public static boolean getNDayLiuCunData(DbDao dao,String packageName,String channel,LiuCunMode liuCunMode,int i){
		/*List<ParamEntity> liucunListi = */
		return dao.getParamItemByTime(packageName, channel,CommonTimeUtil.getNDayStart(i+1), CommonTimeUtil.getNDayEnd(i+1), liuCunMode.getPercentArrById(i)/100,i);
//		return liucunListi;
	}

}
