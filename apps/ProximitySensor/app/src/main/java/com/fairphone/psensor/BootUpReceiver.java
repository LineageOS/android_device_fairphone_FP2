package com.fairphone.psensor;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fairphone.psensor.helpers.CalibrationStatusHelper;


public class BootUpReceiver extends BroadcastReceiver {

    public static final String SETTINGS_SECURE_USER_SETUP_COMPLETE = "user_setup_complete";

    public BootUpReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            UpdateFinalizerService.startActionBootUp(context);

            if (CalibrationStatusHelper.isCalibrationPending(context)) {
                CalibrationStatusHelper.setCalibrationCompleted(context);
            }

            CalibrationService.startActionHandleBootCompleted(context);
        }
        if (Intent.ACTION_SHUTDOWN.equals(intent.getAction())) {
            UpdateFinalizerService.startActionShutdown(context);
        }
    }
}
