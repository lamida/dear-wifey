package net.lamida.dearwifey.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.lamida.dearwifey.dearwifeydroid.R;
import net.lamida.dearwifey.entity.Msg;
import net.lamida.dearwifey.entity.Params;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		ActionBar.OnNavigationListener, MsgListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private Msg message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("onCreate","create");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
								getString(R.string.title_section_find_latest),
								getString(R.string.title_section_find_random),
								getString(R.string.title_section_find_by_date), 
								getString(R.string.title_section_about), 
								}), this);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar().getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		Log.i(MainActivity.class.toString(), "onNavigationItemSelected " + position);
		Fragment fragment = null;
		DownloadDataAsyncTask task = new DownloadDataAsyncTask(this);
		if(position == 0){
			task.execute(Params.actionFindLatest);
			fragment = new FindLatestFragment();
		}
		else if(position == 1){
			task.execute(Params.actionFindRandom);
			fragment = new FindRandomFragment();
		}
		else if(position == 2){
			fragment = new FindByDateFragment();
			DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerFindMessage);
			Date date = new Date();
			if(datePicker != null){
				date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
			}
			Log.i(MainActivity.class.toString(), "onNavigationItemSelected date: " + date + " format " + format.format(date));
			task.execute(Params.actionFindByDate, format.format(date));
		}
		else if(position == 3){
			fragment = new AboutFragment();
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
		return true;
	}


	public static class FindByDateFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_find_by_date, container, false);
		}
	}

	public static class FindLatestFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			
			
			return inflater.inflate(R.layout.fragment_find_latest, container, false);
		}
	}

	public static class FindRandomFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_find_random, container, false);
		}
	}
	
	View.OnClickListener findRandomClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Log.i(MainActivity.class.toString(), "findRandomClickListener.onclick ");
			DownloadDataAsyncTask task = new DownloadDataAsyncTask(MainActivity.this);
			task.execute(Params.actionFindRandom);
		} 
		
	};

	View.OnClickListener findByDateClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			Log.i(MainActivity.class.toString(), "findByDateClickListener.onclick ");
			DownloadDataAsyncTask task = new DownloadDataAsyncTask(MainActivity.this);
			DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerFindMessage);
			Date date = new Date();
			if(datePicker != null){
				date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
			}
			Log.i(MainActivity.class.toString(), "findByDateClickListener.onclick date: " + date + " format: " + format.format(date));
			 
			task.execute(Params.actionFindByDate, format.format(date));
		} 
		
	};
	

	public static class AboutFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_about, container, false);
		}
	}

	@Override
	public void updateMessage(Msg m) {
		Log.i(MainActivity.class.toString(), "updateMessages: " + m);
		if(m == null){
			Toast.makeText(getApplicationContext(), "Cannot Find Love Mail", 2000);
			return;
		}
		Log.i(MainActivity.class.toString(), "subject: " + m.getSubject());
		Log.i(MainActivity.class.toString(), "sentDate: " + m.getSentDate());
		//Log.i(MainActivity.class.toString(), "content: " + m.getContentText());
		if (m.getParam().equals(Params.actionFindLatest)) {
			Log.i(MainActivity.class.toString(), "updateMessages: " + Params.actionFindLatest);
			TextView textViewTitle = (TextView) findViewById(R.id.textViewLatestTitle);
			TextView textViewContent = (TextView) findViewById(R.id.textViewLatest);
			TextView textViewSentDate = (TextView) findViewById(R.id.textViewLatestDate);
			textViewContent.setMovementMethod(new ScrollingMovementMethod());
			if(textViewTitle != null){
				textViewTitle.setText(m.getSubject());
			}
			if(textViewContent != null && m.getContentText() != null){
				textViewContent.setText(m.getContentText().get("value"));
			}
			if(textViewSentDate != null){
				textViewSentDate.setText(m.getDdmmyyyyhhmmss());
			}

		} else if (m.getParam().equals(Params.actionFindRandom)) {
			Log.i(MainActivity.class.toString(), "updateMessages: " + Params.actionFindRandom);
			TextView textViewTitle = (TextView) findViewById(R.id.textViewRandomTitle);
			TextView textViewContent = (TextView) findViewById(R.id.textViewRandom);
			TextView textViewSentDate = (TextView) findViewById(R.id.textViewRandomDate);
			textViewContent.setMovementMethod(new ScrollingMovementMethod());
			Button refreshRandom = (Button) findViewById(R.id.refreshRandom);
			if(textViewTitle != null){
				textViewTitle.setText(m.getSubject());
			}
			if(textViewContent != null && m.getContentText() != null){
				textViewContent.setText(m.getContentText().get("value"));
			}
			if(textViewSentDate != null){
				textViewSentDate.setText(m.getDdmmyyyyhhmmss());
			}
			if(refreshRandom != null){
				refreshRandom.setOnClickListener(findRandomClickListener);
			}
		} else if (m.getParam().equals(Params.actionFindByDate)) {
			Log.i(MainActivity.class.toString(), "updateMessages: " + Params.actionFindByDate);
			TextView textViewTitle = (TextView) findViewById(R.id.textViewByDateTitle);
			TextView textViewContent = (TextView) findViewById(R.id.textViewByDate);
			TextView textViewSentDate = (TextView) findViewById(R.id.textViewByDateDate);
			textViewContent.setMovementMethod(new ScrollingMovementMethod());
			Button refreshFindByDate = (Button) findViewById(R.id.refreshByDate);
			if(textViewTitle != null){
				textViewTitle.setText(m.getSubject());
			}
			if(textViewContent != null && m.getContentText() != null){
				textViewContent.setText(m.getContentText().get("value"));
			}
			if(textViewSentDate != null){
				textViewSentDate.setText(m.getDdmmyyyyhhmmss());
			}
			if(refreshFindByDate != null){
				refreshFindByDate.setOnClickListener(findByDateClickListener);
			}
		}
	}

	@Override
	public void updateMessages(Msg m) {
		
	}

}
