package ru.drudenko.dnd.di;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.drudenko.dnd.model.magic.Spell;
import ru.drudenko.dnd.model.monster.Monster;

public class App extends Application {

    @Inject
    public List<Spell> spells;

    @Inject
    public List<Monster> monsters;

    public List<Spell> spellsFavorite = new ArrayList<>();
    public List<Monster> monstersFavorite = new ArrayList<>();

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

        for (Monster s : monsters) {
            if (s.isFavorite()) {
                monstersFavorite.add(s);
            }
        }
    }


    public AppComponent getComponent() {
        return component;
    }

}