/**
 * 
 */
package com.example.quizclient.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

/**
 * @author leon
 * 
 */
public class Utilities {
    public static String connectGet(String url) throws ClientProtocolException,
            IOException {

        HttpClient httpclient = new DefaultHttpClient();

        // Prepare a request object
        HttpGet httpget = new HttpGet(url);

        // Execute the request
        HttpResponse response;

        response = httpclient.execute(httpget);
        // Examine the response status
        Log.i("a", response.getStatusLine().toString());

        // Get hold of the response entity
        HttpEntity entity = response.getEntity();

        if (entity != null) {

            // A Simple JSON Response Read
            InputStream instream = entity.getContent();
            String result = convertStreamToString(instream);
            // now you have the string representation of the HTML request
            instream.close();
            return result;
        } else {
            return null;
        }
    }

    private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
