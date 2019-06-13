package com.example.dss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;

import okio.Timeout;

public class QrResultActivity extends AppCompatActivity {

    TextView textHospital, textCreateDate, textDoses;
    String hospital, createDate, doses_day, doses_time, id, nickname;
    ListViewAdapter adapter;
    ListView listview;
    JSONObject json;
    JSONArray jsonArray;
    static final String ImageUrl = "http://211.239.124.237:19609/resources/big_image/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String qrcode_result="  {\n" +
                "    \"id\": 10,\n" +
                "    \"create_DATE\": \"2019-06-13\",\n" +
                "    \"hospital_NAME\": \"동양약국\",\n" +
                "    \"doses_DAY\": \"5\",\n" +
                "    \"doses_TIME\": \"3\",\n" +
                "    \"drug_list\":[\n" +
                "           {\n" +
                "           \"DRUG_ID\": \"200004062\",\n" +
                "           \"DRUG_DOSAGE\": \"0.5\",\n" +
                "           \"DRUG_TIME\": \"2\"\n" +
                "           },\n" +
                "           {\n" +
                "           \"DRUG_ID\": \"200703756\",\n" +
                "           \"DRUG_DOSAGE\": \"1\",\n" +
                "           \"DRUG_TIME\": \"3\"\n" +
                "           },\n" +
                "           {\n" +
                "           \"DRUG_ID\": \"200300405\",\n" +
                "           \"DRUG_DOSAGE\": \"1\",\n" +
                "           \"DRUG_TIME\": \"3\"\n" +
                "           }\n" +
                "       ]" +
                "   }";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrresult);

        json = null;

        try {
            json = new JSONObject(qrcode_result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        textHospital =  (TextView)findViewById(R.id.textHospital);
        textCreateDate =  (TextView)findViewById(R.id.textCreateDate);
        textDoses =  (TextView)findViewById(R.id.textDoses);
        listview = (ListView)findViewById(R.id.listview);

        try {
            JSONObject jsonObject = json;
            hospital =  jsonObject.getString("hospital_NAME");
            createDate = jsonObject.getString("create_DATE");
            doses_time = jsonObject.getString("doses_TIME");
            doses_day = jsonObject.getString("doses_DAY");

            textHospital.setText(hospital);
            textCreateDate.setText(createDate);
            textDoses.setText("하루에 "+doses_time+"회 "+doses_day+"일동안 복용");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        nickname = intent.getExtras().getString("nickname");

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

        Button btn_reg = (Button)findViewById(R.id.btn_reg);
        btn_reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                AQuery aq = new AQuery(v);

                String url = "http://211.239.124.237:19613/drug/insertPre/"+id+"/"+hospital+"/"+doses_day+"/"+doses_time+"/"+createDate;
                aq.ajax(url, String.class, new AjaxCallback<String>() {
                    @Override
                    public void callback(String url, String result, AjaxStatus status) {
                        Log.i ("url",url);
                        if (result != null) {
                            //sucess
                            for (int i=0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = null;
                                try {
                                    jsonObject2 = jsonArray.getJSONObject(i);
                                    int drug_id = jsonObject2.getInt("DRUG_ID");
                                    String drug_dosage = jsonObject2.getString("DRUG_DOSAGE");
                                    String drug_time = jsonObject2.getString("DRUG_TIME");
                                    AQuery aq2 = new AQuery(getApplicationContext());
                                    String url2 = "http://211.239.124.237:19613/drug/insertPreDetail/"+result+"/"+drug_id+"/"+drug_dosage+"/"+drug_time;
                                    aq2.ajax(url2, String.class, new AjaxCallback<String>() {
                                        @Override
                                        public void callback(String url2, String result2, AjaxStatus status2) {
                                            Log.i ("url",url2);
                                            if (result2 != null) {
                                                //sucess
                                            } else {
                                                //fail
                                                Toast.makeText(getApplicationContext(), "처방전 약품 리스트 추가를 싪패하였습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Intent outIntent = new Intent(getApplicationContext(), MainActivity.class);
                            outIntent.putExtra("status", "login");
                            outIntent.putExtra("id", id);
                            outIntent.putExtra("nickname", nickname);
                            startActivity(outIntent);
                            finish();
                        } else {
                            //fail
                            Toast.makeText(getApplicationContext(), "처방전 추가를 싪패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void loadData() throws JSONException {
        ArrayList<ListViewItemApi> arItem = new ArrayList<ListViewItemApi>();
        AQuery aq = new AQuery(this);

        JSONObject jsonObject = json;
        jsonArray = jsonObject.getJSONArray("drug_list");

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            int drug_id = jsonObject2.getInt("DRUG_ID");
            String url = "http://211.239.124.237:19613/drug/findId/"+drug_id;
            aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject jsonObject, AjaxStatus status) {
                    Log.i ("url",url);
                    if (jsonObject != null) {
                        //sucess
                        Log.i("test", jsonObject.toString());
                        try {
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
                            if(arItem.size()>=jsonArray.length()){
                                adapter.getArItem().addAll(arItem);
                                adapter.notifyDataSetChanged();
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
            });
        }





    }

}