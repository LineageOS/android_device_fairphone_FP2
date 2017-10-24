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

package com.fairphone.psensor.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.fairphone.psensor.R;
import com.fairphone.psensor.ReceiverModuleChangedActivity;

public class ReceiverModuleChangedNotification {

    private static final int NOTIFICATION_ID = 1;

    /**
     * Dismiss the notification.
     */
    public static void dismiss(Context context) {
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .cancel(NOTIFICATION_ID);
    }

    /**
     * Show a <strong>public</strong> notification requesting the end-user to calibrate the
     * proximity sensor because the receiver module changed.
     * <p>
     * The notification is permanent and can only be dismissed through a call to
     * {@link #dismiss(Context)}.
     */
    public static void show(Context context) {
        final Notification notification = new Notification.Builder(context)
                .setContentTitle(context.getString(R.string.receiver_module_changed_title))
                .setContentText(context.getString(R.string.receiver_module_changed_summary))
                .setSmallIcon(R.drawable.ic_stat_action_calibrate)
                .setColor(context.getResources().getColor(R.color.theme_primary))
                .setStyle(new Notification.BigTextStyle().bigText(
                        context.getString(R.string.receiver_module_changed_extended_summary)))
                .setContentIntent(getPendingContentIntent(context))
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setPriority(Notification.PRIORITY_MAX)
                .setOngoing(true)
                .build();

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(NOTIFICATION_ID, notification);
    }

    private static PendingIntent getPendingContentIntent(Context context) {
        return PendingIntent.getActivity(context, 0,
                new Intent(context, ReceiverModuleChangedActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NEW_TASK),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
