package com.example.dss;
/**
 * Created by ohkaning-office on 2017-05-03.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.ArrayList;


public class ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItemApi> arItem;

    private Context context;
    private AQuery aQuery;

    public ListViewAdapter(Context context) {
        this.arItem = new ArrayList<>();
        this.context = context;
        aQuery = new AQuery(context);
    }

    public ArrayList<ListViewItemApi> getArItem() {
        return arItem;
    }


    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return arItem.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row, parent, false);
        }

        ListViewItemApi item = getItem(position);

        TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
        TextView tvAdress = (TextView)convertView.findViewById(R.id.tvAdress);
        TextView tvTel = (TextView)convertView.findViewById(R.id.tvTel);
        ImageView ivPhoto = (ImageView)convertView.findViewById(R.id.ivPhoto);

        aQuery.id(ivPhoto).image(item.getBig_image(), true, true);
        tvTitle.setText(item.getName());
        tvAdress.setText(item.getCompany_name());
        tvTel.setText(item.getDiv_name());


        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public ListViewItemApi getItem(int position) {

        return arItem.get(position) ;
    }

}

