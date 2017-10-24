package com.fairphone.psensor;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.pm.PackageManager;
import android.util.Log;

public class CalibrationReceiver extends BroadcastReceiver
{
	private static final String TAG = "CalibrationReceiver";
			
	@Override
	public void onReceive(Context localcontext, Intent localintent) 
	{
    	PackageManager hiptest_pm = localcontext.getPackageManager();
    	ComponentName psen_cn = new ComponentName(localcontext, CalibrationActivity.class);
    	String secAction = "android.provider.Telephony.SECRET_CODE";	
		Log.d(TAG, "CalibrationReceiver: " + localintent);
    
	    /* Get user input secret code */
	    String action = localintent.getAction();
	    String host = localintent.getData() != null ? localintent.getData().getHost() : null;

	    /* Secret code: *#*#8765#*#* */
	    if (secAction.equals(action) && "8765".equals(host)) 
	    {
	    	/* Show psensor cal APK icon*/
			hiptest_pm.setComponentEnabledSetting(psen_cn, 
					PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
			  	
	    	Intent hiptest_Intent = new Intent(localcontext, CalibrationActivity.class);
			hiptest_Intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			localcontext.startActivity(hiptest_Intent);
	    }
	    else
	    {
			hiptest_pm.setComponentEnabledSetting(psen_cn, 
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
	    }
	}
}
