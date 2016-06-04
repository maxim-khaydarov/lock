package ua.mkh.finelock;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.provider.SyncStateContract.Constants;
import android.support.v4.app.NotificationCompat;


public class LockscreenService extends Service {

	private BroadcastReceiver mReceiver;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	
	@Override
	public void onCreate() {
		KeyguardManager.KeyguardLock k1;

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

		KeyguardManager km =(KeyguardManager)getSystemService(KEYGUARD_SERVICE);
		k1= km.newKeyguardLock("IN");
		k1.disableKeyguard();
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		mReceiver = new LockscreenIntentReceiver();
		registerReceiver(mReceiver, filter);
		
		super.onCreate();

	}
	
	@Override
    public void onStart(Intent intent, int startId) {
          // TODO Auto-generated method stub
          super.onStart(intent, startId);
}

	

	// Unregister receiver
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}
}
