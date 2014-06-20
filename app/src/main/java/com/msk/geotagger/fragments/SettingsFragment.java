package com.msk.geotagger.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.msk.geotagger.model.Location;
import com.msk.geotagger.utils.DBAdapter;
import com.msk.geotagger.R;
import com.msk.geotagger.model.Settings;
import com.msk.geotagger.utils.FileUtil;
import com.msk.geotagger.utils.Server;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;


public class SettingsFragment extends Fragment
{

    public SettingsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();

        final DBAdapter db = new DBAdapter(getActivity());
        Settings settings = db.getSettings();

        final CheckBox chkOffline = (CheckBox)v.findViewById(R.id.chkOffline);

        if(settings.getOffline() == 0)
        {
            chkOffline.setChecked(false);
        }
        else
        {
            chkOffline.setChecked(true);
        }

        chkOffline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                Settings param = db.getSettings();
                if(isChecked)
                {
                    param.setOffline(1);
                }
                else
                {
                    param.setOffline(0);
                }
                db.editSettings(param);
            }
        });




        Log.d("환경설정", "" + settings.getOffline());

        final EditText etUsername = (EditText)v.findViewById(R.id.etUsername);
        final EditText etApiKey = (EditText)v.findViewById(R.id.etApiKey);

        if(settings.getUsername() != null)
        {
            etUsername.setText(settings.getUsername());
            etUsername.setEnabled(false);
        }

        Log.d("환경설정", ""+settings.getUsername());

        if(settings.getApiKey() != null)
        {
            etApiKey.setText(settings.getApiKey());
            etApiKey.setEnabled(false);
        }

        Log.d("환경설정", ""+settings.getApiKey());

        int numSyncData = db.countUnsyncedData();
        String txtSyncData = "You need to sync " + numSyncData + " data";

        final TextView tvSyncData = (TextView)v.findViewById(R.id.txtSyncData);
        tvSyncData.setText(txtSyncData);

        final TextView txtEditUserInfo = (TextView)v.findViewById(R.id.txtEditUserInfo);
        txtEditUserInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!txtEditUserInfo.getText().equals("Save"))
                {
                    etUsername.setEnabled(true);
                    etApiKey.setEnabled(true);
                    txtEditUserInfo.setText("Save");
                }
                else
                {
                    Settings param = db.getSettings();
                    param.setUsername(etUsername.getText().toString());
                    param.setApiKey(etApiKey.getText().toString());
                    etUsername.setEnabled(false);
                    etApiKey.setEnabled(false);

                    db.editSettings(param);
                    txtEditUserInfo.setText("Edit User Info");
                }
            }
        });

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Wait...");
        progressDialog.setMessage("Uploading photo");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);


        final TextView sync = (TextView) v.findViewById(R.id.sync);
        sync.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                List<Location> list = db.selectUnsyncedLocation();

                final Settings settings = db.getSettings();

                for(int i = 0; list != null && i < list.size(); i++)
                {
                    final Location loc = list.get(i);

                    if(loc.getPhotoRealPath() != null)
                    {
                        Bitmap bm = BitmapFactory.decodeFile(loc.getPhotoRealPath());

                        // 임시파일 생성
                        FileUtil fileUtil = new FileUtil(getActivity());
                        final String photoFileName = db.getSettings().getUsername() + new Timestamp(System.currentTimeMillis()).toString()+".jpg";
                        final File photoFile = fileUtil.SaveBitmapToFile(bm, photoFileName);


                        progressDialog.show();

                        Ion.with(getActivity(), Server.host + "/m/locpic/")
                                .setHeader("Authorization", "ApiKey " + settings.getUsername() + ":" + settings.getApiKey())
                                .uploadProgressDialog(progressDialog)
                                .setMultipartParameter("username", settings.getUsername())
                                .setMultipartFile("pic", "image/jpeg", photoFile)
                                .asJsonObject()
                                .setCallback(new FutureCallback<JsonObject>() {
                                    @Override
                                    public void onCompleted(Exception e, JsonObject result) {
                                        Log.d("파일업로드", "업로드");
                                        progressDialog.dismiss();

                                        loc.setPhotoId(photoFileName);

                                        //// 파일 업로드 완료 ////
                                        photoFile.delete();


                                        Ion.with(getActivity())
                                                .load(Server.host + "/api/0.1/location/")
                                                .setHeader("Authorization", "ApiKey " + settings.getUsername() + ":" + settings.getApiKey())
                                                .setJsonObjectBody(loc.toJSON())
                                                .asJsonObject()
                                                .setCallback(new FutureCallback<JsonObject>() {
                                                    @Override
                                                    public void onCompleted(Exception e, JsonObject result) {
                                                        db.setDataSynced(loc.getRowid());
                                                    }
                                                });
                                    }
                                }); // Ion
                    }

                    else
                    {
                        Ion.with(getActivity())
                                .load(Server.host + "/api/0.1/location/")
                                .setHeader("Authorization", "ApiKey " + settings.getUsername() + ":" + settings.getApiKey())
                                .setJsonObjectBody(loc.toJSON())
                                .asJsonObject()
                                .setCallback(new FutureCallback<JsonObject>() {
                                    @Override
                                    public void onCompleted(Exception e, JsonObject result) {
                                        db.setDataSynced(loc.getRowid());
                                    }
                                });
                    }



                }

                int numSyncData = db.countUnsyncedData();
                String txtSyncData = "You need to sync " + numSyncData + " data";

                tvSyncData.setText(txtSyncData);
                tvSyncData.invalidate();
            }
        });
    } // onActivityCreated
}
