package com.example.dss;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class fragment3 extends Fragment {

    ListViewStoreAdpter adapter;
    EditText editText;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment3, null);
        ListView listview = (ListView) view.findViewById(R.id.listview);
        editText = (EditText) view.findViewById(R.id.edit1);

        Button btn_server = (Button) view.findViewById(R.id.but1);
        btn_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String DrugStore = editText.getText().toString();
                Activity root = getActivity(); //이 클래스가 프레그먼트이기 때문에 액티비티 정보를 얻는다.
                Toast.makeText(root, DrugStore, Toast.LENGTH_SHORT).show();
                loadData2(DrugStore);
            }
        });
        adapter = new ListViewStoreAdpter(this.getActivity());
        adapter.setTelClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListViewStoreItem item = adapter.getItem((Integer) v.getTag());

                String tel = "tel:" + item.getDutyTel1();
//                startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
            }
        });
        adapter.setMapClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListViewStoreItem item = adapter.getItem((Integer) v.getTag());
                // Uri gmmIntentUri = Uri.parse("geo:" + item.getWgs84Lat() + "," + item.getWgs84Lon());
                //Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                //mapIntent.setPackage("com.google.android.apps.maps");
                //startActivity(mapIntent);
                Intent intent = new Intent(getActivity(), fragment4.class);

                intent.putExtra("layx", item.getWgs84Lat());
                intent.putExtra("layy", item.getWgs84Lon());
                intent.putExtra("Seach", editText.getText().toString());
                intent.putExtra("dutyName", item.getDutyName());
                startActivity(intent);//액티비티 띄우기
            }
        });

        listview.setAdapter(adapter);
        return view;
    }
    private void loadData2(String Name) {
        AQuery aq = new AQuery(getActivity());
        HashMap<String, String> params = new HashMap<>();
        params.put("ServiceKey","231va86s8mqm2NVC5V5e3wWxtfRh5%2B1dBBBB2ZJb3E6DoeRzJPv3Kk19IYcZmBUyDez8LoibDglwKyWa3VC0Yg%3D%3D");
        params.put("Q0","서울");
        params.put("Q1",Name);
        params.put("ORD","ADDR");
        params.put("numOfRows","10");
        params.put("_type","json");

        String url = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire";
        url = addParams(url, params);

        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject resutl, AjaxStatus status) {
                Log.i("url", url);
                if (resutl != null) {
                    Log.i("resutl", resutl.toString());
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
