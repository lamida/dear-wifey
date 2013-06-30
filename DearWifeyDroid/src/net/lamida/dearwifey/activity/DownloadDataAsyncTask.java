package net.lamida.dearwifey.activity;

import net.lamida.dearwifey.JsonParser;
import net.lamida.dearwifey.entity.Msg;
import net.lamida.dearwifey.entity.Params;
import android.os.AsyncTask;


public class DownloadDataAsyncTask extends AsyncTask<String, Void, Msg>{
	
	public static final String MESSAGE_DOWNLOAD_ACTION = "com.dearwifeydroid.DOWNLOAD"; 
	private MsgListener listener;
	
	public DownloadDataAsyncTask(MsgListener listener) {
		super();
		this.listener = listener;
	}

	@Override
	protected Msg doInBackground(String... params) {
		String actionParam = params[0];
		if(actionParam.equals(Params.actionFindLatest)){
			Msg m = JsonParser.findLatest();
			m.setParam(Params.actionFindLatest);
			return m;
		}else if(actionParam.equals(Params.actionFindRandom)){
			Msg m = JsonParser.findRandom();
			m.setParam(Params.actionFindRandom);
			return m;
		}else if(actionParam.equals(Params.actionFindByDate) && params.length > 1){
			String mmddyyyy = params[1];
			Msg m = JsonParser.findByDate(mmddyyyy);
			if(m == null){
				m = new Msg();
				m.setSubject("No Mail for this day");
			}
			m.setParam(Params.actionFindByDate);
			return m;
		}else{
			return null;
		}
	}

	@Override
	protected void onPostExecute(Msg result) {
		listener.updateMessage(result);
	}
	


}
