package com.donson.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.donson.xxxiugaiqi.R;

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
	public static AlertDialog showTipsDialog(Context context,String title,String content,final DialogOptListener listener) {
		Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		builder.setTitle(title+"");
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
	

}
