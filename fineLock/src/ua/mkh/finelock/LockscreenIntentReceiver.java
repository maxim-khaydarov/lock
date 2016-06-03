package ua.mkh.finelock;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import ua.mkh.finelock.MainActivity;

public class LockscreenIntentReceiver extends BroadcastReceiver {

	
	int s ;
	private static String mLastState;
	
	protected SharedPreferences mPrefs;
    public static final String PREF_STATE = "phone";
    
	// Handle actions and display Lockscreen
	@Override
	public void onReceive(final Context context, Intent intent) {
		//Log.d("STATUS", Integer.toString(s) + " START RECEIVER");
		String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
		
		final SharedPreferences mPrefs = 
                PreferenceManager.getDefaultSharedPreferences(
                    context.getApplicationContext());
		
		
		 
		
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)
				|| intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			if (mPrefs.getString(PREF_STATE, "IDLE").contains("IDLE")  ){
				Log.d("STATUS", Integer.toString(s) + " SCREEN OFF");
			start_lockscreen(context);
			}
		}
		

		if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
		    TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		    telephony.listen(new PhoneStateListener() {
		        public void onCallStateChanged(int state, String incomingNumber) {
		              switch(state) {
		                case TelephonyManager.CALL_STATE_IDLE:
		                	mPrefs.edit().putString(PREF_STATE, "IDLE").commit();
		                break;
		                case TelephonyManager.CALL_STATE_OFFHOOK:
		                	mPrefs.edit().putString(PREF_STATE, "OFFHOOK").commit();
		                break;
		                case TelephonyManager.CALL_STATE_RINGING:
		                break;
		               }
		        } 
		    }, PhoneStateListener.LISTEN_CALL_STATE);

		}
		    
		       
	}
	
	

	// Display lock screen
	private void start_lockscreen(Context context) {
		Intent mIntent = new Intent(context, MainActivity.class);
		mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(mIntent);
	}
	
	
	
}
