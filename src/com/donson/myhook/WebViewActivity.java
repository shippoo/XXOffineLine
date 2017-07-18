package com.donson.myhook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.donson.config.Logger;
import com.donson.xxxiugaiqi.R;
import com.mz.annotation.ContentViewInject;
import com.mz.annotation.InjectUtils;
import com.mz.annotation.ViewInject;

@ContentViewInject(R.layout.activity_webview)
public class WebViewActivity extends BaseActivity {
	@ViewInject(R.id.webview_aw)
	WebView webView;
	String url = "http://www.aiwan.hk";
	private Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		InjectUtils.injectAll(this);
		WebSettings setting = webView.getSettings();
		setting.setJavaScriptEnabled(true);
		webView.requestFocus();
		webView.setWebViewClient(new ExampleWebViewClient());
		webView.loadUrl(/* "http://www.51cto.com/" */"http://www.aiwan.hk");
//		webView.loadUrl("file:///android_asset/test.html");
//		String s = "(function() {" +
//				"var s = \"_\" + Math.random().toString(36).slice(2);" +
//				"document.write('<div style=\"width:200px;height:200px;background:#ff0000;\" id=\"' + s + '\"></div>');" +
//				"(window.slotbydup=window.slotbydup || [])" +
//				".push({id: '3386142',container: s,size:{w:20,h:10},display: 'float'});" +
//				"})();";
//		webView.loadUrl("javascript:"+s);

	}
	
	@SuppressWarnings("unused")
	private class ExampleWebViewClient extends WebViewClient{
		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler,
				SslError error) {
			// TODO Auto-generated method stub
			super.onReceivedSslError(view, handler, error);
		}
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			System.out.println("url.index::::::::"+url+"    "+url.indexOf("http://www.aiwan.hk"));
//			if (url.indexOf("http://www.aiwan.hk") != -1) {  
//		        // 调用系统默认浏览器处理url  
//		        view.stopLoading();  
//		        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));  
//		        return true;  
//		    }  
		    return false;  
		}
		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
		}
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
		}
		@Override
		public void onLoadResource(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onLoadResource(view, url);
		}
	}
	@Override
	public void onBackPressed() {
//		super.onBackPressed();
		if(webView.canGoBack()){
			webView.goBack();
		}else {
			super.onBackPressed();
		}
	}
	@Override
	public void initRect() {
		// TODO Auto-generated method stub
		
	}
	
	/*public void webView(){
		WebSettings setting = webView.getSettings();
		setting.setJavaScriptEnabled(true);
//		setting.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
//		setting.setLoadWithOverviewMode(true);
		webView.requestFocus();
		// 缩放
//		setting.setBuiltInZoomControls(true);
//		setting.setSupportZoom(true);
		// webView.setScrollBarStyle(0);
		// setting.setDefaultTextEncodingName("GBK");
//		webView.setWebViewClient(new WebViewClient() {
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				return false;
//			}
//		});
		webView.loadUrl( "http://www.51cto.com/" "http://www.aiwan.hk");

		// WebSettings s = webView.getSettings();
		// // s.setBuiltInZoomControls(true);
		// // s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		// // s.setUseWideViewPort(true);
		// // s.setLoadWithOverviewMode(true);
		// // s.setSavePassword(true);
		// // s.setSaveFormData(true);
		// s.setJavaScriptEnabled(true); // enable navigator.geolocation
		// // s.setGeolocationEnabled(true);
		// //
		// s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
		// // // enable Web Storage: localStorage, sessionStorage
		// // s.setDomStorageEnabled(true);
		// // webView.requestFocus();
		// // webView.setScrollBarStyle(0);
		// webView.loadUrl(url);
		// webView.setWebViewClient(new WebViewClient(){
		// @Override
		// public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// view.loadUrl(url);
		// return true;
		// }
		// });
		// webView.setWebViewClient(new WebViewClient(){
		// });
		// http://www.51cto.com/
		// webView.loadUrl("http://www.aiwan.hk");
	}*/
}
