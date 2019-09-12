package com.jarhero790.eub.message.souye;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.jarhero790.eub.R;
import com.jarhero790.eub.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusinessWebTwoActivity extends AppCompatActivity {


    private BridgeWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_web_two);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);

        mWebView = findViewById(R.id.webview);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String good_id = intent.getStringExtra("good_id");
        Log.e("--------", "url=" + url);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setCacheMode(webSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDisplayZoomControls(true);
        webSettings.setDefaultFontSize(12);

        webSettings.setAllowFileAccess(true);
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDomStorageEnabled(true);//开启本地DOM存储
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        mWebView.loadUrl(url);

        mWebView.registerHandler("goBackToVideo", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
//                Toast.makeText(BusinessWebTwoActivity.this, "goBackToVideo--->，" + data, Toast.LENGTH_SHORT).show();
//                function.onCallBack("测试blog");

                finish();
                Log.e("------------我的goodid=", good_id + "    返回的= " + data);
            }
        });

        mWebView.registerHandler("takePhone", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
//                Toast.makeText(BusinessWebTwoActivity.this, "takePhone--->，"+ data, Toast.LENGTH_SHORT).show();
//                function.onCallBack("测试takePhone");
                CommonUtil.callphone(BusinessWebTwoActivity.this, data);
            }
        });

    }


    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (webView.canGoBack()) {
//                webView.goBack();
//                return true;
//            } else {
//                finish();
//                return true;
//            }
//        }
//        return false;
//    }
//
//
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        }
    }
}
