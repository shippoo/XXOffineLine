package Xposed;

import com.donson.config.Logger;
import com.donson.utils.MyfileUtil;
import com.hp.hpl.sparta.Text;
import com.param.utils.FileUtil;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class ANRHook extends XHook {
	private static ANRHook instance;
	private ANRHook() {
	}
	public static ANRHook getInstance(){
		if(instance == null){
			synchronized (ANRHook.class) {
				if(instance == null){
					instance = new ANRHook();
				}
			}
		}
		return instance;
	}
	@Override
	protected void handleMethod(String packageName, ClassLoader classLoader)
			throws Exception {
		HookMethod(View.class, "dispatchTouchEvent", packageName,
				MethodInt.DIS, MotionEvent.class);
		HookMethod(View.class, "refreshDrawableState", packageName,
				MethodInt.ANR_CLICK);
//		HookMethod(View.class, "getMeasuredWidth", packageName,
//				MethodInt.ANR_CLICK);
	}

	@Override
	protected void after(String packageName, MethodHookParam param, int type)
			throws Exception {
		Object obj = null;
		switch (type) {
		case MethodInt.ANR_CLICK:
			obj = param.thisObject;
			if(obj instanceof TextView){
				TextView textView = (TextView)obj;
				if(!TextUtils.isEmpty(textView.getText())&&"确定".equals(textView.getText().toString())){
					Logger.v("确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定"+param.method+"  "+packageName);
					boolean click = textView.performClick();
					Logger.v("确定：："+click);
//					MyfileUtil.recodeSystemANR(packageName);
				}
				if(!TextUtils.isEmpty(textView.getText())&&"取消".equals(textView.getText().toString())){
					Logger.v("取消 取消 取消取消取消取消取消取消取消取消取消取消取消"+param.method+"  "+packageName);
//					boolean click = textView.performClick();
//					Logger.v("取消：："+click);
//					Toast.makeText(textView.getContext(), "ANR", Toast.LENGTH_SHORT).show();
//					MyfileUtil.recodeSystemANR(packageName);
				}
			}
		}
	}

}
