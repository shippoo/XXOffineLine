package com.donson.myhook;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.donson.utils.CommonSprUtil;
import com.donson.utils.OpenActivityUtil;
import com.donson.utils.UtilsDialog;
import com.donson.xxxiugaiqi.R;
import com.mz.annotation.ContentViewInject;
import com.mz.annotation.InjectUtils;
import com.mz.annotation.ViewInject;
import com.param.dao.DBHelper;
import com.param.dao.DbDao;

@ContentViewInject(R.layout.activity_liucun_all_table)
public class LiuCunAllTableActivity extends BaseActivity {
	@ViewInject(R.id.lv_liucun_all_table)
	ListView lvLiucunAllTable;

	@ViewInject(R.id.ll_all_tables)
	LinearLayout llAllTables;
	List<String> allretainTables;
	String curTableName;
	@ViewInject(R.id.retain_no_table)
	TextView tvNoTableTips;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		InjectUtils.injectAll(this);
		initRect();
		int tableType = getIntent().getIntExtra(OpenActivityUtil.TABLETYPE,OpenActivityUtil.RETAIN_TYPE);
		if(tableType == OpenActivityUtil.RETAIN_TYPE){
			showRetainTable();
		}else {
			showAllParamTable();
		}
	}
	private void showRetainTable() {
		setTopTitle(getString(R.string.retain_all_retain_table_title));
		curTableName = CommonSprUtil.getRetainTableName(LiuCunAllTableActivity.this);
		showTables(curTableName, OpenActivityUtil.RETAIN_TYPE);
	}
	
	private void showAllParamTable() {
		setTopTitle(getString(R.string.retain_all_table_title));
		curTableName = CommonSprUtil.getParamTableName(LiuCunAllTableActivity.this);
		showTables(curTableName, OpenActivityUtil.ALLPARAM_TYPE);
	}

	
	private void showTables(String curTableName,int tableType){
		allretainTables = new ArrayList<String>();
		// ArrayAdapter<String> adapter = new ArrayAdapter<>(this, resource,
		// objects);
		DbDao dao = DbDao.getInstance(LiuCunAllTableActivity.this);
		List<String> tables = dao.getAllTables();
		List<String> retainTables = new ArrayList<>();
		for (String table : tables) {
			if(tableType==OpenActivityUtil.RETAIN_TYPE){
				if (table.contains(DBHelper.liucunTableName)) {
					retainTables.add(table);
				}
			}else {
				if(!table.contains(DBHelper.liucunTableName)&&
						!table.equals(DBHelper.liucunSetTableName)&&
						!table.equals("android_metadata")&&
						!table.equals("sqlite_sequence")){
					retainTables.add(table);
				}
			}
		}
		if (retainTables != null && retainTables.size() > 0) {
			tvNoTableTips.setVisibility(View.GONE);
			for (final String table : retainTables) {
				
				Button button = new Button(this);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				// set 四周距离
				params.setMargins(10, 10, 10, 10);

				button.setLayoutParams(params);
				button.setBackground(getDrawable(R.drawable.btn_press_selected));
				button.setText(table);
				button.setTextSize(getResources().getDimension(R.dimen.text_size_smaller));
				button.setTextColor(getResources().getColor(R.color.white));
				System.out.println(table+":\n"+curTableName+":::"+table.equals(curTableName));
				if(table.equals(curTableName)){
					button.setTextColor(getResources().getColor(R.color.red));
				}
				
				llAllTables.addView(button);
				button.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						UtilsDialog.showRetainDetailDialog(LiuCunAllTableActivity.this, table, "contetn");
					}
				});
			}
		}else {
			tvNoTableTips.setVisibility(View.VISIBLE);
			if(tableType == OpenActivityUtil.RETAIN_TYPE){
				tvNoTableTips.setText(getString(R.string.retain_cur_no_retainl_table));
			}else {
				tvNoTableTips.setText(getString(R.string.retain_cur_no_param_table));
			}
		}
	}

	@Override
	public void initRect() {
		initTopBar();
		setTopTitle(getString(R.string.retain_all_retain_table_title));
		setLeftBtnBack();
	}

}
