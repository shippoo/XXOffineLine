package com.donson.utils;

import android.content.Context;
import android.text.TextUtils;

import com.donson.config.ConstantsHookConfig;
import com.donson.config.Logger;

public class UtilsVpn {///65532
	/**
	 * 连接无极VPN
	 * @param context
	 * @return
	 */
	public static boolean connectVpn(Context context){
		boolean connected = false;
		int count=0;
		EasyClickUtil.setwjVpnConnectFlag(context, EasyClickUtil.CONNECT_VPN);
		EasyClickUtil.setwjVpnDisConnectFlag(context, false);
		EasyClickUtil.setvpnOptWhere(context, EasyClickUtil.AUTO_OPT);
		SPrefHookUtil.putSettingBoolean(context, SPrefHookUtil.KEY_SETTING_WUJIVPN_CHANGED, false);
		OpenActivityUtil.openApkByDetailInfo(context, "org.wuji", "org.wuji.InstallActivity", "");
		try {
			while(!connected){
				Thread.sleep(2000);
				count+=2;
				if(isVpnConnected()||SPrefHookUtil.getSettingBoolean(context, SPrefHookUtil.KEY_SETTING_WUJIVPN_CHANGED, SPrefHookUtil.D_SETTING_WUJIVPN_CHANGED))
					connected = true;
				Logger.v("COONECT::"+count+"  isVpnConnected():"+isVpnConnected()+"  "+SPrefHookUtil.getSettingBoolean(context, SPrefHookUtil.KEY_SETTING_WUJIVPN_CHANGED, SPrefHookUtil.D_SETTING_WUJIVPN_CHANGED));
				if(count>60){
					CmdUtil.killProcess(ConstantsHookConfig.PAC_VPN );
					return false;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Logger.v("COONECT:OK :"+count);
		EasyClickUtil.setwjVpnConnectFlag(context, EasyClickUtil.NOT_CONNECT_VPN);
		return true;
	}
	/**
	 * 断开无极vpn MAIN
	 * @param context
	 * @return
	 */
	public static boolean disConnectVpnWithMAIN(Context context){
		EasyClickUtil.setvpnOptWhere(context, EasyClickUtil.MAIN_DISCONNECT);
		return disConnectVpn(context);
	}
	/**
	 * 断开无极vpn AUTO
	 * @param context
	 * @return
	 */
	public static boolean disConnectVpnWithAuto(Context context){
		EasyClickUtil.setvpnOptWhere(context, EasyClickUtil.AUTO_OPT);
		return disConnectVpn(context);
	}
	/**
	 * 断开无极vpn
	 * @param context
	 * @return
	 */
	public static boolean disConnectVpn(Context context){
		EasyClickUtil.setwjVpnConnectFlag(context, EasyClickUtil.NOT_CONNECT_WJ_VPN);
		EasyClickUtil.setwjVpnDisConnectFlag(context, true);
		OpenActivityUtil.openApkByDetailInfo(context, "org.wuji", "org.wuji.InstallActivity", "");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EasyClickUtil.setwjVpnDisConnectFlag(context, false);
//		EasyClickUtil.setvpn
		return false;
		
	}
	/**
	 * 判断VPN是否连接成功
	 * @return
	 */
	public static boolean isVpnConnected(){
		return wJVpnPortUsedState()==VPNCONNECTED;
		
	}
	public static final int VPNNOTCONNECTED = 0;
	public static final int VPNCONNECTED = 1;
	public static final int VPNCONNECTERR = 2;

	/**
	 * 判断无极vpn的端口65532的占用状况
	 * @return
	 */
	public static int wJVpnPortUsedState() {
		String string = "netstat -anp | grep :65532";
		try {
			Process p = CmdUtil.run(string);
			String out = CmdUtil.readResult(p).toUpperCase();
			Logger.d("==VpnUtil---isVpnConnected0=======" + out);
			if (!TextUtils.isEmpty(out) && out.contains("65532")) {
				if (out.contains("ESTABLISHED"))
					return VPNCONNECTED;
				else if (!out.contains("ESTABLISHED")
						&& out.contains("CLOSE_WAIT")) {
					return VPNCONNECTERR;
				}
			}
		} catch (Exception e) {
			Logger.i("vpn exe::" + e);
			e.printStackTrace();
		}
		return VPNNOTCONNECTED;
	}
	/**
	 * 获取无极VPN的大小
	 * @return
	 */
	public static long getCurWJVPNSize() {
		String path = MyfileUtil.getWJApkFilePath();
		if(!TextUtils.isEmpty(path)){
			return MyfileUtil.getFileLoaderSize(path);
		}else {
			return -1;
		}
	}

	/**
	 * 当前无极VPN的类型
	 * @return
	 */
	public static int getCurWJVpnType() {
		long Size = getCurWJVPNSize();
		Logger.i("getCurWJVpnType---Size:"+Size);
		if(Size<=0){
			return -1;
		}
		if((Size/1000000)>3){
			return EasyClickUtil.USE_WJ_VPN;
		}else {
			return EasyClickUtil.USE_WUJI_DULI_VPN;
		}
	}

}
