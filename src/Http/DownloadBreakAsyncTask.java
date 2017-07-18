package Http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.greenrobot.eventbus.EventBus;

import com.donson.config.Logger;
import com.donson.utils.ActivityUtil;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.ProgressBar;

/**
 *先下载到本地备份，再复制到file文件夹中
 * @author Administrator
 *
 */
public class DownloadBreakAsyncTask extends AsyncTask<String, Integer, String> {
	private static final int CONNECTTIME = 40 * 1000;
	private static final String SUCCEED = "SUCCEED";
	String mUrl;
	String mPath;

	public DownloadBreakAsyncTask(String url, String path) {
		mUrl = url;
		mPath = path;
	}

	@SuppressWarnings("resource")
	@Override
	protected String doInBackground(String... params) {
		if (TextUtils.isEmpty(mUrl)) {
			EventBus.getDefault().post(new ProgressEvent(-1, -1));
			return "the url is null";
		}
		String backup = params[0];
//		File backUp = null;
		if (TextUtils.isEmpty(backup)) {
			EventBus.getDefault().post(new ProgressEvent(-1, -1));
			return "PATH null";
		}
		
		InputStream is = null;
//		OutputStream output = null;//文件输出
		OutputStream backOs = null;//备份输出
		RandomAccessFile raFile = null;
		int total = 0;
		try{
			URL url = new URL(mUrl);
			Logger.i("DOWNLOAD", "DOWNLOAD-----------mUrl--:" + mUrl);
			File backUp = new File(backup);
			File dir1 = backUp.getParentFile();
			if (!dir1.exists()) 
				dir1.mkdirs();
			if (!backUp.exists()) {
				backUp.createNewFile();
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(CONNECTTIME);
				connection.setReadTimeout(CONNECTTIME);
				connection.setRequestMethod("GET");
				Logger.i("DOWNLOAD","DOWNLOAD-----------CODE--:head "+connection.getHeaderFields());
				Logger.i("DOWNLOAD","DOWNLOAD-----------CODE--:" + connection.getResponseCode()+"  "+connection.getContentLength());
				if (connection.getResponseCode() == 200) {
					total = connection.getContentLength();//获取总文件大小
					is = connection.getInputStream();
					int tmp = -1;
					int value = 0;
					byte[] buff = new byte[1024*10];
					backOs = new FileOutputStream(backUp);
					while ((tmp = is.read(buff)) != -1) {
						if (isCancelled()) {
							is.close();
							EventBus.getDefault().post(new ProgressEvent(-1, -1));
							return "取消下载";
						}
						backOs.write(buff, 0, tmp);
						value += tmp;
						publishProgress(value, total);
					}
					backOs.flush();
					return SUCCEED;
				} else {
					EventBus.getDefault().post(new ProgressEvent(-1, -1));
					return "response code is:" + connection.getResponseCode();
				}
			} else {
				long readedSize = backUp.length();
				Logger.i("DOWNLOAD", "DOWNLOAD-----------mUrl--:" + mUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(CONNECTTIME);
				connection.setReadTimeout(CONNECTTIME);
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Range", "bytes="+readedSize+"-");
				int code = connection.getResponseCode();
				Logger.i("DOWNLOAD", "DOWNLOAD---------readedSize-"+readedSize+"-code--:" + code+"  "+connection.getContentLength()+"  total:"+total+" head： "+connection.getHeaderFields());
				if (code == 206) {
					int si = connection.getContentLength();//获取总文件大小
					if(si<=0){
						backUp.delete();
					}
					total = (int)readedSize+si;//获取总文件大小
					is = connection.getInputStream();
					raFile = new RandomAccessFile(backUp, "rw");
					raFile.seek(readedSize);
					byte[] buf = new byte[1024*10];
					int inputSize = -1;
					int count = (int) readedSize;
					while((inputSize = is.read(buf))!=-1){
						if (isCancelled()) {
							is.close();
							EventBus.getDefault().post(new ProgressEvent(-1, -1));
							return "取消下载";
						}
						raFile.write(buf, 0, inputSize);
						count+=inputSize;
						publishProgress(count, total);
					}
					return SUCCEED;
				}else if (code == 200) {
					backUp.delete();
					return "ERR";
				}else {
					EventBus.getDefault().post(new ProgressEvent(-1, -1));
					return "response code is:" + connection.getResponseCode();
				}
			}
		}catch(Exception e){
			Logger.i("DOWNLOAD", "DOWNLOAD----------e---:" + e);
			e.printStackTrace();
			EventBus.getDefault().post(new ProgressEvent(-1, -1));
			return e.toString();
		} finally {
			try {
				if (is != null)
					is.close();
				if (raFile != null)
					raFile.close();
			} catch (IOException e) {
				EventBus.getDefault().post(new ProgressEvent(-1, -1));
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (callback != null) {
			if (result.equals(SUCCEED)) {
				callback.onSucceed();
			} else {
				callback.onFailure(result);
			}
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		int value = values[0];
		int total = values[1];
		EventBus.getDefault().post(
				new ProgressEvent(value / 1024, total / 1024));
		Logger.i("DOWNLOAD", "DOWNLOAD-----------value--:" + value + "  total:"
				+ total + "  " + ((float) value * 100 / total) / 100);
		System.out.println("value:" + value + "  total:" + total + "  "
				+ ((float) value * 100 / total) / 100);
	}

	public void setCallback(DownCallback callback) {
		this.callback = callback;
	}

	DownCallback callback = null;

	public interface DownCallback {
		void onSucceed();

		void onFailure(String err);
	}

}
