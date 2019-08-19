package com.jarhero790.eub.message.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xie_yi);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        webView.setWebViewClient(new WebViewClient());

        webView.getSettings().setJavaScriptEnabled(true);


        webView.loadUrl("http://120.79.222.191/zstv/public/index.php/user/index/index.html");

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
