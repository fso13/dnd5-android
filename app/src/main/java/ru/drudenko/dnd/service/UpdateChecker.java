package ru.drudenko.dnd.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.common.base.Strings;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.drudenko.dnd.R;
import ru.drudenko.dnd.service.entity.UpdateDescription;

import static ru.drudenko.dnd.service.Constants.APK_CHECK_EXTERNAL;


public class UpdateChecker extends Fragment {

    private static final String UPDATE_CHANNEL_ID = "AUTO_UPDATE_NOTIFY_CHANNEL";
    private static final String NOTICE_TYPE_KEY = "type";
    private static final String APP_UPDATE_SERVER_URL = "app_update_server_url";
    private static final String APK_IS_AUTO_INSTALL = "apk_is_auto_install";

    private static final int NOTICE_DIALOG = 1;
    private static final int NOTICE_NOTIFICATION = 2;
    private static final int NOTICE_CUSTOM = 3;

    private static final String TAG = "UpdateChecker";
    private static final String HTTP_VERB = "httpverb";
    private static final String CUSTOM_NOTICE = "custom_notice";

    private FragmentActivity mContext;
    private int mTypeOfNotice;
    private boolean mIsAutoInstall;
    private boolean mCheckExternal;
    private UpdateNotice mNotice;

    /**
     * Delegate update handling to custom notice.
     *
     * @param fragmentActivity
     * @param checkUpdateServerUrl
     */
    public static void checkForCustomNotice(FragmentActivity fragmentActivity,
                                            String checkUpdateServerUrl,
                                            UpdateNotice notice) {
        checkForCustomNotice(fragmentActivity, checkUpdateServerUrl, "GET", notice);
    }

    /**
     * Delegate udpate handling to custom notice
     *
     * @param fragmentActivity     Required.
     * @param fragmentActivity
     * @param checkUpdateServerUrl
     * @param httpVerb
     * @param notice
     */
    public static void checkForCustomNotice(FragmentActivity fragmentActivity,
                                            String checkUpdateServerUrl,
                                            String httpVerb,
                                            UpdateNotice notice) {
        checkForAutoUpdate(fragmentActivity, checkUpdateServerUrl, true, true, httpVerb, NOTICE_CUSTOM, notice);
    }

    /**
     * Show a Dialog if an update is available for download. Callable in a
     * FragmentActivity. Number of checks after the dialog will be shown:
     * default, 5
     *
     * @param fragmentActivity     Required.
     * @param fragmentActivity
     * @param checkUpdateServerUrl
     * @param isAutoInstall
     * @param checkExternal
     */
    public static void checkForDialog(FragmentActivity fragmentActivity,
                                      String checkUpdateServerUrl,
                                      boolean isAutoInstall, boolean checkExternal) {
        checkForDialog(fragmentActivity, checkUpdateServerUrl, isAutoInstall, checkExternal, "GET");
    }

    /**
     * Show a Dialog if an update is available for download. Callable in a
     * FragmentActivity. Number of checks after the dialog will be shown:
     * default, 5
     *
     * @param fragmentActivity     Required.
     * @param fragmentActivity
     * @param checkUpdateServerUrl
     * @param isAutoInstall
     * @param checkExternal
     * @param httpVerb
     */
    public static void checkForDialog(FragmentActivity fragmentActivity,
                                      String checkUpdateServerUrl,
                                      boolean isAutoInstall, boolean checkExternal, String httpVerb) {
        checkForAutoUpdate(fragmentActivity, checkUpdateServerUrl, isAutoInstall, checkExternal, httpVerb, NOTICE_DIALOG);
    }

    /**
     * Show a Notification if an update is available for download. Callable in a
     * FragmentActivity Specify the number of checks after the notification will
     * be shown.
     *
     * @param fragmentActivity Required.
     */
    public static void checkForNotification(FragmentActivity fragmentActivity,
                                            String checkUpdateServerUrl,
                                            boolean isAutoInstall,
                                            boolean checkExternal) {
        checkForNotification(fragmentActivity, checkUpdateServerUrl, isAutoInstall, checkExternal, "GET");
    }

    /**
     * Show a Notification if an update is available for download. Callable in a
     * FragmentActivity Specify the number of checks after the notification will
     * be shown.
     *
     * @param fragmentActivity Required.
     */
    public static void checkForNotification(FragmentActivity fragmentActivity,
                                            String checkUpdateServerUrl,
                                            boolean isAutoInstall,
                                            boolean checkExternal,
                                            String httpVerb) {
        checkForAutoUpdate(fragmentActivity, checkUpdateServerUrl, isAutoInstall, checkExternal, httpVerb, NOTICE_NOTIFICATION);
    }

    /**
     * Check whether has update available, notice with dialog, notification or custom notice.
     *
     * @param fragmentActivity
     * @param checkUpdateServerUrl
     * @param isAutoInstall
     * @param checkExternal
     * @param httpVerb
     * @param typeOfNotice
     */
    public static void checkForAutoUpdate(FragmentActivity fragmentActivity,
                                          String checkUpdateServerUrl,
                                          boolean isAutoInstall,
                                          boolean checkExternal,
                                          String httpVerb,
                                          int typeOfNotice) {
        checkForAutoUpdate(fragmentActivity, checkUpdateServerUrl, isAutoInstall, checkExternal, httpVerb, typeOfNotice, null);
    }

    public static void checkForAutoUpdate(FragmentActivity fragmentActivity,
                                          String checkUpdateServerUrl,
                                          boolean isAutoInstall,
                                          boolean checkExternal,
                                          String httpVerb,
                                          int typeOfNotice, UpdateNotice notice) {
        FragmentTransaction content = fragmentActivity.getSupportFragmentManager().beginTransaction();
        UpdateChecker updateChecker = new UpdateChecker();
        Bundle args = new Bundle();
        args.putInt(NOTICE_TYPE_KEY, typeOfNotice);
        args.putString(APP_UPDATE_SERVER_URL, checkUpdateServerUrl);
        args.putBoolean(APK_IS_AUTO_INSTALL, isAutoInstall);
        args.putBoolean(APK_CHECK_EXTERNAL, checkExternal);
        args.putString(HTTP_VERB, httpVerb);

        updateChecker.setNotice(notice);
        updateChecker.setArguments(args);
        content.add(updateChecker, null).commit();
    }

    /**
     * Check if a network available
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                connected = ni.isConnected();
            }
        }
        return connected;
    }

    public void setNotice(UpdateNotice notice) {
        mNotice = notice;
    }

    /**
     * This class is a Fragment. Check for the method you have chosen.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = this.getActivity();
        Bundle args = getArguments();
        mTypeOfNotice = args.getInt(NOTICE_TYPE_KEY);
        mIsAutoInstall = args.getBoolean(APK_IS_AUTO_INSTALL);
        mCheckExternal = args.getBoolean(APK_CHECK_EXTERNAL);
        String mHttpVerb = args.getString(HTTP_VERB);
        String url = args.getString(APP_UPDATE_SERVER_URL);

        if (Strings.isNullOrEmpty(mHttpVerb)) {
            mHttpVerb = "POST";
        }

        createNotificationChannel();

        checkForUpdates(url);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String description = "Update Checker Notification";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(UPDATE_CHANNEL_ID, "UpdateCheckerNotification", importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Heart of the library. Check if an update is available for download
     * parsing the desktop Play Store page of the app
     */
    private void checkForUpdates(final String url) {

        sendPost(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::parseJson);
    }

    private Observable<String> sendPost(String url) {

        return Observable.fromCallable(() -> {
            try {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException ex) {
                Log.d(TAG, "Failed to get update json", ex);
                return "";
            }
        });
    }

    private void parseJson(String json) {
        UpdateDescription description = new Gson().fromJson(json, UpdateDescription.class);

        if (description == null) {
            description = new UpdateDescription();
        }
        try {
            int versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;

            if (mTypeOfNotice == NOTICE_CUSTOM && mNotice != null) {
                mNotice.showCustomNotice(description);
            } else if (description.versionCode > versionCode) {
                description.updateMessage += String.format(Locale.forLanguageTag("ru"), " [%d --> %d]", versionCode, description.versionCode);

                if (mTypeOfNotice == NOTICE_NOTIFICATION ||
                        (mTypeOfNotice == NOTICE_CUSTOM && mNotice == null)) {
                    showNotification(description.updateMessage, description.url, mIsAutoInstall, mCheckExternal);
                } else if (mTypeOfNotice == NOTICE_DIALOG) {
                    showDialog(description.updateMessage, description.url, mIsAutoInstall, mCheckExternal);
                }
            } else {
                Log.i(TAG, mContext.getString(R.string.app_no_new_update) + "Remote: " + description.versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "parse json error", e);
        }
    }

    /**
     * Show dialog
     */
    private void showDialog(String content, String apkUrl, boolean isAutoInstall, boolean checkExternal) {
        UpdateDialog d = new UpdateDialog();
        Bundle args = new Bundle();
        args.putString(Constants.APK_UPDATE_CONTENT, content);
        args.putString(Constants.APK_DOWNLOAD_URL, apkUrl);
        args.putBoolean(Constants.APK_IS_AUTO_INSTALL, isAutoInstall);
        args.putBoolean(APK_CHECK_EXTERNAL, checkExternal);
        d.setArguments(args);

        // http://blog.csdn.net/chenshufei2/article/details/48747149
        // Don't use default d.show(mContext.getSupportFragmentManager(), null);
        FragmentTransaction ft = mContext.getSupportFragmentManager().beginTransaction();
        ft.add(d, this.getClass().getSimpleName());
        ft.commitAllowingStateLoss();//注意这里使用commitAllowingStateLoss()
    }

    /**
     * Show Notification
     */
    private void showNotification(String content, String apkUrl, boolean isAutoInstall, boolean checkExternal) {
        android.app.Notification noti;
        Intent myIntent = new Intent(mContext, DownloadService.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra(Constants.APK_DOWNLOAD_URL, apkUrl);
        myIntent.putExtra(Constants.APK_IS_AUTO_INSTALL, isAutoInstall);
        myIntent.putExtra(APK_CHECK_EXTERNAL, checkExternal);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int smallIcon = mContext.getApplicationInfo().icon;
        noti = new NotificationCompat.Builder(mContext, UPDATE_CHANNEL_ID).setTicker(getString(R.string.newUpdateAvailable))
                .setContentTitle(getString(R.string.newUpdateAvailable)).setContentText(content).setSmallIcon(smallIcon)
                .setContentIntent(pendingIntent).build();

        noti.flags = android.app.Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);
    }


}
