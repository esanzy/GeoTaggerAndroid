package com.msk.geotagger;

import android.content.Context;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

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
	public HttpResponse sendLocation(Location loc, Context mContext)
	{
        String url1="http://192.237.166.7/api/0.1/location/";
        //String url2="http://192.168.0.24:8000/api/0.1/location/";
        //String url3="http://10.0.2.2:8000/api/0.1/location/";
		HttpPost request = new HttpPost(url1);

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
}
