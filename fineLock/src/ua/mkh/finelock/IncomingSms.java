package ua.mkh.finelock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class IncomingSms extends BroadcastReceiver {

	static final String KEY_NUMBER = "number"; // parent node
    static final String KEY_BODY = "body";
    static final String KEY_TIME = "time";
    public static final String SMS_BUNDLE = "pdus";
    ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        HashMap<String, String> map = new HashMap<String, String>();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr += address + "\n";
                smsMessageStr += smsBody;
                
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
           	 String currentDateandTime4 = sdf.format(new Date());
           	 
                // adding each child node to HashMap key =&gt; value
                map.put(KEY_NUMBER, address);
                map.put(KEY_BODY, smsBody);
                map.put(KEY_TIME, currentDateandTime4);
                
     
                // adding HashList to ArrayList
                //songsList.add(map);
                
            }
            //Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();

            //this will update the UI with message
            MainFragment inst = MainFragment.instance();
            inst.updateList(map);
        }
    }
}