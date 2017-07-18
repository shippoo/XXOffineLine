package com.donson.myhook;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.donson.config.Logger;
import com.donson.utils.MyfileUtil;
import com.donson.utils.SPrefHookUtil;
import com.donson.utils.UtilsDialog;
import com.donson.xxxiugaiqi.R;
import com.mz.annotation.ContentViewInject;
import com.mz.annotation.InjectUtils;
import com.mz.annotation.OnClick;
import com.mz.annotation.ViewInject;
import com.param.utils.CommonMathUtil;

@ContentViewInject(R.layout.activity_delete_log)
public class DeleteLogActivity extends BaseActivity {
	@ViewInject(R.id.tv_dead_line)
	TextView deadlineTips;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		InjectUtils.injectAll(this);
		initRect();
		initText();
	}

	private void initText() {
		deadlineTips.setText(String.format(
				getString(R.string.delete_log_tips_deadline_time),
				MyfileUtil.getDeadlineDay()));

	}

	@Override
	public void initRect() {
		initTopBar();
		setTopTitle(getString(R.string.main_btn_delete_log));
		setLeftBtnBack();
	}

	@OnClick({ R.id.btn_set_deadline_time, R.id.btn_clear_all_log,
			R.id.btn_clear_deadline_log, R.id.btn_clear_all_script,
			R.id.btn_clear_deadline_script, R.id.btn_clear_all_apk,
			R.id.btn_clear_deadline_apk, R.id.btn_clear_all_backapk,
			R.id.btn_clear_deadline_backup })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_set_deadline_time:
			UtilsDialog.showChangTextDialog(DeleteLogActivity.this,
					MyfileUtil.getDeadlineDay() + "",
					getString(R.string.delete_log_set_deadine), "", "天",
					new UtilsDialog.DialogOptListener() {

						@Override
						public void onBack(EditText et) {
							if (et != null && !TextUtils.isEmpty(et.getText())) {
								String time = et.getText().toString();
								Logger.i("time:" + time + " isNumeric: "
										+ CommonMathUtil.isNumeric(time));
								if (CommonMathUtil.isNumeric(time)) {
									SPrefHookUtil.putSettingInt(
											getApplicationContext(),
											SPrefHookUtil.KEY_SETTING_DEAD_DAY,
											Integer.parseInt(et.getText()
													.toString()));
									initText();
								} else {
									showToast(getString(R.string.tips_must_num));
								}
							} else {
								showToast(getString(R.string.tips_not_null));
							}
						}
					});

			break;
		case R.id.btn_clear_all_log:
			clearAllLog();
			break;
		case R.id.btn_clear_deadline_log:
			clearDeadlineLog();
			break;
		case R.id.btn_clear_all_script:
			clearAllScript();
			break;
		case R.id.btn_clear_deadline_script:
			clearDeadlineScript();
			break;
		case R.id.btn_clear_all_apk:
			clearAllApk();
			break;
		case R.id.btn_clear_deadline_apk:
			clearDeadlineApk();
			break;
		case R.id.btn_clear_all_backapk:
			clearAllBackUpApk();
			break;
		case R.id.btn_clear_deadline_backup:
			clearDeadlineBackUpApk();
			break;

		default:
			break;
		}
	}

	private void clearAllLog() {
		UtilsDialog.showTipsDialog(DeleteLogActivity.this,
				getString(R.string.delete_log_clear_all_apk), "",
				new UtilsDialog.DialogOptListener() {
					@Override
					public void onBack(EditText et) {
						MyfileUtil.clearAllLogFile();
					}
				});
	}

	private void clearDeadlineLog() {
		UtilsDialog.showTipsDialog(DeleteLogActivity.this,
				getString(R.string.delete_log_clear_all_apk), "",
				new UtilsDialog.DialogOptListener() {

					@Override
					public void onBack(EditText et) {
						MyfileUtil.clearDeadlineLogFile();
					}
				});
	}

	private void clearAllScript() {
		UtilsDialog.showTipsDialog(DeleteLogActivity.this,
				getString(R.string.delete_log_clear_all_apk), "",
				new UtilsDialog.DialogOptListener() {

					@Override
					public void onBack(EditText et) {
						MyfileUtil.clearAllScriptFile();
					}
				});

	}
	private void clearDeadlineScript() {
		UtilsDialog.showTipsDialog(DeleteLogActivity.this,
				getString(R.string.delete_log_clear_all_apk), "",
				new UtilsDialog.DialogOptListener() {

					@Override
					public void onBack(EditText et) {
						MyfileUtil.clearDeatLineScriptFile();
					}
				});
	}
	private void clearAllApk() {
		UtilsDialog.showTipsDialog(DeleteLogActivity.this,
				getString(R.string.delete_log_clear_all_apk), "",
				new UtilsDialog.DialogOptListener() {

					@Override
					public void onBack(EditText et) {
						MyfileUtil.clearAllApkFile();
					}
				});
	}
	private void clearDeadlineApk() {
		UtilsDialog.showTipsDialog(DeleteLogActivity.this,
				getString(R.string.delete_log_clear_all_apk), "",
				new UtilsDialog.DialogOptListener() {

					@Override
					public void onBack(EditText et) {
						MyfileUtil.clearDeatLineApkFile();
					}
				});
	}
	private void clearAllBackUpApk() {
		UtilsDialog.showTipsDialog(DeleteLogActivity.this,
				getString(R.string.delete_log_clear_all_apk), "",
				new UtilsDialog.DialogOptListener() {

					@Override
					public void onBack(EditText et) {
						MyfileUtil.clearAllBackupApkFile();
					}
				});
	}
	private void clearDeadlineBackUpApk() {
		UtilsDialog.showTipsDialog(DeleteLogActivity.this,
				getString(R.string.delete_log_clear_all_apk), "",
				new UtilsDialog.DialogOptListener() {

					@Override
					public void onBack(EditText et) {
						MyfileUtil.clearDeatLineBackupApkFile();
					}
				});
	}

}
