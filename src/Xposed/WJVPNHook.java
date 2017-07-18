package Xposed;

import android.content.SharedPreferences;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.donson.config.Logger;
import com.donson.utils.EasyClickUtil;
import com.donson.utils.SendBroadCastUtil;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class WJVPNHook extends XHook {
	private static WJVPNHook instance;
	private WJVPNHook() {
		// TODO Auto-generated constructor stub
	}
	public static WJVPNHook getInstance(){
		if(instance==null){
			synchronized (WJVPNHook.class) {
				if(instance == null){
					instance = new WJVPNHook();
				}
			}
		}
		return instance;
	}

	String ip,location;SharedPreferences preferences;
	@Override
	protected void handleMethod(String packageName, ClassLoader classLoader)
			throws Exception {
		wjvpnconnclick = false;
		wjvpnchangbtnshow = false;
		wjvpnusername = false;
		wjvpnpassward = false;
		ip = "";location = "";
		HookMethod(View.class, "refreshDrawableState", packageName,MethodInt.WJ_VPN_CONNECT);
		HookMethod(TextView.class, "isSuggestionsEnabled", packageName, MethodInt.WJ_VPN_CONNECT);
		HookMethod(TextView.class, "setText", packageName, MethodInt.WJ_VPN_CONNECT,CharSequence.class);
	}

	@Override
	protected void after(String packageName, MethodHookParam param, int type)
			throws Exception {
		Object obj;
		switch (type) {
		case MethodInt.WJ_VPN_CONNECT:
			obj=param.thisObject;
			if(obj instanceof TextView){
				TextView et = (TextView)obj;
				if(preferences==null){
					preferences = et.getContext().getSharedPreferences("count", 1);
				}
				
				if(!EasyClickUtil.getWJVpnUserName(et.getContext()).equals(preferences.getString("UserName", ""))){
					boolean res1 = preferences.edit().putString("UserName", EasyClickUtil.getWJVpnUserName(et.getContext())).commit();
				}
				if(!EasyClickUtil.getWJVpnPassword(et.getContext()).equals(preferences.getString("PassWord", ""))){
					boolean res1 = preferences.edit().putString("PassWord", EasyClickUtil.getWJVpnPassword(et.getContext())).commit();
				}
				
//				boolean res2 = preferences.edit().putString("PassWord", "522964").commit();
//				

				
				
			}
			if(obj instanceof CheckBox){
				CheckBox cb=(CheckBox)obj;
				if(!TextUtils.isEmpty(cb.getText())&&("开机自动连接(登陆后生效)".equals(cb.getText().toString())||"自动过滤无效ip".equals(cb.getText().toString()))){
					Logger.v("WUJI----111--"+cb.getText().toString()+"  cb.isChecked()::"+cb.isChecked());
					if(!cb.isChecked()){
						cb.performClick();
					}
				}
			}
			if(obj instanceof RadioButton){
				RadioButton radioButton = (RadioButton)obj;
				if(!TextUtils.isEmpty(radioButton.getText())&&"专业版".equals(radioButton.getText().toString())){
					Logger.v("WUJI----radioButton--"+radioButton.getText().toString()+"  cb.isChecked()::"+radioButton.isChecked());
					if(!radioButton.isChecked()){
						radioButton.performClick();
					}
				}
			}
			if (obj instanceof TextView) {
				TextView btn = (TextView) obj;
				Logger.v("-----WUJI^^^^^^^^^^^^^^^^text:"+btn.getText().toString()+"--btn::"+btn.getId()+"  me:"+param.method +" not_conn::?"+
				(EasyClickUtil.getwjVpnConnectFlag(btn.getContext()) == EasyClickUtil.NOT_CONNECT_WJ_VPN)+"  dis::?"+EasyClickUtil.getwjVpnDisConnectFlag(btn.getContext()));
				if ((EasyClickUtil.getwjVpnConnectFlag(btn.getContext()) == EasyClickUtil.NOT_CONNECT_WJ_VPN)&&!EasyClickUtil.getwjVpnDisConnectFlag(btn.getContext())) {
					return;
				}
				Logger.v("WUJI-------"+ btn.getId()+"  "+btn.getText().toString()+ "  wjvpnconnclick"+ wjvpnconnclick+ "  "+ ((!TextUtils.isEmpty(btn.getText())) && (btn.getText().toString().equals("切换VPN")|| btn.getText().toString().equals("尚未使用VPN") || btn
								.getText().toString().equals("登陆"))));
				if ((!TextUtils.isEmpty(btn.getText()))) {
					if (EasyClickUtil.getwjVpnConnectFlag(btn.getContext()) == EasyClickUtil.CONNECT_WJ_VPN){
						if (btn.getId()==2131099661||btn.getId()==2131099654) {
							ip = btn.getText().toString();
						}
						if (btn.getId()==2131099663||btn.getId()==2131099655) {
							location = btn.getText().toString();
						}
						
						if((/*"切换VPN".equals(btn.getText().toString())
								|| "切换vpn".equals(btn.getText().toString())
								|| "尚未使用VPN".equals(btn.getText().toString()) 
								|| */"登陆".equals(btn.getText().toString())
								||"登       陆".equals(btn.getText().toString()))) {
							Logger.v("res------WUJI----2222222---"+ btn.getText().toString() + "  wjvpnconnclick"+ wjvpnconnclick);
							if (wjvpnconnclick)
								return;
							wjvpnconnclick = true;
							try {
								boolean res = btn.performClick();
							} catch (Exception e) {
								Logger.v("wujivpn:" + e);
								e.printStackTrace();
							}
						}
						if(("确定".equals(btn.getText().toString())&&btn.getId()==16908313)){
							boolean res = btn.performClick();
							wjvpnconnclick = false;
							Logger.v("WUJI---===确定=====-"+ btn.getId()+btn.getText().toString()+"  res::"+res);
//							Message msg = handler.obtainMessage();
//							msg.obj = btn;
//							msg.what = MethodInt.WJ_VPN_CONNECT;
//							handler.sendMessageDelayed(msg,5000);
						}
						if (btn.getId()==2131099664||btn.getId()==2131099658) {
							if(("切换vpn".equals(btn.getText().toString())||"切换VPN".equals(btn.getText().toString()))&&!wjvpnchangbtnshow){
								wjvpnchangbtnshow = true;
								Message msg = handler.obtainMessage();
								msg.obj = btn;
								msg.what = MethodInt.WJ_VPN_CONNECT;
								handler.sendMessageDelayed(msg, 1000);
								try{
									Thread.sleep(1000);
								}catch(Exception e){
									e.printStackTrace();
								}
								SendBroadCastUtil.sendWjVpnChanged(btn.getContext(),ip,location);
							}else if ("切换失败".equals(btn.getText().toString())) {
								boolean res = btn.performClick();
							}
							Logger.v("WUJI---====2131099664====-"+ btn.getText().toString()+"ip ::"+ip+" location::"+location+"  me:"+param.method);
						}
					}else if (EasyClickUtil.getwjVpnDisConnectFlag(btn.getContext())) {
						
						Logger.v("WUJI---断开测试========-"+ btn.getId()+btn.getText().toString()+"me::"+param.method);
						if(("退出无极".equals(btn.getText().toString()))) {
							
							boolean res = btn.performClick();
							Logger.v("WUJI---========-"+ btn.getId()+btn.getText().toString()+"  res::"+res);
						}
						if(("确定".equals(btn.getText().toString())&&btn.getId()==16908313)){
							boolean res = btn.performClick();
							Logger.v("WUJI---===确定=====-"+ btn.getId()+btn.getText().toString()+"  res::"+res);
							Message msg = handler.obtainMessage();
							msg.obj = btn;
							msg.what = MethodInt.WJ_VPN_CONNECT;
							handler.sendMessageDelayed(msg,5000);
						}
						
					}
					
				}
			}
			break;

		default:
			break;
		}

	}

}
