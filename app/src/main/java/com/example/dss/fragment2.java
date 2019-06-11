package com.example.dss;

import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class fragment2 extends Fragment implements OnMapReadyCallback {

    private MapView mapView = null;
    private GoogleMap mMap;
    private double X;
    private double Y;

    ArrayList<ListDrugStore> DrugStore = new ArrayList<ListDrugStore>();
    LocationManager lm;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( getActivity(), new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }
        else{
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //  String provider = location.getProvider();
            //   double longitude = location.getLongitude();
            //    double latitude = location.getLatitude();
            //    double altitude = location.getAltitude();

            //    String tmp = Double.toString(longitude);
            //    String tmp2 = Double.toString(latitude);
            //     String tmp3 = Double.toString(altitude);

            //   Log.i("tmp1",tmp);
           //  Log.i("tmp2",tmp2);
           //   Log.i("tmp3",tmp3);

           //   X=longitude;
           //   Y=latitude;
            //  API 호출
            loadData();

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);

        }
    }
    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();

            X=longitude;
            Y=latitude;
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    private void loadData() {
        AQuery aq = new AQuery(getActivity());

        //String url = "http://211.239.124.237:19613/store/location/126.8655206/37.500246";
        String url = "http://211.239.124.237:19613/store/address/서울/구로";

        aq.ajax(url, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String resutl, AjaxStatus status) {
                Log.i ("url",url);
                if (resutl != null) {
                    //sucess

                    try {
                        JSONArray jsonArray = new JSONArray(resutl);
                        Log.i("jsonArray",jsonArray.toString());

                        for (int i=0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            ListDrugStore item = new ListDrugStore();
                            item.setX(jsonObject.getDouble("wgs84Lon"));
                            item.setY(jsonObject.getDouble("wgs84Lat"));
                            item.setDutyName(jsonObject.getString("dutyName"));

                            DrugStore.add(item);

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.location, container, false);

        mapView = (MapView)layout.findViewById(R.id.map);
        mapView.getMapAsync(this);

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //액티비티가 처음 생성될 때 실행되는 함수

        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng Drug_instance = new LatLng(37.500246 ,126.8655206);
        String val = Integer.toString(DrugStore.size());

        for (int i=0 ;i<DrugStore.size(); i++){
            MarkerOptions makerOptions = new MarkerOptions();
            makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                    .position(new LatLng(DrugStore.get(i).getY(), DrugStore.get(i).getX()))
                    .title( DrugStore.get(i).getDutyName()); // 타이틀.

            // 2. 마커 생성 (마커를 나타냄)
            googleMap.addMarker(makerOptions);
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(Drug_instance));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }
}