package com.donson.myhook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.donson.controller.VpnViewController;
import com.donson.utils.SPrefHookUtil;
import com.donson.viewinterface.VpnViewInterface;
import com.donson.xxxiugaiqi.R;
import com.mz.annotation.ContentViewInject;
import com.mz.annotation.InjectUtils;
import com.mz.annotation.ViewInject;

@ContentViewInject(R.layout.activity_wujivpn_set)
public class WUJIVpnSetActivity extends BaseActivity implements VpnViewInterface{
	@ViewInject(R.id.rl_vpn_auto_connect)
	RelativeLayout rlVpnAutoCon;
	@ViewInject(R.id.cb_vpn_connect)
	CheckBox cbVpnConn;
	@ViewInject(R.id.et_vpn_reconnect_time)
	EditText etVpnReconnTime;
	@ViewInject(R.id.et_vpn_connect_time)
	EditText etVpnConTime;
	@ViewInject(R.id.et_vpn_server_add)
	EditText etVpnServerAdd;
	@ViewInject(R.id.et_vpn_username)
	EditText etVpnUserName;
	@ViewInject(R.id.et_vpn_password)
	EditText etVpnPassword;
	@ViewInject(R.id.btn_vpn_save)
	Button btnVpnSave;
	@ViewInject(R.id.btn_vpn_delete_vpn)
	Button btnDeleteVpn;
	@ViewInject(R.id.btn_vpn_add_vpn)
	Button btnAddVpn;
	@ViewInject(R.id.btn_vpn_change_vpn)
	Button btnChangeVpn;
	@ViewInject(R.id.btn_vpn_connect_vpn)
	Button btnConnectVpn;
	@ViewInject(R.id.btn_vpn_disconnect_vpn)
	Button btnDisconnectVpn;
	
	@ViewInject(R.id.btn_vpn_system_vpn)
	Button btnVpnChoseVpnModel;
	
	@ViewInject(R.id.btn_vpn_cur_vpn)
	Button btnVPNCurVpn;
	
	@ViewInject(R.id.ll_vpn_system_panel)
	LinearLayout llSystemPanel;
	
	@ViewInject(R.id.ll_vpn_wuji_panel)
	LinearLayout llWJPanel;
	
	@ViewInject(R.id.btn_vpn_cur_vpn)
	Button btnCurVpn;
	
	@ViewInject(R.id.et_wjvpn_username)
	EditText etVpnWJUserName;
	@ViewInject(R.id.et_wjvpn_password)
	EditText etVpnWJPassword;
	@ViewInject(R.id.btn_wj_vpn_save)
	Button btnWJVPNSave;
	
	@ViewInject(R.id.btn_vpn_clear_system_value)
	Button btnClearSystemValue;
	
	@ViewInject(R.id.tv_vpn_wj_version)
	TextView tvWJWersion;
	
	VpnViewController controller;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		InjectUtils.injectAll(this);
		controller = new VpnViewController(this, this);
		initRect();
		setEvent();
	}
	public void initRect() {
		initTopBar();
		setTopTitle(getString(R.string.vpn_title));
		setLeftBtnBack();
	}
	private void setEvent() {
		rlVpnAutoCon.setOnClickListener(controller);
		btnVpnSave.setOnClickListener(controller);
		btnAddVpn.setOnClickListener(controller);
		btnDeleteVpn.setOnClickListener(controller);
		btnChangeVpn.setOnClickListener(controller);
		btnConnectVpn.setOnClickListener(controller);
		btnDisconnectVpn.setOnClickListener(controller);
		controller.addTextWatchConn(etVpnConTime);
		controller.addTextWatchReConn(etVpnReconnTime);
		btnVpnChoseVpnModel.setOnClickListener(controller);
		btnVPNCurVpn.setOnClickListener(controller);
		btnCurVpn.setOnClickListener(controller);
		btnWJVPNSave.setOnClickListener(controller);
		btnClearSystemValue.setOnClickListener(controller);
	}

	@Override
	public void toast(String tips) {
		showToast(tips);
	}

	@Override
	public boolean getCheckBox() {
		return cbVpnConn.isChecked();
	}

	@Override
	public void setCheckBox(boolean value) {
		cbVpnConn.setChecked(value);
	}

	@Override
	public String getReConnText() {
		return etVpnReconnTime.getText().toString();
	}

	@Override
	public void setReConnText(boolean err,String time) {
		if(err){
			etVpnReconnTime.setError(time);
		}else{
			etVpnReconnTime.setText(time);
		}
		
	}

	@Override
	public String getConnText() {
		return etVpnConTime.getText().toString();
	}

	@Override
	public void setConnText(boolean err,String time) {
		if(err){
			etVpnConTime.setError(time);
		}else {
			etVpnConTime.setText(time);
		}
	}

	@Override
	public void toastBig(String tips) {
		showBigToast(this,tips);
	}
	@Override
	public String getServer() {
		return etVpnServerAdd.getText().toString();
	}
	@Override
	public void setServer(boolean err, String server) {
		etVpnServerAdd.setText(server);
		
	}
	@Override
	public String getUserName() {
		return etVpnUserName.getText().toString();
	}
	@Override
	public void setUserName(boolean err, String userName) {
		etVpnUserName.setText(userName);
	}
	@Override
	public String getPassword() {
		return etVpnPassword.getText().toString();
	}
	@Override
	public void setPassword(boolean err, String password) {
		etVpnPassword.setText(password);
	}
	@Override
	public void setSystemVpnShow() {
		llSystemPanel.setVisibility(View.VISIBLE);
		llWJPanel.setVisibility(View.GONE);
		btnVpnChoseVpnModel.setText(getString(R.string.vpn_btn_usewj_vpn));
		
	}
	@Override
	public void setWJVpnShow() {
		llWJPanel.setVisibility(View.VISIBLE);
		llSystemPanel.setVisibility(View.GONE);
		btnVpnChoseVpnModel.setText(getString(R.string.vpn_btn_use_system_vpn));
	}
	@Override
	public void clickTochoseSystemVpn() {
		btnCurVpn.setText(getString(R.string.vpn_btn_cur_usewj_vpn));
		
	}
	@Override
	public void clickTochoseWjVpn() {
		btnCurVpn.setText(getString(R.string.vpn_btn_cur_use_system_vpn));
	}
	@Override
	public boolean isSettingSystemVpn() {
		// TODO Auto-generated method stub
		return llSystemPanel.getVisibility()==View.VISIBLE;
	}
	@Override
	public String getWjUserName() {
		return etVpnWJUserName.getText().toString();
	}
	@Override
	public String getWjPassword() {
		return etVpnWJPassword.getText().toString();
	}
	@Override
	public void setWJUserName(String userName) {
		etVpnWJUserName.setText(userName+"");
	}
	@Override
	public void setWJPassward(String password) {
		etVpnWJPassword.setText(password+"");
	}
	@Override
	public void setWUJIVpnVersion(String version) {
		tvWJWersion.setText(version);
		
	}
}
