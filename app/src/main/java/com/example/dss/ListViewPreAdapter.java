package com.example.dss;
/**
 * Created by ohkaning-office on 2017-05-03.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.ArrayList;


public class ListViewPreAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewPreItem> arItem;

    private Context context;
    private AQuery aQuery;

    private View.OnClickListener ShowClickListener;

    public ListViewPreAdapter(Context context) {
        this.arItem = new ArrayList<>();
        this.context = context;
        aQuery = new AQuery(context);
    }

    public ArrayList<ListViewPreItem> getArItem() {
        return arItem;
    }

    public void ShowClickListener(View.OnClickListener ShowClickListener) {
        this.ShowClickListener = ShowClickListener;
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
            convertView = inflater.inflate(R.layout.prerow, parent, false);
        }

        ListViewPreItem item = getItem(position);

        TextView preHospitalName = (TextView)convertView.findViewById(R.id.preHospitalName);
        TextView preCreateDate = (TextView)convertView.findViewById(R.id.preCreateDate);
        TextView preDoses = (TextView)convertView.findViewById(R.id.preDoses);
//        Button btnShow = (Button)convertView.findViewById(R.id.btnShow);

//        aQuery.id(ivPhoto).image(item.getBig_image(), true, true);
        preHospitalName.setText(item.getHOSPITAL_NAME());
        preCreateDate.setText(item.getCREATE_DATE());
        preDoses.setText(item.getDOSES_TIME()+"회씩 "+item.getDOSES_DAY()+"일 복용");


//        btnShow.setTag(position);
//        if(ShowClickListener != null){
//            btnShow.setOnClickListener(ShowClickListener);
//        }

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public ListViewPreItem getItem(int position) {

        return arItem.get(position) ;
    }

}

