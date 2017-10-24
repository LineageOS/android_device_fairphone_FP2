package com.fairphone.psensor.helpers;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Helper methods to access the proximity sensor.
 */
public class ProximitySensorHelper {
    private static final String TAG = ProximitySensorHelper.class.getSimpleName();
    
    /**
     * Minimal value to accept as valid from the sensor reading (in sensor units).
     */
    public static final int READ_MIN_LIMIT = 0;
    /**
     * Maximal value to accept as valid from the sensor reading (in sensor units).
     */
    public static final int READ_MAX_LIMIT = 255;

    /**
     * Command to read the sensor value.
     */
    private static final String READ_COMMAND = "/system/bin/senread";
    /**
     * Result prefix returned by the reading command.
     */
    private static final String READ_COMMAND_RESULT_PREFIX = "[RESULT]";

    /**
     * Amount of times to perform a sensor reading.
     */
    private static final int READ_N_TIMES = 3;
    /**
     * Time to wait between two sensor readings (in milliseconds).
     */
    private static final int READ_DELAY_MS = 500;

    /**
     * Empty constructor to avoid instantiation.
     */
    private ProximitySensorHelper() {
    }

    /**
     * Determine whether the proximity sensor value is readable or not based on the reading tool availability. <br>
     * <br>
     * The reading tool is tested to exist and be executable.
     *
     * @return <code>true</code> if the proximity sensor value is readable, <code>false</code> if not.
     */
    public static boolean canReadProximitySensorValue() {
        final File tool = new File(READ_COMMAND);

        return tool.exists() && tool.canExecute();
    }

    /**
     * Read the proximity sensor value read_times times and return the mean value. <br>
     * <br>
     * Wait {@link #READ_DELAY_MS} between each read, even if there is only one read planned.
     *
     * @param min_value The lower threshold (inclusive) of accepted range.
     * @param max_value The upper threshold (inclusive) of accepted range.
     * @return The mean of all the value read (up to {@link #READ_N_TIMES}) or -1 if no read succeeded.
     */
    public static int read(int read_times, int min_value, int max_value) {
        int result;
        int summed_result = 0;
        int nb_result_read = 0;
        int final_result = -1;

        for (int i = 0; i < read_times; i++) {
            result = readProximitySensorValue();

            if (min_value <= result && result <= max_value) {
                summed_result += result;
                nb_result_read++;
            } else {
                Log.d(TAG, "Ignored value out of accepted range (" + result + " not in [" + min_value + "," + max_value + "])");
            }

            // wait a bit between two sensor reading
            try {
                Thread.sleep(READ_DELAY_MS);
            } catch (Exception e) {
                Log.wtf(TAG, e);
            }
        }

        if (nb_result_read == 0) {
            // something went wrong with READ_COMMAND, are we allowed to execute it?
            Log.e(TAG, "Could not read sensor value " + read_times + " " + ((read_times == 1) ? "time" : "times"));
        } else {
            if (nb_result_read < read_times) {
                Log.w(TAG, "Read " + nb_result_read + "/" + read_times + " values");
            }

            final_result = Math.round(summed_result / nb_result_read);
        }

        return final_result;
    }

    /**
     * Call to read(1, {@link #READ_MIN_LIMIT}, {@link #READ_MAX_LIMIT})
     *
     * @return The value read or -1 if read failed.
     * @see ProximitySensorHelper#read(int, int, int)
     */
    public static int read() {
        return read(1, READ_MIN_LIMIT, READ_MAX_LIMIT);
    }

    /**
     * Call to read({@link #READ_N_TIMES}, min_value, max_value)
     *
     * @param min_value The lower threshold (inclusive) of accepted range.
     * @param max_value The upper threshold (inclusive) of accepted range.
     * @return The mean of all the value read (up to {@link #READ_N_TIMES}) or -1 if no read succeeded.
     * @see ProximitySensorHelper#read(int, int, int)
     */
    public static int read(int min_value, int max_value) {
        return read(READ_N_TIMES, min_value, max_value);
    }

    /**
     * Read the proximity sensor value using an external command ({@link #READ_COMMAND}).
     *
     * @return the proximity sensor value (>= {@link #READ_MIN_LIMIT and <= {@link #READ_MAX_LIMIT}}) or
     * <code>-1</code> if there was an error (parsing the value or using the external command).
     */
    private static int readProximitySensorValue() {
        int value = -1;
        Process process = null;

        try {
            process = Runtime.getRuntime().exec(new String[]{READ_COMMAND});
            final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            final String line = reader.readLine();

            if (line != null && line.startsWith(READ_COMMAND_RESULT_PREFIX)) {
                value = Integer.parseInt(line.replace(READ_COMMAND_RESULT_PREFIX, "").trim());
            }
        } catch (IOException e) {
            Log.wtf(TAG, "Could not execute command `" + READ_COMMAND + "`", e);
        } catch (NumberFormatException e) {
            Log.wtf(TAG, e);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }

        return value;
    }
}
