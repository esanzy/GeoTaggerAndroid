package com.msk.geotagger;

import android.app.Activity;
import android.net.Uri;
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

        int numSyncData = settings.getNumSyncData();
        String txtSyncData = "You need to sync " + numSyncData + " data";

        TextView tvSyncData = (TextView)v.findViewById(R.id.txtSyncData);
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
    }
}
