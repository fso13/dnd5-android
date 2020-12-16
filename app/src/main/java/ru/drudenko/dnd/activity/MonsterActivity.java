package ru.drudenko.dnd.activity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.adapter.ViewPagerAdapter;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.di.ConstantMonsters;
import ru.drudenko.dnd.fragment.monster.MonsterAbilityFragment;
import ru.drudenko.dnd.fragment.monster.MonsterActionFragment;
import ru.drudenko.dnd.fragment.monster.MonsterInfoFragment;
import ru.drudenko.dnd.fragment.monster.MonsterTraitFragment;
import ru.drudenko.dnd.model.monster.Monster;


public class MonsterActivity extends AppCompatActivity {
    private Monster monster;

    public Drawable getDrawableFromAssets(String path) throws IOException {
        return Drawable.createFromStream(getAssets().open(path), null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_monster);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle bundle = getIntent().getExtras();

        monster = (Monster) bundle.get("MONSTER");

        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(monster.getName());

        try {
            String name;
            int indexOf = monster.getName().indexOf("(");
            if (indexOf >= 0) {
                name = monster.getName().substring(indexOf + 1, monster.getName().indexOf(")")).trim().toUpperCase().replace(" ", "_") + ".jpg";
            } else {
                indexOf = monster.getName().indexOf("[");
                name = monster.getName().substring(indexOf + 1, monster.getName().indexOf("]")).trim().toUpperCase().replace(" ", "_") + ".jpg";
            }

            appBarLayout.setBackground(getDrawableFromAssets(name));
        } catch (Exception e) {
            appBarLayout.setExpanded(false, false);
        }

        if (monster.getCr() != null) {
            TextView cr = findViewById(R.id.textView_cr);
            cr.setText("Опасность " + monster.getCr());

            TextView exp = findViewById(R.id.textView_exp);
            exp.setText(ConstantMonsters.exps.get(monster.getCr()) + " опыт");

        }

        if (monster.getAc() != null) {
            String[] acc = monster.getAc().split("\\(");
            TextView ac = findViewById(R.id.textView_ac);
            ac.setText(acc[0] + "КД");
            if (acc.length > 1) {
                TextView acType = findViewById(R.id.editText_ac_type);
                acType.setText(acc[1].substring(0, acc[1].length() - 1));
            }
        }

        if (monster.getHp() != null) {
            String[] hpp = monster.getHp().split("\\(");

            TextView hp = findViewById(R.id.textView_hp);
            hp.setText(hpp[0] + "ХП");

            if (hpp.length > 1) {
                TextView hpDice = findViewById(R.id.editText_hp_dice);
                hpDice.setText(hpp[1].substring(0, hpp[1].length() - 1));
            }
        }
        if (monster.getSpeed() != null) {
            TextView speed = findViewById(R.id.textView_speed);
            Matcher matcher = Pattern.compile("(\\d+)").matcher(monster.getSpeed());
            if (matcher.find()) {
                speed.setText(matcher.group());
            }

            String[] details = monster.getSpeed().split(",");
            if (details.length > 1) {
                TextView speedDetails = findViewById(R.id.textView_speed_details);
                speedDetails.setText(details[1]);
            }

        }
        toolbar.setTitle(monster.getName());


        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MonsterAbilityFragment(monster), "способности");
        adapter.addFragment(new MonsterTraitFragment(monster), "черты");
        adapter.addFragment(new MonsterActionFragment(monster), "действия");
        adapter.addFragment(new MonsterInfoFragment(monster), "описание");
        viewPager.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favorite, menu);

        menu.getItem(0).setIcon(monster.isFavorite() ? R.drawable.stars_on : R.drawable.stars_off);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        if (item.getItemId() == R.id.action_favorite) {
            ((App) getApplication()).monsterFavoriteService.setFavorite(monster, !monster.isFavorite());
            item.setIcon(monster.isFavorite() ? R.drawable.stars_on : R.drawable.stars_off);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        ((App) getApplication()).monsterFavoriteService.setFavorite(monster, monster.isFavorite());
        super.onDestroy();
    }
}
