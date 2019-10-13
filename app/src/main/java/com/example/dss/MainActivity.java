package com.example.dss;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.design.widget.NavigationView;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;
    private final int FRAGMENT3 = 3;
    private final int FRAGMENT4 = 4;
    private final int FRAGMENT5 = 5;
    private final int FRAGMENT6 = 6;

    private Button bt_tab1, bt_tab2,bt_tab3, bt_tab4, bt_tab5;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;

    private SharedPreferences appData;

    public boolean saveLoginData;
    public String id;
    public String nickname;

    //2019 10.07 최홍준: User_No ,변수 생성
    public String user_no;
    Bundle bundle;

    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 위젯에 대한 참조
        bt_tab1 = (Button)findViewById(R.id.bt_tab1);
        bt_tab2 = (Button)findViewById(R.id.bt_tab2);
        bt_tab3 = (Button)findViewById(R.id.bt_tab3);
        bt_tab4 = (Button) findViewById(R.id.bt_tab4);
        bt_tab5 = (Button) findViewById(R.id.bt_tab5);

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("IDService","device token : "+token);

        try {
            Intent intent = getIntent();
            status = intent.getExtras().getString("status");
            if(status.equals("login")) {
                String id = intent.getExtras().getString("id");
                String nickname = intent.getExtras().getString("nickname");
                //User_No 생성
                user_no = intent.getExtras().getString("id");

                bt_tab4.setVisibility(View.GONE);
                bt_tab5.setVisibility(View.VISIBLE);
                appData = getSharedPreferences("appData", MODE_PRIVATE);
                save(true, id, nickname);
                load();
                Toast.makeText(getApplicationContext(), nickname+"님 환영합니다!", Toast.LENGTH_SHORT).show();
            }else if(status.equals("logout")){
                bt_tab4.setVisibility(View.VISIBLE);
                bt_tab5.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다!", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            bt_tab4.setVisibility(View.VISIBLE);
            bt_tab5.setVisibility(View.GONE);
        }


        // 위젯에 대한 참조
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DSS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //네비게이션 메뉴 생성
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer_root);
        navigationView = (NavigationView) findViewById(R.id.nv_main_navigation_root);
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
        navigationView.setNavigationItemSelectedListener(this);

        //약 검색
        bt_tab1.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                 callFragment(FRAGMENT1);
                }
        });
        //내위치 검색
        bt_tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFragment(FRAGMENT2);
            }
        });
        //약국검색
        bt_tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFragment(FRAGMENT3);
            }
        });
        //로그인
        bt_tab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFragment(FRAGMENT4);
            }
        });
        //내정보
        bt_tab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFragment(FRAGMENT6);
            }
        });
        callFragment(FRAGMENT1);
    }

    //네비게이션 이벤트 함수
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.item1:
                callFragment(FRAGMENT1);
                break;
            case R.id.item2:
                callFragment(FRAGMENT3);
                break;
            case R.id.item3:

                Toast.makeText(this, "준비중 입니다 ..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item4:

                try {

                    if(status.equals("login")) {
                        Toast.makeText(getApplicationContext(), nickname+"님 스케줄 확인!", Toast.LENGTH_SHORT).show();
                        callFragment(FRAGMENT6);
                        break;
                    }
                }catch (Exception e){
                    callFragment(FRAGMENT4);
                    Toast.makeText(this, "로그인 후 이용 가능합니다.", Toast.LENGTH_SHORT).show();
                }


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("resultCode" + resultCode);
        if(resultCode == RESULT_OK){
            appData = getSharedPreferences("appData", MODE_PRIVATE);
            load();
            String DSS_ID = data.getStringExtra("DSS_ID");
            String nickname = data.getStringExtra("nickname");
            save(true, DSS_ID, nickname);
            Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        }else if(resultCode == RESULT_CANCELED){

        }
        //super.onActivityResult(requestCode, resultCode, data);

    }

    //프래그먼트 이동 함수
    private void callFragment(int frament_no){
        // 프래그먼트 사용
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (frament_no){
            case 1:
                // '프래그먼트1' 호출
                fragment1 fragment1 = new fragment1();
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.commit();
                break;

            case 2:
                // '프래그먼트2' 호출
                fragment2 fragment2 = new fragment2();
                transaction.replace(R.id.fragment_container, fragment2);
                transaction.commit();
                break;

            case 3:
                // '프래그먼트3' 호출
                fragment3 fragment3 = new fragment3();
                transaction.replace(R.id.fragment_container, fragment3);
                transaction.commit();
                break;

            case 4:
                // '프래그먼트 로그인' 호출
                fragment_login fragment_login = new fragment_login();
                transaction.replace(R.id.fragment_container, fragment_login);
                transaction.commit();

                break;
            case 5:
                // '프래그먼트 로그인' 호출
                fragment_mypage fragment_mypage = new fragment_mypage();
                bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("nickname", nickname);
                fragment_mypage.setArguments(bundle);
                transaction.replace(R.id.fragment_container, fragment_mypage);
                transaction.commit();

                break;

            case 6:
                // '프래그먼트 로그인' 호출
                fragment7 fragment7 = new fragment7();

                bundle = new Bundle();
                bundle.putString("User_no", user_no);
                bundle.putString("nickname", nickname);
                fragment7.setArguments(bundle);
                transaction.replace(R.id.fragment_container, fragment7);
                transaction.commit();
                break;
        }
    }

    private void save(Boolean isLogin, String id, String nickname) {
        SharedPreferences.Editor editor = appData.edit();

        editor.putBoolean("isLogin", isLogin);
        editor.putString("id", id.trim());
        editor.putString("nickname", nickname.trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    private void load() {
        saveLoginData = appData.getBoolean("isLogin", false);
        id = appData.getString("id", "");
        nickname = appData.getString("nickname", "");
    }
}



