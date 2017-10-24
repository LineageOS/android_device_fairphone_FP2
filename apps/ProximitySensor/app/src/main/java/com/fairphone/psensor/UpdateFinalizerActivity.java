/*
 * Copyright (C) 2016 Fairphone B.V.
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

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.fairphone.psensor.helpers.CalibrationStatusHelper;


public class UpdateFinalizerActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private TextView mTextViewMain;
    private Button mButtonNext;
    private boolean mShouldNotBeShownAgain;


    static final String ACTION_SHOW_ON_FIRST_BOOT_AFTER_UPDATE = "com.fairphone.updatefinalizer.firstbootafterupdate";
    private CheckBox mCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_finalizer);

        mButtonNext = (Button) findViewById(R.id.button_next);
        mButtonNext.setOnClickListener(this);
        mCheckBox = (CheckBox) findViewById(R.id.checkBoxSuppress);
        mCheckBox.setOnCheckedChangeListener(this);
        mShouldNotBeShownAgain = false;

        if(!wasAlreadyShown()) {
            mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            mCheckBox.setVisibility(View.VISIBLE);

        }

        mTextViewMain = (TextView) findViewById(R.id.instructions);
        mTextViewMain.setText(Html.fromHtml(getString(R.string.Text)));

        setAlreadyShown();

        UpdateFinalizerService.startActionClearNotification(this);

        if (isWizard() && !CalibrationStatusHelper.hasToBeCalibrated(this)) {
            disable(this);
            finish();
        }
    }

    protected boolean isWizard() {
        return true;
    }

    private void setAlreadyShown() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.preference_already_shown),true);
        editor.apply();
    }

    private boolean wasAlreadyShown() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), MODE_PRIVATE);
        return sharedPref.getBoolean(getString(R.string.preference_already_shown),false);
    }

    @Override
    protected void onDestroy() {
        disable(this);
        checkCalibratePending();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(getString(R.string.logtag), "returned");
    }

    @Override
    protected void onPause() {
        checkCalibratePending();
        super.onPause();
    }

    private void checkCalibratePending() {
            UpdateFinalizerService.startActionCheckCalibrationPending(this);
    }

    public static void disable(Context ctx) {
        try {
            PackageManager localPackageManager = ctx.getPackageManager();
            localPackageManager.setComponentEnabledSetting(new ComponentName("com.fairphone.psensor", "com.fairphone.psensor.UpdateFinalizerActivity"),PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 1);
        } catch (Exception e) {
            // TODO do something clever
        }
    }

    public static void enable(Context ctx) {
        try {
            PackageManager localPackageManager = ctx.getPackageManager();
            localPackageManager.setComponentEnabledSetting(new ComponentName("com.fairphone.psensor", "com.fairphone.psensor.UpdateFinalizerActivity"),PackageManager.COMPONENT_ENABLED_STATE_ENABLED,0);
        } catch (Exception e) {
            // TODO do something clever
        }
    }

    @Override
    public void onClick(View v) {
        if (mButtonNext == v) {
            if(mShouldNotBeShownAgain) {
                disable(this);
                finish();
            }else {
                startCalibrationIntent();
                finish();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_HOME)) {
            disable(this);
            finish();
        }
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            disable(this);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void startCalibrationIntent() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.fairphone.psensor", "com.fairphone.psensor.CalibrationActivity"));
        intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivityForResult(intent,0);
    }

    private void askReallyDoNotShowAgain() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        final Context ctx = this;
        alertDialogBuilder.setTitle(R.string.ask_really_dont_show_title);
        alertDialogBuilder
                .setMessage(Html.fromHtml(getString(R.string.ask_really_dont_show_text)))
                .setCancelable(false)
                .setPositiveButton(R.string.OK,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this fairphone_button is clicked, close
                        // current activity
                        mCheckBox.setChecked(true);
                        mButtonNext.setText(R.string.Exit);
                        mShouldNotBeShownAgain = true;
                        setNotShowAnymore(ctx, true);
                    }
                })
                .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this fairphone_button is clicked, just close
                        // the dialog box and do nothing
                        mCheckBox.setChecked(false);
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView == mCheckBox)
                if (isChecked) {
                    askReallyDoNotShowAgain();
                } else {
                    mButtonNext.setText(R.string.next);
                }
    }

    static protected void setNotShowAnymore(Context ctx, boolean doNotShowAnymore) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(
                ctx.getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(ctx.getString(R.string.preference_do_not_show_again), doNotShowAnymore);
        editor.apply();
    }

    static protected boolean isNotShowAnymore(Context ctx) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(
                ctx.getString(R.string.preference_file_key), MODE_PRIVATE);
        return sharedPref.getBoolean(ctx.getString(R.string.preference_do_not_show_again), false);
    }

}
