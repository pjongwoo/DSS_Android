package com.example.dss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class fragment5 extends AppCompatActivity  {

    String ingredient_detail;
    String validity;
    String manufacturing;
    String usage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment4);

        Intent Intent = getIntent();
        ingredient_detail = Intent.getExtras().getString("ingredient_detail");
        validity = Intent.getExtras().getString("validity");
        manufacturing = Intent.getExtras().getString("manufacturing");
        usage = Intent.getExtras().getString("usage");

        TextView textView = (TextView) findViewById(R.id.View1);
        TextView textView2 = (TextView) findViewById(R.id.View2);
        TextView textView3 = (TextView) findViewById(R.id.View3);

        textView.setText(ingredient_detail);
        textView2.setText(validity);
        textView3.setText(manufacturing);

    }


}