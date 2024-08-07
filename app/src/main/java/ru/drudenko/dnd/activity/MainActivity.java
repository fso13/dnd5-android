package ru.drudenko.dnd.activity;


import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import ru.drudenko.dnd.BuildConfig;
import ru.drudenko.dnd.R;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.dialog.DnDUpdateDialog;
import ru.drudenko.dnd.service.Constants;
import ru.drudenko.dnd.service.UpdateChecker;
import ru.drudenko.dnd.service.UpdateNotice;
import ru.drudenko.dnd.service.entity.UpdateDescription;


public class MainActivity extends AppCompatActivity implements UpdateNotice {
    private static final String APP_UPDATE_SERVER_URL = "https://raw.githubusercontent.com/fso13/dnd5-android/master/release.json";
    public static final AtomicBoolean isM = new AtomicBoolean(false);
    private AppBarConfiguration mAppBarConfiguration;
    private final AtomicInteger count = new AtomicInteger(0);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_all_spells, R.id.nav_all_monsters, R.id.nav_master_help, R.id.nav_profiles, R.id.nav_randoms, R.id.nav_class_master_help, R.id.nav_race_master_help, R.id.nav_order)
                .setDrawerLayout(drawer)
                .build();


        TextView versionName = navigationView.getHeaderView(0).findViewById(R.id.textViewVersion);
        versionName.setText(String.format("v.%s", BuildConfig.VERSION_NAME));
        TextView donat = navigationView.getHeaderView(0).findViewById(R.id.textView);
        donat.setText(Html.fromHtml("<a href=\"https://yoomoney.ru/to/41001853413229\">Поддержи</a>"));;
        donat.setMovementMethod(LinkMovementMethod.getInstance());
        donat.setClickable(true);


        Button button = navigationView.getHeaderView(0).findViewById(R.id.button);
        button.setOnClickListener(v -> {
            isM.set(true);
            UpdateChecker.checkForCustomNotice(MainActivity.this, APP_UPDATE_SERVER_URL, MainActivity.this);
        });

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        ((App) getApplication()).getComponent().inject(this);

        Runnable runnable = new Runnable() {
            public void run() {

                while (true) {
                    synchronized (this) {
                        try {
                            wait(10_000);
                            UpdateChecker.checkForCustomNotice(MainActivity.this, APP_UPDATE_SERVER_URL, MainActivity.this);
                            wait(5 * 60_000);
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();


        try {
            JSONObject props = new JSONObject();

            props.put("Android Version", String.format(Locale.getDefault(), "Версия Android: %s (%d)", Build.VERSION.RELEASE, Build.VERSION.SDK_INT));
            ((App) getApplication()).mixpanel.track("Main activity", props);

        } catch (Exception ignored) {
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        ((App) getApplication()).mixpanel.flush();
        super.onDestroy();
    }

    @Override
    public void showCustomNotice(UpdateDescription description) {
        if (!DnDUpdateDialog.isVisible.get()) {
            long version = BuildConfig.VERSION_CODE;
            if (description.versionCode > version) {
                DnDUpdateDialog d = new DnDUpdateDialog();
                Bundle args = new Bundle();
                args.putString(Constants.APK_UPDATE_CONTENT, description.updateMessage);
                args.putString(Constants.APK_DOWNLOAD_URL, description.url);
                args.putBoolean(Constants.APK_IS_AUTO_INSTALL, true);
                args.putBoolean(Constants.APK_CHECK_EXTERNAL, true);
                d.setArguments(args);

                FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
                ft.add(d, this.getClass().getSimpleName());
                ft.commitAllowingStateLoss();
            } else {
                if (isM.get()) {
                    Toast toast;
                    if (count.addAndGet(1) % 5 == 0) {
                        toast = Toast.makeText(getApplicationContext(), "Да нет обновлений, успокойся!!!", Toast.LENGTH_SHORT);
                    } else {
                        toast = Toast.makeText(getApplicationContext(), "Обновлений нет!", Toast.LENGTH_SHORT);
                    }
                    toast.show();

                    isM.set(false);
                }
            }
        }
    }
}
