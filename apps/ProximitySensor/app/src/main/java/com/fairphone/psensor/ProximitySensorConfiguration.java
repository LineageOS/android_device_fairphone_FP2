package com.fairphone.psensor;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Locale;

/**
 * Configuration fields for the proximity sensor. <br>
 * <br>
 * Using the vendor wording, the "near threshold" is the "Proximity Interrupt High threshold", the "far threshold"
 * is the "Proximity Interrupt Low threshold", and the "offset compensation" is the "Proximity Offset Compensation".
 */
public class ProximitySensorConfiguration {
    private static final String TAG = ProximitySensorConfiguration.class.getSimpleName();

    /**
     * Minimal offset compensation value allowed to be persisted. <br>
     * The minimal value accepted in the registry is <code>0x0000</code>, whereas this is the minimal value allowed by
     * the device.
     */
    public static final int MIN_OFFSET_COMPENSATION = 0x0000;
    /**
     * Maximal offset compensation value allowed to be persisted. <br>
     * The maximal value accepted in the registry is <code>0xFFFF</code>, whereas this is the maximal value allowed by
     * the device.
     */
    public static final int MAX_OFFSET_COMPENSATION = 0x000F;
    /**
     * Minimal near threshold value allowed to be persisted. <br>
     * The minimal value accepted in the registry is <code>0x0000</code>, whereas this is the minimal value allowed by
     * the device.
     */
    public static final int MIN_NEAR_THRESHOLD = 0x0000;
    /**
     * Maximal near threshold value allowed to be persisted. <br>
     * The maximal value accepted in the registry is <code>0xFFFF</code>, whereas this is the maximal value allowed by
     * the device.
     */
    public static final int MAX_NEAR_THRESHOLD = 0x00FF;
    /**
     * Minimal far threshold value allowed to be persisted. <br>
     * The minimal value accepted in the registry is <code>0x0000</code>, whereas this is the minimal value allowed by
     * the device.
     */
    public static final int MIN_FAR_THRESHOLD = 0x0000;
    /**
     * Maximal far threshold value allowed to be persisted. <br>
     * The maximal value accepted in the registry is <code>0xFFFF</code>, whereas this is the maximal value allowed by
     * the device.
     */
    public static final int MAX_FAR_THRESHOLD = 0x00FF;

    /**
     * Default value for the offset compensation.
     */
    private static final int DEFAULT_OFFSET_COMPENSATION = 0x0001;
    /**
     * Default value for the near threshold.
     */
    private static final int DEFAULT_NEAR_THRESHOLD = 0x00FF;
    /**
     * Default value for the far threshold.
     */
    private static final int DEFAULT_FAR_THRESHOLD = 0x0000;

    /**
     * Path to the persisted calibration file.
     */
    private static final String CALIBRATION_FILE = "/persist/sns.reg";
    /**
     * Offset in the calibration file to reach the offset compensation value.
     */
    private static final int OFFSET_COMPENSATION_OFFSET = 0x00000120 + 8;
    /**
     * Offset in the calibration file to reach the near threshold value.
     */
    private static final int NEAR_THRESHOLD_OFFSET = 0x00000100 + 4;
    /**
     * Offset in the calibration file to reach the far threshold value.
     */
    private static final int FAR_THRESHOLD_OFFSET = 0x00000100 + 6;

    /**
     * The proximity sensor offset compensation.
     */
    public int offsetCompensation;
    /**
     * The proximity sensor interrupt high threshold, to change state from free to blocked.
     */
    public int nearThreshold;
    /**
     * The proximity sensor interrupt low threshold, to change state from blocked to free.
     */
    public int farThreshold;

    /**
     * Default constructor based on the default values.
     *
     * @see #DEFAULT_OFFSET_COMPENSATION
     * @see #DEFAULT_NEAR_THRESHOLD
     * @see #DEFAULT_FAR_THRESHOLD
     */
    public ProximitySensorConfiguration() {
        offsetCompensation = DEFAULT_OFFSET_COMPENSATION;
        nearThreshold = DEFAULT_NEAR_THRESHOLD;
        farThreshold = DEFAULT_FAR_THRESHOLD;
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "{offset compensation=%d, near threshold=%d, far threshold=%d}", offsetCompensation, nearThreshold, farThreshold);
    }

    /**
     * Determine whether the memory can be read from and persisted by trying to open a handle to it.
     *
     * @return <code>true</code> if the memory is both readable and writable, <code>false</code> if not.
     */
    public static boolean canReadFromAndPersistToMemory() {
        final File calibrationFile = new File(CALIBRATION_FILE);

        return calibrationFile.canRead() && calibrationFile.canWrite();
    }

    /**
     * Read the configuration persisted into memory.
     *
     * @return The persisted configuration or <code>null</code> if not accessible.
     */
    public static ProximitySensorConfiguration readFromMemory() {
        ProximitySensorConfiguration configuration = new ProximitySensorConfiguration();

        try {
            byte[] buffer = new byte[4];
            RandomAccessFile file = new RandomAccessFile(CALIBRATION_FILE, "r");

            file.seek(OFFSET_COMPENSATION_OFFSET);
            file.read(buffer, 0, 2);
            configuration.offsetCompensation = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();

            file.seek(NEAR_THRESHOLD_OFFSET);
            file.read(buffer, 0, 2);
            configuration.nearThreshold = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();

            file.seek(FAR_THRESHOLD_OFFSET);
            file.read(buffer, 0, 2);
            configuration.farThreshold = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();

            file.close();

            Log.d(TAG, "Configuration " + configuration.toString() + " read from `" + CALIBRATION_FILE + "`");
        } catch (Exception e) {
            Log.wtf(TAG, e);

            configuration = null;
        }

        return configuration;
    }

    /**
     * Persist the configuration into memory. <br>
     * <br>
     * This method <strong>does not</strong> update the current configuration of the proximity sensor. It does set the
     * values that live in the <code>/persist/</code> directory only.
     *
     * @throws IllegalArgumentException if one of the configuration element does not respect the acceptable range
     * (see {@link #MIN_OFFSET_COMPENSATION}, {@link #MAX_OFFSET_COMPENSATION}, {@link #MIN_NEAR_THRESHOLD},
     * {@link #MAX_NEAR_THRESHOLD}, {@link #MIN_FAR_THRESHOLD}, and {@link #MAX_FAR_THRESHOLD}).
     * @return <code>true</code> if the configuration could be persisted, <code>false</code> if it failed.
     */
    public boolean persistToMemory() throws IllegalArgumentException {
        boolean success = false;
        byte[] buffer;
        RandomAccessFile file = null;

        if (offsetCompensation < MIN_OFFSET_COMPENSATION || MAX_OFFSET_COMPENSATION < offsetCompensation) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Offset compensation (%d) not in the acceptable range [%d;%d]", offsetCompensation, MIN_OFFSET_COMPENSATION, MAX_OFFSET_COMPENSATION));
        } else if (nearThreshold < MIN_NEAR_THRESHOLD || MAX_NEAR_THRESHOLD < nearThreshold) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Near threshold (%d) not in the acceptable range [%d;%d]", nearThreshold, MIN_NEAR_THRESHOLD, MAX_NEAR_THRESHOLD));
        }if (farThreshold < MIN_FAR_THRESHOLD || MAX_FAR_THRESHOLD < farThreshold) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Far threshold (%d) not in the acceptable range [%d;%d]", farThreshold, MIN_FAR_THRESHOLD, MAX_FAR_THRESHOLD));
        }

        try {
            file = new RandomAccessFile(CALIBRATION_FILE, "rw");

            file.seek(OFFSET_COMPENSATION_OFFSET);
            buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(offsetCompensation).array();
            file.write(buffer, 0, 2);

            file.seek(NEAR_THRESHOLD_OFFSET);
            buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(nearThreshold).array();
            file.write(buffer, 0, 2);

            file.seek(FAR_THRESHOLD_OFFSET);
            buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(farThreshold).array();
            file.write(buffer, 0, 2);

            file.close();
            success = true;

            Log.d(TAG, "Configuration " + this.toString() + " persisted to `" + CALIBRATION_FILE + "`");
        } catch (Exception e) {
            Log.wtf(TAG, e);
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    // fall-through
                }
            }
        }

        return success;
    }
}
