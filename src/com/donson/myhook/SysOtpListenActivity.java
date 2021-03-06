package com.donson.myhook;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.donson.config.Logger;
import com.donson.controller.SysOptListenViewController;
import com.donson.myhook.adapters.SysRecordAdapter;
import com.donson.viewinterface.SysOptListenViewInterface;
import com.donson.xxxiugaiqi.R;

public class SysOtpListenActivity extends BaseActivity implements SysOptListenViewInterface ,OnClickListener{
	
	Button btnDelete;
	TextView tvNoDataTips;
	ListView lvFileRecord;
	SysRecordAdapter adapter;
	List<String> list;
	SysOptListenViewController controller;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_opt_listen);
		list = new ArrayList<String>();
		btnDelete = (Button) findViewById(R.id.btn_delete_file_record);
		tvNoDataTips = (TextView) findViewById(R.id.tv_no_data_tips);
		lvFileRecord = (ListView) findViewById(R.id.lv_file_record);
		controller = new SysOptListenViewController(this, this);
		adapter = new SysRecordAdapter(this, controller ,list);
		lvFileRecord.setAdapter(adapter);
		notifyList();
		initRect();
		setEvent();
	}
	public void initRect() {
		initTopBar();
		setTopTitle(getString(R.string.title_listen_sys_opt));
		setLeftBtnBack();
	}
	private void setEvent() {
		btnDelete.setOnClickListener(this);
	}
	/**
	 * notify File operation Path record
	 */
	@Override
	public void notifyList(){
		Set<String> set =controller.getAllRecoredData();
		if(set.size()>0){
			tvNoDataTips.setVisibility(View.GONE);
			list.clear();
			list.addAll(set);
			adapter.setList(list);
			adapter.notifyDataSetChanged();
		}else {
			tvNoDataTips.setVisibility(View.VISIBLE);
		}
	}
	@Override
	public void toast(String tips) {
		showToast(tips);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_delete_file_record:
			controller.deleteAllRecord();
			break;
		default:
			break;
		}
	}
	@Override
	public void toastBig(String tips) {
		showBigToast(this,tips);
	}

}
