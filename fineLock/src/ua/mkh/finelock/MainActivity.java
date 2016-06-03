package ua.mkh.finelock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.KeyguardManager;
import android.app.WallpaperManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)

@SuppressWarnings("deprecation")
public class MainActivity extends FragmentActivity implements ActionBar.TabListener, LockscreenUtils.OnLockStatusChangedListener{
	@Override
	public void onAttachedToWindow() {
		this.getWindow().setType(
				WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
		this.getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN
						| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
						);

		super.onAttachedToWindow();
	}
	
	public static final String APP_PREFERENCES = "mysettings"; 
	public static final String APP_PREFERENCES_PIN_PASS = "pin_pass";
	public static final String APP_PREFERENCES_PIN = "pin";
	private LockscreenUtils mLockscreenUtils;

	SharedPreferences mSettings;
	// Set appropriate flags to make the screen appear over the keyguard
	
	TelephonyManager telephonyManager;
	myPhoneStateListener psListener;
	
	WallpaperManager wallpaperManager;
	
	int pin_pass = 1234;
	Boolean pin = false;
	private ViewPager viewPager;
	TextView  operator, battery;
	ImageView gps, bluetooth;
	 //private ActionBar actionBar;
	 private TabsFragmentPagerAdapter tabsAdapter;
	 private TabsFragmentPagerAdapterOne tabsAdapterOne;
	 
	
	 
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 

	 setContentView(R.layout.activity_main);
	 viewPager = (ViewPager) findViewById(R.id.viewPager);
	 
	 Typeface regular = Typeface.createFromAsset(getAssets(), "fonts/Regular.ttf");
	 
	 
	 mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
	 pin_pass = mSettings.getInt(APP_PREFERENCES_PIN_PASS, 1234);
	 pin = mSettings.getBoolean(APP_PREFERENCES_PIN, false);
	 
	 wallpaperManager = WallpaperManager.getInstance(this);
	
	 operator = (TextView) findViewById(R.id.textView7);
	 battery = (TextView) findViewById(R.id.textView15);
	 gps = (ImageView) findViewById(R.id.imageView5);
	 bluetooth = (ImageView) findViewById(R.id.imageView2);
	 top_bar();
	 
	 
	 operator.setTypeface(regular);
	 battery.setTypeface(regular);
	
	    IntentFilter filtertime = new IntentFilter();
	    filtertime.addAction("android.intent.action.TIME_TICK");
	    registerReceiver(mTimeInfoReceiver, filtertime);
	    
	    
	    
	    IntentFilter filters = new IntentFilter();
	    filters.addAction("android.intent.action.BATTERY_CHANGED");
	    filters.addAction("android.location.PROVIDERS_CHANGED");
	    filters.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
	    filters.addAction("android.net.wifi.WIFI_STATE_CHANGED");
	    filters.addAction("android.net.wifi.STATE_CHANGE");
	    filters.addAction("android.intent.action.WALLPAPER_CHANGED");
	    registerReceiver(mInfoReceiver, filters);
	    
	    psListener = new myPhoneStateListener();
		 telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		 telephonyManager.listen(psListener,PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		 
	    
	 if(pin == true){
		 tabsAdapter = new TabsFragmentPagerAdapter(getSupportFragmentManager());
		 viewPager.setAdapter(tabsAdapter);
		 viewPager.setCurrentItem(1);
	 }
	 else {
		 tabsAdapterOne = new TabsFragmentPagerAdapterOne(getSupportFragmentManager());
		 viewPager.setAdapter(tabsAdapterOne);
		 viewPager.setCurrentItem(1);
	 }
	 
	 
	 //actionBar = getActionBar();
	 //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	 /*
	 for (String tab_name : days) {
         actionBar.addTab(actionBar.newTab().setText(tab_name)
                 .setTabListener(this));
     }
	 */
	 
	 viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
	 
	 @Override
	 public void onPageSelected(int arg) {
	 // TODO Auto-generated method stub
	// actionBar.setSelectedNavigationItem(arg);
		 if(pin == false){
		 unlockHomeButton();
		 }
	 }
	 
	 @Override
	 public void onPageScrolled(int arg0, float arg1, int arg2) {
	 // TODO Auto-generated method stub
	 
	 }
	 
	 @Override
	 public void onPageScrollStateChanged(int arg0) {
	 // TODO Auto-generated method stub
	 
	 }
	 });
	 
	 init();

		// unlock screen in case of app get killed by system
		if (getIntent() != null && getIntent().hasExtra("kill")
				&& getIntent().getExtras().getInt("kill") == 1) {
			enableKeyguard();
			unlockHomeButton();
		} else {

			try {
				// disable keyguard
				disableKeyguard();
				

				// lock home button
				lockHomeButton();
				

				// start service for observing intents
				startService(new Intent(this, LockscreenService.class));
				
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

				// listen the events get fired during the call
				StateListener phoneStateListener = new StateListener();
				TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
				telephonyManager.listen(phoneStateListener,
						PhoneStateListener.LISTEN_CALL_STATE);

			} catch (Exception e) {
			}

		}
	 
	 }
	 
	
	 
	 @Override
	 public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
	 // TODO Auto-generated method stub
	 
	 }
	 
	 @Override
	 public void onTabSelected(Tab tab, FragmentTransaction arg1) {
	 // TODO Auto-generated method stub
	 viewPager.setCurrentItem(tab.getPosition());
	 }
	 
	 @Override
	 public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
	 // TODO Auto-generated method stub
	 
	 }
	 
	 private void init() {
			mLockscreenUtils = new LockscreenUtils();
			
		}

		// Handle events of calls and unlock screen if necessary
		public class StateListener extends PhoneStateListener {
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {

				super.onCallStateChanged(state, incomingNumber);
				switch (state) {
				case TelephonyManager.CALL_STATE_RINGING:
					unlockHomeButton();
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:
					
					break;
				case TelephonyManager.CALL_STATE_IDLE:
					
					break;
				}
			}
		};

		// Don't finish Activity on Back press
		@Override
		public void onBackPressed() {
			return;
		}

		// Handle button clicks
		@Override
		public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

			if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
					|| (keyCode == KeyEvent.KEYCODE_POWER)
					|| (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
					|| (keyCode == KeyEvent.KEYCODE_CAMERA)) {
				return true;
			}
			if ((keyCode == KeyEvent.KEYCODE_HOME)) {

				return true;
			}

			return false;

		}

		// handle the key press events here itself
		public boolean dispatchKeyEvent(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP
					|| (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)
					|| (event.getKeyCode() == KeyEvent.KEYCODE_POWER)) {
				return false;
			}
			if ((event.getKeyCode() == KeyEvent.KEYCODE_HOME)) {

				return true;
			}
			return false;
		}

		// Lock home button
		public void lockHomeButton() {
			mLockscreenUtils.lock(MainActivity.this);
			
		}

		// Unlock home button and wait for its callback
		public void unlockHomeButton() {
			mLockscreenUtils.unlock();
		}
		
		public void noLockButton(){
			mLockscreenUtils.nolock();
		}
		
		

		// Simply unlock device when home button is successfully unlocked
		@Override
		public void onLockStatusChanged(boolean isLocked) {
			if (!isLocked) {
				unlockDevice();
			}
		}

		@Override
		protected void onStop() {
			super.onStop();
			unlockHomeButton();
			
		}

		public void hiddenInputMethod() {

		    InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.this.INPUT_METHOD_SERVICE);
		    if (getCurrentFocus() != null)
		        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
		
		@SuppressWarnings("deprecation")
		private void disableKeyguard() {
			KeyguardManager mKM = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
			KeyguardManager.KeyguardLock mKL = mKM.newKeyguardLock("IN");
			mKL.disableKeyguard();
		}

		@SuppressWarnings("deprecation")
		private void enableKeyguard() {
			KeyguardManager mKM = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
			KeyguardManager.KeyguardLock mKL = mKM.newKeyguardLock("IN");
			mKL.reenableKeyguard();
		}
		
		//Simply unlock device by finishing the activity
		private void unlockDevice()
		{
			finish();
		}

		private void top_bar() {
			
			////OPERATOR
			TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			   operator.setText(tManager.getSimOperatorName());
			   if(operator.getText().toString().length() == 0){
				   operator.setText("NO SIM");
			   }
			   
			
		   ///GPS 	
		    	LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
		        	gps.setVisibility(View.VISIBLE);
		        }
		        else{
		        	gps.setVisibility(View.GONE);
		        }
		        
		        
		   ///BLUETOOTH
		      BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		      if (mBluetoothAdapter != null){
		    	if (mBluetoothAdapter.isEnabled()) {
		    		bluetooth.setVisibility(View.VISIBLE);
		    	}
		      }
		    	
		    ////WALLPAPER
				WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
				Drawable wallpaperDrawable = wallpaperManager.getDrawable();
				ImageView img6 = (ImageView) findViewById(R.id.imageView6);
				img6.setImageDrawable(wallpaperDrawable);
		        }
		
		 
		
		
		public class myPhoneStateListener extends PhoneStateListener {
		    public int signalStrengthValue;

		    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
		        super.onSignalStrengthsChanged(signalStrength);
		        if (signalStrength.isGsm()) {
		            if (signalStrength.getGsmSignalStrength() != 99)
		                signalStrengthValue = signalStrength.getGsmSignalStrength() * 2 - 113;
		            else
		                signalStrengthValue = signalStrength.getGsmSignalStrength();
		        } else {
		            signalStrengthValue = signalStrength.getCdmaDbm();
		        }
		        
		        ImageView i = (ImageView) findViewById(R.id.imageView3);
		        
		        int signalStrengthValue2 = signalStrengthValue * -1;
		        
		        if(signalStrengthValue2 > 50){
		        	i.setImageDrawable(getResources().getDrawable(R.drawable.s5));
		        }
		        else if (signalStrengthValue2 > 40){
		        	i.setImageDrawable(getResources().getDrawable(R.drawable.s4));
		        }
		        else if (signalStrengthValue2 > 30){
		        	i.setImageDrawable(getResources().getDrawable(R.drawable.s3));
		        }
		        else if (signalStrengthValue2 > 15){
		        	i.setImageDrawable(getResources().getDrawable(R.drawable.s2));
		        }
		        else if (signalStrengthValue2 > 0){
		        	i.setImageDrawable(getResources().getDrawable(R.drawable.s1));
		        }
		        else{
		        	i.setVisibility(View.GONE);
		        }
		        
		    }
		}


		
	
	private BroadcastReceiver mTimeInfoReceiver = new BroadcastReceiver(){
	    @Override
	    public void onReceive(Context ctxt, Intent intent) {
	    	
	    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	   	 String currentDateandTime4 = sdf.format(new Date());
	   	 
    	
	    	MainFragment.textView1.setText(currentDateandTime4);
	    	
	    	
	    	
	    	
	    	
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
				MainFragment.textView3.setText(currentDateandudd + currentDateandud + currentDateandum);
			}
			
			
			
			else if (Locale.getDefault().getLanguage().contains("ru")){
				
				

				String ssz = currentDateandudd.substring(0,1).toUpperCase() + currentDateandudd.substring(1);
				
				if(currentDateandum.contains("Декабрь")){
					currentDateandum = "декабря";
					MainFragment.textView3.setText(ssz + currentDateandud + currentDateandum);
				}
				else if(currentDateandum.contains("Январь")){
					currentDateandum = "января";
					MainFragment.textView3.setText(ssz + currentDateandud + currentDateandum);
				}
				else if(currentDateandum.contains("Февраль")){
					currentDateandum = "февраля";
					MainFragment.textView3.setText(ssz + currentDateandud + currentDateandum);
				}
				else if(currentDateandum.contains("Март")){
					currentDateandum = "марта";
					MainFragment.textView3.setText(ssz + currentDateandud + currentDateandum);
				}
				else if(currentDateandum.contains("Апрель")){
					currentDateandum = "апреля";
					MainFragment.textView3.setText(ssz + currentDateandud + currentDateandum);
				}
				else if(currentDateandum.contains("Май")){
					currentDateandum = "мая";
					MainFragment.textView3.setText(ssz + currentDateandud + currentDateandum);
				}
				else if(currentDateandum.contains("Июнь")){
					currentDateandum = "июня";
					MainFragment.textView3.setText(ssz + currentDateandud + currentDateandum);
				}
				else if(currentDateandum.contains("Июль")){
					currentDateandum = "июля";
					MainFragment.textView3.setText(ssz + currentDateandud + currentDateandum);
				}
				else if(currentDateandum.contains("Август")){
					currentDateandum = "августа";
					MainFragment.textView3.setText(ssz + currentDateandud + currentDateandum);
				}
				else if(currentDateandum.contains("Сентябрь")){
					currentDateandum = "сентября";
					MainFragment.textView3.setText(ssz + currentDateandud + currentDateandum);
				}
				else if(currentDateandum.contains("Октябрь")){
					currentDateandum = "октября";
					MainFragment.textView3.setText(ssz + currentDateandud + currentDateandum);
				}
				else if(currentDateandum.contains("Ноябрь")){
					currentDateandum = "ноября";
					MainFragment.textView3.setText(ssz + currentDateandud + currentDateandum);
				}
				
				
				
			}
			else {
				MainFragment.textView3.setText(currentDateandDate);
			}
	    	
	  }
};






private BroadcastReceiver mInfoReceiver = new BroadcastReceiver(){
    @Override
    public void onReceive(Context ctxt, Intent intent) {
    	
    	if (intent.getAction().matches("android.net.wifi.WIFI_STATE_CHANGED") || intent.getAction().matches("android.net.wifi.STATE_CHANGE")) {
    		ConnectivityManager connMgr = (ConnectivityManager) ctxt.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            ImageView img1 = (ImageView) findViewById(R.id.imageView1);
            boolean isConnected = wifi != null && wifi.isConnectedOrConnecting(); 
            if (isConnected) {
                img1.setVisibility(View.VISIBLE);
            } else {
            	img1.setVisibility(View.GONE);
            }
    	}
    	
    	else if(intent.getAction().matches("android.location.PROVIDERS_CHANGED")){
    		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
	        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
	        	gps.setVisibility(View.VISIBLE);
	        }
	        else{
	        	gps.setVisibility(View.GONE);
	        }
    	}
    	else if(intent.getAction().matches("android.bluetooth.adapter.action.STATE_CHANGED")){
    		final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.ERROR);
    		switch (state) {
    			case BluetoothAdapter.STATE_OFF:
    				bluetooth.setVisibility(View.GONE);
    				break;
    			case BluetoothAdapter.STATE_ON:
    				bluetooth.setVisibility(View.VISIBLE);
    				break;
    		}
    	}
    	else if(intent.getAction().matches("android.intent.action.BATTERY_CHANGED")){
    		int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
		      battery.setText(String.valueOf(level) + " %");
		      
		      ImageView im4 = (ImageView) findViewById(R.id.imageView4);
		      if (level == 100){
		    	  im4.setImageDrawable(getResources().getDrawable(R.drawable.b_100));
		      }
		      else if (level > 90){
		    	  im4.setImageDrawable(getResources().getDrawable(R.drawable.b_90));
		      }
		      else if (level > 70){
		    	  im4.setImageDrawable(getResources().getDrawable(R.drawable.b_70));
		      }
		      else if (level > 50){
		    	  im4.setImageDrawable(getResources().getDrawable(R.drawable.b_50));
		      }
		      else if (level > 30){
		    	  im4.setImageDrawable(getResources().getDrawable(R.drawable.b_30));
		      }
		      else{
		    	  im4.setImageDrawable(getResources().getDrawable(R.drawable.b_10));
		      }
		      
		      int status = intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
		      
		     if (status == BatteryManager.BATTERY_STATUS_CHARGING){
		    	 im4.setImageDrawable(getResources().getDrawable(R.drawable.b_ch));
		      } else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING){
		     
		      } else if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING){
		    
		      } else if (status == BatteryManager.BATTERY_STATUS_FULL){
		     
		     } else {
		    
		      }
		     
    	}
    	
    	else if (intent.getAction().matches("android.intent.action.WALLPAPER_CHANGED")){
    		
			Drawable wallpaperDrawable = wallpaperManager.getDrawable();
			ImageView img6 = (ImageView) findViewById(R.id.imageView6);
			img6.setImageDrawable(wallpaperDrawable);
    	}
    	
  }
    


};


}
