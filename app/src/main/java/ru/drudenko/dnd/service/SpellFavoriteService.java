package ru.drudenko.dnd.service;

import android.content.SharedPreferences;

import java.util.Collections;
import java.util.Comparator;

import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.magic.Spell;

public class SpellFavoriteService {
    private final SharedPreferences preferences;
    private final App app;

    private final Comparator<Spell> compareById = (Spell o1, Spell o2) -> {
        int i = o1.getLevel().compareTo(o2.getLevel());
        if (i == 0) {
            return o1.getName().compareTo(o2.getName());
        }
        return i;
    };

    public SpellFavoriteService(SharedPreferences preferences, App app) {
        this.preferences = preferences;
        this.app = app;
    }

    public void setFavorite(Spell spell, boolean isFavorite) {
        final String key = spell.getName().toUpperCase().replace(" ", "_");

        if (isFavorite) {
            spell.setFavorite(true);

            if (!app.spellsFavorite.contains(spell)) {
                app.spellsFavorite.add(spell);
                app.spells.get(app.spells.indexOf(spell)).setFavorite(true);
            }
            preferences.edit().putBoolean(key, true).apply();

        } else {
            spell.setFavorite(false);
            app.spellsFavorite.remove(spell);
            app.spells.get(app.spells.indexOf(spell)).setFavorite(false);
            preferences.edit().putBoolean(key, false).apply();
        }

        Collections.sort(app.spellsFavorite, compareById);
    }
}
