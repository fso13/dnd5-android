package ru.drudenko.dnd.fragment.master;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.adapter.ViewPagerAdapter;


public class MasterActionActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.action_activity);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TextView info = findViewById(R.id.textView4);

        info.setText("За один раунд игрок может совершить:  перемещение + одно действие + одно бонусное действие + одну реакцию.");

        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ActionFragment("<td style=\"text-align:left;width:13cm; \" class=\"Таблица1_A1\"><p class=\"P1\"><span class=\"T7\"><b>Скорость</b></span><span\n" +
                "                class=\"T8\"> - Вы можете в свой ход переместиться на расстояние, не превышающее вашу скорость (если не используете Рывок) </span><span\n" +
                "                class=\"T10\">(Книга игрока стр.190)</span><span class=\"T8\">;</span></p>\n" +
                "            <p class=\"P1\"><span class=\"T7\"><b>Прерывание перемещения</b></span><span class=\"T8\"> - Вы можете прерывать перемещение в свой ход, совершая что-то между двумя перемещениями </span><span\n" +
                "                    class=\"T10\">(Книга игрока стр.190)</span><span class=\"T8\">;</span></p>\n" +
                "            <p class=\"P1\"><span class=\"T7\"><b>Труднопроходимая местность</b></span><span class=\"T8\"> - Каждый фут перемещения по труднопроходимой местности стоит 1 дополнительный фут </span><span\n" +
                "                    class=\"T10\">(Книга игрока стр.190)</span><span class=\"T8\">;</span></p>\n" +
                "            <p class=\"P1\"><span class=\"T7\"><b>Перемещение лёжа</b></span><span class=\"T9\"> - </span><span class=\"T8\">Каждый фут перемещения во время ползания стоит 1 дополнительный фут</span>\n" +
                "                <span class=\"T10\">(Книга игрока стр.190)</span><span class=\"T8\">;</span></p>\n" +
                "            <p class=\"P1\"><span class=\"T7\"><b>Перемещение в полёте</b></span><span class=\"T8\"> </span><span class=\"T10\">(Книга игрока стр.191)</span><span\n" +
                "                    class=\"T8\">;</span></p>\n" +
                "            <p class=\"P1\"><span class=\"T7\"><b>Вставание</b></span><span class=\"T8\"> – чтобы встать Вы должны потратить половину своей скорости </span><span\n" +
                "                    class=\"T10\">(Книга игрока стр.190)</span></p></td>"), "Перемещение");
        adapter.addFragment(new BasisActionFragment(), "Основные действия");
        adapter.addFragment(new ActionFragment("Если вы совершаете действие Атака и атакуете рукопашным оружием со свойством «лёгкое», удерживаемым в одной руке, вы можете бонусным действием атаковать другим рукопашным оружием со свойством «лёгкое», удерживаемым в другой руке.<br>" +
                "Вы не добавляете модификатор характеристики к урону от бонусной атаки, если он положительный.<br>" +
                "Если у любого из оружий есть свойство «метательное», Вы можете не совершать им рукопашную атаку, а метнуть его.<br>" +
                "<br>" +
                "(Книга игрока стр.195)"), "Бонусные действия");
        adapter.addFragment(new ActionFragment("Вы можете совершить провоцированную атаку, если враждебное существо, которое Вы видите, покидает Вашу досягаемость. Для этого Вы реакцией совершаете одну рукопашную атаку по спровоцировавшему существу. Эта атака прерывает перемещение спровоцировавшего существа, происходя как раз перед тем, как оно покинет пределы вашей досягаемости.<br>" +
                "<br>" +
                "Телепортация не провоцирует атаки. (Книга игрока стр.195)"), "Реакции");
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
