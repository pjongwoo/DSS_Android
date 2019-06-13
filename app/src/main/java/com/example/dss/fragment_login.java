package com.example.dss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

public class fragment_login extends Fragment {

    EditText loginId, loginPwd;
    String id, pwd;
    Button login, signUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, null);

        loginId =  (EditText)view.findViewById(R.id.loginId);
        loginPwd =  (EditText)view.findViewById(R.id.loginPwd);
        login = (Button)view.findViewById(R.id.loginBtn1);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                id = loginId.getText().toString();
                pwd = loginPwd.getText().toString();


                AQuery aq = new AQuery(getActivity());

                String url = "http://211.239.124.237:19613/user/userCheck/"+id+"/"+pwd;

                aq.ajax(url, String.class, new AjaxCallback<String>() {
                    @Override
                    public void callback(String url, String result, AjaxStatus status) {
                        Log.i ("url",url);
                        if (result != null) {
                            //sucess
                            Log.i("test", result.toString());
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.putExtra("status", "login");
                                intent.putExtra("id", (String) new JSONObject(result).get("id"));
                                intent.putExtra("nickname", (String) new JSONObject(result).get("nickname"));

                                startActivity(intent);
                                getActivity().finish();
                            }
                            catch (Exception e){
                                Toast.makeText(getActivity(), "아이디 혹은 비밀번호가 잘못 입력됐습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            //fail
                            Toast.makeText(getActivity(), "예기치 못한 오류입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.timeout(20000));
            }
        });

        signUp = (Button)view.findViewById(R.id.loginBtn2);
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}