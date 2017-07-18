package com.donson.realparam.utils;

import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class ConvertUtil {
	public static String mccMnc_str2Str(String str) {
		// 46000 �й��ƶ� ��GSM��
		// 46001 �й���ͨ ��GSM��
		// 46002 �й��ƶ� ��TD-S��
		// 46003 �й����ţ�CDMA��
		// 46004 �գ��ƺ���ר�����������Եģ�
		// 46005 �й����� ��CDMA��
		// 46006 �й���ͨ ��WCDMA��
		// 46007 �й��ƶ� ��TD-S��
		// 46008
		// 46009
		// 46010
		// 46011 �й����� ��FDD-LTE��
		if (TextUtils.equals(str, "46000"))
			return str + " �й��ƶ�(GSM)";

		if (TextUtils.equals(str, "46001"))
			return str + " �й���ͨ(GSM)";

		if (TextUtils.equals(str, "46002"))
			return str + " �й��ƶ�(TD-S)";

		if (TextUtils.equals(str, "46003"))
			return str + " �й�����(CDMA)";

		if (TextUtils.equals(str, "46005"))
			return str + " �й�����(CDMA)";

		if (TextUtils.equals(str, "46006"))
			return str + " �й���ͨ(WCDMA)";

		if (TextUtils.equals(str, "46007"))
			return str + " �й��ƶ�(TD-S)";

		if (TextUtils.equals(str, "46011"))
			return str + " �й�����(FDD-LTE)";

		return str;
	}
	
	public static final int NETWORK_CLASS_UNKNOWN = 0;
	public static final int NETWORK_CLASS_2_G = 1;
	public static final int NETWORK_CLASS_3_G = 2;
	public static final int NETWORK_CLASS_4_G = 3;

	public static String netWorkClassint2String(int type) {
		switch (type) {
		case NETWORK_CLASS_2_G:

			return "2G";
		case NETWORK_CLASS_3_G:

			return "3G";
		case NETWORK_CLASS_4_G:

			return "4G";
		default:

			return "UNKNOWN";
		}
	}
	public static String array2String(String[] strArray) {
		if (strArray != null) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < strArray.length; i++) {
				sb.append(strArray[i]);
				if (i != strArray.length - 1)
					sb.append("\n");
			}
			return sb.toString();
		}
		return null;
	}
	public static String phoneTypeint2Str(int type) {
		switch (type) {
		case TelephonyManager.PHONE_TYPE_NONE:
			return type + " (NONE)";
		case TelephonyManager.PHONE_TYPE_CDMA:
			return type + " (CDMA)";
		case TelephonyManager.PHONE_TYPE_GSM:
			return type + " (GSM)";
		case TelephonyManager.PHONE_TYPE_SIP:
			return type + " (SIP)";
		default:
			return String.valueOf(type);
		}
	}
	
	public static String phoneTypestr2Str(String type) {
		if(TextUtils.equals(type,String.valueOf(TelephonyManager.PHONE_TYPE_NONE))){
			 return type + " (NONE)";
		} else if(TextUtils.equals(type,String.valueOf(TelephonyManager.PHONE_TYPE_CDMA))){
			return type + " (CDMA)";
		} else if(TextUtils.equals(type,String.valueOf(TelephonyManager.PHONE_TYPE_GSM))){
			return type + " (GSM)";
		} else if(TextUtils.equals(type,String.valueOf(TelephonyManager.PHONE_TYPE_CDMA))){
			return type + " (SIP)";
		} 
		return type;
	}
	
	public static byte[] mac2byte(String mac){
		String[] strArr = mac.split(":");
		byte [] addr = new byte[6];
		for (int i = 0; i < strArr.length; i++) {
			int res = Integer.parseInt(strArr[i],16);
			addr[i]=(byte) res;
		}
		return addr;
	}
	public static String byte2mac(byte[] addr){
		StringBuilder buf = new StringBuilder();
		for (byte b : addr) {
			buf.append(String.format("%02X:", b));
		}
		if (buf.length() > 0) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}
}
