package ru.drudenko.dnd.service;

import android.content.SharedPreferences;

import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.magic.Spell;

public class SpellFavoriteService {
    private final SharedPreferences preferences;
    private final App app;

    public SpellFavoriteService(SharedPreferences preferences, App app) {
        this.preferences = preferences;
        this.app = app;
    }

    public void setFavorite(Spell spell, boolean isFavorite) {
        String profileName = app.getCurrent().getName().equals("Default") ? "" : app.getCurrent().getName();
        final String key = profileName + spell.getName().toUpperCase().replace(" ", "_");

        spell.setFavorite(isFavorite);

        Spell m = null;
        try {
            m = app.getCurrent().getSpells().get(app.spells.indexOf(spell));
        } catch (Exception ignored) {
        }

        if (m == null) {
            try {
                app.getCurrent().getSpells().add((Spell) spell.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        } else {
            m.setFavorite(isFavorite);
        }


        if (isFavorite) {
            preferences.edit().putBoolean(key, true).apply();

        } else {
            app.spells.get(app.spells.indexOf(spell)).setFavorite(false);
            preferences.edit().putBoolean(key, false).apply();
        }

    }
}
