package com.jarhero790.eub.message.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.jarhero790.eub.R;
import com.jarhero790.eub.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XieYiActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.rc_webview)
    WebView webView;
    @BindView(R.id.center_title)
    TextView centerTitle;

    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xie_yi);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        webView.getSettings().setJavaScriptEnabled(true);
        centerTitle.setText(getIntent().getStringExtra("name"));
        url=getIntent().getStringExtra("url");
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(settings.LOAD_CACHE_ELSE_NETWORK);
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDisplayZoomControls(true);
        settings.setDefaultFontSize(15);

        settings.setAllowFileAccess(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        webView.setWebViewClient(new WebViewClient());

//        webView.loadUrl("http://120.79.222.191/zstv/public/index.php/user/index/index.html");
//        webView.loadUrl("http://www.51ayhd.com/user/index/index.html");//下载app页面
//        webView.loadUrl("http://www.51ayhd.com/user/index/agreement.html");//下载app页面
         webView.loadUrl(url);

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
