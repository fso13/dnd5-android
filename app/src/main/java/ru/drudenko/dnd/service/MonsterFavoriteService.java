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
        final String key = "MONSTER_" + monster.getName().replace(" ", "_");

        if (isFavorite) {
            monster.setFavorite(true);

            preferences.edit().putBoolean(key, true).apply();

        } else {
            monster.setFavorite(false);
            app.monsters.get(app.monsters.indexOf(monster)).setFavorite(false);
            preferences.edit().putBoolean(key, false).apply();
        }
    }
}
