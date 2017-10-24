package com.fairphone.psensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.fairphone.psensor.helpers.ProximitySensorHelper;

public class DiagnosticsActivity extends Activity {

    private int mSensorChangeCount = 0;
    private SensorEventListener mSensorEventListener;
    private SensorManager mSensorManager;
    private Sensor mProximitySensor;

    private TextView mSensorValueTextView;
    private TextView mBlockValueTextView;
    private TextView mUnblockValueTextView;

    private int sensorValue;

    private Handler mHandler;

    private ProximitySensorConfiguration mPersistedConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPersistedConfiguration = ProximitySensorConfiguration.readFromMemory();
        mHandler = new Handler();


        setContentView(R.layout.activity_diagnostics);

//        mActionBar = getActionBar();
//        if (mActionBar != null) {
//            mActionBar.setDisplayHomeAsUpEnabled(true);
//            mActionBar.setHomeButtonEnabled(true);
//        }

        mSensorValueTextView = (TextView)findViewById(R.id.sensorValue);
        mBlockValueTextView = (TextView)findViewById(R.id.blockValue);
        mUnblockValueTextView = (TextView)findViewById(R.id.unblockValue);

        mBlockValueTextView.setText(mPersistedConfiguration.nearThreshold);
        mUnblockValueTextView.setText(mPersistedConfiguration.farThreshold);

        getProximitySensor();
        setupSensorStateListener();
        startSensorMonitor();
    }

    private final Runnable mSensorMonitor = new Runnable() {
        @Override
        public void run() {
            try {
                sensorValue = ProximitySensorHelper.read();
                Log.i(DiagnosticsActivity.class.getName(), String.valueOf(sensorValue));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSensorValueTextView.setText(String.valueOf(sensorValue));
                    }
                });
            } finally {
                mHandler.post(mSensorMonitor);
            }

        }
    };

    void startSensorMonitor() {
        mSensorMonitor.run();
    }

    private void stopSensorMonitor() {
        mHandler.removeCallbacks(mSensorMonitor);
    }


    @Override
    protected void onDestroy() {
        mSensorChangeCount = 0;
        mSensorManager.unregisterListener(mSensorEventListener);
        mSensorEventListener = null;
        stopSensorMonitor();
        super.onDestroy();
    }

    private void onSensorChange(SensorEvent event) {
        mSensorChangeCount++;
        ((TextView) findViewById(R.id.proximity_sensor_state_text)).setText(event.values[0] < 0.1 ? "Triggered" : "Not triggered");
        Log.i("SENSOR", "Callback called");
        if (mSensorChangeCount > 5) {
            Log.i("SENSOR", "Proximity changed");
        }
    }

    private void getProximitySensor() {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (mProximitySensor == null) {
            // TODO do something clever
        }
    }

    private void setupSensorStateListener() {
        mSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    onSensorChange(event);

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        mSensorManager.registerListener(mSensorEventListener, mProximitySensor, 100);
    }

}
