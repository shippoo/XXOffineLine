package com.donson.myhook;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androlua.CrashHandler;
import com.donson.config.ConstantsHookConfig;
import com.donson.config.Logger;
import com.donson.controller.MainViewController;
import com.donson.myhook.adapters.ParamAdapter;
import com.donson.myhook.bean.DataMode;
import com.donson.myhook.services.ListenService;
import com.donson.myhook.services.MyInternetService;
import com.donson.utils.ActivityUtil;
import com.donson.utils.CommonSprUtil;
import com.donson.utils.EasyClickUtil;
import com.donson.utils.OpenActivityUtil;
import com.donson.utils.SPrefHookUtil;
import com.donson.utils.StringUtil;
import com.donson.utils.UtilsVpn;
import com.donson.viewinterface.MainViewInterface;
import com.donson.widget.MyListView;
import com.donson.xxxiugaiqi.R;
import com.donson.zhushoubase.BaseApplication;
import com.param.bean.LiuCunMode;
import com.param.config.ConstantsConfig;
import com.param.dao.DbDao;
import com.param.utils.FileUtil;

public class MainActivity extends BaseActivity implements MainViewInterface,
		OnLongClickListener {
	private Button btnEasyClick = null, 
			btnListenApk = null,
			btnOpen;
	private ViewGroup listenGroup;
	private ImageView ivListenIcon;
	private TextView tvListenAppName, tvListenPackageName, tvIsXpUsed;

	private TextView tvTips = null;
	private MyListView paramList = null;
	private ParamAdapter adapter = null;
	private List<DataMode> list = null;
	private MainViewController controller;
	BaseApplication app;
	private void CheckPermission() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				ActivityUtil.showPermission(MainActivity.this);
			}
		}).start();
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CheckPermission();
		DbDao dao = DbDao.getInstance(this);
//		String packageName = CommonSprUtil.getListenPackageName(this);
//		String channel = CommonSprUtil.getCurChannel(this);
//		LiuCunMode mode = dao.getLiuCunSetByPackageName(packageName, channel);
//		System.out.println("model::"+mode.toString());
		
		EasyClickUtil.setMarketHookFlag(MainActivity.this,false);
//		SPrefHookUtil.putSettingBoolean(MainActivity.this, SPrefHookUtil.KEY_SETTING_NET_DEBUG, true);
		SPrefHookUtil.putSettingBoolean(MainActivity.this, SPrefHookUtil.KEY_SETTING_IS_RUN_SCRIPT, true);
		SPrefHookUtil.putSettingBoolean(MainActivity.this, SPrefHookUtil.KEY_SETTING_UNINSTALL_APK, false);
		app = (BaseApplication) getApplication();
		if (!app.getIsRunning()) {
			EasyClickUtil.setIsTaskRunning(getApplicationContext(),
					EasyClickUtil.TASK_NOT_RUNNING);
		}
		EasyClickUtil.setXposedHook(MainActivity.this, false);
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
		setContentView(R.layout.activity_main);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				SPrefHookUtil.setWjVpnApk(getApplicationContext(), UtilsVpn.getCurWJVpnType());
				FileUtil.copyDbToLocalRaw(MainActivity.this,ConstantsConfig.FILE_MOBILE_AREA_DB_NAME, R.raw.mobilearea);
				FileUtil.copyDbToLocalRaw(MainActivity.this,ConstantsConfig.FILE_CELL_DB_NAME, R.raw.cell);
				FileUtil.copyDbToLocalRaw(MainActivity.this,ConstantsConfig.FILE_PHOND_DB_NAME, R.raw.phone);
				FileUtil.copyDbToLocalRaw(MainActivity.this,ConstantsConfig.FILE_CONTACTS_DB_NAME, R.raw.contacts);
				FileUtil.copyDbToLocalRaw(MainActivity.this,ConstantsConfig.FILE_IPDATA_DB_NAME, R.raw.ipdata);
//				if(!CmdUtil.isAppInstalled(MainActivity.this, ConstantsHookConfig.PAC_VPN)
//						||!MyfileUtil.isMd5Same(MainActivity.this, ConstantsHookConfig.FILE_VPN_APK_NAME, R.raw.vpn_919)){
////					if(!new File(ConstantsHookConfig.PATH_VPN_APK).exists()||!com.param.utils.CmdUtil.canApkInstall(MainActivity.this, ConstantsHookConfig.PATH_VPN_APK)){
////						MyfileUtil.copyAssetFileToSd(MainActivity.this, ConstantsHookConfig.ASSET_VPN, ConstantsHookConfig.PATH_VPN_APK);
//						MyfileUtil.copyDbToLocalRaw(MainActivity.this, ConstantsHookConfig.FILE_VPN_APK_NAME, R.raw.vpn_919);
////					}
//					CmdUtil.installApk(MainActivity.this, ConstantsHookConfig.PATH_VPN_APK);
//				}
			}
		}).start();

//		DbDao.getInstance(this).getTables();
		controller = new MainViewController(this, this);
		
		String deviceId = SPrefHookUtil.getLoginStr(this,SPrefHookUtil.KEY_LOGIN_DEVICE_ID);
		String deviceCode = SPrefHookUtil.getLoginStr(this,SPrefHookUtil.KEY_LOGIN_DEVICE_CODE);
		Logger.i("app.getIsLogined()===" + app.getIsLogined()+"  "+deviceId+"  "+deviceCode);
		
		if (TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(deviceCode)) {
			EasyClickUtil.setIsLogined(getApplicationContext(),false);
			OpenActivityUtil.startLoginActivity(this);
			finish();
			overridePendingTransition(android.R.anim.fade_out,
					android.R.anim.fade_in);
		} else {
			EasyClickUtil.setIsLogined(getApplicationContext(),true);
			init();
		}
		
		/*
		 * if(!app.getIsLogined()){ //
		 * SPrefHookUtil.putSettingBoolean(getApplicationContext(),
		 * SPrefHookUtil.KEY_SETTING_RUN_AUTO,false);
		 * OpenActivityUtil.startLoginActivity(this); finish();
		 * overridePendingTransition(android.R.anim.fade_out,
		 * android.R.anim.fade_in); } else { init(); }
		 */
		// init();
	}

	private void init() {
		list = new ArrayList<DataMode>();
		listenGroup = (ViewGroup) findViewById(R.id.listen_layout);
		ivListenIcon = (ImageView) findViewById(R.id.iv_listen_icon);
		tvListenAppName = (TextView) findViewById(R.id.tv_listen_apk_name);
		tvListenPackageName = (TextView) findViewById(R.id.tv_listen_package);
		tvIsXpUsed = (TextView) findViewById(R.id.main_tv_xposed_is_used);
		btnListenApk = (Button) findViewById(R.id.btn_listen_apk);
		btnEasyClick = (Button) findViewById(R.id.btn_easyClick);
		btnOpen = (Button) findViewById(R.id.btn_open);
		tvTips = (TextView) findViewById(R.id.tv_tips);
		paramList = (MyListView) findViewById(R.id.param_list);
		adapter = new ParamAdapter(this, list);
		paramList.setAdapter(adapter);
		controller.init();
		startService(new Intent(this, MyInternetService.class));
		startService(new Intent(getApplicationContext(), ListenService.class));
		initRect();
		setEvent();
		paramList.setFocusable(false);
		btnEasyClick.requestFocus();
	}
	private void setEvent() {
		btnListenApk.setOnLongClickListener(this);
		listenGroup.setOnLongClickListener(this);
		btnEasyClick.setOnLongClickListener(this);
		btnOpen.setOnLongClickListener(this);
	}

	public void initRect() {
		initTopBar();
		if(ConstantsHookConfig.IS_MOBILE){
			setTopTitle(getString(R.string.title_main_offline));
		}else {
			setTopTitle(getString(R.string.title_main_pc));
			
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		controller.unregistReceiver();
	}

	@Override
	public void toast(String tips) {
		showToast(tips);
	}

	@Override
	public void setTips(String tips) {
		tvTips.setText(tips);
	}

	@Override
	public void notifyAdapter(List<DataMode> list) {
		adapter.setList(list);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void setListenPart(String packageName, Drawable icon, String appName) {
		ivListenIcon.setImageDrawable(icon);
		tvListenAppName.setText(appName);
		tvListenPackageName.setText(packageName);
	}

	@Override
	public void setListenBtnVisible(boolean isvisible) {
		if (isvisible) {
			btnListenApk.setVisibility(View.VISIBLE);
			listenGroup.setVisibility(View.GONE);
		} else {
			btnListenApk.setVisibility(View.GONE);
			listenGroup.setVisibility(View.VISIBLE);
		}
	}

	
	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		return;
	}

	@Override
	public void toastBig(String tips) {
		showBigToast(this, tips);
	}
	Toast toast1;
	@Override
	public void toastBig(final String tips, final int color) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				toast1 = toast1 == null ? Toast.makeText(MainActivity.this, "",Toast.LENGTH_SHORT) : toast1;
				toast1.setText(StringUtil.SpanColor(StringUtil.SpanSize(tips, 35),
						getResources().getColor(color)));
				toast1.show();
			}
		});
	}
	@Override
	public void showNoUseTips(final boolean isvisible, final String text) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				tvIsXpUsed.setText(text);
				tvIsXpUsed.setVisibility(isvisible ? View.VISIBLE : View.GONE);
			}
		});
	}

	@Override
	public void easyClickable(boolean clickable) {
		btnEasyClick.setClickable(clickable);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {
		case R.id.btn_easyClick:
			controller.setEasyClick();
//			download();
//			controller.uploadParam();
//			controller.limitContinue();
			break;
		case R.id.btn_listen_apk:
		case R.id.listen_layout:
			controller.openListenChoose();
			break;
		case R.id.btn_open:
			if(paramList.getVisibility() == View.VISIBLE){
				paramList.setVisibility(View.GONE);
				btnOpen.setText(getString(R.string.btn_show_params));
			}else {
				paramList.setVisibility(View.VISIBLE);
				btnOpen.setText(getString(R.string.btn_hide_params));
			}
			
			break;
		default:
			break;
		}
		
		return true;
	}

	@Override
	public void setMyTitleMainThread(final String title) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				setTopTitle(title);
			}
		});
	}
}
