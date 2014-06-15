package com.msk.geotagger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * 
 * author 이준원
 * since 2014-05-07
 * update 2014-05-07 이준원
 * reference http://whitememo.tistory.com/241
 * charset UTF-8
 */
public class DBHelper extends  SQLiteOpenHelper 
{
	private static final String DB_NAME = "geo_tagger.db";
	private static final int DB_VER = 1;  

	public DBHelper(Context context) 
	{
		super(context, DB_NAME, null, DB_VER);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub
		String sql = "create table geo_tagger ("
                + "rowid integer primary key autoincrement"
                + ", created timestamp default current_timestamp"

                + ", latitude real"
                + ", longitude real"

                + ", evanType integer"
                + ", trainType integer"
                + ", mercyType integer"

                + ", youthType integer"
                + ", campusType integer"
                + ", indigenousType integer"

                + ", prisonType integer"
                + ", prostitutesType integer"
                + ", orphansType integer"

                + ", womenType integer"
                + ", urbanType integer"
                + ", hospitalType integer"

                + ", mediaType integer"
                + ", communityDevType integer"
                + ", bibleStudyType integer"

                + ", churchPlantingType integer"
                + ", artsType integer"
                + ", counselingType integer"

                + ", healthcareType integer"
                + ", constructionType integer"
                + ", researchType integer"


                + ", desc text"
                + ", tags text"
                + ", contactConfirmed integer"

                + ", photoId text"
                + ", contactEmail text"
                + ", contactPhone text"
                + ", contactWebsite text" +
                  ", sync integer"
                + ");";
		db.execSQL(sql);

        String sql2 = "create table settings (" +
                "rowid integer primary key autoincrement" +
                ", offline integer" +
                ", username text" +
                ", apiKey text" +
                ", numSyncData integer" +
                ");";
        db.execSQL(sql2);

        String sql3 = "insert into settings(offline, username, apiKey, numSyncData) values(0, null, null, 0);";
        db.execSQL(sql3);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
		String sql_droptable = "DROP TABLE IF EXISTS " + "geo_tagger;";  
        db.execSQL(sql_droptable);

        String sql_droptable2 = "DROP TABLE IF EXISTS settings";
        db.execSQL(sql_droptable2);
	}

}
