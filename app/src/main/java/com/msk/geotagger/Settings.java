package com.msk.geotagger;

/**
 * Created by Junwon on 2014-06-01.
 */
public class Settings
{
    private int offline;
    private String username;
    private String apiKey;
    private int numSyncData;

    public int getOffline()
    {
        return offline;
    }

    public void setOffline(int offline)
    {
        this.offline = offline;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getApiKey()
    {
        return apiKey;
    }

    public void setApiKey(String apiKey)
    {
        this.apiKey = apiKey;
    }

    public int getNumSyncData()
    {
        return numSyncData;
    }

    public void setNumSyncData(int numSyncData)
    {
        this.numSyncData = numSyncData;
    }
}
