package com.donson.utils;

import android.text.TextUtils;

import com.donson.config.Logger;
import com.shell.RootShell;

public class InputSetUtil {

	public enum InputeEnum {
		SOUGOU(1, "SogouIME"), ADB(2, "AdbIME");
		InputeEnum(int type, String note) {
			this.type = type;
			this.note = note;
		}

		private String note;
		private int type;

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

	};

	/**
	 * 获取输入法
	 */
	public static int getInputType() {
		String cmd = "settings get secure default_input_method";
		Process process;
		try {
			process = CmdUtil.run(cmd);
			process.waitFor();
			String res = CmdUtil.readResult(process);

			Logger.i("CMD setInput====" + res + " ");
			if (!TextUtils.isEmpty(res)) {
				if (res.contains(InputeEnum.SOUGOU.getNote())) {
					return InputeEnum.SOUGOU.getType();
				} else if (res.contains(InputeEnum.ADB.getNote())) {
					return InputeEnum.ADB.getType();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			process = null;
		}
		return -1;
	}

	/**
	 * 修改输入法
	 * 
	 * @param type
	 */
	public static void setInputType(InputeEnum type) {
//		String cmd = "settings put secure default_input_method com.android.adbkeyboard/.AdbIME";
//		RootShell.getInstance().execCmd(cmd);
//		Logger.i("CMD setInput====" + cmd + " ");
		String cmd = "settings put secure default_input_method com.android.adbkeyboard/.AdbIME";
		if (type.getType() == InputeEnum.SOUGOU.getType()) 
			cmd = "settings put secure default_input_method com.sohu.inputmethod.sogou.xiaomi/.SogouIME";
		Process process;
		try {
			process = CmdUtil.run(cmd);
			process.waitFor();
			String res = CmdUtil.readResult(process);
			Logger.i("CMD setInput====" + res + " " + type);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			process = null;
		}
	}
}
