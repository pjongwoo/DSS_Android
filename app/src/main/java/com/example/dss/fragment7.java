package com.example.dss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
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

public class fragment7 extends Fragment {

    //현재 시간 Get
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String getTime = sdf.format(date);

    ListViewItemCalAdapter listViewItemCalAdapter;
    public ArrayList<ListViewItemCalendar> arItem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment6, null);
        ListView listview = (ListView) view.findViewById(R.id.listview);
        CalendarView calendar = (CalendarView)view.findViewById(R.id.calendar);
        loadData(getTime);

            calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                    String New_year = Integer.toString(year);
                    String New_month = Integer.toString(month+1);
                    String New_dayOfMonth = Integer.toString(dayOfMonth);

                    getTime = New_year + "-" + New_month + "-" + New_dayOfMonth;
                    listViewItemCalAdapter.getArItem().removeAll(arItem);

                    Log.i("NewGetTime",getTime);
                    loadData(getTime);
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
        Log.i("getTime",getTime);
        aq.ajax(url, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String resutl, AjaxStatus status) {
                Log.i ("url",url);
                if (resutl != null) {
                    //sucess
                    Log.i("resutl", resutl.toString());
                    try {
                        JSONArray jsonArray = new JSONArray(resutl);
                        arItem = new ArrayList<ListViewItemCalendar>();

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
}
