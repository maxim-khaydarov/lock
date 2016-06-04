package ua.mkh.finelock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartLock extends Activity{
	
	
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 
	 Intent myIntent = new Intent(this, MainActivity.class);
	 myIntent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
		startActivity(myIntent);
	 
	}
	

}
