package com.msk.geotagger.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.util.Enumeration;


/**
 * Created by junwon on 14. 6. 25.
 */
public class SendPicTask extends AsyncTask<JsonObject, Void, HttpResponse> {

    private static String TAG = "사진 전송";

    @Override
    protected HttpResponse doInBackground(JsonObject... jsonObjects) {

        HttpClient httpClient = getHttpClient();
        String urlString = Server.host + "/m/locpic/";


        try {

            URI url = new URI(urlString);
            HttpPost httpPost = new HttpPost();
            httpPost.setURI(url);
            JsonObject json = jsonObjects[0];
            httpPost.setHeader("Authorization", "ApiKey "+json.get("credential").getAsString());
            json.remove("credential");

            String filepath = json.get("filepath").getAsString();
            File file = new File(filepath);

            MultipartEntity entity = new MultipartEntity( HttpMultipartMode.BROWSER_COMPATIBLE );
            entity.addPart("username", new StringBody(json.get("username").getAsString()));
            entity.addPart("pic", new FileBody(file));

            Log.d("요청", httpPost.getMethod());

            Header[] headers = httpPost.getAllHeaders();
            for(int i = 0; i < headers.length; i++)
            {
                Log.d("요청", headers[i].getName()+ " : " +headers[i].getValue());
            }


            httpPost.setEntity(entity);


            HttpResponse response = httpClient.execute(httpPost, new PhotoUploadResponseHandler());

            //Log.d("결과", "" + response.getStatusLine().getStatusCode());

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
    }

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

    private class PhotoUploadResponseHandler implements ResponseHandler<HttpResponse> {

        @Override
        public HttpResponse handleResponse(HttpResponse response)
                throws ClientProtocolException, IOException {

            HttpEntity r_entity = response.getEntity();
            String responseString = EntityUtils.toString(r_entity);
            Log.d("UPLOAD", responseString);

            return null;
        }

    }
}
