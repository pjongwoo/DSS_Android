package com.example.dss;

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

public class ListViewStoreAdpter extends BaseAdapter {

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewStoreItem> arItem;

    private View.OnClickListener telClickListener;
    private View.OnClickListener mapClickListener;

    private Context context;
    private AQuery aQuery;

    public ListViewStoreAdpter(Context context) {
        this.arItem = new ArrayList<>();
        this.context = context;
        aQuery = new AQuery(context);
    }

    public ArrayList<ListViewStoreItem> getArItem() {
        return arItem;
    }

    public void setTelClickListener(View.OnClickListener telClickListener) {
        this.telClickListener = telClickListener;
    }

    public void setMapClickListener(View.OnClickListener mapClickListener) {
        this.mapClickListener = mapClickListener;
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
            convertView = inflater.inflate(R.layout.storerow, parent, false);
        }

        ListViewStoreItem item = getItem(position);

        TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
        TextView tvAdress = (TextView)convertView.findViewById(R.id.tvAdress);
        TextView tvTel = (TextView)convertView.findViewById(R.id.tvTel);
        Button btnMap = (Button)convertView.findViewById(R.id.btnMap);
        Button btnTel = (Button)convertView.findViewById(R.id.btnTel);


        tvTitle.setText(item.getDutyName());
        tvAdress.setText(item.getDutyAddr());
        tvTel.setText(item.getDutyTel1());

        btnTel.setTag(position);
        if(telClickListener != null){
            btnTel.setOnClickListener(telClickListener);
        }

        btnMap.setTag(position);
        if(mapClickListener!= null){
            btnMap.setOnClickListener(mapClickListener);
        }


        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public ListViewStoreItem getItem(int position) {
        return arItem.get(position) ;
    }

}

