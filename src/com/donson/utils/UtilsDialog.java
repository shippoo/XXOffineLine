package com.donson.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.SimpleFormatter;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.donson.myhook.BaseActivity;
import com.donson.xxxiugaiqi.R;
import com.param.bean.ParamEntity;
import com.param.dao.DbDao;

public class UtilsDialog {
	
	
	public interface DialogOptListener{
		public void onBack(EditText et);
	}
	/**
	 * 带输入框的Dialog
	 * @param context
	 * @param originalText
	 * @param title
	 * @param orTips
	 * @param orsuf
	 * @param listener
	 */
	public static AlertDialog showChangTextDialog(Context context,String originalText, String title,String orTips,String orsuf,
			final DialogOptListener listener) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View dialogView = layoutInflater.inflate(
				R.layout.dialog_chang_script_id, null);
		final EditText et = (EditText) dialogView
				.findViewById(R.id.et_script_or_id);
		TextView suf = (TextView) dialogView
				.findViewById(R.id.et_script_suffix);
		TextView tip = (TextView) dialogView.findViewById(R.id.script_tc_tips);
		suf.setText(orsuf);
		tip.setText(orTips);
		et.setText(originalText + "");
		et.setSelection(originalText.length());
		builder.setView(dialogView);
		builder.setTitle(title);
		builder.setPositiveButton(context.getString(R.string.dialog_ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						listener.onBack(et);
					}
				});
		builder.setNegativeButton(context.getString(R.string.dialog_cancle),
				null);
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
		return alertDialog;
	}
	/**
	 * 提示框dialog
	 * @param context
	 * @param title
	 * @param content
	 * @param listener
	 * @return
	 */
	public static AlertDialog showTipsDialog(Context context,String title,String content,final DialogOptListener listener) {
		Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		if(!TextUtils.isEmpty(title)){
			builder.setTitle(title+"");
		}
		if(!TextUtils.isEmpty(content)){
			builder.setMessage(content);
		}
		
		builder.setPositiveButton(context.getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				listener.onBack(null);
			}
		}).setNegativeButton(context.getString(R.string.dialog_cancle), null);
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
		return alertDialog;
	}
	
	/**
	 * 留存数据条数
	 * @param context
	 * @param tableName
	 * @param content
	 * @return
	 */
	public static AlertDialog showRetainDetailDialog(final BaseActivity context,final String tableName,String content ){
//		Builder builder = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT);
//		AlertDialog alertDialog = builder.create();
//		alertDialog.show();
		final DbDao dao = DbDao.getInstance(context);
//		final Map<Integer, String> details = dao.getRetainIdsByTable(tableName);
		final List<Integer> ids = dao.getRetainIdsByTable(tableName);
//		final List<Integer> ids = new ArrayList<Integer>(details.keySet());
//		Set<Integer> ids = details.keySet();
//		for(Entry<Integer, String> entry:details.entrySet()){
//			ids.add(entry.getKey());
//		}
		int count = ids.size();
		LayoutInflater inflater = context.getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_retain_detail, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT);
		ListView listView = (ListView) view.findViewById(R.id.lv_retain_datas);
		TextView tvRetainCount = (TextView)view.findViewById(R.id.tv_retain_item_count);
		TextView tvOk = (TextView)view.findViewById(R.id.tv_ok);
		TextView tvClear = (TextView)view.findViewById(R.id.tv_retain_clear);
		tvRetainCount.setText(String.format(context.getResources().getString(R.string.retain_cur_count), count));
		
		ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, ids);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ParamEntity entity = dao.getRetainEntityByTableByID(tableName, ids.get(position));
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(entity.getGeneration_time()));
				String detail = "IMEI:"+entity.getImei()+"\n机型:"+entity.getBuild_brand()+"_"+entity.getBuild_model()+"\n日期:"+date;
				context.showBigToast(context, detail);
			}
		});
		
		builder.setView(view);
		final AlertDialog alertDialog = builder.create();
		tvOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
			}
		});
		tvClear.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dao.dropTableByTableName(tableName);
				context.showToast(context.getString(R.string.retain_clear_ok));
				alertDialog.dismiss();
				
			}
		});
		alertDialog.show();
		return alertDialog;
		
	}

}
