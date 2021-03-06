package net.dxs.wxhb.activities;

import android.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebViewActivity extends Activity {
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loadUI();

		Bundle bundle = getIntent().getExtras();
		if (bundle != null && !bundle.isEmpty()) {
			String webViewTitle = bundle.getString("title");
			String webViewUrl = bundle.getString("url");

			TextView webViewBar = (TextView) findViewById(R.id.webview_bar);
			webViewBar.setText(webViewTitle);

			webView = (WebView) findViewById(R.id.webView);
			webView.getSettings().setBuiltInZoomControls(false);
			webView.getSettings().setJavaScriptEnabled(true);
			webView.getSettings().setDomStorageEnabled(true);
			webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
			webView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return false;
				}

				@Override
				public void onPageFinished(WebView view, String url) {
					CookieSyncManager.getInstance().sync();
				}
			});
			webView.loadUrl(webViewUrl);
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void loadUI() {
		setContentView(R.layout.activity_webview);

		if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
			return;

		Window window = this.getWindow();

		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

		window.setStatusBarColor(0xffd84e43);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void performBack(View view) {
		super.onBackPressed();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				if (webView.canGoBack()) {
					webView.goBack();
				} else {
					finish();
				}
				return true;
			}

		}
		return super.onKeyDown(keyCode, event);
	}
}
