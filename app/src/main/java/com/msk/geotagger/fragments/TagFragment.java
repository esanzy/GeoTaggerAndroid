package com.msk.geotagger.fragments;



import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.msk.geotagger.AddActivity;
import com.msk.geotagger.R;
import com.msk.geotagger.utils.DBAdapter;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class TagFragment extends Fragment
{
    private double latitude;
    private double longitude;


    public TagFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();
        ImageView btnAdd = (ImageView) v.findViewById (R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                getActivity().startActivity(intent);
            }
        });


        WebView map = (WebView) v.findViewById(R.id.map);
        RelativeLayout info = (RelativeLayout) v.findViewById(R.id.loc_info);



        ConnectivityManager cManager;
        NetworkInfo mobile;
        NetworkInfo wifi;

        cManager=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        DBAdapter db = new DBAdapter(getActivity());

        // 인터넷에 연결 되어있을 경우
        if(db.getSettings().getOffline() == 0 && (mobile.isConnected() || wifi.isConnected())) {

            info.setVisibility(View.GONE);
            map.setVisibility(View.VISIBLE);

            map.getSettings().setJavaScriptEnabled(true);
            map.loadUrl("file:///android_asset/map.html?&lat="+latitude+"&lon="+longitude);
        }

        else
        {
            map.setVisibility(View.GONE);
            info.setVisibility(View.VISIBLE);

            TextView latitude = (TextView) v.findViewById(R.id.latitude);
            TextView longitude = (TextView) v.findViewById(R.id.longitude);

            latitude.setText(""+this.latitude);
            longitude.setText(""+this.longitude);
        }
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public void setLocation(double lat, double lng)
    {
        this.latitude = lat;
        this.longitude = lng;

        //map.loadUrl("javascript:setLatLng(" + latitude + "," + longitude + ");");
    }
}
