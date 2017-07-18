package com.donson.operation;

import java.util.Set;

import android.content.Context;

import com.donson.config.ConstantsHookConfig;
import com.donson.config.Logger;
import com.donson.utils.CmdUtil;
import com.donson.utils.MyfileUtil;
import com.donson.utils.SPrefHookUtil;

public class UnInstallRecordApks {
	String curlistenName = "";
	String listernName = "";
	public UnInstallRecordApks(Context context) {
		curlistenName = SPrefHookUtil.getCurTaskStr(context, SPrefHookUtil.KEY_CUR_PACKAGE_NAME);
		listernName = SPrefHookUtil.getTaskStr(context, SPrefHookUtil.KEY_HHOOK_PACKAGE_NAME);
	}
	public void unInstallAllApks(){
//		String source=MyfileUtil.readFileFromSDCard(ConstantsHookConfig.PATH_RECORD_APK_INSTALL);
//		Logger.d("unInstallAllApks:source::"+source);
//		Set<String> packages = new HashSet<String>();
//		packages.addAll(Arrays.asList(source));
		Set<String> packages = MyfileUtil.getSetFromFile(ConstantsHookConfig.PATH_RECORD_APK_INSTALL);
		for(String pac:packages){
			Logger.d("unInstallAllApks:::"+pac+"listenName:_"+curlistenName+"_"+pac.equals(curlistenName));//com.market2345.dingzhi
			if(!pac.equals(curlistenName)&&!pac.equals(listernName)
					&&!pac.equals(ConstantsHookConfig.PAC_XX_BASE)
					&&!pac.equals(ConstantsHookConfig.PAC_ADB_KEY_BORD)
					&&!pac.equals(ConstantsHookConfig.PAC_VPN)){
				Logger.d("pac:"+pac+"----------"+CmdUtil.unInstallApk(pac));
			}
		}
	}
}
