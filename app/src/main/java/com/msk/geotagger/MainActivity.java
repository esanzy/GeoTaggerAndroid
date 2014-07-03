package com.msk.geotagger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.msk.geotagger.fragments.HistoryFragment;
import com.msk.geotagger.fragments.SettingsFragment;
import com.msk.geotagger.fragments.TagFragment;
import com.msk.geotagger.utils.DialogManager;


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


        final ImageView tag = (ImageView)findViewById(R.id.btnTag);
        final ImageView history = (ImageView)findViewById(R.id.History);
        final ImageView settings = (ImageView)findViewById(R.id.Settings);

        final TextView tagText = (TextView) findViewById(R.id.textView);
        final TextView historyText = (TextView) findViewById(R.id.textView2);
        final TextView settingsText = (TextView) findViewById(R.id.textView3);








        tag.setBackgroundColor(Color.parseColor("#7a9aea"));
        tagText.setBackgroundColor(Color.parseColor("#7a9aea"));


        tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TagFragment tagFragment = new TagFragment();
                tagFragment.setLatitude(lat);
                tagFragment.setLongitude(lon);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, tagFragment)
                        .commit();

                tag.setBackgroundColor(Color.parseColor("#7a9aea"));
                tagText.setBackgroundColor(Color.parseColor("#7a9aea"));
                history.setBackgroundColor(Color.WHITE);
                historyText.setBackgroundColor(Color.WHITE);
                settings.setBackgroundColor(Color.WHITE);
                settingsText.setBackgroundColor(Color.WHITE);


            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new HistoryFragment())
                        .commit();


                tag.setBackgroundColor(Color.WHITE);
                tagText.setBackgroundColor(Color.WHITE);
                history.setBackgroundColor(Color.parseColor("#7a9aea"));
                historyText.setBackgroundColor(Color.parseColor("#7a9aea"));
                settings.setBackgroundColor(Color.WHITE);
                settingsText.setBackgroundColor(Color.WHITE);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new SettingsFragment())
                        .commit();

                tag.setBackgroundColor(Color.WHITE);
                tagText.setBackgroundColor(Color.WHITE);
                history.setBackgroundColor(Color.WHITE);
                historyText.setBackgroundColor(Color.WHITE);
                settings.setBackgroundColor(Color.parseColor("#7a9aea"));
                settingsText.setBackgroundColor(Color.parseColor("#7a9aea"));
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


    }

    @Override
    public void onConnected(Bundle bundle)
    {
        Location location = mLocationClient.getLastLocation();

        if(location == null)
        {
            // mock location
            //lat = 47.905034;
            //lon = 106.924488;

            DialogManager dialogManager = new DialogManager(MainActivity.this);
            dialogManager.showGPSWarning();
        }

        else
        {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("종료");
            alertDialogBuilder.setMessage("앱을 종료하시겠습니까?");
            alertDialogBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });
            alertDialogBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialogBuilder.show();
        }

        return super.onKeyDown(keyCode, event);
    }
}
