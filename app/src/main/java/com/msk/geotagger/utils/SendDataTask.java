package com.msk.geotagger.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.msk.geotagger.model.Location;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by junwon on 14. 6. 25.
 */
public class SendDataTask extends AsyncTask<JsonObject, Void, HttpResponse>
{
    private static String TAG = "위치정보 전송";

    @Override
    protected HttpResponse doInBackground(JsonObject... locations) {


        HttpClient httpClient = getHttpClient();
        String urlString = Server.host + "/api/0.1/location/";


        try {

            URI url = new URI(urlString);


            HttpPost httpPost = new HttpPost();

            httpPost.setURI(url);


            List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();

            /*
            nameValuePairs.add(new BasicNameValuePair("userId", "saltfactory"));

            nameValuePairs.add(new BasicNameValuePair("password", "password"));
            */

            JsonObject json = locations[0];

            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "ApiKey "+json.get("credential").getAsString());

            json.remove("credential");
/*
            Set<Map.Entry<String, JsonElement>> jsonEntry = json.entrySet();
            Iterator<Map.Entry<String, JsonElement>> iter = jsonEntry.iterator();

            while(iter.hasNext())
            {
                Map.Entry<String, JsonElement> entry = iter.next();
                if( !(entry.getValue() instanceof JsonNull) ) {
                    nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().getAsString()));
                }
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
*/

            StringEntity params = new StringEntity(json.toString());
            httpPost.setEntity(params);

            Log.d(TAG, httpPost.toString());

            HttpResponse response = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
            Log.d(TAG, responseString);

            return response;


        } catch (URISyntaxException e) {

            Log.e(TAG, e.getLocalizedMessage());

            e.printStackTrace();

        } catch (ClientProtocolException e) {

            Log.e(TAG, e.getLocalizedMessage());

            e.printStackTrace();

        } catch (IOException e) {

            Log.e(TAG, e.getLocalizedMessage());

            e.printStackTrace();

        }

        return null;
    } // doInBackground

    public HttpClient getHttpClient()
    {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory socketFactory = new MySSLSocketFactory(trustStore);
            socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);


            SchemeRegistry registry = new SchemeRegistry();

            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", socketFactory, 443));


            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }
}
