package com.msk.geotagger.fragments;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.msk.geotagger.AddActivity;
import com.msk.geotagger.R;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class TagFragment extends Fragment
{
    private double latitude;
    private double longitude;

    private WebView map;

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


        map = (WebView) v.findViewById(R.id.map);

        map.getSettings().setJavaScriptEnabled(true);
        map.loadUrl("file:///android_asset/map.html");
        map.loadUrl("javascript:setLatLng(" + latitude + "," + longitude + ");");
        map.invalidate();
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
