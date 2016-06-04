package ua.mkh.finelock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class MainFragment extends Fragment implements OnItemClickListener {

	Context mContext;
	Typeface typefaceRoman, typefaceMedium, typefaceBold;
	static TextView textView1, textView3;
	private MainActivity mMainActivity;
	
	static final String KEY_NUMBER = "number"; // parent node
    static final String KEY_BODY = "body";
    static final String KEY_TIME = "time";
    ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    
    
	static ListView lvMain;
	
	 private static MainFragment inst;
	    //ArrayList<String> smsMessagesList = new ArrayList<String>();
	    //ArrayAdapter<String> arrayAdapter;
	    
	    ListAdapter adapter;

	    public static MainFragment instance() {
	        return inst;
	    }
    
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	 Bundle savedInstanceState) {
	 // TODO Auto-generated method stub
	 View view = inflater.inflate(R.layout.fragment_monday, container, false);
	 
	 inst = this;
	 	
	 lvMain = (ListView) view.findViewById(R.id.lvMain);
	 adapter=new ListAdapter(getActivity(), songsList, mContext);
	lvMain.setAdapter(adapter);
	 //refreshSmsInbox();
	 lvMain.setOnItemClickListener(this);

	 
	 
	 Typeface ultra = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Ultralight.ttf");
	 Typeface thin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Thin.ttf");
	 Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Regular.ttf");
		
	 textView1 = (TextView) view.findViewById(R.id.textView1);
	 TextView textView2 = (TextView) view.findViewById(R.id.textView2);
	 textView3 = (TextView) view.findViewById(R.id.textView3);
	 
	 
	 
	 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	 String currentDateandTime4 = sdf.format(new Date());
	 textView1.setText(currentDateandTime4);
	 
	 
		SimpleDateFormat day = new SimpleDateFormat("dd");
		SimpleDateFormat day2 = new SimpleDateFormat("EEEE");
		String currentDateday = day.format(new Date());
		String currentDateday2 = day2.format(new Date());
		
		//UK
		SimpleDateFormat sdfudd = new SimpleDateFormat("EEEE, ");
		SimpleDateFormat sdfud = new SimpleDateFormat("d ");
		SimpleDateFormat sdfum = new SimpleDateFormat("LLLL");
		String currentDateandudd = sdfudd.format(new Date());
		String currentDateandud = sdfud.format(new Date());
		String currentDateandum = sdfum.format(new Date());
		
		
		SimpleDateFormat sdfd = new SimpleDateFormat("EEEE, dd LLLL");
		String currentDateandDate = sdfd.format(new Date());
		
		
		
		
		if (Locale.getDefault().getLanguage().contains("uk")){
			if (currentDateandum.contains("Грудень")){
				currentDateandum = "грудня";
			}
			else if (currentDateandum.contains("Січень")){
				currentDateandum = "січня";
			}
			else if (currentDateandum.contains("Лютий")){
				currentDateandum = "лютня";
			}
			else if (currentDateandum.contains("Березень")){
				currentDateandum = "березня";
			}
			else if (currentDateandum.contains("Квітень")){
				currentDateandum = "квітня";
			}
			else if (currentDateandum.contains("Травень")){
				currentDateandum = "травня";
			}
			else if (currentDateandum.contains("Червень")){
				currentDateandum = "червня";
			}
			else if (currentDateandum.contains("Липень")){
				currentDateandum = "липня";
			}
			else if (currentDateandum.contains("Серпень")){
				currentDateandum = "серпня";
			}
			else if (currentDateandum.contains("Вересень")){
				currentDateandum = "вересня";
			}
			else if (currentDateandum.contains("Жовтень")){
				currentDateandum = "жовтня";
			}
			else if (currentDateandum.contains("Листопад")){
				currentDateandum = "листопада";
			}
			textView3.setText(currentDateandudd + currentDateandud + currentDateandum);
		}
		
		
		
		else if (Locale.getDefault().getLanguage().contains("ru")){
			
			

			String ssz = currentDateandudd.substring(0,1).toUpperCase() + currentDateandudd.substring(1);
			
			if(currentDateandum.contains("Декабрь")){
				currentDateandum = "декабря";
				textView3.setText(ssz + currentDateandud + currentDateandum);
			}
			else if(currentDateandum.contains("Январь")){
				currentDateandum = "января";
				textView3.setText(ssz + currentDateandud + currentDateandum);
			}
			else if(currentDateandum.contains("Февраль")){
				currentDateandum = "февраля";
				textView3.setText(ssz + currentDateandud + currentDateandum);
			}
			else if(currentDateandum.contains("Март")){
				currentDateandum = "марта";
				textView3.setText(ssz + currentDateandud + currentDateandum);
			}
			else if(currentDateandum.contains("Апрель")){
				currentDateandum = "апреля";
				textView3.setText(ssz + currentDateandud + currentDateandum);
			}
			else if(currentDateandum.contains("Май")){
				currentDateandum = "мая";
				textView3.setText(ssz + currentDateandud + currentDateandum);
			}
			else if(currentDateandum.contains("Июнь")){
				currentDateandum = "июня";
				textView3.setText(ssz + currentDateandud + currentDateandum);
			}
			else if(currentDateandum.contains("Июль")){
				currentDateandum = "июля";
				textView3.setText(ssz + currentDateandud + currentDateandum);
			}
			else if(currentDateandum.contains("Август")){
				currentDateandum = "августа";
				textView3.setText(ssz + currentDateandud + currentDateandum);
			}
			else if(currentDateandum.contains("Сентябрь")){
				currentDateandum = "сентября";
				textView3.setText(ssz + currentDateandud + currentDateandum);
			}
			else if(currentDateandum.contains("Октябрь")){
				currentDateandum = "октября";
				textView3.setText(ssz + currentDateandud + currentDateandum);
			}
			else if(currentDateandum.contains("Ноябрь")){
				currentDateandum = "ноября";
				textView3.setText(ssz + currentDateandud + currentDateandum);
			}
			
			
			
		}
		else {
			textView3.setText(currentDateandDate);
		}
		
		
		
		textView1.setTypeface(ultra);
		textView2.setTypeface(thin);
		textView3.setTypeface(thin);
	 return view;
	 }
	
	

  /*
	public void refreshSmsInbox() {
	    ContentResolver contentResolver = getActivity().getContentResolver();
	    Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
	    int indexBody = smsInboxCursor.getColumnIndex("body");
	    int indexAddress = smsInboxCursor.getColumnIndex("address");
	    int indexStatus = smsInboxCursor.getColumnIndex("read");
	    if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
	    arrayAdapter.clear();
	    
	    do {
	    	String str = smsInboxCursor.getString(indexAddress) +
	                "\n" + smsInboxCursor.getString(indexBody) + "\n" + smsInboxCursor.getString(indexStatus);
	    	if (smsInboxCursor.getString(indexStatus).contains("0")){
	        arrayAdapter.add(str);}
	    } while (smsInboxCursor.moveToNext());
	    }
	*/

	public void updateList(final HashMap<String, String> map) {
		songsList.add(map);
		adapter=new ListAdapter(getActivity(), songsList, getActivity());
		lvMain.setAdapter(adapter);
	   // adapter.insert(songsList, 0);
	   // arrayAdapter.notifyDataSetChanged();
	}

	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
	    //try {
	    	/*
	    	Intent intent = new Intent(Intent.ACTION_MAIN);
	    	intent.addCategory(Intent.CATEGORY_DEFAULT);
	    	intent.setType("vnd.android-dir/mms-sms");
	    	startActivity(intent);
	    	*/
	    	songsList.remove(pos);
	    	adapter.notifyDataSetChanged();
	    	adapter.notifyDataSetInvalidated();
	    	/*
	    	
	        String[] smsMessages = smsMessagesList.get(pos).split("\n");
	        String address = smsMessages[0];
	        String smsMessage = "";
	        for (int i = 1; i < smsMessages.length; ++i) {
	            smsMessage += smsMessages[i];
	        }

	        String smsMessageStr = address + "\n";
	        smsMessageStr += smsMessage;
	        Toast.makeText(getActivity(), smsMessageStr, Toast.LENGTH_SHORT).show();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }*/
	}
	
	
	

}
