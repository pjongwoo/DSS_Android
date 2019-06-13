package com.example.dss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidquery.AQuery;

import static android.content.Context.MODE_PRIVATE;

public class fragment_mypage extends Fragment {
    Button btn_insertPre, btn_logout;
    String id, nickname;
    ListViewPreAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, null);

        if(getArguments() != null){
            id = getArguments().getString("id");
            nickname = getArguments().getString("nickname");
        }



        btn_insertPre = (Button) view.findViewById(R.id.btn_insertPre);
        btn_insertPre.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

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

        AQuery aq = new AQuery(getActivity());

        String url = "http://211.239.124.237:19613/selectPre/";
        //String url = "http://211.239.124.237:19613/drug/findName/타이";

//        aq.ajax(url, String.class, new AjaxCallback<String>() {
//            @Override
//            public void callback(String url, String resutl, AjaxStatus status) {
//                Log.i ("url",url);
//                if (resutl != null) {
//                    //sucess
//                    Log.i("test", resutl.toString());
//                    try {
//                        JSONArray jsonArray = new JSONArray(resutl);
//                        ArrayList<ListViewItemApi> arItem = new ArrayList<ListViewItemApi>();
//
//                        for (int i=0; i < jsonArray.length(); i++){
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                            String str = jsonObject.getString("big_image");
//                            String[] array = str.split("/");
//                            String newurl = ImageUrl + array[6] + ".jpg";
//
//                            ListViewItemApi item = new ListViewItemApi();
//                            item.setName(jsonObject.getString("name"));
//                            item.setCompany_name(jsonObject.getString("company_name"));
//                            item.setDiv_name(jsonObject.getString("div_name"));
//
//                            item.setIngredient_detail(jsonObject.getString("ingredient_detail"));
//                            item.setUsage(jsonObject.getString("usage"));
//                            item.setValidity(jsonObject.getString("validity"));
//                            item.setManufacturing(jsonObject.getString("manufacturing"));
//
//                            item.setBig_image(newurl);
//
//                            arItem.add(item);
//                        }
//                        if(arItem.size()>0){
//                            adapter.getArItem().addAll(arItem);
//                            adapter.notifyDataSetChanged();
//                        }
//                    }
//                    catch (Exception e){
//                        Toast.makeText(getActivity(), "JSON Parsing 오류 발생", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    //fail
//                    Toast.makeText(getActivity(), "잘못된 요청입니다", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }.timeout(20000));

        return view;
    }

}