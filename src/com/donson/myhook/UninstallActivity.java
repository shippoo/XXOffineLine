package com.donson.myhook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.donson.config.ConstantsHookConfig;
import com.donson.config.Logger;
import com.donson.myhook.adapters.AppInfosAdapter;
import com.donson.myhook.adapters.AppInfosAdapter.Holder;
import com.donson.myhook.bean.AppInfosMode;
import com.donson.utils.AppInfosUtil;
import com.donson.utils.CmdUtil;
import com.donson.xxxiugaiqi.R;

public class UninstallActivity extends BaseActivity {
	private ListView lv_list;
	public ArrayList<AppInfosMode> mAppList;
	private AppInfosAdapter mSelectAppAdapter;
	public PackageManager mPm;
	Button btn_clear;
	HashMap<Integer, Boolean> selectedMap = new HashMap<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mPm = getPackageManager();
		setContentView(R.layout.activity_select_app);
		initRect();
		lv_list = (ListView) findViewById(R.id.lv_list);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		btn_clear.setVisibility(View.GONE);
		getInstallApp();
		lv_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(!mSelectAppAdapter.getIsShowCb()){
					AppInfosMode packageInfoMode = mAppList.get(position);
					showDialog(packageInfoMode);
				}else {
					AppInfosAdapter.Holder holder = (Holder) view.getTag();
					if(!(holder.cb_checkbox.getVisibility()==View.INVISIBLE)){
						Logger.d("ischecked:"+holder.cb_checkbox.isChecked());
						
						holder.cb_checkbox.setChecked(!holder.cb_checkbox.isChecked());
						if(holder.cb_checkbox.isChecked()){
							selectedMap.put(position, true);
							mSelectAppAdapter.addVisibleList(new Integer(position));
						}else {
							selectedMap.remove(position);
							mSelectAppAdapter.removeVisibleList(new Integer(position));
						}
					}
					
					
				}
				
			}
		});
		lv_list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				setLefTitleVisible(true);
				mSelectAppAdapter.setIsShowCb(true);
				mSelectAppAdapter.notifyDataSetChanged();
				return true;
			}
		});
	}
	public void initRect() {
		initTopBar();
		setTopLeftText(getResources().getString(R.string.delete));
		setTopTitle(getResources().getString(R.string.btn_unInstall_apks));
		setLefTitleVisible(false);
//		setTopRightText(getResources().getString(R.string.check_all));
		setLefTextClick(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showMultiDeletDialog();
			}
		});
	}
	ProgressDialog progressDialog = null;
	protected void deletingDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.show();
	}
	protected void deletingDialogDismiss() {
		if(progressDialog!=null){
			progressDialog.dismiss();
		}
	}
	AlertDialog multiDialog;
	protected void showMultiDeletDialog() {
		Builder builder = new AlertDialog.Builder(UninstallActivity.this,AlertDialog.THEME_HOLO_LIGHT);
		multiDialog = builder.create();
		multiDialog.setTitle(String.format(getString(R.string.tips_delete_all), selectedMap.size()                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     ));
		multiDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.btn_ok),new OnClickListener() {
			String new_packageName ;
			List<AppInfosMode> removeAPKs = new ArrayList<AppInfosMode>();
			@Override
			public void onClick(DialogInterface dialog, int which) {
				deletingDialog();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
//						Looper.prepare();
						Iterator<Integer> iterator = selectedMap.keySet().iterator();
						while (iterator.hasNext()) {
							int key = iterator.next();
							AppInfosMode packageInfoMode = mAppList.get(key);
							new_packageName = AppInfosUtil.getPackageName(packageInfoMode);
							if(!new_packageName.equals(ConstantsHookConfig.PAC_KEYBOARD)&&!new_packageName.equals(ConstantsHookConfig.CONTROL_PACKAGE_NAME)){
								if(CmdUtil.unInstallApk(new_packageName)){
									removeAPKs.add(packageInfoMode);
								}else {
									try{
										String appName = AppInfosUtil.getAppNameByPackageInfo(packageInfoMode.getPackageInfo(), mPm);
										showToast(""+appName+getResources().getString(R.string.uninstall_err));
									}catch (Exception e) {
										System.out.println(e);
									}
								}
							}
						}
						if(removeAPKs!=null&&removeAPKs.size()>0){
							for (int i = 0; i < removeAPKs.size(); i++) {
								AppInfosMode index = removeAPKs.get(i);
								mAppList.remove(index);
							}
						}
						int len = mAppList.size();
						deletingDialogDismiss();
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								setLefTitleVisible(false);
								mSelectAppAdapter.notifyDataSetChanged();
								mSelectAppAdapter.setIsShowCb(false);
								mSelectAppAdapter.notifyDataSetChanged();
							}
						});
//						Looper.loop();
					}
				}).start();
			}
		});
		multiDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		multiDialog.show();
		
	}
	AlertDialog dialog;
	protected void showDialog(final AppInfosMode packageInfoMode) {
		final String new_packageName = AppInfosUtil.getPackageName(packageInfoMode);
//		View view = getAppInfoView(packageInfoMode, mPm, UninstallActivity.this);
		Builder builder = new AlertDialog.Builder(UninstallActivity.this, AlertDialog.THEME_HOLO_LIGHT);
		dialog = builder.create();
		dialog.setTitle(getResources().getString(R.string.uninstall_please_confirm_uninstall));
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.btn_ok), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
//				CmdUtil.unInstallApk(new_packageName);
				if(CmdUtil.unInstallApk(new_packageName)){
					ArrayList<AppInfosMode> mAppList2 = AppInfosUtil.getInstallApp(UninstallActivity.this);
					mAppList.clear();
					mAppList.addAll(mAppList2);
					mSelectAppAdapter.notifyDataSetChanged();
					showToast(getResources().getString(R.string.uninstall_ok));
				} else {
					showToast(getResources().getString(R.string.uninstall_err));
				}
			}
		});
		dialog.setButton(DialogInterface.BUTTON_NEGATIVE,getString(R.string.btn_cancel), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
//		dialog.setView(view);
		builder.setNegativeButton(getResources().getString(R.string.cancel), null);
		dialog.show();
	}
	public static View getAppInfoView(AppInfosMode packageInfoMode, PackageManager mPm, Context context) {
		CharSequence name = AppInfosAdapter.getName(packageInfoMode, mPm);
		View view = View.inflate(context, R.layout.item_select_app, null);
		ImageView iv_image = (ImageView) view.findViewById(R.id.iv_image);
		TextView tv_text = (TextView) view.findViewById(R.id.tv_text);
		tv_text.setText(name);
		AppInfosAdapter.setImage(iv_image, packageInfoMode, mPm);
		return view;
	}
	private void getInstallApp() {
		mAppList = AppInfosUtil.getInstallApp(UninstallActivity.this);
		mSelectAppAdapter = new AppInfosAdapter(UninstallActivity.this, mAppList, true, false);
		lv_list.setAdapter(mSelectAppAdapter);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(dialog!=null){
			dialog.dismiss();
			dialog = null;
		}
		if(multiDialog!=null){
			multiDialog.dismiss();
			multiDialog = null;
		}
		if(progressDialog!=null){
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

}
