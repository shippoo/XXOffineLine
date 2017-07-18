package com.donson.myhook.services;

import com.donson.config.ConstantsHookConfig;
import com.donson.config.Logger;
import com.donson.utils.SPrefHookUtil;
import com.param.config.SPrefUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class VpnReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ConstantsHookConfig.ACTION_VPN_CHANGED)) {
			
			SPrefHookUtil.putSettingBoolean(context, SPrefHookUtil.KEY_SETTING_WUJIVPN_CHANGED, true);
			String ip = intent.getExtras().getString("ip");
			String location = intent.getExtras().getString("location");
			Logger.v("VVVVVVVVVVVVVVVVVVVVVVVVVVVV"+ip+"  "+location);
			SPrefUtil.putString(context, SPrefHookUtil.KEY_SETTING_WUJIVPN_IP, ip);
			SPrefUtil.putString(context, SPrefHookUtil.KEY_SETTING_WUJIVPN_LOC, location);
		}
		// TODO Auto-generated method stub

	}

}
