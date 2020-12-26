package ru.drudenko.dnd.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.concurrent.atomic.AtomicBoolean;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.service.Constants;
import ru.drudenko.dnd.service.DownloadService;
import ru.drudenko.dnd.service.UpdateDialog;

public class DnDUpdateDialog extends UpdateDialog {
    public static final AtomicBoolean isVisible = new AtomicBoolean(false);

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.newUpdateAvailable);
        builder.setMessage(getArguments().getString(Constants.APK_UPDATE_CONTENT))
                .setPositiveButton(R.string.dialogPositiveButton, (dialog, id) -> {
                    // FIRE ZE MISSILES!
                    DnDUpdateDialog.this.goToDownload();
                    DnDUpdateDialog.this.dismiss();
                })
                .setNegativeButton(R.string.dialogNegativeButton, (dialog, id) -> {
                    // User cancelled the dialog
                    isVisible.set(false);
                    DnDUpdateDialog.this.dismiss();
                });
        // Create the AlertDialog object and return it
        isVisible.set(true);
        return builder.create();
    }


    private void goToDownload() {
        Intent intent = new Intent(getActivity().getApplicationContext(), DownloadService.class);
        intent.putExtra(Constants.APK_DOWNLOAD_URL, getArguments().getString(Constants.APK_DOWNLOAD_URL));
        intent.putExtra(Constants.APK_IS_AUTO_INSTALL, getArguments().getBoolean(Constants.APK_IS_AUTO_INSTALL));
        intent.putExtra(Constants.APK_CHECK_EXTERNAL, getArguments().getBoolean(Constants.APK_CHECK_EXTERNAL));
        getActivity().startService(intent);
    }
}
