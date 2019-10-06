package com.example.dss;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class fragment7 extends Fragment {

    //현재 시간
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String getTime = sdf.format(date);

    ListViewItemCalAdapter listViewItemCalAdapter;
    public ArrayList<ListViewItemCalendar> arItem;
    ListView listview;

    //Retrofit 생성
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://3.18.0.46:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Retrofit2Service retrofit2Service = retrofit.create(Retrofit2Service.class);


    //Fragment 생성
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment6, null);
         listview = (ListView) view.findViewById(R.id.listview);
        CalendarView calendar = (CalendarView)view.findViewById(R.id.calendar);
        Button button = (Button)view.findViewById(R.id.but1);

        //회원 약정보 호출
        loadData(getTime);
            //캘린더 이벤트
            calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                    String New_year = Integer.toString(year);
                    String New_month = Integer.toString(month+1);
                    String New_dayOfMonth = Integer.toString(dayOfMonth);

                    getTime = New_year + "-" + New_month + "-" + New_dayOfMonth;
                    listViewItemCalAdapter.getArItem().removeAll(arItem);
                    //회원 약정보 호출
                    loadData(getTime);
                }
            });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(getTime);
            }
        });
        listViewItemCalAdapter = new ListViewItemCalAdapter(this.getActivity());
        listview.setAdapter(listViewItemCalAdapter);
        return  view;

    }

    //약이름 검색 API 호출
    private void loadData(String getTimes) {
        AQuery aq = new AQuery(getActivity());
        String time =  getTimes;
        String url = "http://3.18.0.46:8080/userdrug/2/"+time;

        aq.ajax(url, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String resutl, AjaxStatus status) {
                Log.i ("url",url);
                if (resutl != null) {
                    //sucess
                    try {
                        JSONArray jsonArray = new JSONArray(resutl);
                        arItem = new ArrayList<ListViewItemCalendar>();

                        if (jsonArray.length() == 0)
                        {
                            Log.i("if 문 resutl", resutl.toString());
                            listview.setVisibility(View.INVISIBLE);

                        }else{
                            listview.setVisibility(View.VISIBLE);
                        }
                        for (int i=0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            ListViewItemCalendar item = new ListViewItemCalendar();
                            item.setDurg(jsonObject.getString("drug"));

                            arItem.add(item);

                        }
                        if(arItem.size()>0){

                            listViewItemCalAdapter.getArItem().addAll(arItem);
                            listViewItemCalAdapter.notifyDataSetChanged();

                        }
                    }
                    catch (Exception e){
                        Toast.makeText(getActivity(), "JSON Parsing 오류 발생", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //fail
                    Toast.makeText(getActivity(), "잘못된 요청입니다", Toast.LENGTH_SHORT).show();
                }
            }
        }.timeout(20000));
    }

    //다이얼로그 호출
    private void show(String getTime)
    {
        String Show_getTime = getTime;

        Log.i("Show_getTime",Show_getTime);

        final EditText edittext = new EditText(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("약 스케줄 관리.");
        builder.setMessage("복용중인 약을 입력해주세요.");
        builder.setView(edittext);

        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String DrugText = edittext.getText().toString();
                        Log.i("DrugText",DrugText);
                        retrofit2Service.postdata(2,DrugText,"false",Show_getTime)
                                .enqueue(new Callback<Retrofit2TestModel>() {
                                    @Override
                                    public void onResponse(Call<Retrofit2TestModel> call, Response<Retrofit2TestModel> response) {
                                        Log.i("onResponse",response.message());
                                    }

                                    @Override
                                    public void onFailure(Call<Retrofit2TestModel> call, Throwable t) {
                                        Log.i("onFailure",t.getMessage());

                                    }
                                });


                        Toast.makeText(getActivity(),"등록 완료 되었습니다." ,Toast.LENGTH_LONG).show();

                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

}
