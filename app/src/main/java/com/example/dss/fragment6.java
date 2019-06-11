package com.example.dss;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class fragment6 extends AppCompatActivity {
    private WebView mWebView;
    private String myUrl = "http://www.amc.seoul.kr/asan/healthstory/medicalcolumn/medicalColumnList.do";
    private  WebView mwv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        // 웹뷰 셋팅
         mwv=(WebView)findViewById(R.id.webView);
          WebSettings mws=mwv.getSettings();//Mobile Web Setting
         mws.setJavaScriptEnabled(true);//자바스크립트 허용
         mws.setLoadWithOverviewMode(true);//컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
         mwv.loadUrl(myUrl);
    }
}
