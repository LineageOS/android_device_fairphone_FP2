package com.fairphone.psensor.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.fairphone.psensor.R;

public class IncompatibleDeviceDialog extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface IncompatibleDeviceDialogListener {
        void onIncompatibleDeviceDialogPositiveAction(DialogFragment dialog);

        void onIncompatibleDeviceDialogNegativeAction(DialogFragment dialog);

        void onDismissIncompatibleDeviceDialog(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    IncompatibleDeviceDialogListener mListener;

    public IncompatibleDeviceDialog() {
        // default constructor for easy instantiation
    }

    // Override the Fragment.onAttach() method to instantiate the EnterTheBetaDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (IncompatibleDeviceDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString() + " must implement IncompatibleDeviceDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.incompatible_device);
        builder.setMessage(R.string.device_cannot_run_calibration_tool);

        builder.setPositiveButton(R.string.go_to_updater, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mListener.onIncompatibleDeviceDialogPositiveAction(IncompatibleDeviceDialog.this);
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mListener.onIncompatibleDeviceDialogNegativeAction(IncompatibleDeviceDialog.this);
            }
        });
        builder.setCancelable(false);

        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mListener.onDismissIncompatibleDeviceDialog(this);
    }
}
