package ru.drudenko.dnd.di;

import android.app.Application;
import android.content.SharedPreferences;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.drudenko.dnd.model.magic.Spell;
import ru.drudenko.dnd.model.monster.Monster;
import ru.drudenko.dnd.service.MonsterFavoriteService;
import ru.drudenko.dnd.service.SpellFavoriteService;

public class App extends Application {
    public static final String MIXPANEL_TOKEN = "fb4524b60ad1c7da4eabf26e7bb6d714";

    @Inject
    public List<Spell> spells;

    @Inject
    public List<Monster> monsters;

    @Inject
    public SharedPreferences sharedPreferences;

    public final List<Spell> spellsFavorite = new ArrayList<>();
    public SpellFavoriteService spellFavoriteService;
    public MonsterFavoriteService monsterFavoriteService;

    private AppComponent component;
    public MixpanelAPI mixpanel;
    @Override
    public void onCreate() {
        super.onCreate();


// Initialize the library with your
// Mixpanel project token, MIXPANEL_TOKEN, and a reference
// to your application context.
         mixpanel =
                MixpanelAPI.getInstance(getApplicationContext(), MIXPANEL_TOKEN);
        //needs to run once to generate it
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        component.inject(this);

        for (Spell s : spells) {
            if (s.isFavorite()) {
                spellsFavorite.add(s);
            }
        }

//        Comparator<Spell> compareById = (Spell o1, Spell o2) -> {
//            int i = o1.getLevel().compareTo(o2.getLevel());
//            if (i == 0) {
//                return o1.getName().compareTo(o2.getName());
//            }
//            return i;
//        };
//        Collections.sort(spellsFavorite, compareById);
        spellFavoriteService = new SpellFavoriteService(sharedPreferences, this);
        monsterFavoriteService = new MonsterFavoriteService(sharedPreferences, this);
    }


    public AppComponent getComponent() {
        return component;
    }

}