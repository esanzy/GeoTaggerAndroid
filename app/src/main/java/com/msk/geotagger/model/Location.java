package com.msk.geotagger.model;

import com.google.gson.JsonObject;


import java.sql.Timestamp;

/*
 * 
 * author 이준원
 * since 2014-05-08
 * update 2014-05-08
 */
public class Location 
{
    private int rowid;
    private int sync;
	private Timestamp created;
	
	private double latitude;
	private double longitude;

	private String photoId;
	private String contactEmail;
	private String contactPhone;
	private String contactWebsite;

    private int evanType;
    private int trainType;
    private int mercyType;
    private int youthType;
    private int campusType;
    private int indigenousType;
    private int prisonType;
    private int prostitutesType;
    private int orphansType;
    private int womenType;
    private int urbanType;
    private int hospitalType;
    private int mediaType;
    private int communityDevType;
    private int bibleStudyType;
    private int churchPlantingType;
    private int artsType;
    private int counselingType;
    private int healthcareType;
    private int constructionType;
    private int researchType;
    private String desc;
    private String tags;
    private int contactConfirmed;

    private String user;

    public Timestamp getCreated()
    {
        return created;
    }

    public void setCreated(Timestamp created)
    {
        this.created = created;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public String getPhotoId()
    {
        return photoId;
    }

    public void setPhotoId(String photoId)
    {
        this.photoId = photoId;
    }

    public String getContactEmail()
    {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone()
    {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }

    public String getContactWebsite()
    {
        return contactWebsite;
    }

    public void setContactWebsite(String contactWebsite)
    {
        this.contactWebsite = contactWebsite;
    }

    public int getEvanType()
    {
        return evanType;
    }

    public void setEvanType(int evanType)
    {
        this.evanType = evanType;
    }

    public int getTrainType()
    {
        return trainType;
    }

    public void setTrainType(int trainType)
    {
        this.trainType = trainType;
    }

    public int getMercyType()
    {
        return mercyType;
    }

    public void setMercyType(int mercyType)
    {
        this.mercyType = mercyType;
    }

    public int getYouthType()
    {
        return youthType;
    }

    public void setYouthType(int youthType)
    {
        this.youthType = youthType;
    }

    public int getCampusType()
    {
        return campusType;
    }

    public void setCampusType(int campusType)
    {
        this.campusType = campusType;
    }

    public int getIndigenousType()
    {
        return indigenousType;
    }

    public void setIndigenousType(int indigenousType)
    {
        this.indigenousType = indigenousType;
    }

    public int getPrisonType()
    {
        return prisonType;
    }

    public void setPrisonType(int prisonType)
    {
        this.prisonType = prisonType;
    }

    public int getProstitutesType()
    {
        return prostitutesType;
    }

    public void setProstitutesType(int prostitutesType)
    {
        this.prostitutesType = prostitutesType;
    }

    public int getOrphansType()
    {
        return orphansType;
    }

    public void setOrphansType(int orphansType)
    {
        this.orphansType = orphansType;
    }

    public int getWomenType()
    {
        return womenType;
    }

    public void setWomenType(int womenType)
    {
        this.womenType = womenType;
    }

    public int getUrbanType()
    {
        return urbanType;
    }

    public void setUrbanType(int urbanType)
    {
        this.urbanType = urbanType;
    }

    public int getHospitalType()
    {
        return hospitalType;
    }

    public void setHospitalType(int hospitalType)
    {
        this.hospitalType = hospitalType;
    }

    public int getMediaType()
    {
        return mediaType;
    }

    public void setMediaType(int mediaType)
    {
        this.mediaType = mediaType;
    }

    public int getCommunityDevType()
    {
        return communityDevType;
    }

    public void setCommunityDevType(int communityDevType)
    {
        this.communityDevType = communityDevType;
    }

    public int getBibleStudyType()
    {
        return bibleStudyType;
    }

    public void setBibleStudyType(int bibleStudyType)
    {
        this.bibleStudyType = bibleStudyType;
    }

    public int getChurchPlantingType()
    {
        return churchPlantingType;
    }

    public void setChurchPlantingType(int churchPlantingType)
    {
        this.churchPlantingType = churchPlantingType;
    }

    public int getArtsType()
    {
        return artsType;
    }

    public void setArtsType(int artsType)
    {
        this.artsType = artsType;
    }

    public int getCounselingType()
    {
        return counselingType;
    }

    public void setCounselingType(int counselingType)
    {
        this.counselingType = counselingType;
    }

    public int getHealthcareType()
    {
        return healthcareType;
    }

    public void setHealthcareType(int healthcareType)
    {
        this.healthcareType = healthcareType;
    }

    public int getConstructionType()
    {
        return constructionType;
    }

    public void setConstructionType(int constructionType)
    {
        this.constructionType = constructionType;
    }

    public int getResearchType()
    {
        return researchType;
    }

    public void setResearchType(int researchType)
    {
        this.researchType = researchType;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getTags()
    {
        return tags;
    }

    public void setTags(String tags)
    {
        this.tags = tags;
    }

    public int getContactConfirmed()
    {
        return contactConfirmed;
    }

    public void setContactConfirmed(int contactConfirmed)
    {
        this.contactConfirmed = contactConfirmed;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public int getRowid() {
        return rowid;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

    public JsonObject toJSON()
    {
        JsonObject json = new JsonObject();


        json.addProperty("latitude", this.latitude);
        json.addProperty("longitude", this.longitude);

        json.addProperty("photoId", this.photoId);
        json.addProperty("contactEmail", this.contactEmail);
        json.addProperty("contactPhone", this.contactPhone);
        json.addProperty("contactWebsite", this.contactWebsite);

        json.addProperty("evanType", this.evanType);
        json.addProperty("trainType", this.trainType);
        json.addProperty("mercyType", this.mercyType);
        json.addProperty("youthType", this.youthType);
        json.addProperty("campusType", this.campusType);
        json.addProperty("indigenousType", this.indigenousType);
        json.addProperty("prisonType", this.prisonType);
        json.addProperty("prostitutesType", this.prostitutesType);
        json.addProperty("orphansType", this.orphansType);
        json.addProperty("womenType", this.womenType);
        json.addProperty("urbanType", this.urbanType);
        json.addProperty("hospitalType", this.hospitalType);
        json.addProperty("mediaType", this.mediaType);
        json.addProperty("communityDevType", this.communityDevType);
        json.addProperty("bibleStudyType", this.bibleStudyType);
        json.addProperty("churchPlantingType", this.churchPlantingType);
        json.addProperty("artsType", this.artsType);
        json.addProperty("counselingType", this.counselingType);
        json.addProperty("healthcareType", this.healthcareType);
        json.addProperty("constructionType", this.constructionType);
        json.addProperty("researchType", this.researchType);
        json.addProperty("desc", this.desc);
        json.addProperty("tags", this.tags);
        json.addProperty("contactConfirmed", this.contactConfirmed);

        return json;
    }
}
