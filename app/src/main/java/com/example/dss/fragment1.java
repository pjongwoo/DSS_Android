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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class fragment1 extends Fragment {

    ListViewAdapter adapter;
    EditText editText;

    static final String ImageUrl = "http://211.239.124.237:19609/resources/big_image/";

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
        adapter.ShowClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListViewItemApi item = adapter.getItem((Integer) v.getTag());
                Intent intent = new Intent(getActivity(), fragment5.class);

                intent.putExtra("ingredient_detail", item.getIngredient_detail());
                intent.putExtra("validity", item.getValidity());
                intent.putExtra("manufacturing", item.getManufacturing());
                intent.putExtra("usage", item.getUsage());
                startActivity(intent);//액티비티 띄우기
            }
        });
        listview.setAdapter(adapter);
        return  view;

    }
    private void loadData(String Drug) {
        AQuery aq = new AQuery(getActivity());

        String url = "http://211.239.124.237:19613/drug/findName/"+Drug;
        //String url = "http://211.239.124.237:19613/drug/findName/타이";

        aq.ajax(url, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String resutl, AjaxStatus status) {
                Log.i ("url",url);
                if (resutl != null) {
                    //sucess
                    Log.i("test", resutl.toString());
                    try {
                        JSONArray jsonArray = new JSONArray(resutl);
                        ArrayList<ListViewItemApi> arItem = new ArrayList<ListViewItemApi>();

                        for (int i=0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String str = jsonObject.getString("big_image");
                            String[] array = str.split("/");
                            String newurl = ImageUrl + array[6] + ".jpg";

                            ListViewItemApi item = new ListViewItemApi();
                            item.setName(jsonObject.getString("name"));
                            item.setCompany_name(jsonObject.getString("company_name"));
                            item.setDiv_name(jsonObject.getString("div_name"));

                            item.setIngredient_detail(jsonObject.getString("ingredient_detail"));
                            item.setUsage(jsonObject.getString("usage"));
                            item.setValidity(jsonObject.getString("validity"));
                            item.setManufacturing(jsonObject.getString("manufacturing"));

                            item.setBig_image(newurl);

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
}