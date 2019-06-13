package com.example.dss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PreResultActivity extends AppCompatActivity {

    TextView textHospital, textCreateDate, textDoses;
    int pre_id;
    String hospital, createDate, doses_day, doses_time, id, nickname;
    ListViewAdapter adapter;
    ListView listview;
    static final String ImageUrl = "http://211.239.124.237:19609/resources/big_image/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preresult);

        textHospital =  (TextView)findViewById(R.id.textHospital);
        textCreateDate =  (TextView)findViewById(R.id.textCreateDate);
        textDoses =  (TextView)findViewById(R.id.textDoses);
        listview = (ListView)findViewById(R.id.listview);

        Intent intent = getIntent();
        pre_id =  intent.getExtras().getInt("pre_id");
        hospital =  intent.getExtras().getString("hospital_name");
        createDate = intent.getExtras().getString("create_date");
        doses_time = intent.getExtras().getString("doses_time");
        doses_day = intent.getExtras().getString("doses_day");
        id = intent.getExtras().getString("id");
        nickname = intent.getExtras().getString("nickname");

        textHospital.setText(hospital);
        textCreateDate.setText(createDate);
        textDoses.setText("하루에 "+doses_time+"회 "+doses_day+"일동안 복용");

        adapter = new ListViewAdapter(this);
        adapter.ShowClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListViewItemApi item = adapter.getItem((Integer) v.getTag());
                Intent intent = new Intent(getApplicationContext(), fragment5.class);

                intent.putExtra("ingredient_detail", item.getIngredient_detail());
                intent.putExtra("validity", item.getValidity());
                intent.putExtra("manufacturing", item.getManufacturing());
                intent.putExtra("usage", item.getUsage());

                intent.putExtra("name", item.getName());
                intent.putExtra("company_name", item.getCompany_name());
                intent.putExtra("big_image",item.getBig_image());

                startActivity(intent);//액티비티 띄우기
            }
        });
        listview.setAdapter(adapter);

        try {
            loadData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadData() throws JSONException {
        ArrayList<ListViewItemApi> arItem = new ArrayList<ListViewItemApi>();
        AQuery aq = new AQuery(this);

        String url = "http://211.239.124.237:19613/drug/selectPreDrugById/"+pre_id;
        aq.ajax(url, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String result, AjaxStatus status) {
                Log.i ("url",url);
                if (result != null) {
                    //sucess
                    Log.i("test", result.toString());
                    try {
                        AQuery aq2 = new AQuery(getApplicationContext());
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i=0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int drug_id = jsonObject.getInt("DRUG_ID");

                            String url2 = "http://211.239.124.237:19613/drug/findId/" + drug_id;
                            aq2.ajax(url2, JSONObject.class, new AjaxCallback<JSONObject>() {
                                @Override
                                public void callback(String url2, JSONObject jsonObject2, AjaxStatus status2) {
                                    Log.i("url", url);
                                    if (jsonObject2 != null) {
                                        //sucess
                                        Log.i("test", jsonObject2.toString());
                                        try {
                                            String str = jsonObject2.getString("big_image");
                                            String[] array = str.split("/");
                                            String newurl = ImageUrl + array[6] + ".jpg";

                                            ListViewItemApi item = new ListViewItemApi();
                                            item.setName(jsonObject2.getString("name"));
                                            item.setCompany_name(jsonObject2.getString("company_name"));
                                            item.setDiv_name(jsonObject2.getString("div_name"));

                                            item.setIngredient_detail(jsonObject2.getString("ingredient_detail"));
                                            item.setUsage(jsonObject2.getString("usage"));
                                            item.setValidity(jsonObject2.getString("validity"));
                                            item.setManufacturing(jsonObject2.getString("manufacturing"));

                                            item.setBig_image(newurl);

                                            arItem.add(item);
                                            if (arItem.size() >= jsonArray.length()) {
                                                adapter.getArItem().addAll(arItem);
                                                adapter.notifyDataSetChanged();
                                            }
                                        } catch (Exception e) {
                                            Toast.makeText(getApplicationContext(), "JSON Parsing 오류 발생", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }.timeout(20000));
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), "JSON Parsing 오류 발생", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //fail
                    Toast.makeText(getApplicationContext(), "잘못된 요청입니다", Toast.LENGTH_SHORT).show();
                }
            }
        }.timeout(20000));





    }

}