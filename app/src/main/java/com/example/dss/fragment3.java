package com.example.dss;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class fragment3 extends Fragment {

    ListViewStoreAdpter adapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment3, null);
        ListView listview = (ListView) view.findViewById(R.id.listview);

        Button btn_server = (Button) view.findViewById(R.id.but1);
        btn_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("STATE", "LOG in SERVER");
                Activity root = getActivity(); //이 클래스가 프레그먼트이기 때문에 액티비티 정보를 얻는다.
                Toast.makeText(root, "토스트 사용!", Toast.LENGTH_SHORT).show();
                loadData2();
            }
        });
        adapter = new ListViewStoreAdpter (this.getActivity());
        listview.setAdapter(adapter);
        return view;
    }
    private void loadData2() {
        AQuery aq = new AQuery(getActivity());
        HashMap<String, String> params = new HashMap<>();
        params.put("ServiceKey","231va86s8mqm2NVC5V5e3wWxtfRh5%2B1dBBBB2ZJb3E6DoeRzJPv3Kk19IYcZmBUyDez8LoibDglwKyWa3VC0Yg%3D%3D");
        params.put("Q0","서울");
        params.put("Q1","구로");
        params.put("ORD","ADDR");
        params.put("numOfRows","10");
        params.put("_type","json");

        String url = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire";
        url = addParams(url, params);

        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject resutl, AjaxStatus status) {

                if (resutl != null) {
                    try {
                        JSONArray jar = resutl.optJSONObject("response").optJSONObject("body").optJSONObject("items").optJSONArray("item");

                        ArrayList<ListViewStoreItem> arItem = new ArrayList<ListViewStoreItem>();
                        for(int i=0; i<jar.length(); i++){
                            JSONObject jobj = jar.optJSONObject(i);

                            ListViewStoreItem item = new ListViewStoreItem();
                            item.setDutyName(jobj.optString("dutyName"));
                            item.setDutyAddr(jobj.optString("dutyAddr"));
                            item.setDutyTel1(jobj.optString("dutyTel1"));
                            item.setWgs84Lat(jobj.optDouble("wgs84Lat"));
                            item.setWgs84Lon(jobj.optDouble("wgs84Lon"));

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
