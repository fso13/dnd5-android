package ru.drudenko.dnd.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.io.File;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import ru.drudenko.dnd.R;

public class DownloadService extends IntentService {
    private static final String DOWNLOAD_NOTIFY_CHANNEL = "DOWNLOAD_NOTIFY_CHANNEL";
    private static final int notificationId = 1;
    private static final int BUFFER_SIZE = 10 * 1024; // 8k ~ 32K
    private static final String TAG = "AutoUpdate";
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String description = "Download Update Notification";

            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(DOWNLOAD_NOTIFY_CHANNEL, "UpdateCheckerNotification", importance);
            channel.enableLights(false);
            channel.enableVibration(false);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this, DOWNLOAD_NOTIFY_CHANNEL);

        String appName = getString(getApplicationInfo().labelRes);
        int icon = getApplicationInfo().icon;

        mBuilder.setContentTitle(appName).setSmallIcon(icon);
        String urlStr = intent.getStringExtra(Constants.APK_DOWNLOAD_URL);
        boolean isAutoInstall = intent.getBooleanExtra(Constants.APK_IS_AUTO_INSTALL, false);
        boolean checkExternal = intent.getBooleanExtra(Constants.APK_CHECK_EXTERNAL, true);

        BufferedSink sink = null;
        BufferedSource source = null;
        try {
            // apk local file paths.
            File dir = StorageUtils.getCacheDirectory(this, checkExternal);
            String apkName = urlStr.substring(urlStr.lastIndexOf("/") + 1);
            File apkFile = new File(dir, apkName);


            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(urlStr)
                    .addHeader("Charset", "UTF-8")
                    .addHeader("Connection", "Keep-Alive")
                    .addHeader("Charset", "UTF-8")
                    .addHeader("Accept-Encoding", "gzip, deflate")
                    .build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            long contentLength = body.contentLength();
            source = body.source();
            sink = Okio.buffer(Okio.sink(apkFile));

            Buffer sinkBuffer = sink.buffer();
            long totalBytesRead = 0;
            int bufferSize = 8 * 1024;
            long bytesRead;
            int lastProgress = -1;
            while ((bytesRead = source.read(sinkBuffer, bufferSize)) != -1) {
                sink.emit();
                totalBytesRead += bytesRead;
                int progress = (int) ((totalBytesRead * 100) / contentLength);

                if (progress != lastProgress) {
                    lastProgress = progress;
                    updateProgress(lastProgress);
                }
            }
            sink.flush();

            boolean readable = apkFile.setReadable(true, false);

            Log.d(TAG, String.format(Locale.forLanguageTag("ru"), "Download Apk to %s, readable: %s", apkFile, readable));

            mBuilder.setContentText(getString(R.string.download_success)).setProgress(0, 0, false);

            Uri fileUri = StorageUtils.getFileUri(this, apkFile);

            Intent installAPKIntent = new Intent(Intent.ACTION_VIEW);
            installAPKIntent.setDataAndType(fileUri, "application/vnd.android.package-archive");

            if (isAutoInstall) {
                installAPKIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 24) {
                    intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                    installAPKIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                startActivity(installAPKIntent);
                mNotifyManager.cancel(notificationId);
                return;
            }

            String[] command = {"chmod", "777", apkFile.toString()};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, installAPKIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder.setContentIntent(pendingIntent);
            Notification noti = mBuilder.build();
            noti.flags = Notification.FLAG_AUTO_CANCEL;
            mNotifyManager.notify(notificationId, noti);

        } catch (Exception e) {
            Log.e(TAG, "download apk file error", e);
        } finally {
            Util.closeQuietly(sink);
            Util.closeQuietly(source);
        }
    }

    private void updateProgress(int progress) {
        mBuilder.setContentText(this.getString(R.string.download_progress, progress)).setProgress(100, progress, false);
        PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingintent);
        mNotifyManager.notify(notificationId, mBuilder.build());
    }

}
