package com.msk.geotagger.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by junwon on 14. 6. 27.
 */
public class DialogManager
{
    private Context mContext;

    public DialogManager(Context context)
    {
        mContext = context;
    }

    public void showApiKeyMissingDialog()
    {
        int identifier = mContext.getResources().getIdentifier("auth_error", "string", "com.msk.geotagger");

        String auth_error = "Authentication Error";
        if(identifier != 0)
        {
            auth_error = mContext.getResources().getString(identifier);
        }

        identifier =  mContext.getResources().getIdentifier("apikey_missing", "string", "com.msk.geotagger");

        String apikey_missing = "Username or Api Key is missing !!";

        if(identifier != 0)
        {
            apikey_missing = mContext.getResources().getString(identifier);
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(auth_error);
        alertDialogBuilder.setMessage(apikey_missing);
        alertDialogBuilder.setPositiveButton("확인", dismissListener);
        alertDialogBuilder.show();
    }

    public void showApiKeyInvalidDialog()
    {
        int identifier = mContext.getResources().getIdentifier("auth_error", "string", "com.msk.geotagger");

        String auth_error = "Authentication Error";
        if(identifier != 0)
        {
            auth_error = mContext.getResources().getString(identifier);
        }

        identifier = mContext.getResources().getIdentifier("apikey_missing", "string", "com.msk.geotagger");

        String apikey_missing = "Username or Api Key is missing !!";

        if(identifier != 0)
        {
            apikey_missing = mContext.getResources().getString(identifier);
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(auth_error);
        alertDialogBuilder.setMessage(apikey_missing);
        alertDialogBuilder.setPositiveButton("확인", dismissListener);
        alertDialogBuilder.show();
    }


    private DialogInterface.OnClickListener dismissListener = new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    };

}
