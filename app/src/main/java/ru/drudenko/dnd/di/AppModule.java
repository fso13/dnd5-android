package ru.drudenko.dnd.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.drudenko.dnd.model.magic.Spell;
import ru.drudenko.dnd.model.monster.Monster;


@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }


    @Provides
    @Singleton
    public List<Spell> provideListSpells() {
        try {
            SharedPreferences preferences = application.getApplicationContext().getSharedPreferences("application_preferences", Context.MODE_PRIVATE);
            List<Spell> spells = new ConstantSpells().spells;
            for (Spell spell : spells) {
                final String key = spell.getName().toUpperCase().replace(" ", "_");
                spell.setFavorite(preferences.getBoolean(key, false));
            }
            return spells;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Provides
    @Singleton
    public SharedPreferences preferences() {
        return application.getApplicationContext().getSharedPreferences("application_preferences", Context.MODE_PRIVATE);
    }


    @Provides
    @Singleton
    public List<Monster> provideListMonsters() {
        try {
            SharedPreferences preferences = application.getApplicationContext().getSharedPreferences("application_preferences", Context.MODE_PRIVATE);
            List<Monster> monsters = new ConstantMonsters().monsters;
            for (Monster monster : monsters) {
                final String key = "MONSTER_" + monster.getName().replace(" ", "_");
                monster.setFavorite(preferences.getBoolean(key, false));
            }

            return monsters;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}