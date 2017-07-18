package Xposed;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.donson.config.Logger;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
/**
 * 各种权限允许
 * @author Administrator
 *
 */
public class MUSecurityHook extends XHook{
	private static MUSecurityHook instance;
	private MUSecurityHook() {
	}
	public static MUSecurityHook getIntance(){
		if(instance == null){
			synchronized (MUSecurityHook.class) {
				if(instance == null){
					instance = new MUSecurityHook();
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
				MethodInt.SECURITY_ACCEPT);
		HookMethod(Dialog.class, "show", packageName,
				MethodInt.SECURITY_ACCEPT);
	}

	@Override
	protected void after(String packageName, MethodHookParam param, int type)
			throws Exception {
		Object obj;
		switch(type){
		case MethodInt.SECURITY_ACCEPT:
		obj = param.thisObject;
		if (obj instanceof TextView) {
			TextView text = (TextView) obj;
			if (text.getId() == 16908313 && text.getWidth() > 0) {
				boolean res = text.performClick();
				Logger.h("MIUI===DOWN======SYSTEM=============click16908313===="
						+ res + "    " + text.getText()+"-----result------ "+param.getResult()+"   "+param.method +"  visibility:"+text.getVisibility()+"==="+ text.VISIBLE+"===" +text.getWidth()+"  "+text.getHeight());
			}
//			if(!TextUtils.isEmpty(text.getText())&&"允许".equals(text.getText().toString())){
//				 text.performClick();
//			}
		}
		//启动迅雷加速器的dialog 
		if(obj instanceof Dialog){
			Dialog dia=(Dialog) obj;
			View view=dia.findViewById(16908313);
			if(view instanceof Button&& view.getWidth()>0){
				Button btn= (Button)view;
				if(!TextUtils.isEmpty(btn.getText())){
					boolean res = btn.performClick();
				}
				Logger.h("MIUI===DOWN======SYSTEM--------dialog-------text:"+btn.getText()+"we:"+btn.getWidth()+" height:"+btn.getHeight()+" "+"id:"+btn.getId()/*+"-------res--"+res*/+"  visibility:"+btn.VISIBLE);
			}else {
				View view2 = dia.findViewById(2131230730);
				if(view2 instanceof Button){
					Button btn= (Button)view2;
					Logger.h("MIUI===DOWN======SYSTEM--------dialog-------text:"+btn.getText()+"we:"+btn.getWidth()+" height:"+btn.getHeight()+" "+"id:"+btn.getId()/*+"-------res--"+res*/+"  visibility:"+btn.VISIBLE);
					if(!TextUtils.isEmpty(btn.getText())&&"允许".equals(btn.getText().toString())){
//						boolean res = btn.performClick();
						boolean res = setSimulateClick(btn, 0, 0);
						Logger.h("MIUI===DOWN======res:"+res);

					}
					
				}
						
			}
			
		}
		break;
		}
	}

}
