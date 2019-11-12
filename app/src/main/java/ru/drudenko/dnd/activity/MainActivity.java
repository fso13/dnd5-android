package ru.drudenko.dnd.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
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

import com.artwl.update.Constants;
import com.artwl.update.UpdateChecker;
import com.artwl.update.UpdateDialog;
import com.artwl.update.UpdateNotice;
import com.artwl.update.entity.UpdateDescription;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import ru.drudenko.dnd.BuildConfig;
import ru.drudenko.dnd.R;
import ru.drudenko.dnd.di.App;


public class MainActivity extends AppCompatActivity implements UpdateNotice {
    private static final String APP_UPDATE_SERVER_URL = "https://dnd5-webapi.herokuapp.com/application/version";
    private boolean isUpdating = false;
    @Inject
    Context context;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                R.id.nav_all_spells, R.id.nav_favorite_spells, R.id.nav_all_monsters, R.id.nav_favorite_monsters, R.id.nav_item_update)
                .setDrawerLayout(drawer)
                .build();


        TextView versionName = navigationView.getHeaderView(0).findViewById(R.id.textViewVersion);
        versionName.setText("v." + BuildConfig.VERSION_NAME);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.nav_item_update) {
                UpdateChecker.checkForCustomNotice(MainActivity.this, APP_UPDATE_SERVER_URL, this);
            } else {
                NavigationUI.setupWithNavController(navigationView, navController);
            }
            return true;
        });

        ((App) getApplication()).getComponent().inject(this);
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
        if (isUpdating) {
            Toast toast = Toast.makeText(getApplicationContext(), "Приложение уже обновляется", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        long version = BuildConfig.VERSION_CODE;
        if (description.versionCode > version) {
            UpdateDialog d = new UpdateDialog();
            Bundle args = new Bundle();
            args.putString(Constants.APK_UPDATE_CONTENT, description.updateMessage);
            args.putString(Constants.APK_DOWNLOAD_URL, description.url);
            args.putBoolean(Constants.APK_IS_AUTO_INSTALL, false);
            args.putBoolean(Constants.APK_CHECK_EXTERNAL, true);
            d.setArguments(args);

            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.add(d, this.getClass().getSimpleName());
            ft.commitAllowingStateLoss();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Нет новых версий!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
