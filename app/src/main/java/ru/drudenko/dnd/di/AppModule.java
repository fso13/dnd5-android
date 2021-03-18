package ru.drudenko.dnd.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.drudenko.dnd.model.Profile;
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
            List<Spell> spells = ConstantSpells.spells;
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
            List<Monster> monsters = ConstantMonsters.monsters;
//            for (Monster monster : monsters) {
//                final String key = "MONSTER_" + monster.getName().replace(" ", "_");
//                monster.setFavorite(preferences.getBoolean(key, false));
//            }

            return monsters;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Provides
    @Singleton
    public List<Profile> provideListProfiles() {
        try {
            SharedPreferences preferences = application.getApplicationContext().getSharedPreferences("application_preferences", Context.MODE_PRIVATE);
            List<Profile> profiles = new ArrayList<>();
            String profilesString = preferences.getString("PROFILES", "Default=true");

            for (String s : profilesString.split(";")) {
                String[] p = s.split("=");
                Profile profile = new Profile(p[0], Boolean.parseBoolean(p[1]));
                profiles.add(profile);
                List<Monster> monsters = ConstantMonsters.monsters;
                String profileName = p[0].equals("Default") ? "" : p[0];
                for (Monster monster : monsters) {
                    final String key = profileName + "MONSTER_" + monster.getName().replace(" ", "_");
                    if (preferences.getBoolean(key, false)) {
                        monster.setFavorite(true);
                        profile.getMonsters().add((Monster) monster.clone());
                    }
                }

                List<Spell> spells = ConstantSpells.spells;
                for (Spell spell : spells) {
                    final String key = profileName + spell.getName().toUpperCase().replace(" ", "_");
                    if (preferences.getBoolean(key, false)) {
                        spell.setFavorite(true);
                        profile.getSpells().add((Spell) spell.clone());
                    }
                }

            }

            return profiles;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}