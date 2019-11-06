package ru.drudenko.dnd.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.adapter.ViewPagerAdapter;
import ru.drudenko.dnd.fragment.MonsterAbilityFragment;
import ru.drudenko.dnd.fragment.MonsterActionFragment;
import ru.drudenko.dnd.fragment.MonsterInfoFragment;
import ru.drudenko.dnd.fragment.MonsterTraitFragment;
import ru.drudenko.dnd.fragment.MonstersAllFragment;
import ru.drudenko.dnd.model.monster.Monster;


public class MonsterActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Monster monster;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();

        setContentView(R.layout.activity_monster);
        Bundle bundle = getIntent().getExtras();

        monster = (Monster) bundle.get("MONSTER");

        if (monster.getCr() != null) {
            TextView cr = findViewById(R.id.textView_cr);
            cr.setText("Опасность " + monster.getCr());

            TextView exp = findViewById(R.id.textView_exp);
            exp.setText(MonstersAllFragment.exps.get(monster.getCr()) + " опыт");

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

        }
        actionBar.setTitle(monster.getName());


        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tablayout);
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

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
