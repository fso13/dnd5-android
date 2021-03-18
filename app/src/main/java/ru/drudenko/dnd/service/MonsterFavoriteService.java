package ru.drudenko.dnd.service;

import android.content.SharedPreferences;

import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.monster.Monster;

public class MonsterFavoriteService {
    private final SharedPreferences preferences;

    private final App app;

    public MonsterFavoriteService(SharedPreferences preferences, App app) {
        this.preferences = preferences;
        this.app = app;
    }

    public void setFavorite(Monster monster, boolean isFavorite) {
        String profileName = app.getCurrent().getName().equals("Default") ? "" : app.getCurrent().getName();
        final String key = profileName + "MONSTER_" + monster.getName().replace(" ", "_");

        monster.setFavorite(isFavorite);

        Monster m = null;
        try {
            m = app.getCurrent().getMonsters().get(app.monsters.indexOf(monster));
        } catch (Exception ignored) {
        }

        if (m == null) {
            try {
                app.getCurrent().getMonsters().add((Monster) monster.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        } else {
            m.setFavorite(isFavorite);
        }


        if (isFavorite) {
            preferences.edit().putBoolean(key, true).apply();

        } else {
            app.monsters.get(app.monsters.indexOf(monster)).setFavorite(false);
            preferences.edit().putBoolean(key, false).apply();
        }
    }
}
