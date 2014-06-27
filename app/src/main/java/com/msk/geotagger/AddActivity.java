package com.msk.geotagger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.msk.geotagger.model.Location;
import com.msk.geotagger.model.Settings;
import com.msk.geotagger.utils.DBAdapter;
import com.msk.geotagger.utils.DialogManager;
import com.msk.geotagger.utils.FileUtil;
import com.msk.geotagger.utils.SendDataTask;
import com.msk.geotagger.utils.SendPicTask;


import org.apache.http.HttpResponse;

import java.io.File;
import java.sql.Timestamp;


public class AddActivity extends Activity {

    private double latitude;
    private double longitude;
    private ImageView myImage;
    private DBAdapter dba;
    private Settings settings;
    private String photoFileName;           // 글로벌리 유니크한 이름의 임시파일
    private File tmpFile;
    private Location loc;
    private Bitmap myImageBitmap;
    private ProgressDialog progressDialog;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);

        myImage = (ImageView) findViewById(R.id.myImage);

        if(savedInstanceState != null)
        {

            if(savedInstanceState.containsKey("imageBitmap"))
            {
                myImageBitmap = savedInstanceState.getParcelable("imageBitmap");
                myImage.setImageBitmap(myImageBitmap);
                myImage.invalidate();
            }

        }


        dba = new DBAdapter(AddActivity.this);
        settings = dba.getSettings();


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

        View.OnFocusChangeListener keyboardDissmissListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus)
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        };



        // 질문 3
        final EditText editText1 = (EditText)findViewById(R.id.editText1); // description
        editText1.setOnFocusChangeListener(keyboardDissmissListener);
        // 질문 4
        final EditText editText2 = (EditText)findViewById(R.id.EditText2); // keywords (comma)
        editText2.setOnFocusChangeListener(keyboardDissmissListener);

        // 질문 5 - 정보 수집 동의
        final CheckBox chkQ5 = (CheckBox)findViewById(R.id.chk_q5);

        final EditText editText3 = (EditText)findViewById(R.id.editText3); // email
        final EditText editText4 = (EditText)findViewById(R.id.editText4); // phone
        final EditText editText5 = (EditText)findViewById(R.id.editText5); // website

        editText3.setOnFocusChangeListener(keyboardDissmissListener);
        editText4.setOnFocusChangeListener(keyboardDissmissListener);
        editText5.setOnFocusChangeListener(keyboardDissmissListener);

        TextView btnCancel = (TextView)findViewById(R.id.btn_cancel);
        TextView btnSave = (TextView)findViewById(R.id.btn_save);

        final LinearLayout form = (LinearLayout) findViewById(R.id.form);



        form.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                return false;
            }
        });



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


        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Wait...");
        progressDialog.setMessage("Sending Location Info...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);




        /**
         * @since 2014-05-08
         * @update 2014-05-08 이준원
         */
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                loc = new Location();

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
                    loc.setIndigenousType(1);
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

                loc.setCreated(new Timestamp(System.currentTimeMillis()));
                Log.d("created", loc.getCreatedTimestamp().toString());

				/* http://shstarkr.tistory.com/158 참고 */
                ConnectivityManager cManager;
                NetworkInfo mobile;
                NetworkInfo wifi;

                cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                //3G 또는 WiFi 에 연결되어 있을 경우
                if(settings.getOffline() == 0 && (mobile.isConnected() || wifi.isConnected()))
                {

                    // api key 가 없을 때
                    if( settings.getUsername() == null || "".equals(settings.getUsername().trim()) || settings.getApiKey() == null || "".equals(settings.getApiKey().trim()))
                    {
                        // show dialog
                        DialogManager dialogManager = new DialogManager(AddActivity.this);
                        dialogManager.showApiKeyMissingDialog();

                    }

                    // 설정에 username 항목과 api key 항목이 저장되어 있을 경우
                    else
                    {
                        progressDialog.show();
                        //사진이 있을 때
                        if (myImageBitmap != null)
                        {
                            Log.d("사진 전송", "Bitmap을 이용해 사진 정보 전송");
                            photoFileName = dba.getSettings().getUsername() + "_" + Long.toString(loc.getCreatedTimestamp().getTime()) + ".jpg";

                            FileUtil fileUtil = new FileUtil(AddActivity.this);
                            tmpFile = fileUtil.SaveBitmapToFile(myImageBitmap, photoFileName);

                            loc.setPhotoRealPath(tmpFile.getAbsolutePath());

                            JsonObject json = new JsonObject();
                            json.addProperty("credential", dba.getSettings().getUsername()+":"+dba.getSettings().getApiKey());
                            json.addProperty("username", dba.getSettings().getUsername());
                            json.addProperty("filepath", tmpFile.getAbsolutePath());

                            sendPhoto(json);
                        }

                        // 사진파일이 없을 때
                        else
                        {
                            sendLocation();

                            loc.setSync(1);
                            dba.insertLocation(loc);
                        }
                    } // api 키 관련 if
                } // 인터넷 접속 관련 if



                // 인터넷이 안될 때
                else
                {
                    // api key check
                    if( settings.getUsername() == null || "".equals(settings.getUsername().trim()) || settings.getApiKey() == null || "".equals(settings.getApiKey().trim()))
                    {
                        // show dialog
                        DialogManager dialogManager = new DialogManager(AddActivity.this);
                        dialogManager.showApiKeyMissingDialog();
                    }

                    else {

                        if (myImageBitmap != null) {
                            Log.d("사진 전송", "Bitmap을 이용해 사진 정보 전송");
                            photoFileName = dba.getSettings().getUsername() + "_" + Long.toString(loc.getCreatedTimestamp().getTime()) + ".jpg";

                            FileUtil fileUtil = new FileUtil(AddActivity.this);
                            tmpFile = fileUtil.SaveBitmapToFile(myImageBitmap, photoFileName);

                            loc.setPhotoRealPath(tmpFile.getAbsolutePath());

                        }

                        loc.setSync(0);
                        dba.insertLocation(loc);

                        finish();
                    }
                }
            }
        });
        // setOnClickListener
    } // onCreate





    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(extras != null)
        {
            outState.putParcelable("imageBitmap", extras.getParcelable("data"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            if( requestCode == 81 || requestCode == 82 )
            {

                Uri imageURI = data.getData();

                Intent cropIntent = new Intent("com.android.camera.action.CROP");
                cropIntent.setDataAndType(imageURI, "image/*");
                //set crop properties
                cropIntent.putExtra("crop", "true");
                //indicate aspect of desired crop
                cropIntent.putExtra("aspectX", 1);
                cropIntent.putExtra("aspectY", 1);
                //indicate output X and Y
                cropIntent.putExtra("outputX", 256);
                cropIntent.putExtra("outputY", 256);
                //retrieve data on return
                cropIntent.putExtra("return-data", true);
                //start the activity - we handle returning in onActivityResult
                startActivityForResult(cropIntent, 88);

            } // else if 81  || 82

            else if ( requestCode == 88 )
            {
                //get the returned data
                extras = data.getExtras();
                //get the cropped bitmap
                myImageBitmap = extras.getParcelable("data");

                myImage.setImageBitmap(myImageBitmap);
                myImage.setScaleType(ImageView.ScaleType.FIT_START);
            }

        } // if RESULT_OK
    } // onActivityResult





    private void sendLocation()
    {
        JsonObject json = loc.toJSON();
        json.addProperty("credential", dba.getSettings().getUsername()+":"+dba.getSettings().getApiKey());
        new SendLocationTask().execute(json);
    }




    private void sendPhoto(JsonObject json)
    {
        new SendPhotoTask().execute(json);
    }



    private class SendLocationTask extends SendDataTask
    {
        @Override
        protected void onPostExecute(HttpResponse httpResponse) {
            super.onPostExecute(httpResponse);

            if(httpResponse.getStatusLine().getStatusCode() == 401)
            {
                DialogManager dialogManager = new DialogManager(AddActivity.this);
                dialogManager.showApiKeyInvalidDialog();

                if(progressDialog.isShowing())
                    progressDialog.dismiss();
            }

            else {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();

                loc.setSync(1);
                dba.insertLocation(loc);

                AddActivity.this.finish();
            }
        }
    }






    private class SendPhotoTask extends SendPicTask
    {
        @Override
        protected void onPostExecute(HttpResponse httpResponse) {
            super.onPostExecute(httpResponse);


            if(httpResponse.getStatusLine().getStatusCode() == 401)
            {
                DialogManager dialogManager = new DialogManager(AddActivity.this);
                dialogManager.showApiKeyInvalidDialog();

                if(progressDialog.isShowing())
                    progressDialog.dismiss();
            }

            else {
                loc.setPhotoId(photoFileName);

                sendLocation();
            }
        }
    }
}
