package com.donson.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import okhttp3.ResponseBody;
import android.R;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.donson.config.ConstantsHookConfig;
import com.donson.config.Logger;
import com.donson.utils.GetPackageSizeUtil.SizeResponseListener;
import com.donson.viewinterface.CallBackInterface;
import com.donson.zhushoubase.BaseApplication;
import com.param.config.ConstantsConfig;
import com.param.utils.FileUtil;
import com.param.utils.MD5Util;

public class MyfileUtil extends FileUtil {
	public static boolean isExternalStorageFile(String path) {
		String externalPath = ConstantsConfig.ROOTPATH;
		int length = externalPath.length();
		if (!TextUtils.isEmpty(path) && path.length() >= length
				&& externalPath.equals(path.substring(0, length))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param path
	 * @param listenPacName
	 * @return
	 */
	public static boolean isDataStorageFile(String path, String listenPacName) {
		if (TextUtils.isEmpty(listenPacName) || TextUtils.isEmpty(path)) {
			return false;
		}
		if (path.contains("data/data/" + listenPacName)) {
			return false;
		}
		if (path.contains(listenPacName)) {
			return true;
		}

		String dataPath = Environment.getDataDirectory().getAbsolutePath()
				+ File.separator + "data/" + listenPacName;
		int length = dataPath.length();
		if (!TextUtils.isEmpty(path) && path.length() >= length
				&& dataPath.equals(path.substring(0, length))) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean deleteFile(String path, String packageName) {
		try {
			File file = new File(path);
			if (file.exists()) {
				if (MyfileUtil.isExternalStorageFile(path)) {
					boolean result = file.delete();
					if (result)
						return true;
				} else {
					if (!path.contains("data/data/" + packageName)
							&& !path.contains("data/app/" + packageName)
							&& !path.contains("data/user/0/" + packageName)
							&& !path.contains("data/app-lib")) {
						boolean dataDelete = CmdUtil.clearDataFile(path);
						if (dataDelete)
							return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteFileDir(String path, String packageName) {
		try {
			File file = new File(path);
			if (file.exists()) {
				if (MyfileUtil.isExternalStorageFile(path)) {
					return deleteFileDirectory(file, packageName);
					// boolean result = file.delete();
					// if (result) return true;
				} else {
					if (!path.contains("data/data/" + packageName)
							&& !path.contains("data/app/" + packageName)
							&& !path.contains("data/user/0/" + packageName)
							&& !path.contains("data/app-lib")) {
						return CmdUtil.clearDataFile(path);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private static boolean deleteFileDirectory(File file, String packageName) {
		String path = file.getAbsolutePath();
		Logger.i("path-----------" + path + "  "
				+ Environment.getExternalStorageDirectory().getAbsolutePath());
		if (path.equals("/storage/sdcard1") || path.equals("storage/sdcard1")
				|| path.equals("/storage/sdcard1/")
				|| path.equals("storage/sdcard1/")) {
			return false;
		}
		if (path.equals("/storage/emulated/0")
				|| path.equals("storage/emulated/0/")
				|| path.equals("storage/emulated/0")
				|| path.equals("/storage/emulated/0/")) {
			return false;
		}
		if (path.equals("/storage/emulated/0/Android")
				|| path.equals("storage/emulated/0/Android/")
				|| path.equals("storage/emulated/0/Android")
				|| path.equals("/storage/emulated/0/Android/")) {
			return false;
		}
		if (path.equals("/storage/sdcard1/Android")
				|| path.equals("storage/sdcard1/Android/")
				|| path.equals("storage/sdcard1/Android")
				|| path.equals("/storage/sdcard1/0/Android/")) {
			return false;
		}
		if (path.contains("MIUI")
				|| (path.contains("Android/data") && !path
						.contains(packageName)) || path.contains("Xiaomi")) {
			file.delete();
			return false;
		}
		if (path.contains(ConstantsConfig.EXTRA_PATH)
				|| path.contains("xx/crash")
				|| path.contains("/storage/emulated/0/xx")) {
			return false;
		}
		if (path.contains("storage/emulated/0/./xx/file")
				|| path.contains("storage/emulated/0/./xx")) {
			return false;
		}
		if (path.contains("storage/sdcard0/XX")
				|| path.contains("storage/sdcard0/XX/file")
				|| path.contains("/XX/file")) {
			return false;
		}
		if (path.contains(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/xx")) {
			return false;
		}

		if (file.isDirectory()) {
			Logger.f2("file::" + file.getAbsolutePath());
			File[] files = file.listFiles();
			if (files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					deleteFileDirectory(files[i], packageName);
				}
				file.delete();
			} else {
				boolean res = file.delete();
				Logger.i("--Path----" + file.getAbsolutePath() + "  " + res);
			}
		} else {
			return file.delete();
		}
		return false;
	}

	/**
	 * b
	 */
	static long mSize = -1;

	public static long getDataSize2(Context context, String packageName) {
		mSize = -1;
		GetPackageSizeUtil packageSizeUtil = new GetPackageSizeUtil(context);
		packageSizeUtil.setListener(new SizeResponseListener() {

			@Override
			public void response(Object size) {
				long double1 = (long) size;
				;
				mSize = double1;
			}
		});
		packageSizeUtil.getpkginfo(packageName);
		while (mSize == -1) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return mSize;
	}

	/**
	 * @param packageName
	 * @return
	 */
	public static double getDataSize(String packageName) {
		String path = "data/data/" + packageName;
		return getFileLoaderSize(path) / 1024;
	}

	public static long getFileLoaderSize(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				long size = 0;
				String[] paths = CmdUtil.lsDataDir(path);
				if (paths != null) {
					for (int i = 0; i < paths.length; i++) {
						size += getFileLoaderSize(paths[i]);
					}
					return size;
				}
			} else {
				long size = file.length();
				return size; // KB
			}
		}
		return 0;
	}

	public static void deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	public static boolean copyFile2Other(String sourceP, String destinationP) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			File sourceFile = new File(sourceP);
			if (!sourceFile.exists()) {
				return false;
			}
			File desFile = new File(destinationP);
			if (!desFile.exists()) {
				desFile.getParentFile().mkdirs();
				desFile.createNewFile();
			}
			fis = new FileInputStream(sourceFile);
			fos = new FileOutputStream(desFile);
			int tmp = -1;
			while ((tmp = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, tmp);
			}
			fos.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
					fis = null;
				}
				if (fos != null) {
					fos.close();
					fos = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;

	}

	public static byte[] buffer = new byte[1024];

	public static boolean copyDownApk2Backups(String source, String desPath) {
		return copyFile2Other(source, desPath);
	}

	public static String getBackUpPath(String packageName, String channel) {
		return ConstantsHookConfig.APK_LOCAL_BACKUP_PATH + "/" + packageName
				+ "_" + channel + ".apk";
	}

	public static boolean copyLua2XXScriptFile(String source,
			String desFileName, boolean replace) {
		File file = new File(source);
		if (!file.exists()) {
			return false;
		}
		String desPath = ConstantsHookConfig.SCRIPT_FILE + desFileName;
		File des = new File(desPath);
		checkDir(des.getParentFile());
		if (des.exists() && !replace) {
			return false;
		}
		return copyFile2Other(source, desPath);
	}

	public static void recordAddApk(String packageName) {
		File file = new File(ConstantsHookConfig.PATH_RECORD_APK_INSTALL);
		appendStringToFile(packageName, file);
	}

	public static void appendStringToFile(String string, File file) {
		RandomAccessFile accessFile = null;
		try {
			checkDir(file.getParentFile());
			if (!file.exists()) {
				file.createNewFile();
			}
			accessFile = new RandomAccessFile(file.getAbsolutePath(), "rw");
			accessFile.seek(accessFile.length());
			accessFile.writeBytes(string + "\n");
		} catch (Exception e) {
			e.printStackTrace();
			Logger.h("record===ex=======" + e);
		} finally {
			try {
				if (accessFile != null) {
					accessFile.close();
					accessFile = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean writeResponseBodyToDisk(String channel,
			String packageName, File downFile, ResponseBody body) {
		Logger.i("==========" + downFile + "  " + downFile.exists());
		FileOutputStream fileOutputStream = null;
		BufferedInputStream inputStream = null;
		try {
			if (!downFile.exists())
				downFile.createNewFile();
			fileOutputStream = new FileOutputStream(downFile);
			inputStream = new BufferedInputStream(body.byteStream());
			byte[] tmp = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(tmp)) != -1) {
				fileOutputStream.write(tmp, 0, len);
			}
			fileOutputStream.flush();
			String soucrePath = downFile.getAbsolutePath();
			if (soucrePath.contains(ConstantsHookConfig.APK_LOCAL_PATH)) {
				MyfileUtil.copyDownApk2Backups(soucrePath,
						MyfileUtil.getBackUpPath(packageName, channel));
			}

			Logger.i("==========" + downFile + "  " + fileOutputStream);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
					fileOutputStream = null;
				}
				if (inputStream != null) {
					// inputStream.close();
					// inputStream = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 从 文件 逐行读取
	 * 
	 * @param path
	 * @return
	 */
	public static String readFileFromSDCard(String path) {
		File file = new File(path);
		checkDir(file.getParentFile());
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static Set<String> getSetFromFile(String path) {
		Set<String> set = new HashSet<String>();
		File file = new File(path);
		checkDir(file.getParentFile());
		BufferedReader reader = null;
		if (file.exists()) {
			try {
				reader = new BufferedReader(new FileReader(file));
				String tmp = null;
				while ((tmp = reader.readLine()) != null) {
					set.add(tmp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (reader != null) {
						reader.close();
						reader = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return set;

	}

	public final int vpnConnectErr = 0;
	public final int paramuploadErr = 1;
	static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd-hh-mm-ss");

	public void recordLog(int reason, String packageName) {
		File file = new File(ConstantsHookConfig.PATH_RECORD_ERR_LOG);
		checkDir();
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			String time = dateFormat
					.format(new Date(System.currentTimeMillis()));
			String reasons = reason == vpnConnectErr ? "___VPN   "
					: "___PARAM ";
			String record = time + ":" + reasons + "   :" + packageName;
			appendStringToFile(record, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void recordUploadLog(String err, String packageName) {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		String data = dateFormat1.format(System.currentTimeMillis());
		File file = new File(ConstantsHookConfig.PATH_RECORD_UPLOAD_ERR_LOG
				+ "" + data + ".txt");
		checkDir(file.getParentFile());
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			String time = dateFormat
					.format(new Date(System.currentTimeMillis()));
			String record = time + ":" + packageName + "   :" + err;
			appendStringToFile(record, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void recordUploadERRLog(String device) {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		String data = dateFormat1.format(System.currentTimeMillis());
		File file = new File(ConstantsHookConfig.PATH_RECORD_UPLOAD_ERR_LOG2
				+ "" + data + ".txt");
		Logger.i("========================recordUploadERRLog::" + file);
		checkDir(file.getParentFile());
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			String record = device;
			appendStringToFile(record, file);
		} catch (IOException e) {
			Logger.i("recordUploadERRLog:=======e:" + e);
			e.printStackTrace();
		}
	}

	public static void recodeSystemANR(String packageName) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd-HH-mm-ss");
		String data = dateFormat.format(System.currentTimeMillis());
		File file = new File(ConstantsHookConfig.PATH_ANR_ERR_LOG);
		checkDir(file.getParentFile());
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			String recString = data + "  : " + packageName;
			appendStringToFile(recString, file);
		} catch (Exception e) {
			Logger.v("eeee" + e);
			e.printStackTrace();
		}
	}

	/**
	 * 清除所有脚本文件
	 */
	public static void clearAllScriptFile() {
		File scriptDir = new File(ConstantsHookConfig.SCRIPT_LOCAL_PATH);
		deleteFileDir(scriptDir);
	}

	public static void clearAllApkFile() {
		File dir = new File(ConstantsHookConfig.APK_LOCAL_PATH);
		deleteFileDir(dir);
	}

	public static void clearAllBackupApkFile() {
		File dir = new File(ConstantsHookConfig.APK_LOCAL_BACKUP_PATH);
		deleteFileDir(dir);
	}

	/**
	 * 获取过期时间
	 * 
	 * @return 天
	 */
	public static int getDeadlineDay() {
		return SPrefHookUtil.getSettingInt(BaseApplication.getContextObject(),
				SPrefHookUtil.KEY_SETTING_DEAD_DAY,
				SPrefHookUtil.D_SETTING_DEAD_DAY);
	}

	/**
	 * 删除超过?天的脚本
	 */
	public static void clearDeatLineScriptFile() {
		File dir = new File(ConstantsHookConfig.SCRIPT_LOCAL_PATH);
		deleteDeadlineFile(dir);
	}

	/**
	 * 删除超过?天的apk
	 */
	public static void clearDeatLineApkFile() {
		File dir = new File(ConstantsHookConfig.APK_LOCAL_PATH);
		deleteDeadlineFile(dir);
	}

	/**
	 * 删除超过?天的backup apk
	 */
	public static void clearDeatLineBackupApkFile() {
		File dir = new File(ConstantsHookConfig.APK_LOCAL_BACKUP_PATH);
		deleteDeadlineFile(dir);
	}

	/**
	 * 删除所有日志文件
	 */
	public static void clearAllLogFile() {
		File updateLogDir = new File(
				ConstantsHookConfig.PATH_RECORD_UPLOAD_ERR_LOG);
		File updateErrLogDir = new File(
				ConstantsHookConfig.PATH_RECORD_UPLOAD_ERR_LOG2);
		File errLog = new File(ConstantsHookConfig.PATH_RECORD_ERR_LOG);
		File crashLog = new File(ConstantsHookConfig.PATH_RECORD_CRASH_LOG);
		deleteFileDir(crashLog);
		deleteFileDir(updateLogDir);
		deleteFileDir(updateErrLogDir);
		deleteFileDir(errLog);

	}

	/**
	 * 删除所有日志文件
	 */
	public static void clearDeadlineLogFile() {
		File updateLogDir = new File(
				ConstantsHookConfig.PATH_RECORD_UPLOAD_ERR_LOG);
		File updateErrLogDir = new File(
				ConstantsHookConfig.PATH_RECORD_UPLOAD_ERR_LOG2);
		File errLog = new File(ConstantsHookConfig.PATH_RECORD_ERR_LOG);
		File crashLog = new File(ConstantsHookConfig.PATH_RECORD_CRASH_LOG);
		deleteDeadlineFile(crashLog);
		deleteDeadlineFile(updateLogDir);
		deleteDeadlineFile(updateErrLogDir);
		deleteDeadlineFile(errLog);

	}

	/**
	 * 逐級刪除File
	 * 
	 * @param dir
	 */
	public static void deleteFileDir(File dir) {
		Logger.i("dir:" + dir + " " + dir.isDirectory());
		if (dir.exists()) {
			if (dir.isDirectory()) {
				File[] files = dir.listFiles();
				if (files != null && files.length > 0) {
					for (File file : files) {
						deleteFileDir(file);
					}
				}
			} else {
				boolean res = dir.delete();
				Logger.i("dir:" + dir + "  res " + res);
			}
		}
	}

	/**
	 * 删除过期文件
	 * 
	 * @param dir
	 */
	public static void deleteDeadlineFile(File dir) {
		if (dir.exists()) {
			if (dir.isDirectory()) {
				File[] files = dir.listFiles();
				if (files != null && files.length > 0) {
					for (File file : files) {
						deleteDeadlineFile(file);
					}
				}
			} else {
				if (dir.lastModified() < System.currentTimeMillis()
						- CommonTimeUtil.ONE_DAY * getDeadlineDay()) {
					Logger.i("clear:" + dir + " " + dir.lastModified());
					dir.delete();
				}
			}
		}
	}

	private static final int BUFFER_SIZE = 1024; 
	public static void copyAssetFileToSd(Context context, String assetName,
			String dest) {
		File file = new File(dest);
		checkDir(file.getParentFile());
		InputStream is = null;
		FileOutputStream fos = null;
//		FileChannel input = null;
//		FileChannel output = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
				is = context.getAssets().open(assetName);
				fos = new FileOutputStream(file);
				byte[] buffer = new byte[BUFFER_SIZE];
				int tmp;
				while((tmp = is.read(buffer))!=-1){
					
					fos.write(buffer, 0, tmp);
				}
				fos.flush();
//				fis = new FileInputStream(context.getAssets().openFd("build_id.txt")
//						.getFileDescriptor());
//				fos = new FileOutputStream(file);
//				input = fis.getChannel();
//				output = fos.getChannel();
//				// input.transferTo(0, input.size(), output);
//				long size = input.size();
//				long pos = 0;
//				long count = 0;
//				while (pos < size) {
//					count = size - pos > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE
//							: size - pos;
//					pos += output.transferFrom(input, pos, count);
//				}
////			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			UtilsIO.closeAllQuietly(is, fos);
		}
	}
	public static void copyRawFileToSd(Context context, int id,
			String dest) {
		File file = new File(dest);
		checkDir(file.getParentFile());
		InputStream is = null;
		FileOutputStream fos = null;
//		FileChannel input = null;
//		FileChannel output = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
				is = context.getResources().openRawResource(id);
				fos = new FileOutputStream(file);
				byte[] buffer = new byte[BUFFER_SIZE];
				int tmp;
				while((tmp = is.read(buffer))!=-1){
					
					fos.write(buffer, 0, tmp);
				}
				fos.flush();
//				fis = new FileInputStream(context.getAssets().openFd("build_id.txt")
//						.getFileDescriptor());
//				fos = new FileOutputStream(file);
//				input = fis.getChannel();
//				output = fos.getChannel();
//				// input.transferTo(0, input.size(), output);
//				long size = input.size();
//				long pos = 0;
//				long count = 0;
//				while (pos < size) {
//					count = size - pos > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE
//							: size - pos;
//					pos += output.transferFrom(input, pos, count);
//				}
////			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			UtilsIO.closeAllQuietly(is, fos);
		}
	}
	
	public static boolean isMd5Same(Context context,String desFileName,int sourceId){
		InputStream in = null;
		try {
			File dir=checkDir();
			File desFile = new File(dir,desFileName);
			in = context.getResources().openRawResource(sourceId);
			String md5a = MD5Util.md5sumIs(in);
			Logger.i(desFileName+":**********MD5a:::"+md5a+"  sourceId"+sourceId);
			if(desFile.exists()){
				String md5b = MD5Util.md5sum(desFile.getAbsolutePath());
				Logger.i("MD5bb  "+"  "+md5b);
				if(md5a.equals(md5b)){
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			try {
				if(in!=null) in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getWJApkFilePath(){
		String dirpath = "/data/app/";
		String[] fileNames = CmdUtil.lsDataDir(dirpath);
		Logger.i("ls -al fileNames "+fileNames);
		if(fileNames!=null){
			for(String name1:fileNames){
				if(name1.contains(ConstantsHookConfig.PAC_VPN)){
					Logger.i("ls -al name1 "+name1);
					return name1;
				}
			}
		}
		
		return "";
	}
	public static String getApkFileName2(){
		String dirpath = "/data/app/";
		String[] strs = CmdUtil.lsLDataDir(dirpath);
		
		for(String str:strs){
			if(str.contains(ConstantsHookConfig.PAC_VPN)){
				Logger.i("ls -al "+str);
				String[] details = str.split("[ ]+",7);
				Logger.i("ls -al 0:"+details[0]+" 1: "+details[1]+" 2:"+details[2]+" 3:"+details[3]+"");
				String size = details[3];
				return size;
			}
		}
		return "";
	}
}
