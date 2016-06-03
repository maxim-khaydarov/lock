package ua.mkh.finelock;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;


public class PinFragment extends Fragment {

	public static final String APP_PREFERENCES = "mysettings"; 
	public static final String APP_PREFERENCES_PIN_PASS = "pin_pass";
	
	SharedPreferences mSettings;
	
	int pin_pass = 1234;
	
	Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b11, cancel, sos;
	ToggleButton b13, b14, b15, b16;
	TextView tx1, tx2, tx3, tx4, tx5, tx6, tx7, tx8, tx9, tx10, tx11, tx12, tx13, tx14, tx15,
	tx16, tx17, tx18, tx19;
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	 Bundle savedInstanceState) {
	 // TODO Auto-generated method stub
	 View view = inflater.inflate(R.layout.fragment_pin, container, false);
	 
	 Typeface thin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Thin.ttf");
	 Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Regular.ttf");
	 
	 
	 mSettings = this.getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
	 pin_pass = mSettings.getInt(APP_PREFERENCES_PIN_PASS, 1234);
	 
	 
	 b1 = (Button) view.findViewById(R.id.button1);
	 b2 = (Button) view.findViewById(R.id.button2);
	 b3 = (Button) view.findViewById(R.id.button3);
	 b4 = (Button) view.findViewById(R.id.button4);
	 b5 = (Button) view.findViewById(R.id.button5);
	 b6 = (Button) view.findViewById(R.id.button6);
	 b7 = (Button) view.findViewById(R.id.button7);
	 b8 = (Button) view.findViewById(R.id.button8);
	 b9 = (Button) view.findViewById(R.id.button9);
	 b11 = (Button) view.findViewById(R.id.button11);
	 
	 cancel = (Button) view.findViewById(R.id.button13);
	 sos = (Button) view.findViewById(R.id.button14);
	
	 tx1 = (TextView) view.findViewById(R.id.textView1);
	 tx2 = (TextView) view.findViewById(R.id.textView2);
	 tx3 = (TextView) view.findViewById(R.id.textView3);
	 tx4 = (TextView) view.findViewById(R.id.textView4);
	 tx5 = (TextView) view.findViewById(R.id.textView5);
	 tx6 = (TextView) view.findViewById(R.id.textView6);
	 tx7 = (TextView) view.findViewById(R.id.textView7);
	 tx8 = (TextView) view.findViewById(R.id.textView8);
	 tx9 = (TextView) view.findViewById(R.id.textView9);
	 tx10 = (TextView) view.findViewById(R.id.textView10);
	 tx11 = (TextView) view.findViewById(R.id.textView11);
	 tx12 = (TextView) view.findViewById(R.id.textView12);
	 tx13 = (TextView) view.findViewById(R.id.textView13);
	 tx14 = (TextView) view.findViewById(R.id.textView14);
	 tx15 = (TextView) view.findViewById(R.id.textView15);
	 tx16 = (TextView) view.findViewById(R.id.textView16);
	 tx17 = (TextView) view.findViewById(R.id.textView17);
	 tx18 = (TextView) view.findViewById(R.id.textView18);
	 tx19 = (TextView) view.findViewById(R.id.textView19);
	 
	 tx1.setTypeface(regular);
	 tx2.setTypeface(thin);
	 tx3.setTypeface(thin);
	 tx4.setTypeface(thin);
	 tx5.setTypeface(thin);
	 tx6.setTypeface(thin);
	 tx7.setTypeface(thin);
	 tx8.setTypeface(thin);
	 tx9.setTypeface(thin);
	 tx10.setTypeface(thin);
	 tx11.setTypeface(thin);
	 tx12.setTypeface(thin);
	 tx13.setTypeface(thin);
	 tx14.setTypeface(thin);
	 tx15.setTypeface(thin);
	 tx16.setTypeface(thin);
	 tx17.setTypeface(thin);
	 tx18.setTypeface(thin);
	 tx19.setTypeface(thin);
	 cancel.setTypeface(regular);
	 sos.setTypeface(regular);
	 

	 
	 
	 b13 = (ToggleButton) view.findViewById(R.id.toggleButton1);
	 b14 = (ToggleButton) view.findViewById(R.id.toggleButton2);
	 b15 = (ToggleButton) view.findViewById(R.id.toggleButton3);
	 b16 = (ToggleButton) view.findViewById(R.id.toggleButton4);
	 final EditText ed1 = (EditText) view.findViewById(R.id.editText1);
	 
	 cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ed1.setText("");
				b13.setChecked(false);
          		b14.setChecked(false);
          		b15.setChecked(false);
          		b16.setChecked(false);
			}
		});
	 
	 b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ed1.append("1");
			}
		});
	 b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ed1.append("2");
			}
		});
	 b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ed1.append("3");
			}
		});
	 b4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ed1.append("4");
			}
		});
	 b5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ed1.append("5");
			}
		});
	 b6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ed1.append("6");
			}
		});
	 b7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ed1.append("7");
			}
		});
	 b8.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ed1.append("8");
			}
		});
	 b9.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ed1.append("9");
			}
		});
	 b11.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ed1.append("0");
			}
		});
	 
	
	 ed1.addTextChangedListener(new TextWatcher() {

         public void afterTextChanged(Editable s) {

        	 

         }

         public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

         public void onTextChanged(CharSequence s, int start, int before, int count) {
        	 if (s.toString().length() == 1){
           	  b13.setChecked(true);
           	
             }
        	 if (s.toString().length() == 2){
              	  b14.setChecked(true);
              	
                }
        	 if (s.toString().length() == 3){
              	  b15.setChecked(true);
              	
                }
        	 if (s.toString().length() == 4){
              	  b16.setChecked(true);
              	  if (Integer.parseInt(s.toString()) == pin_pass){
              		((MainActivity) getActivity()).unlockHomeButton();
              	  }
              	  else{
              		  ed1.setText("");
              		
              		Handler handler = new Handler(); 
              	    handler.postDelayed(new Runnable() { 
              	         public void run() { 
              	        	b13.setChecked(false);
                      		b14.setChecked(false);
                      		b15.setChecked(false);
                      		b16.setChecked(false);
              	         } 
              	    }, 200); 
              	 
              	  }
                }
        	 
         }
      });
	 
	 return view;
	 
	
	 }
	
	
}
