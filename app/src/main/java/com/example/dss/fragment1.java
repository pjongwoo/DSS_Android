package com.example.dss;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.HashMap;

public class fragment1 extends Fragment {

    private ListViewItem data;
    ListView listView = null;
    ListViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1, null);
        ListView listview = (ListView) view.findViewById(R.id.listview);

        Button btn_server = (Button) view.findViewById(R.id.but1);
        btn_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("STATE", "LOG in SERVER");
                Activity root = getActivity(); //이 클래스가 프레그먼트이기 때문에 액티비티 정보를 얻는다.
                Toast.makeText(root, "토스트 사용!", Toast.LENGTH_SHORT).show();
                loadData();
            }
        });
         adapter = new ListViewAdapter (this.getActivity());
        listview.setAdapter(adapter);
        return view;
    }

    private void loadData() {
        AQuery aq = new AQuery(getActivity());

        HashMap<String, String> params = new HashMap<>();
        params.put("ServiceKey","WMoao2wlyH%2Fg8VDiX%2Bg4dmAimYFxy58FB7Qu%2FNbKl4wOGlNq%2FJGYs7dfK3x3FpKQK9zysPxgunNGdE4bsO15dA%3D%3D");
        params.put("numOfRows","30");
        params.put("pageNo","1");
        params.put("MobileOS","ETC");
        params.put("MobileApp","AppTest");
        params.put("arrange","A");
        params.put("listYN","Y");
        params.put("areaCode","1");
//        params.put("sigunguCode","1");
        params.put("eventStartDate","20180101");
//        params.put("eventStartDate","20170901");
//        params.put("eventEndDate","");
        params.put("_type","json");

        String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival";
        url = addParams(url, params);

        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject resutl, AjaxStatus status) {
                if (resutl != null) {
                    //sucess
                    Log.i("test", resutl.toString());

                    try {
                        JSONArray jar = resutl.optJSONObject("response").optJSONObject("body").optJSONObject("items").optJSONArray("item");

                        ArrayList<ListViewItem> arItem = new ArrayList<ListViewItem>();
                        for(int i=0; i<jar.length(); i++){
                            JSONObject jobj = jar.optJSONObject(i);

                            ListViewItem item = new ListViewItem();
                            item.setTitle(jobj.optString("title"));
                            item.setAddress(jobj.optString("addr1"));
                            item.setFirstimage(jobj.optString("firstimage"));
                            item.setMapx(jobj.optDouble("mapx"));
                            item.setMapy(jobj.optDouble("mapy"));
                            item.setTel(jobj.optString("tel"));

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
