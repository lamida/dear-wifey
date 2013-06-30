package net.lamida.dearwifey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import net.lamida.dearwifey.entity.Params;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;


public class JsonDownloader {
	private static final String rootUrl = "http://mydearwifey.appspot.com/data";
	private static final String scheme = "http";
	private static final String host = "mydearwifey.appspot.com";
	private static final String path = "/data";
	
	public static String findRandom(){
		HttpGet httpGet = null;
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Params.key, Params.secretKey));
		qparams.add(new BasicNameValuePair(Params.action, Params.actionFindRandom));
		try {
			URI uri = URIUtils.createURI(scheme, host, -1, path, 
			    URLEncodedUtils.format(qparams, "UTF-8"), null);
			httpGet = new HttpGet(uri);
			String json = readJsonResponse(httpGet);
			Log.i(JsonDownloader.class.toString(), "findRandom: " + json);
			return json;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String findLatest(){
		HttpGet httpGet = null;
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Params.key, Params.secretKey));
		qparams.add(new BasicNameValuePair(Params.action, Params.actionFindLatest));
		try {
			URI uri = URIUtils.createURI(scheme, host, -1, path, 
			    URLEncodedUtils.format(qparams, "UTF-8"), null);
			httpGet = new HttpGet(uri);
			String json = readJsonResponse(httpGet);
			Log.i(JsonDownloader.class.toString(), "findLatest: " + json);
			return json;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String findByDate(String mmddyyyy){
		HttpGet httpGet = null;
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Params.key, Params.secretKey));
		qparams.add(new BasicNameValuePair(Params.action, Params.actionFindByDate));
		qparams.add(new BasicNameValuePair(Params.messageDate, mmddyyyy));
		try {
			URI uri = URIUtils.createURI(scheme, host, -1, path, 
			    URLEncodedUtils.format(qparams, "UTF-8"), null);
			httpGet = new HttpGet(uri);
			String json = readJsonResponse(httpGet);
			Log.i(JsonDownloader.class.toString(), "findByDate: " + json);
			return json;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static String readJsonResponse(HttpGet httpGet) {
		Log.i(JsonDownloader.class.toString(), "readJsonResponse");
		StringBuilder builder = new StringBuilder();
		try {
			HttpClient client = new DefaultHttpClient();
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
				Log.e(JsonDownloader.class.toString(), "Failed to download response");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

}
