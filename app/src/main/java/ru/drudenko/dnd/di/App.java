package ru.drudenko.dnd.di;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.drudenko.dnd.model.magic.Spell;
import ru.drudenko.dnd.model.monster.Monster;
import ru.drudenko.dnd.service.MonsterFavoriteService;
import ru.drudenko.dnd.service.SpellFavoriteService;

public class App extends Application {

    @Inject
    public List<Spell> spells;

    @Inject
    public List<Monster> monsters;

    @Inject
    public SharedPreferences sharedPreferences;

    public final List<Spell> spellsFavorite = new ArrayList<>();
    public final List<Monster> monstersFavorite = new ArrayList<>();
    public SpellFavoriteService spellFavoriteService;
    public MonsterFavoriteService monsterFavoriteService;

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

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

        for (Monster m : monsters) {
            if (m.isFavorite()) {
                monstersFavorite.add(m);
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