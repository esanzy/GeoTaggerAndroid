package com.msk.geotagger;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.msk.geotagger.fragments.HistoryFragment;
import com.msk.geotagger.fragments.SettingsFragment;
import com.msk.geotagger.fragments.TagFragment;


public class MainActivity extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener
{

    private LocationClient mLocationClient;
    private double lat;
    private double lon;


    private TagFragment tagFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tagFragment = new TagFragment();



        // 위치정보 불러오기
        mLocationClient = new LocationClient(this, this, this);
        mLocationClient.connect();


        if (savedInstanceState == null) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    tagFragment.setLatitude(lat);
                    tagFragment.setLongitude(lon);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, tagFragment)
                            .commit();
                }
            });
        }





        ImageView tag = (ImageView)findViewById(R.id.btnTag);
        ImageView history = (ImageView)findViewById(R.id.History);
        ImageView settings = (ImageView)findViewById(R.id.Settings);

        tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TagFragment tagFragment = new TagFragment();
                tagFragment.setLatitude(lat);
                tagFragment.setLongitude(lon);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, tagFragment)
                        .commit();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new HistoryFragment())
                        .commit();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new SettingsFragment())
                        .commit();
            }
        });
    } // onCreate

    @Override
    protected void onStart()
    {
        super.onStart();
        mLocationClient.connect();
    }

    @Override
    protected void onStop()
    {
        mLocationClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        Location location = mLocationClient.getLastLocation();

        if(location == null)
        {
            // mock location
            lat = 47.905034;
            lon = 106.924488;

            // TODO show dialog
        }
        else {
            lat = location.getLatitude();
            lon = location.getLongitude();
        }
        tagFragment.setLocation(lat, lon);
    }

    @Override
    public void onDisconnected()
    {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {

    }
}
