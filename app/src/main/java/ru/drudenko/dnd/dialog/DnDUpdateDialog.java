package ru.drudenko.dnd.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.artwl.update.Constants;
import com.artwl.update.DownloadService;
import com.artwl.update.UpdateDialog;

import java.util.concurrent.atomic.AtomicBoolean;

import ru.drudenko.dnd.R;

public class DnDUpdateDialog extends UpdateDialog {
    public static AtomicBoolean isVisible = new AtomicBoolean(false);

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.newUpdateAvailable);
        builder.setMessage(getArguments().getString(Constants.APK_UPDATE_CONTENT))
                .setPositiveButton(R.string.dialogPositiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        DnDUpdateDialog.this.goToDownload();
                        DnDUpdateDialog.this.dismiss();
                    }
                })
                .setNegativeButton(R.string.dialogNegativeButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        isVisible.set(false);
                        DnDUpdateDialog.this.dismiss();
                    }
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
