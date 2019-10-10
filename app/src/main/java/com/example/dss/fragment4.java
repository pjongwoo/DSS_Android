package com.example.dss;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class fragment4 extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView = null;
    double x;
    double y;
    String Seach;
    String StoreName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment2);

        Intent Intent = getIntent();
        x = Intent.getExtras().getDouble("layx");
        y = Intent.getExtras().getDouble("layy");
        Seach = Intent.getExtras().getString("Seach");
        StoreName = Intent.getExtras().getString("dutyName");


        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        LatLng DrugStore = new LatLng(x, y);

        Log.i("x좌표",Double.toString(x));
        Log.i("x좌표",Double.toString(y));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DrugStore);
        markerOptions.title("약국명");
        markerOptions.snippet(StoreName);
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(x,y)));
        map.animateCamera(CameraUpdateFactory.zoomTo(12));
    }

}