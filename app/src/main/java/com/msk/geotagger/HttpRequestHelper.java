package com.msk.geotagger;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/*
 * 
 * author 이준원
 * since 2014-05-11
 * update 2014-05-11 (이준원)
 * charset UTF-8
 */
public class HttpRequestHelper 
{	@SuppressWarnings("unchecked")

    private Context mContext;

    public HttpRequestHelper(Context context)
    {
        mContext = context;
    }

    //private static final String host = "http://192.237.166.7";
    private static final String host = "http://192.168.0.24:8000";

	public HttpResponse sendLocation(Location loc)
	{
        String url = host + "/api/0.1/location/";

		HttpPost request = new HttpPost(url);

        DBAdapter db = new DBAdapter(mContext);
        Settings settings = db.getSettings();

		request.setHeader("Accept", "application/json");
		request.setHeader("Content-Type", "application/json");
		request.setHeader("Authorization", "ApiKey "+ settings.getUsername() + ":" + settings.getApiKey() );


        JSONObject jobj = new JSONObject();

        try
        {
            jobj.put("evanType", loc.getEvanType());
            jobj.put("trainType", loc.getTrainType());
            jobj.put("mercyType", loc.getMercyType());
            jobj.put("youthType", loc.getYouthType());
            jobj.put("campusType", loc.getCampusType());
            jobj.put("indigenousType", loc.getIndigenousType());
            jobj.put("prisonType", loc.getPrisonType());
            jobj.put("prostitutesType", loc.getProstitutesType());
            jobj.put("orphansType", loc.getOrphansType());
            jobj.put("womenType", loc.getWomenType());
            jobj.put("urbanType", loc.getUrbanType());
            jobj.put("hospitalType", loc.getHospitalType());
            jobj.put("mediaType", loc.getMediaType());
            jobj.put("communityDevType", loc.getCommunityDevType());
            jobj.put("bibleStudyType", loc.getBibleStudyType());
            jobj.put("churchPlantingType", loc.getChurchPlantingType());
            jobj.put("artsType", loc.getArtsType());
            jobj.put("counselingType", loc.getCounselingType());
            jobj.put("healthcareType", loc.getHealthcareType());
            jobj.put("constructionType", loc.getConstructionType());
            jobj.put("researchType", loc.getResearchType());

            jobj.put("desc", loc.getDesc());

            jobj.put("tags", loc.getTags());

            jobj.put("contactConfirmed", loc.getContactConfirmed());

            jobj.put("contactEmail", loc.getContactEmail());
            jobj.put("contactPhone", loc.getContactPhone());
            jobj.put("contactWebsite", loc.getContactWebsite());

            jobj.put("latitude", loc.getLatitude());
            jobj.put("longitude", loc.getLongitude());

            jobj.put("created", loc.getCreated());
        }
		
		catch (JSONException e2) 
		{
			e2.printStackTrace();
		}
		
		
		
		
		try 
		{
			String jsonStr = jobj.toString();
			StringEntity se = new StringEntity(jsonStr, HTTP.UTF_8);

			se.setContentType("application/json");
			request.setEntity(se);
			
			
			HttpClient client = new DefaultHttpClient();

			
			HttpResponse response = client.execute(request);

            return response;
		}
		
		catch (UnsupportedEncodingException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
			return null;
		}
		
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
		
	}

    public void uploadImage(Uri image)
    {
        String url = host+ "/m/locpic";

        //HttpPost request = new HttpPost(url);

        DBAdapter db = new DBAdapter(mContext);
        Settings settings = db.getSettings();


        //request.setHeader("Accept", "application/json");
        //request.setHeader("Content-Type", "application/json");
        //request.setHeader("Authorization", "ApiKey "+ settings.getUsername() + ":" + settings.getApiKey() );


        String[] imageRealPath = getRealPathFromURI(image).split("/");
        pictureFileName = imageRealPath[imageRealPath.length-1];

        try
        {
            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), image);


        }

        catch (IOException e)
        {

        }
    }

    static String CRLF = "\r\n";
    static String twoHyphens = "--";
    static String boundary = "----------V4xnHDg04ehbqgZCaMO5jx";



    private String pictureFileName = null;
    private DataOutputStream dataStream = null;

    private String TAG = "멀티파트 테스트";


    /**
     * write one form field to dataSream
     * @param fieldName
     * @param fieldValue
     */

    private void writeFormField(String fieldName, String fieldValue)  {

        try
        {

            dataStream.writeBytes(twoHyphens + boundary + CRLF);
            dataStream.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\"" + CRLF);
            dataStream.writeBytes(CRLF);
            dataStream.writeBytes(fieldValue);
            dataStream.writeBytes(CRLF);
        }
        catch(Exception e)
        {
            //System.out.println("AndroidUploader.writeFormField: got: " + e.getMessage());
            Log.e(TAG, "AndroidUploader.writeFormField: " + e.getMessage());
        }

    }

    public String getRealPathFromURI(Uri contentUri)
    {
        Cursor cursor = null;
        try
        {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = mContext.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }

        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
    }
}
