/*
 * Copyright (C) 2017 Fairphone B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fairphone.psensor;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fairphone.psensor.helpers.CalibrationStatusHelper;
import com.fairphone.psensor.notifications.ReceiverModuleChangedNotification;

public class CalibrationService extends IntentService {

    private static final String TAG = "CalibrationService";

    private static final boolean DEBUG = false;

    /**
     * Grace period before firing a reminder (in milliseconds): 1 day.
     */
    private static final long REMINDER_GRACE_PERIOD_MS = 1000 * 60 * 60 * 24;

    /**
     * Action to handle a change of receiver module by scheduling a new calibration.
     */
    private static final String ACTION_HANDLE_RECEIVER_MODULE_CHANGED =
            "com.fairphone.psensor.action.handle_receiver_module_changed";

    /**
     * Action to remind the user at a later point (next reboot or after a grace period) that there
     * was a change of receiver module.
     */
    private static final String ACTION_REMIND_RECEIVER_MODULE_CHANGED_LATER =
            "com.fairphone.psensor.action.remind_receiver_module_changed_later";

    public static void startActionHandleBootCompleted(Context context) {
        context.startService(
                new Intent(context, CalibrationService.class).setAction(Intent.ACTION_BOOT_COMPLETED));
    }

    public static void startActionRemindReceiverModuleChangedLater(Context context) {
        context.startService(
                new Intent(context, CalibrationService.class)
                        .setAction(ACTION_REMIND_RECEIVER_MODULE_CHANGED_LATER));
    }

    public CalibrationService() {
        super("CalibrationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
                handleBootComplete();
            } else if (ACTION_HANDLE_RECEIVER_MODULE_CHANGED.equals(action)) {
                handleActionReceiverModuleChanged();
            } else if (ACTION_REMIND_RECEIVER_MODULE_CHANGED_LATER.equals(action)) {
                handleActionRemindReceiverModuleChangedLater();
            } else {
                Log.e(TAG, "Unknown action received: " + action);
            }
        }
    }

    /**
     * Handle the boot completed action.
     */
    private void handleBootComplete() {
        if (CalibrationStatusHelper.isCalibrationNeededAfterReceiverModuleChanged(this)) {
            ReceiverModuleChangedNotification.show(this);
        }
    }

    /**
     * Handle the receiver module changed action.
     */
    private void handleActionReceiverModuleChanged() {
        CalibrationStatusHelper.setCalibrationNeededAfterReceiverModuleChanged(this);

        ReceiverModuleChangedNotification.show(this);
    }

    /**
     * Handle the receiver module changed reminder action.
     */
    private void handleActionRemindReceiverModuleChangedLater() {
        ReceiverModuleChangedNotification.dismiss(this);

        // TODO Activate the boot-up receiver

        ((AlarmManager) getSystemService(Context.ALARM_SERVICE))
                .set(AlarmManager.RTC, System.currentTimeMillis() + REMINDER_GRACE_PERIOD_MS,
                        PendingIntent.getService(this, 0,
                                new Intent(this, CalibrationService.class)
                                        .setAction(ACTION_HANDLE_RECEIVER_MODULE_CHANGED),
                                PendingIntent.FLAG_UPDATE_CURRENT));
        if (DEBUG) Log.d(TAG, "Alarm set to trigger " + ACTION_HANDLE_RECEIVER_MODULE_CHANGED
                + " once again in " + REMINDER_GRACE_PERIOD_MS / 1000 + " seconds");
    }

}
