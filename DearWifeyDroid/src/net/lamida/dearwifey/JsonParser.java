package net.lamida.dearwifey;

import net.lamida.dearwifey.entity.Msg;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParser {
	
	public static Msg findRandom(){
		Log.i(JsonParser.class.toString(), "findRandom");
		String json = JsonDownloader.findRandom();
		Log.i(JsonParser.class.toString(), "findRandom.json: " + json);
		if(json != null && !json.isEmpty()){
			return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(json, Msg.class);
		}else{
			return null;
		}
	}

	public static Msg findLatest(){
		Log.i(JsonParser.class.toString(), "findLatest");
		String json = JsonDownloader.findLatest();
		Log.i(JsonParser.class.toString(), "findLatest.json: " + json);
		if(json != null && !json.isEmpty()){
			return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(json, Msg.class);
		}else{
			return null;
		}
	}

	public static Msg findByDate(String mmddyyyy){
		Log.i(JsonParser.class.toString(), "findByDate");
		String json = JsonDownloader.findByDate(mmddyyyy);
		Log.i(JsonParser.class.toString(), "findByDate--> " + json);
		if(json != null && !json.isEmpty() && !json.equals("[]")){
			Log.i(JsonParser.class.toString(), "findByDate-->" + json + "<--");
			return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(json, Msg.class);
		}else{ 
			return null;
		}
	}

}
