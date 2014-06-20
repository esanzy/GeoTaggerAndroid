package com.msk.geotagger.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by junwon on 14. 6. 20.
 */
public class FileUtil
{
    private Context mContext;

    public FileUtil(Context context)
    {
        mContext = context;
    }

    public File SaveBitmapToFile(Bitmap bitmap, String filename)
    {
        File dcim =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        Log.d("FileUtil", dcim.getPath() + "/geotagger");

        File photodirpath = new File(dcim.getPath() + "/geotagger");
        if( !photodirpath.exists() )
        {
            photodirpath.mkdir();
        }

        File photofilepath = new File(photodirpath.getPath() + "/" + filename);
        OutputStream out = null;

        try
        {
            photofilepath.createNewFile();
            out = new FileOutputStream(photofilepath);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            return photofilepath;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(out != null)
                    out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

}