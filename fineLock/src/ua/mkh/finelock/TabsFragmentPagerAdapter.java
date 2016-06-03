package ua.mkh.finelock;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsFragmentPagerAdapter extends FragmentPagerAdapter {
	 
	 public TabsFragmentPagerAdapter(FragmentManager fm) {
	 super(fm);
	 // TODO Auto-generated constructor stub
	 }
	 
	 @Override
	 public Fragment getItem(int index) {
	 // TODO Auto-generated method stub
	 if(index == 1)
	 return new MainFragment();
	 if(index == 0)
	 return new PinFragment();
	 
	 
	 return null;
	 }
	 
	 @Override
	 public int getCount() {
	 // TODO Auto-generated method stub
	 return 2;
	 }
	 
	}
