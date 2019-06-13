package com.example.dss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    EditText regId, regPwd, regRePwd, regNickname, regEmail1, regEmail2, regBirth;
    String id, pwd, repwd, nickname, email, birth;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup);
    }


    public void register(View view){
        regId =  (EditText)findViewById(R.id.regId);
        regPwd =  (EditText)findViewById(R.id.regPwd);
        regRePwd =  (EditText)findViewById(R.id.regRePwd);
        regNickname =  (EditText)findViewById(R.id.regNickname);
        regEmail1 =  (EditText)findViewById(R.id.regEmail1);
        regEmail2 =  (EditText)findViewById(R.id.regEmail2);
        regBirth =  (EditText)findViewById(R.id.regBirth);

        id = regId.getText().toString();
        pwd = regPwd.getText().toString();
        repwd = regRePwd.getText().toString();
        nickname = regNickname.getText().toString();
        email = regEmail1.getText().toString()+"@"+regEmail2.getText().toString();
        birth = regBirth.getText().toString();

        if(!pwd.equals(repwd)){
            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        AQuery aq = new AQuery(this);
        User user = new User(id, pwd, nickname, email, birth);
        HashMap<String, String> params = new HashMap<>();
        params.put("id",id);
        params.put("password",pwd);
        params.put("nickname",nickname);
        params.put("email",email);
        params.put("birth",birth);

        String url = "http://211.239.124.237:19613/user";
        url = addParams(url, params);
        aq.ajax(url, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String resutl, AjaxStatus status) {
                Log.i ("url",url);
                if (resutl != null) {
                    //sucess
                    Intent outIntent = new Intent(getApplicationContext(), MainActivity.class);
                    setResult(RESULT_OK, outIntent);
                    finish();
                } else {
                    //fail
                    Toast.makeText(getApplicationContext(), "회원가입에 문제가 생겼습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }.timeout(20000));
    }

    public void cancel(View view){
        Intent outIntent = new Intent(getApplicationContext(), MainActivity.class);
        setResult(RESULT_CANCELED, outIntent);
        finish();
    }

    private String addParams(String url, HashMap<String, String> mapParam) {
        StringBuilder stringBuilder = new StringBuilder(url+"?");

        if(mapParam != null){
            for ( String key : mapParam.keySet() ) {
                stringBuilder.append(key+"=");
                stringBuilder.append(mapParam.get(key)+"&");
            }
        }
        return stringBuilder.toString();
    }
}