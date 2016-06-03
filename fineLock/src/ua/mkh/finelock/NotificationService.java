package ua.mkh.finelock;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

public class NotificationService extends AccessibilityService {
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		
	    System.out.println("onAccessibilityEvent");
	    if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
	        System.out.println("notification: " + event.getText());
	        //Toast.makeText(getApplication(), (CharSequence) event.getText(), Toast.LENGTH_SHORT);
	    }
	}
	@Override
	protected void onServiceConnected() {
	    System.out.println("onServiceConnected");
	    AccessibilityServiceInfo info = new AccessibilityServiceInfo();
	    info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
	    info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
	    info.notificationTimeout = 100;
	    setServiceInfo(info);
	}

	@Override
	public void onInterrupt() {
	    System.out.println("onInterrupt");
	}
	}
