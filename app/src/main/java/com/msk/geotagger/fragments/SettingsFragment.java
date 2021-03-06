package com.msk.geotagger.fragments;

import android.app.ProgressDialog;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.msk.geotagger.model.Location;
import com.msk.geotagger.utils.DBAdapter;
import com.msk.geotagger.R;
import com.msk.geotagger.model.Settings;
import com.msk.geotagger.utils.DialogManager;
import com.msk.geotagger.utils.SendDataTask;
import com.msk.geotagger.utils.SendPicTask;
import com.msk.geotagger.utils.SyncPicTask;

import org.apache.http.HttpResponse;
import java.io.File;
import java.util.List;


public class SettingsFragment extends Fragment
{

    private File tmpFile;
    private ProgressDialog progressDialog;
    private List<Location> list;
    private DBAdapter db;
    private TextView tvSyncData;

    private JsonArray jsonArray;

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

        db = new DBAdapter(getActivity());
        final Settings settings = db.getSettings();

        final CheckBox chkOffline = (CheckBox)v.findViewById(R.id.chkOffline);





        if(settings.getOffline() == 0) { chkOffline.setChecked(false); }

        else { chkOffline.setChecked(true); }




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




        tvSyncData = (TextView)v.findViewById(R.id.txtSyncData);
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


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Wait...");
        progressDialog.setMessage("Sending Location Info...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);


        final TextView sync = (TextView) v.findViewById(R.id.sync);
        sync.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {


                // api key check
                if( settings.getUsername() == null || "".equals(settings.getUsername().trim()) || settings.getApiKey() == null || "".equals(settings.getApiKey().trim()))
                {
                    // show dialog
                    DialogManager dialogManager = new DialogManager(getActivity());
                    dialogManager.showApiKeyMissingDialog();
                }

                // api key 가 있음
                else {

                    list = db.selectUnsyncedLocation();

                    //progressDialog.show();


                    jsonArray = new JsonArray();
                    for ( int i = 0; i < list.size(); i++ ) {


                        Location loc = list.get(i);

                        String imagePath = loc.getPhotoRealPath();


                        // 사진이 있는 경우
                        if (imagePath != null) {

                            tmpFile = new File(imagePath);
                            loc.setPhotoId(tmpFile.getName());

                            JsonObject json = new JsonObject();
                            json.addProperty("credential", db.getSettings().getUsername() + ":" + db.getSettings().getApiKey());
                            json.addProperty("username", db.getSettings().getUsername());
                            json.addProperty("filepath", tmpFile.getAbsolutePath());


                            sendLocation(loc);

                            if(tmpFile != null)
                            {
                                jsonArray.add(json);
                                //sendPhoto(json, progressDialog/*, loc*/);
                            }
                        }

                        // 사진이 없는 경우
                        else {

                            sendLocation(loc);
                        }

                    } // for
                    sendPhoto(jsonArray);
                    //progressDialog.dismiss();
                } // api key check
            } // onClick
        }); // setOnClickListener
    } // onActivityCreated

    private void sendPhoto(JsonArray json)
    {
        if(!progressDialog.isShowing())
            progressDialog.show();

        SyncPicTask task = new SyncPicTask() {
            @Override
            protected void onPostExecute(Void aVoid)
            {
                super.onPostExecute(aVoid);

                if(progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        };
        task.execute(json);
    }

    private void sendLocation(Location loc)
    {
        JsonObject json = loc.toJSON();
        json.addProperty("credential", db.getSettings().getUsername()+":"+db.getSettings().getApiKey());

        SendLocationTask task = new SendLocationTask();
        task.setLoc(loc);
        task.execute(json);
    }


    private class SendLocationTask extends SendDataTask
    {
        private Location loc;

        protected void setLoc(Location loc)
        {
            this.loc = loc;
        }

        @Override
        protected void onPostExecute(HttpResponse httpResponse) {


            if(httpResponse.getStatusLine().getStatusCode() == 401)
            {
                DialogManager dialogManager = new DialogManager(getActivity());
                dialogManager.showApiKeyInvalidDialog();
            }

            else {
                db.setDataSynced(loc.getRowid());

                int numSyncData = db.countUnsyncedData();
                String txtSyncData = "You need to sync " + numSyncData + " data";

/*
                if(numSyncData == 0)
                {
                    if(progressDialog.isShowing()) progressDialog.dismiss();
                }
*/
                tvSyncData.setText(txtSyncData);
                tvSyncData.invalidate();

            }


            super.onPostExecute(httpResponse);
        }
    }
}
