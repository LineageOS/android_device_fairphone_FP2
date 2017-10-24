package com.fairphone.psensor.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fairphone.psensor.ProximitySensorConfiguration;
import com.fairphone.psensor.R;

/**
 * Helper methods to access the shared preferences of the app.
 */
public class CalibrationStatusHelper {

    private static final String TAG = "CalibrationStatusHelper";

    private static final boolean DEBUG = false;

    /**
     * Empty constructor to avoid instantiation.
     */
    private CalibrationStatusHelper() {
    }

    /**
     * Determine if the current device needs to be calibrated.<br>
     * <br>
     * The conditions are as follows:
     * <ol>
     * <li>The memory needs to be accessible (R/W).</li>
     * <li>Either: there must not be an evidence that the device has been calibrated in the shared preferences.</li>
     * <li>Or either: the persisted offset compensation must be equal to 0.</li>
     * </ol>
     *
     * @param context The context.
     * @param calibrateNullCompensation Flag to decide if the logic is based on the current compensation offset or on
     * the shared preferences.
     * @return <em>true</em> if a calibration should take place, <em>false</em> if the device has been calibrated at
     * one point or if the memory is not accessible.
     */
    public static boolean hasToBeCalibrated(Context context, boolean calibrateNullCompensation) {
        boolean hasToBeCalibrated;

        if (ProximitySensorConfiguration.canReadFromAndPersistToMemory()) {
            if (calibrateNullCompensation) {
                final ProximitySensorConfiguration persistedConfiguration = ProximitySensorConfiguration.readFromMemory();
                hasToBeCalibrated = (persistedConfiguration != null) && (persistedConfiguration.offsetCompensation == 0);

                if (DEBUG) Log.d(TAG, "Calibration depends on null compensation, required=" + hasToBeCalibrated);
            } else {
                final SharedPreferences sharedPref = context.getSharedPreferences(
                        context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                hasToBeCalibrated = !sharedPref.getBoolean(context.getString(R.string.preference_successfully_calibrated), false);

                if (DEBUG) Log.d(TAG, "Calibration does not depend on null compensation, required=" + hasToBeCalibrated);
            }
        } else {
            /* Memory is not accessible, so no calibration is required. */
            hasToBeCalibrated = false;
        }

        return hasToBeCalibrated;
    }

    /**
     * Call to <code>hasToBeCalibrated(context, false)</code>.
     *
     * @param context The context.
     * @return <em>true</em> if a calibration should take place, <em>false</em> if the device has been calibrated at
     * one point.
     * @see CalibrationStatusHelper#hasToBeCalibrated(Context, boolean)
     */
    public static boolean hasToBeCalibrated(Context context) {
        return hasToBeCalibrated(context, false);
    }

    public static boolean isCalibrationPending(Context context) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(context.getString(R.string.preference_pending_calibration), false);
    }

    public static void setCalibrationCompleted(Context context) {
        if (DEBUG) Log.d(TAG, "Calibration completed");

        final SharedPreferences sharedPreferences = context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(
                context.getString(R.string.preference_calibration_needed_after_receiver_module_changed), false);
        editor.putBoolean(context.getString(R.string.preference_pending_calibration), false);

        editor.apply();
    }

    public static void setCalibrationSuccessful(Context context) {
        if (DEBUG) Log.d(TAG, "Calibration process successful, now pending");

        final SharedPreferences sharedPreferences = context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(context.getString(R.string.preference_successfully_calibrated), true);
        editor.putBoolean(context.getString(R.string.preference_pending_calibration), true);

        editor.apply();
    }

    public static boolean isCalibrationNeededAfterReceiverModuleChanged(Context context) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        return sharedPreferences.getBoolean(
                context.getString(R.string.preference_calibration_needed_after_receiver_module_changed), false);
    }

    public static void setCalibrationNeededAfterReceiverModuleChanged(Context context) {
        if (DEBUG) Log.d(TAG, "Calibration needed because the receiver module changed");

        final SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(
                context.getString(R.string.preference_calibration_needed_after_receiver_module_changed), true);
        /*
         * Force any previous calibration done during this session, but before this call, to be
         * invalid. We want to make sure the pending calibration was not done before we could detect
         * a module change.
         */
        editor.putBoolean(context.getString(R.string.preference_pending_calibration), false);

        editor.apply();
    }
}
