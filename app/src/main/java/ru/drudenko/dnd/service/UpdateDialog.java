package ru.drudenko.dnd.service;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ru.drudenko.dnd.R;

public class UpdateDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        var builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.newUpdateAvailable);
        builder.setMessage(getArguments().getString(Constants.APK_UPDATE_CONTENT))
                .setPositiveButton(R.string.dialogPositiveButton, (dialog, id) -> {
                    // FIRE ZE MISSILES!
                    goToDownload();
                    dismiss();
                })
                .setNegativeButton(R.string.dialogNegativeButton, (dialog, id) -> {
                    // User cancelled the dialog
                    dismiss();
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }


    private void goToDownload() {
        var intent = new Intent(getActivity().getApplicationContext(), DownloadService.class);
        intent.putExtra(Constants.APK_DOWNLOAD_URL, getArguments().getString(Constants.APK_DOWNLOAD_URL));
        intent.putExtra(Constants.APK_IS_AUTO_INSTALL, getArguments().getBoolean(Constants.APK_IS_AUTO_INSTALL));
        intent.putExtra(Constants.APK_CHECK_EXTERNAL, getArguments().getBoolean(Constants.APK_CHECK_EXTERNAL));
        getActivity().startService(intent);
    }
}
