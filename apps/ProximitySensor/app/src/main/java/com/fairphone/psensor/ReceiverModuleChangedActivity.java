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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReceiverModuleChangedActivity extends Activity implements View.OnClickListener {

    private Button mButtonLater;
    private Button mButtonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_module_changed);

        mButtonLater = (Button) findViewById(R.id.button_later);
        mButtonLater.setOnClickListener(this);
        mButtonNext = (Button) findViewById(R.id.button_next);
        mButtonNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mButtonNext == v) {
            startCalibrationIntent();
            // Do not dismiss the notification; using it again will resume the calibration activity
            // until it is complete.
        } else if (mButtonLater == v) {
            CalibrationService.startActionRemindReceiverModuleChangedLater(this);
            finish();
        }
    }

    private void startCalibrationIntent() {
        startActivity(new Intent()
                .setClass(this, CalibrationActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME | Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

}
