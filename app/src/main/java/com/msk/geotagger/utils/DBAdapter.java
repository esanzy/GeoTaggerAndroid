package com.msk.geotagger.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.msk.geotagger.model.Location;
import com.msk.geotagger.model.Settings;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/*
 * 
 * author 이준원
 * since 2014-05-07
 * update 2014-05-08 이준원
 * reference http://whitememo.tistory.com/241
 * charset UTF-8
 */
public class DBAdapter 
{
	static final String DB_NAME = "geo_tagger";
	
	//데이터 베이스를 이용하는 컨텍스트  
    private Context context;  
    //데이터 연동객체   
    private SQLiteDatabase db;  
    
    public DBAdapter(Context context) 
    {
    	this.context = context;
    }
    
    public long insertLocation(Location loc)
    {
    	long result;
    	
    	try
    	{
    		db = (new DBHelper(context).getWritableDatabase());
    		
	    	ContentValues values = new ContentValues();
	    	
	    	values.put("latitude", loc.getLatitude());
	    	values.put("longitude", loc.getLongitude());
	    	
	    	values.put("evanType", loc.getEvanType());
            values.put("trainType", loc.getTrainType());
            values.put("mercyType", loc.getMercyType());

            values.put("youthType", loc.getYouthType());
            values.put("campusType", loc.getCampusType());
            values.put("indigenousType", loc.getIndigenousType());
            values.put("prisonType", loc.getPrisonType());
            values.put("prostitutesType", loc.getProstitutesType());
            values.put("orphansType", loc.getOrphansType());
            values.put("womenType", loc.getWomenType());
            values.put("urbanType", loc.getUrbanType());
            values.put("hospitalType", loc.getHospitalType());
            values.put("mediaType", loc.getMediaType());
            values.put("communityDevType", loc.getCommunityDevType());
            values.put("bibleStudyType", loc.getBibleStudyType());
            values.put("churchPlantingType", loc.getChurchPlantingType());
            values.put("artsType", loc.getArtsType());
            values.put("counselingType", loc.getCounselingType());
            values.put("healthcareType", loc.getHealthcareType());
            values.put("constructionType", loc.getConstructionType());
            values.put("researchType", loc.getResearchType());

	    	values.put("desc", loc.getDesc());
            values.put("tags", loc.getTags());
            values.put("contactConfirmed", loc.getContactConfirmed());

	    	values.put("photoId", loc.getPhotoId());
            values.put("photoRealPath", loc.getPhotoRealPath());

	    	values.put("contactEmail", loc.getContactEmail());
	    	values.put("contactPhone", loc.getContactPhone());
	    	values.put("contactWebsite", loc.getContactWebsite());
            values.put("sync", loc.getSync());

            values.put("created", loc.getCreatedTimestamp().getTime());
            Log.d("created2", loc.getCreatedTimestamp().toString());
            Log.d("created time2", ""+loc.getCreatedTimestamp().getTime());

	    	result = db.insert(DB_NAME,null,values);
	    	
	    	return result;
    	}
    	
    	catch(SQLException e)
    	{
    		return -1;
    	}
    	
    	finally
    	{
            if(db != null && db.isOpen())
            {
                db.close();
            }
    	}
    }

    public List<Location> selectAllLocation()
    {
        return selectLocations("select * from geo_tagger");
    }

    public List<Location> selectUnsyncedLocation()
    {
        return selectLocations("select * from geo_tagger where sync = 0");
    }


    public Location selectLocation(int rowid)
    {
        List<Location> list = selectLocations("select * from geo_tagger where rowid = " + rowid);

        if(list.size() > 0)
        {
            return list.get(0);
        }

        else
        {
            return null;
        }
    }

    /**
     * @since 2014-05-08 이준원
     * update 2014-05-08 이준원
     * 
     */
    public List<Location> selectLocations(String sql)
    {
    	List<Location> result = new ArrayList<Location>();
    	
    	try
    	{
    		db = (new DBHelper(context).getReadableDatabase());
    		Cursor c = db.rawQuery(sql, null);
    		
    		if(c.moveToFirst()) {
    		
	    		do
	    		{
	    			Location loc = new Location();

                    loc.setRowid(c.getInt(c.getColumnIndex("rowid")));
                    loc.setCreated(new Timestamp(  c.getLong(c.getColumnIndex("created"))));
                    Log.d("created3", loc.getCreatedTimestamp().toString());
                    Log.d("created time3", ""+c.getLong(c.getColumnIndex("created")));

	    			loc.setLatitude(c.getDouble(c.getColumnIndex("latitude")));
	    			loc.setLongitude(c.getDouble(c.getColumnIndex("longitude")));
	    			
	    			loc.setEvanType(c.getInt(c.getColumnIndex("evanType")));
                    loc.setTrainType(c.getInt(c.getColumnIndex("trainType")));
                    loc.setMercyType(c.getInt(c.getColumnIndex("mercyType")));

                    loc.setYouthType(c.getInt(c.getColumnIndex("youthType")));
                    loc.setCampusType(c.getInt(c.getColumnIndex("campusType")));
                    loc.setIndigenousType(c.getInt(c.getColumnIndex("indigenousType")));
                    loc.setPrisonType(c.getInt(c.getColumnIndex("prisonType")));
                    loc.setProstitutesType(c.getInt(c.getColumnIndex("prostitutesType")));
                    loc.setOrphansType(c.getInt(c.getColumnIndex("orphansType")));
                    loc.setWomenType(c.getInt(c.getColumnIndex("womenType")));
                    loc.setUrbanType(c.getInt(c.getColumnIndex("urbanType")));
                    loc.setHospitalType(c.getInt(c.getColumnIndex("hospitalType")));
                    loc.setMediaType(c.getInt(c.getColumnIndex("mediaType")));
                    loc.setCommunityDevType(c.getInt(c.getColumnIndex("communityDevType")));
                    loc.setBibleStudyType(c.getInt(c.getColumnIndex("bibleStudyType")));
                    loc.setChurchPlantingType(c.getInt(c.getColumnIndex("churchPlantingType")));
                    loc.setArtsType(c.getInt(c.getColumnIndex("artsType")));
                    loc.setCounselingType(c.getInt(c.getColumnIndex("counselingType")));
                    loc.setHealthcareType(c.getInt(c.getColumnIndex("healthcareType")));
                    loc.setConstructionType(c.getInt(c.getColumnIndex("constructionType")));
                    loc.setResearchType(c.getInt(c.getColumnIndex("researchType")));

                    loc.setDesc(c.getString(c.getColumnIndex("desc")));
                    loc.setTags(c.getString(c.getColumnIndex("tags")));

                    loc.setContactConfirmed(c.getInt(c.getColumnIndex("contactConfirmed")));

	    			loc.setPhotoId(c.getString(c.getColumnIndex("photoId")));
                    loc.setPhotoRealPath(c.getString(c.getColumnIndex("photoRealPath")));
	    			loc.setContactEmail(c.getString(c.getColumnIndex("contactEmail")));
	    			loc.setContactPhone(c.getString(c.getColumnIndex("contactPhone")));
	    			loc.setContactWebsite(c.getString(c.getColumnIndex("contactWebsite")));

                    loc.setSync(c.getInt(c.getColumnIndex("sync")));

	    			result.add(loc);
	    		} while(c.moveToNext());


                c.close();
    		}

    	}
    	
    	finally
    	{
            if(db != null && db.isOpen())
            {
                db.close();
            }
    	}
    	
    	return result;
    }

    public Settings getSettings()
    {
        Settings result = new Settings();

        String sql = "select * from settings";

        try
        {
            db = (new DBHelper(context).getReadableDatabase());
            Cursor c = db.rawQuery(sql, null);

            if(c.moveToFirst())
            {
                result.setOffline(c.getInt(c.getColumnIndex("offline")));
                result.setUsername(c.getString(c.getColumnIndex("username")));
                result.setApiKey(c.getString(c.getColumnIndex("apiKey")));
                result.setNumSyncData(c.getInt(c.getColumnIndex("numSyncData")));
            }

            c.close();
        }

        finally
        {


            if(db != null && db.isOpen())
            {
                db.close();
            }
        }
        return result;
    }

    public long editSettings(Settings param)
    {
        long result;
        try
        {
            db = (new DBHelper(context).getWritableDatabase());

            ContentValues values = new ContentValues();

            values.put("offline", param.getOffline());
            values.put("username", param.getUsername());
            values.put("apiKey", param.getApiKey());
            values.put("numSyncData", param.getNumSyncData());

            result = db.update("settings",values,"rowid=1", null);
            return result;
        }

        catch(SQLException e)
        {
            return -1;
        }

        finally
        {
            if(db != null && db.isOpen())
            {
                db.close();
            }
        }
    }

    public int countUnsyncedData()
    {
        String sql = "SELECT COUNT(sync) AS unsynced FROM geo_tagger WHERE sync = 0";



        db = (new DBHelper(context).getReadableDatabase());

        Cursor c = db.rawQuery(sql, null);

        if(c.moveToFirst())
        {
            return c.getInt(c.getColumnIndex("unsynced"));
        }

        else
        {
            return -1;
        }
    }

    public void setDataSynced(int rowid)
    {
        String sql = "UPDATE geo_tagger SET sync = 1 WHERE rowid = "+ rowid;

        db = (new DBHelper(context).getWritableDatabase());

        try
        {
            db.execSQL(sql);
        }

        finally
        {
            if(db != null && db.isOpen())
            {
                db.close();
            }
        }
    }
}
