package ua.mkh.finelock;

import java.util.concurrent.TimeUnit;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import ua.mkh.finelock.MainActivity;

public class LockscreenIntentReceiver extends BroadcastReceiver {

	
	int s ;
	private static String mLastState;
	private Context mContext;
	
	protected SharedPreferences mPrefs;
    public static final String PREF_STATE = "phone";
    
	// Handle actions and display Lockscreen
	@Override
	public void onReceive(final Context context, Intent intent) {
		//Log.d("STATUS", Integer.toString(s) + " START RECEIVER");
		mContext = context;
		
		String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
		
		final SharedPreferences mPrefs = 
                PreferenceManager.getDefaultSharedPreferences(
                    context.getApplicationContext());
		
		
		 
		
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)
				|| intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) || intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
			if (mPrefs.getString(PREF_STATE, "IDLE").contains("IDLE")  ){
				Log.d("STATUS", Integer.toString(s) + " SCREEN OFF");
			start_lockscreen();
			}
		}
		/*
		if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
			Log.d("STATUS", Integer.toString(s) + " SCREEN ON");
			 Thread t = new Thread(new Runnable() {
			        public void run() {
			        	
			        	 for (int i = 1; i <= 5; i++) {
			        		 try {
								TimeUnit.MILLISECONDS.sleep(1000);
								
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			        		 
			        		 Log.d("STATUS", String.valueOf(i));
			                 if(i == 5){
			                	/* WakeLock screenLock =    ((PowerManager)mContext.getSystemService(mContext.POWER_SERVICE)).newWakeLock(
			                			    PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
			                			    screenLock.acquire();

			                			  //later
			                			  screenLock.release();
			                			  
			                     Log.d("STATUS", "i = 5");
			                 }
			                 
			               }
			        	 
			        }
			 });
			 t.start();
		}
    
		*/

		if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
		    TelephonyManager telephony = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
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
	private void start_lockscreen() {
		Intent mIntent = new Intent(mContext, MainActivity.class);
		mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(mIntent);
	}
	 
	
	
	
}
