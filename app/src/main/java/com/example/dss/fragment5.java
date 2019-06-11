package com.example.dss;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class fragment5 extends AppCompatActivity  {

    String ingredient_detail;
    String validity;
    String manufacturing;
    String usage;
    String name;
    String company_name;
    String big_image;
    private  WebView mwv;
    ImageView imView;
    Bitmap bmImg;
    back task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment4);

        Intent Intent = getIntent();
        ingredient_detail = Intent.getExtras().getString("ingredient_detail");
        validity = Intent.getExtras().getString("validity");
        manufacturing = Intent.getExtras().getString("manufacturing");
        usage = Intent.getExtras().getString("usage");
        name = Intent.getExtras().getString("name");
        company_name = Intent.getExtras().getString("company_name");
        big_image = Intent.getExtras().getString("big_image");

      //  mwv=(WebView)findViewById(R.id.WebView);
      //  WebSettings mws=mwv.getSettings();//Mobile Web Setting
       // mws.setJavaScriptEnabled(true);//자바스크립트 허용
        //mws.setLoadWithOverviewMode(true);//컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
       // mwv.loadUrl("http://211.239.124.237:19609/resources/big_image/1MfSEl6hMOG.jpg");

        TextView textView = (TextView) findViewById(R.id.View1);
        TextView textView2 = (TextView) findViewById(R.id.View2);
        TextView textView3 = (TextView) findViewById(R.id.View3);
        TextView textView4 = (TextView) findViewById(R.id.View4);
        TextView textView5 = (TextView) findViewById(R.id.View5);

        textView.setText(ingredient_detail);
        textView2.setText(validity);
        textView3.setText(manufacturing);
        textView4.setText(name);
        textView5.setText(company_name);
        imView = (ImageView) findViewById(R.id.imageView1);

        task = new back();
        task.execute(big_image);

    }
    private class back extends AsyncTask<String, Integer,Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            // TODO Auto-generated method stub
            try{
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bmImg = BitmapFactory.decodeStream(is);

            }catch(IOException e){
                e.printStackTrace();
            }
            return bmImg;
        }
        protected void onPostExecute(Bitmap img){
            imView.setImageBitmap(bmImg);
        }
    }

}