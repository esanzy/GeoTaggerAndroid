package com.msk.geotagger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;


public class AddActivity extends Activity {

    private double latitude;
    private double longitude;

    private ImageView myImage;
    private Uri imageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //StrictMode.enableDefaults();

        /**
         * @since 2014-05-08
         * @update 2014-05-08 이준원
         */
        // 질문 1
        final CheckBox chkQ1_1 = (CheckBox)findViewById(R.id.chk_q1_1); // Evangelism
        final CheckBox chkQ1_2 = (CheckBox)findViewById(R.id.chk_q1_2); // Training
        final CheckBox chkQ1_3 = (CheckBox)findViewById(R.id.chk_q1_3); // Mercy

        // 질문 2
        final CheckBox chkQ2_1 = (CheckBox)findViewById(R.id.chk_q2_1); // Youth / Children
        final CheckBox chkQ2_2 = (CheckBox)findViewById(R.id.chk_q2_2); // Campus Ministry
        final CheckBox chkQ2_3 = (CheckBox)findViewById(R.id.chk_q2_3); // Indigenous People
        final CheckBox chkQ2_4 = (CheckBox)findViewById(R.id.chk_q2_4); // Prison Ministry
        final CheckBox chkQ2_5 = (CheckBox)findViewById(R.id.chk_q2_5); // Prostitutes
        final CheckBox chkQ2_6 = (CheckBox)findViewById(R.id.chk_q2_6); // Orphans
        final CheckBox chkQ2_7 = (CheckBox)findViewById(R.id.chk_q2_7); // Woman
        final CheckBox chkQ2_8 = (CheckBox)findViewById(R.id.chk_q2_8); // Urban Ministry
        final CheckBox chkQ2_9 = (CheckBox)findViewById(R.id.chk_q2_9); // Hospital Ministry
        final CheckBox chkQ2_10 = (CheckBox)findViewById(R.id.chk_q2_10); // Media / Communications
        final CheckBox chkQ2_11 = (CheckBox)findViewById(R.id.chk_q2_11); // Community Development
        final CheckBox chkQ2_12 = (CheckBox)findViewById(R.id.chk_q2_12); // Bible Studies
        final CheckBox chkQ2_13 = (CheckBox)findViewById(R.id.chk_q2_13); // Church Planting
        final CheckBox chkQ2_14 = (CheckBox)findViewById(R.id.chk_q2_14); // Arts / Entertainment / Sports
        final CheckBox chkQ2_15 = (CheckBox)findViewById(R.id.chk_q2_15); // Counseling
        final CheckBox chkQ2_16 = (CheckBox)findViewById(R.id.chk_q2_16); // Healthcare
        final CheckBox chkQ2_17 = (CheckBox)findViewById(R.id.chk_q2_17); // Maintenance / Construction
        final CheckBox chkQ2_18 = (CheckBox)findViewById(R.id.chk_q2_18); // Research

        // 질문 3
        final EditText editText1 = (EditText)findViewById(R.id.editText1); // description

        // 질문 4
        final EditText editText2 = (EditText)findViewById(R.id.EditText2); // keywords (comma)

        // 질문 5 - 정보 수집 동의
        final CheckBox chkQ5 = (CheckBox)findViewById(R.id.chk_q5);

        final EditText editText3 = (EditText)findViewById(R.id.editText3); // email
        final EditText editText4 = (EditText)findViewById(R.id.editText4); // phone
        final EditText editText5 = (EditText)findViewById(R.id.editText5); // website

        TextView btnCancel = (TextView)findViewById(R.id.btn_cancel);
        TextView btnSave = (TextView)findViewById(R.id.btn_save);

        myImage = (ImageView) findViewById(R.id.myImage);

        Intent i = getIntent();
        latitude = i.getDoubleExtra("latitude", 0);
        longitude = i.getDoubleExtra("longitude", 0);


        TextView btnTakePhoto = (TextView)findViewById(R.id.btn_take_photo);
        TextView btnSelectPhoto = (TextView)findViewById(R.id.btn_select_photo);

        btnTakePhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 81);
            }
        });

        btnSelectPhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 82);
            }
        });


        Log.d("위도", ""+latitude);
        Log.d("경도", ""+longitude);



        btnCancel.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                finish();
            }
        });

        /**
         * @since 2014-05-08
         * @update 2014-05-08 이준원
         */
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Location loc = new Location();

                // Question 1
                if(chkQ1_1.isChecked())
                {
                    loc.setEvanType(1);
                }

                if(chkQ1_2.isChecked())
                {
                    loc.setTrainType(1);
                }

                if(chkQ1_3.isChecked())
                {
                    loc.setMercyType(1);
                }

                // Question 2
                if(chkQ2_1.isChecked())
                {
                    loc.setYouthType(1);
                }

                if(chkQ2_2.isChecked())
                {
                    loc.setCampusType(1);
                }

                if(chkQ2_3.isChecked())
                {
                    loc.setIndigineousType(1);
                }

                if(chkQ2_4.isChecked())
                {
                    loc.setPrisonType(1);
                }

                if(chkQ2_5.isChecked())
                {
                    loc.setProstitutesType(1);
                }

                if(chkQ2_6.isChecked())
                {
                    loc.setOrphansType(1);
                }

                if(chkQ2_7.isChecked())
                {
                    loc.setWomenType(1);
                }

                if(chkQ2_8.isChecked())
                {
                    loc.setUrbanType(1);
                }

                if(chkQ2_9.isChecked())
                {
                    loc.setHospitalType(1);
                }

                if(chkQ2_10.isChecked())
                {
                    loc.setMediaType(1);
                }

                if(chkQ2_11.isChecked())
                {
                    loc.setCommunityDevType(1);
                }

                if(chkQ2_12.isChecked())
                {
                    loc.setBibleStudyType(1);
                }

                if(chkQ2_13.isChecked())
                {
                    loc.setChurchPlantingType(1);
                }

                if(chkQ2_14.isChecked())
                {
                    loc.setArtsType(1);
                }

                if(chkQ2_15.isChecked())
                {
                    loc.setCounselingType(1);
                }

                if(chkQ2_16.isChecked())
                {
                    loc.setHealthcareType(1);
                }

                if(chkQ2_17.isChecked())
                {
                    loc.setConstructionType(1);
                }

                if(chkQ2_18.isChecked())
                {
                    loc.setResearchType(1);
                }

                loc.setDesc(editText1.getText().toString());
                loc.setTags(editText2.getText().toString());

                // Question 5 - yes | no
                if(chkQ5.isChecked())
                {
                    loc.setContactConfirmed(1);
                }

                loc.setContactEmail(editText3.getText().toString());
                loc.setContactPhone(editText4.getText().toString());
                loc.setContactWebsite(editText5.getText().toString());

                loc.setLatitude(latitude);
                loc.setLongitude(longitude);

                loc.setCreated(new Timestamp(new Date().getTime()));



                DBAdapter dba = new DBAdapter(AddActivity.this);
                Settings settings = dba.getSettings();

				/* http://shstarkr.tistory.com/158 참고 */
                ConnectivityManager cManager;
                NetworkInfo mobile;
                NetworkInfo wifi;

                cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if(settings.getOffline() == 0 && (mobile.isConnected() || wifi.isConnected()))
                {
                    //3G 또는 WiFi 에 연결되어 있을 경우
                    //HttpRequestHelper postHelper = new HttpRequestHelper();
                    new AddActivityTask().execute(loc);

                    dba.insertLocation(loc, true);
                }

                else
                {
                    dba.insertLocation(loc, false);
                }



                finish();
            }
        });
        // setOnClickListener
    } // onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            if(requestCode == 81 || requestCode == 82)
            {
                Uri currImageURI = data.getData();

                imageURI = currImageURI;
                //myImage.setImageURI(currImageURI);
            }
        }

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (imageURI != null)
        {

            try
            {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageURI);
                myImage.setImageBitmap(bmp);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            //myImage.setImageURI(imageURI);
        }
    }

    private class AddActivityTask extends AsyncTask<Location, Void, HttpResponse>
    {

        @Override
        protected HttpResponse doInBackground(Location... params)
        {
            HttpRequestHelper helper = new HttpRequestHelper();
            return helper.sendLocation(params[0], AddActivity.this);
        }

        @Override
        protected void onPostExecute(HttpResponse httpResponse) {
            super.onPostExecute(httpResponse);

            if(httpResponse != null)
            {
                Log.i("응답", ""+httpResponse.getStatusLine().getStatusCode());
            }
        }
    }
}