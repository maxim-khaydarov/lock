package ua.mkh.finelock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ToggleButton;

public class SettingsActivity extends Activity implements
OnCheckedChangeListener {
	
	public static final String APP_PREFERENCES = "mysettings"; 
	public static final String APP_PREFERENCES_PIN_PASS = "pin_pass";
	public static final String APP_PREFERENCES_PIN = "pin";
	
	SharedPreferences mSettings;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        
        final EditText ed1 = (EditText) findViewById(R.id.editText1);
        ToggleButton tg1 = (ToggleButton) findViewById(R.id.toggleButton1);
        tg1.setOnCheckedChangeListener(this);
        
        Button save = (Button) findViewById(R.id.button1);
        
        save.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		if (ed1.getText().toString().length() == 0){
        			Intent i = new Intent(SettingsActivity.this, MainActivity.class);
            	   	startActivity(i);
        		}
        		else {
        		Editor editor = mSettings.edit();
        	   	editor.putInt(APP_PREFERENCES_PIN_PASS, Integer.parseInt(ed1.getText().toString()));
        	   	editor.commit();
        	   	Intent i = new Intent(SettingsActivity.this, MainActivity.class);
        	   	startActivity(i);
        		}
        	}
        });
        
       
        
        
	}

	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked){
			Editor editor = mSettings.edit();
    	   	editor.putBoolean(APP_PREFERENCES_PIN, true).commit();
		}
			
		else{
			Editor editor = mSettings.edit();
			editor.remove(APP_PREFERENCES_PIN).commit();
		}
			
	}
	
}
