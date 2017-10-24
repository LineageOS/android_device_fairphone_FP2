package com.fairphone.psensor;

import android.provider.BaseColumns;

public final class CalibrationContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private CalibrationContract() {}

    /* Inner class that defines the table contents */
    public static abstract class CalibrationData implements BaseColumns {
        public static final String TABLE_NAME = "calibration";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_PREVIOUS_NEAR = "previous_near";
        public static final String COLUMN_NAME_PREVIOUS_FAR = "previous_far";
        public static final String COLUMN_NAME_PREVIOUS_OFFSET = "previous_offset";
        public static final String COLUMN_NAME_NEAR = "near";
        public static final String COLUMN_NAME_FAR = "far";
        public static final String COLUMN_NAME_OFFSET = "offset";
        public static final String COLUMN_NAME_APP_VERSION = "app_version";
    }
}

