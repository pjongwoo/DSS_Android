package com.example.dss;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


    ListViewAdapter adapter;
    EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1, null);
        ListView listview = (ListView) view.findViewById(R.id.listview);
        editText = (EditText) view.findViewById(R.id.edit1);

        Button btn_server = (Button) view.findViewById(R.id.but1);

        btn_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Drug = editText.getText().toString();
                Activity root = getActivity(); //이 클래스가 프레그먼트이기 때문에 액티비티 정보를 얻는다.
                Toast.makeText(root, Drug, Toast.LENGTH_SHORT).show();
                loadData(Drug);
            }
        });
        adapter = new ListViewAdapter(this.getActivity());
        listview.setAdapter(adapter);
        return  view;

    }
    private void loadData(String Drug) {
        AQuery aq = new AQuery(getActivity());

        HashMap<String, String> params = new HashMap<>();
        params.put("ServiceKey","231va86s8mqm2NVC5V5e3wWxtfRh5%2B1dBBBB2ZJb3E6DoeRzJPv3Kk19IYcZmBUyDez8LoibDglwKyWa3VC0Yg%3D%3D");
        params.put("numOfRows","10");
        params.put("pageNo","1");
        params.put("itmNm",Drug);
        params.put("_type","json");

        String url = "http://apis.data.go.kr/B551182/dgamtCrtrInfoService/getDgamtList";
        url = addParams(url, params);

        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject resutl, AjaxStatus status) {
                Log.i ("url",url);
                if (resutl != null) {
                    //sucess
                    Log.i("test", resutl.toString());

                    try {
                        JSONArray jar = resutl.optJSONObject("response").optJSONObject("body").optJSONObject("items").optJSONArray("item");

                        ArrayList<ListViewItem> arItem = new ArrayList<ListViewItem>();
                        for(int i=0; i<jar.length(); i++){
                            JSONObject jobj = jar.optJSONObject(i);

                            ListViewItem item = new ListViewItem();
                            item.setItmNm(jobj.optString("itmNm"));
                            item.setMnfEntpNm(jobj.optString("mnfEntpNm"));
                            item.setGnlNmCd(jobj.optString("GnlNmCd"));

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

    private String addParams(String url, HashMap<String, String> mapParam){
            StringBuilder stringBuilder = new StringBuilder(url + "?");

            if (mapParam != null) {
                for (String key : mapParam.keySet()) {
                    stringBuilder.append(key + "=");
                    stringBuilder.append(mapParam.get(key) + "&");
                }
            }
            return stringBuilder.toString();
        }
}
