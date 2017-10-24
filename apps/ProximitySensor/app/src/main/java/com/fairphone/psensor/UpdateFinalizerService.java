package com.fairphone.psensor;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.fairphone.psensor.helpers.CalibrationStatusHelper;

public class UpdateFinalizerService extends IntentService {

    private static final String ACTION_BOOTUP_COMPLETE   = "com.fairphone.updatefinalizer.action.BOOT_COMPLETED";
    private static final String ACTION_CHECK_CALIBRATION_PENDING = "com.fairphone.updatefinalizer.action.CALIBRATION_PENDING";
    private static final String ACTION_CLEAR_NOTIFICATION   = "com.fairphone.updatefinalizer.action.CLEAR_NOTIFICATION";
    private static final String ACTION_SHUTDOWN = "com.fairphone.updatefinalizer.action.SHUTDOWN_ACTION";

    /* check every 3 hours */
    private static final int checkIfCalibrationPendingDelay = 1000 /*seconds*/ * 60 /* minutes */ * 60 /* hours */ * 3;

    private static final int mNotificationIDPleaseCalibrate = 1;

    public UpdateFinalizerService() {
        super("UpdateFinalizerService");
    }

    public static void startActionCheckCalibrationPending(Context context) {
        Intent intent = new Intent(context, UpdateFinalizerService.class);
        intent.setAction(ACTION_CHECK_CALIBRATION_PENDING);
        context.startService(intent);
    }

    public static void startActionShutdown(Context context) {
        Intent intent = new Intent(context, UpdateFinalizerService.class);
        intent.setAction(ACTION_SHUTDOWN);
        context.startService(intent);
    }

    public static void startActionClearNotification(Context context) {
        Intent intent = new Intent(context, UpdateFinalizerService.class);
        intent.setAction(ACTION_CLEAR_NOTIFICATION);
        context.startService(intent);
    }

    public static void startActionBootUp(Context context) {
        Intent intent = new Intent(context, UpdateFinalizerService.class);
        intent.setAction(ACTION_BOOTUP_COMPLETE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_BOOTUP_COMPLETE.equals(action)) {
                handleCheckCalibrationPending();
            }
            if (ACTION_CHECK_CALIBRATION_PENDING.equals(action)) {
                handleCheckCalibrationPending();
            }
            if (ACTION_CLEAR_NOTIFICATION.equals(action)) {
                handleClearNotification();
            }
            if (ACTION_SHUTDOWN.equals(action)) {
                handleShutdown();
            }
        }
    }

    private void handleClearNotification() {
        clearNotification();
    }

    private void clearNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(mNotificationIDPleaseCalibrate);
    }

    private void handleCheckCalibrationPending() {
        if (!UpdateFinalizerActivityFromNotification.isNotShowAnymore(this) && CalibrationStatusHelper.hasToBeCalibrated(this)) {
            showNotification();
            setAlarm();
        }
        else {
            clearNotification();
        }
    }

    private void setAlarm() {
        Intent intent = new Intent(this, UpdateFinalizerService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this,0, intent, PendingIntent.FLAG_ONE_SHOT);
        intent.setAction(ACTION_CHECK_CALIBRATION_PENDING);
        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME, checkIfCalibrationPendingDelay, pendingIntent);
    }

    private void showNotification() {
        Log.d(getString(R.string.logtag), "Update not yet Finalized... Setting Notification.");
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, getUpdateFinalizerActivity(), PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder  notBuilder = new NotificationCompat.Builder(this);
        notBuilder.setContentTitle(getString(R.string.NotificationTitle));
        notBuilder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(getString(R.string.NotificationText)));
        notBuilder.setSmallIcon(R.drawable.ic_stat_action_info);
        notBuilder.setContentIntent(pendingIntent);
        notBuilder.setColor(getResources().getColor(R.color.theme_primary));
        NotificationManager notificationManager =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationIDPleaseCalibrate, notBuilder.build());
    }

    @NonNull
    private Intent getUpdateFinalizerActivity() {
        Intent dialogIntent = new Intent(this, UpdateFinalizerActivityFromNotification.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return dialogIntent;
    }

    @NonNull
    private Intent getConfirmationModalIntent() {
        Intent intent = new Intent(this, UpdateFinalizerService.class);
        intent.setAction(ACTION_CHECK_CALIBRATION_PENDING);
        return intent;
    }

    private void handleShutdown() {
//        That might be a bit too anoying...
//        if (!isSuppressed())
//            UpdateFinalizerActivity.enable(this);
    }
}
