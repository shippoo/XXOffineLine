package com.donson.controller;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.donson.myhook.LiucunActivity;
import com.donson.utils.CommonSprUtil;
import com.donson.utils.SPrefHookUtil;
import com.donson.utils.UtilRetain;
import com.donson.utils.UtilsDialog;
import com.donson.utils.UtilsDialog.DialogOptListener;
import com.donson.viewinterface.LiuCunViewInterface;
import com.donson.xxxiugaiqi.R;
import com.param.bean.LiuCunMode;
import com.param.config.SPrefUtil;
import com.param.dao.DBHelper;
import com.param.dao.DbDao;

public class LiuCunViewController {
	private Activity context;
	private LiuCunViewInterface viewInterface;
	private DbDao dao;
	List<LiuCunMode> setList;

	// List<String> packageList ;
	public LiuCunViewController(Activity context,
			LiuCunViewInterface viewInterface) {
		this.context = context;
		this.viewInterface = viewInterface;
		dao = DbDao.getInstance(context);
		getpackageList();
	}
	public String getListenPackageName(){
		return SPrefHookUtil.getSettingStr(context,SPrefHookUtil.KEY_HHOOK_PACKAGE_NAME);
	}
	public String getCurChannel(){
		return SPrefUtil.getString(context,SPrefUtil.C_CHANNEL,SPrefUtil.D_CHANNEL);
	}
	public List<String> getpackageList() {
		setList = dao.showLiuCunSetting();
		List<String> packageList = new ArrayList<String>();
		for (LiuCunMode liuCunMode : setList) {
			packageList.add(liuCunMode.getPackageName()+"_"+liuCunMode.getChannel());
		}
		String curListenPac = getListenPackageName();
		if(!packageList.contains(curListenPac+"_"+getCurChannel())&&!TextUtils.isEmpty(curListenPac)){
			packageList.add(curListenPac+"_"+getCurChannel());
		}
		return packageList;
	}
	int totalCount = 0;
	public void init() {
//		int runCount = SPrefUtil.getInt(context, SPrefUtil.C_RUN_COUNT, SPrefUtil.D_RUN_COUNT);
		int runCount = SPrefHookUtil.getTaskInt(context, SPrefHookUtil.KEY_TASK_CUR_RUN_TIME, SPrefHookUtil.D_TASK_CUR_RUN_TIME);
		int runLiucunCount = SPrefUtil.getInt(context, SPrefUtil.C_RUN_LIU_CUN_COUNT, SPrefUtil.D_RUN_LIU_CUN_COUNT);
		totalCount = SPrefHookUtil.getTaskInt(context, SPrefHookUtil.KEY_TASK_RUN_TIMES, SPrefHookUtil.D_TASK_RUN_TIMES);
		
		viewInterface.setLiuCunDataText(runCount, runLiucunCount);
		viewInterface.setTotalCountText(totalCount);
		String tableName = CommonSprUtil.getRetainTableName(context);
		int remainCount = dao.getRetainIdsByTable(tableName).size();
		viewInterface.setTvLiucunDataRunNumTips(remainCount);
	}
	public void setLiuCunPercent() {
		String result = viewInterface.getPercentSet();
		if (result != null) {
			boolean succeed = dao.insertOrReplaceLiuCunSetting(getListenPackageName(),SPrefUtil.getString(context, SPrefUtil.C_CHANNEL, SPrefUtil.D_CHANNEL),result);
			getpackageList();
			if (succeed) {
				viewInterface.toast(context.getString(R.string.liucun_set_ok));
			} else {
				viewInterface.toast(context.getString(R.string.liucun_set_err));
			}
		}
	}
	/**
	 * Edittext textWatcher
	 * @param etLiucun
	 * @param flag
	 */
	public void addTextChangedListener(EditText etLiucun, final int flag) {
		etLiucun.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				if (TextUtils.isEmpty(s)) {
					viewInterface.setEt(flag, true,
							context.getString(R.string.et_not_null));
				} else if (Float.parseFloat(s.toString()) > 100) {
					viewInterface.setEt(flag, true,
							context.getString(R.string.et_must_less_than_100));
				}
			}
		});
	}

	public void handleSpinnerSelected(AdapterView<?> parent, View view,
			int position, long id) {
		if (position >= setList.size()) {
			for (int i = 1; i <= 9; i++) {
				viewInterface.setEt(i, false, "");
			}
		} else {
			LiuCunMode mode = setList.get(position);
			for (int i = 1; i <= 9; i++) {
				viewInterface.setEt(i, false, String.valueOf(mode.getPercentByID(i-1)));
			}
		}
	}

	/**
	 * @param i
	 */
	public void hanldeLiucunRemove(final int i) {
		if (setList == null || setList.size() <= 0) {
			viewInterface.toast(context.getString(R.string.no_liucun_set));
			return;
		}
		UtilsDialog.showTipsDialog(context,  "",String.format(context
				.getString(R.string.btn_liucun_remove_tips),
				setList.get(i).getPackageName()), new DialogOptListener() {
			
			@Override
			public void onBack(EditText et) {
				String packageName = setList.get(i).getPackageName();//CommonSprUtil.getCurChannel(context);
				String channel = setList.get(i).getChannel();//CommonSprUtil.getListenPackageName(context);
				String paramTable = packageName.replace(".", "_")+"_"+channel;
				String retainTable = DBHelper.liucunTableName+"_"+paramTable;
				dao.dropTableByTableName(paramTable);
				dao.dropTableByTableName(retainTable);
			}
		});
	}

	/**
	 * 设置总执行次数
	 */
	public void handleSetTotalCount() {
		UtilsDialog.showChangTextDialog(context, totalCount+"", context.getString(R.string.liucun_dialog_set_total_title), "",
				"", new DialogOptListener() {
			
			@Override
			public void onBack(EditText et) {
				String count = et.getText().toString();
				if (TextUtils.isEmpty(count)) {
					et.setError(context.getString(R.string.liucun_tip_total_count_not_null));
					viewInterface.toast(context.getString(R.string.liucun_tip_total_count_not_null));
				} else {
					int integer = Integer.parseInt(count);
					boolean result = SPrefHookUtil.putTaskInt(context,SPrefHookUtil.KEY_TASK_RUN_TIMES,integer);
					if (result) {
						viewInterface.toast(context.getString(R.string.liucun_tip_total_count_set_ok));
						int totalCount = SPrefHookUtil.getTaskInt(context,SPrefHookUtil.KEY_TASK_RUN_TIMES,SPrefHookUtil.D_TASK_RUN_TIMES);
						viewInterface.setTotalCountText(totalCount);
					} else {
						viewInterface.toast(context.getString(R.string.liucun_tip_total_count_set_err));
					}
//					dialog.dismiss();
				}
			}
		});		
	}
	/**
	 * 生成选定设置的留存数据
	 * @param i
	 */
	public void formSelectedRetainData(int i) {
		String packageName = setList.get(i).getPackageName();//CommonSprUtil.getCurChannel(context);
		String channel = setList.get(i).getChannel();//CommonSprUtil.getListenPackageName(context);
		String paramTable = packageName.replace(".", "_")+"_"+channel;
		boolean result = UtilRetain.formRetainData(context,packageName,channel);
		if(result){
			viewInterface.toast(context.getString(R.string.retain_retain_table_generate_ok));
		}else {
			viewInterface.toast(context.getString(R.string.retain_retain_table_generate_err));
		}
		
	}
	/**
	 * 运行指定ID的数据
	 */
	public void runSelectedIDParam() {
		UtilsDialog.showChangTextDialog(context, 
				""+SPrefHookUtil.getCurTaskInt(context, SPrefHookUtil.KEY_TASK_CUR_RUN_ID, SPrefHookUtil.D_TASK_CUR_RUN_ID), 
				context.getString(R.string.retain_run_selected_id_param), "", "", new DialogOptListener() {
			
			@Override
			public void onBack(EditText et) {
				if(!TextUtils.isEmpty(et.getText())){
					String idStr = et.getText().toString();
					int id = Integer.parseInt(idStr);
					SPrefHookUtil.putCurTaskInt(context, SPrefHookUtil.KEY_TASK_CUR_RUN_ID, id);
					viewInterface.toast(context.getString(R.string.retain_select_id_ok));
				}
			}
		});
	}
}
