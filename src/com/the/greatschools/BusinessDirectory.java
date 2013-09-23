package com.the.greatschools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Observable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class BusinessDirectory extends Observable {
	BusinessDownloader downloader;
	Boolean directoryLoaded = false;
	public JSONObject mDirectory = null;
	
	public BusinessDirectory() {
		downloader = new BusinessDownloader();
		downloader.execute();		
	}
	
	public JSONObject getDirectory() {
		return mDirectory;
	}
	
	public JSONArray getDirectoryArray() throws JSONException {
		return mDirectory.getJSONArray("business");		
	}
	
	public Boolean isDirectoyLoaded() {
		return directoryLoaded;
	}
	
	public void saveDataAndNotifyObservers(JSONObject theDirectory) {
		mDirectory = theDirectory;
		directoryLoaded = true;
        setChanged();
        notifyObservers();
	}
	
	private class BusinessDownloader extends AsyncTask<Void, Integer, JSONObject> {
		
    	@Override
    	protected JSONObject doInBackground(Void... params) {
    		
    		String theBusinessData = readBusinessData();
    		System.out.print(theBusinessData);
    		Log.e("BusinessCursorLoader", theBusinessData);
    		JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(theBusinessData);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return jsonObject;
    	}
    	
    	@Override
    	protected void onPostExecute(JSONObject theDirectory) {
    		saveDataAndNotifyObservers(theDirectory);
    	}
    	
		public String readBusinessData() {
    		StringBuilder builder = new StringBuilder();
    		HttpClient client = new DefaultHttpClient();
    		HttpGet httpGet = new HttpGet("https://dl.dropbox.com/s/fdgus4o4llyvl9r/gsw-2.json?dl=1");
    		try {
    			HttpResponse response = client.execute(httpGet);
    			StatusLine statusLine = response.getStatusLine();
    			int statusCode = statusLine.getStatusCode();
    			if (statusCode == 200) {
    				HttpEntity entity = response.getEntity();
    				InputStream content = entity.getContent();
    				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
    				String line;
    				while ((line = reader.readLine()) != null) {
    					builder.append(line);
    				}
    			} else {
    				Log.e("BusinessCursorLoader", "Faled to download file");
    			}
    		} catch (ClientProtocolException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		
    		return builder.toString();
    	}

	}
}
