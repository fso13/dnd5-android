package ru.drudenko.dnd.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
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

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

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
    public static AtomicBoolean isM = new AtomicBoolean(false);
    @Inject
    Context context;
    private AppBarConfiguration mAppBarConfiguration;
    private DnDUpdateDialog d = new DnDUpdateDialog();

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
                R.id.nav_all_spells, R.id.nav_favorite_spells, R.id.nav_all_monsters, R.id.nav_favorite_monsters, R.id.nav_master_help)
                .setDrawerLayout(drawer)
                .build();


        TextView versionName = navigationView.getHeaderView(0).findViewById(R.id.textViewVersion);
        versionName.setText("v." + BuildConfig.VERSION_NAME);

        Button button = navigationView.getHeaderView(0).findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isM.set(true);
                UpdateChecker.checkForCustomNotice(MainActivity.this, APP_UPDATE_SERVER_URL, MainActivity.this);
            }
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
                        } catch (Exception e) {
                        }
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();


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
    public void showCustomNotice(UpdateDescription description) {
        if (!DnDUpdateDialog.isVisible.get()) {
            long version = BuildConfig.VERSION_CODE;
            if (description.versionCode > version) {
                d = new DnDUpdateDialog();
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
                    Toast toast = Toast.makeText(getApplicationContext(), "Обновлений нет!", Toast.LENGTH_SHORT);
                    toast.show();

                    isM.set(false);
                }
            }
        }
    }
}
