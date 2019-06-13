package com.example.dss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static android.content.Context.MODE_PRIVATE;

public class fragment_mypage extends Fragment {
    Button btn_insertPre, btn_logout;
    String id, nickname;
    JSONArray jsonArray;
    ListViewPreAdapter adapter;
    private IntentIntegrator qrScan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, null);
        ListView listview = (ListView) view.findViewById(R.id.listview);
        if(getArguments() != null){
            id = getArguments().getString("id");
            nickname = getArguments().getString("nickname");
        }


        qrScan = new IntentIntegrator(getActivity());
        btn_insertPre = (Button) view.findViewById(R.id.btn_insertPre);
        btn_insertPre.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                qrScan.setPrompt("Scanning...");
                qrScan.initiateScan();
            }
        });
        btn_insertPre.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getActivity(), QrResultActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("nickname", nickname);
                startActivity(intent);
//                getActivity().finish();
                return false;
            }
        });

        btn_logout = (Button) view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("status", "logout");
                startActivity(intent);
                getActivity().finish();
            }
        });

        adapter = new ListViewPreAdapter(this.getActivity());
        adapter.ShowClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListViewPreItem item = adapter.getItem((Integer) v.getTag());
                Intent intent = new Intent(getActivity(), PreResultActivity.class);

                intent.putExtra("pre_id", item.getId());
                intent.putExtra("hospital_name", item.getHOSPITAL_NAME());
                intent.putExtra("create_date", item.getCREATE_DATE());
                intent.putExtra("doses_day", item.getDOSES_DAY());
                intent.putExtra("doses_time", item.getDOSES_TIME());
                intent.putExtra("id", id);
                intent.putExtra("nickname", nickname);

                startActivity(intent);
            }
        });
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long idx) {
                Intent intent2 = new Intent(getActivity(), PreResultActivity.class);

                try {
                    intent2.putExtra("pre_id", jsonArray.getJSONObject(position).getInt("id"));
                    intent2.putExtra("hospital_name", jsonArray.getJSONObject(position).getString("hospital_NAME"));
                    intent2.putExtra("create_date", jsonArray.getJSONObject(position).getString("create_DATE"));
                    intent2.putExtra("doses_day", jsonArray.getJSONObject(position).getString("doses_DAY"));
                    intent2.putExtra("doses_time", jsonArray.getJSONObject(position).getString("doses_TIME"));
                    intent2.putExtra("id", id);
                    intent2.putExtra("nickname", nickname);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startActivity(intent2);
            }
        });

        AQuery aq = new AQuery(getActivity());

        String url = "http://211.239.124.237:19613/drug/selectPreById/"+id;
        //String url = "http://211.239.124.237:19613/drug/findName/타이";

        aq.ajax(url, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String resutl, AjaxStatus status) {
                Log.i ("url",url);
                if (resutl != null) {
                    //sucess
                    Log.i("test", resutl.toString());
                    try {
                        jsonArray = new JSONArray(resutl);
                        ArrayList<ListViewPreItem> arItem = new ArrayList<ListViewPreItem>();

                        for (int i=0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);


                            ListViewPreItem item = new ListViewPreItem();
                            item.setId(jsonObject.getInt("id"));
                            item.setHOSPITAL_NAME(jsonObject.getString("hospital_NAME"));
                            item.setCREATE_DATE(jsonObject.getString("create_DATE"));
                            item.setDOSES_DAY(jsonObject.getString("doses_DAY"));
                            item.setDOSES_TIME(jsonObject.getString("doses_TIME"));

                            arItem.add(item);
                        }
                        if(arItem.size()>0){
                            adapter.getArItem().addAll(arItem);
                            adapter.notifyDataSetChanged();
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

        return view;
    }

}