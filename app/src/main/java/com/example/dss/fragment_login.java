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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class fragment_login extends Fragment {

    EditText loginId, loginPwd;
    String id, pwd;
    Button login, signUp;


    //Retrofit 생성
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://3.18.0.46:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Retrofit2Service retrofit2Service = retrofit.create(Retrofit2Service.class);

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


                retrofit2Service.userCheck(id,pwd)
                        .enqueue(new Callback<Retrofit2UserModel>() {
                            @Override
                            public void onResponse(Call<Retrofit2UserModel> call, Response<Retrofit2UserModel> response) {
                                Log.i("onResponse",response.message());
                                Log.i("pase",response.toString());


                                Retrofit2UserModel resulut = response.body();

                                Log.i("plase", resulut.getUser_no().toString());
                                Log.i("plase", resulut.getPassword());
                                Log.i("plase", resulut.getEmail());

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.putExtra("status", "login");
                                intent.putExtra("id", resulut.getUser_no().toString());
                                intent.putExtra("nickname", resulut.getEmail());

                                startActivity(intent);
                                getActivity().finish();
                            }

                            @Override
                            public void onFailure(Call<Retrofit2UserModel> call, Throwable t) {
                                Log.i("onFailure",t.getMessage());
                                Toast.makeText(getActivity(), "아이디 혹은 비밀번호가 잘못 입력됐습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });

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