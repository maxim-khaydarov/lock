package ua.mkh.finelock;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    private Context mContext;
    
 
    public ListAdapter(Activity a, ArrayList<HashMap<String, String>> d, Context context) {
    	this.mContext = context;
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);
 
        Typeface thin = Typeface.createFromAsset(mContext.getAssets(), "fonts/Thin.ttf");
   	 Typeface regular = Typeface.createFromAsset(mContext.getAssets(), "fonts/Regular.ttf");
   	 
        TextView number = (TextView)vi.findViewById(R.id.number); // number
        number.setTypeface(regular);
        TextView body = (TextView)vi.findViewById(R.id.body); //  body
        body.setTypeface(regular);
        TextView time = (TextView)vi.findViewById(R.id.textView2); //  time
        time.setTypeface(regular);
        ImageView icon =(ImageView)vi.findViewById(R.id.icon); // thumb image
 
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
 
        // Setting all values in listview
        number.setText(song.get(MainFragment.KEY_NUMBER));
        body.setText(song.get(MainFragment.KEY_BODY));
        time.setText(song.get(MainFragment.KEY_TIME));
        
        return vi;
    }
}