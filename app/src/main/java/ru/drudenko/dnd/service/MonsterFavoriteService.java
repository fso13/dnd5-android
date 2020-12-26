package ru.drudenko.dnd.service;

import android.content.SharedPreferences;

import java.util.Collections;
import java.util.Comparator;

import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.monster.Monster;

public class MonsterFavoriteService {
    private final SharedPreferences preferences;

    private final App app;
    private final Comparator<Monster> compareById = (Monster o1, Monster o2) -> {
        int i = o1.getName().compareTo(o2.getName());
        if (i == 0) {
            return o1.getName().compareTo(o2.getName());
        }
        return i;
    };

    public MonsterFavoriteService(SharedPreferences preferences, App app) {
        this.preferences = preferences;
        this.app = app;
    }

    public void setFavorite(Monster monster, boolean isFavorite) {
        final String key = "MONSTER_" + monster.getName().replace(" ", "_");

        if (isFavorite) {
            monster.setFavorite(true);

            if (!app.monstersFavorite.contains(monster)) {
                app.monstersFavorite.add(monster);
                app.monsters.get(app.monsters.indexOf(monster)).setFavorite(true);
            }
            preferences.edit().putBoolean(key, true).apply();

        } else {
            monster.setFavorite(false);
            app.monstersFavorite.remove(monster);
            app.monsters.get(app.monsters.indexOf(monster)).setFavorite(false);
            preferences.edit().putBoolean(key, false).apply();
        }

        Collections.sort(app.monstersFavorite, compareById);
    }
}
