package ru.drudenko.dnd.service;

import android.content.SharedPreferences;

import java.util.List;

import ru.drudenko.dnd.model.Profile;
import ru.drudenko.dnd.model.magic.Spell;
import ru.drudenko.dnd.model.monster.Monster;

public class ProfileService {
    public List<Profile> profiles;
    public SharedPreferences sharedPreferences;

    public ProfileService(List<Profile> profiles, SharedPreferences sharedPreferences) {
        this.profiles = profiles;
        this.sharedPreferences = sharedPreferences;
    }

    public void updateProfile(Profile profile, boolean isCurrent) {

        if (isCurrent) {
            profile.setCurrent(true);
            for (Profile profile1 : profiles) {
                if (!profile.equals(profile1)) {
                    profile1.setCurrent(false);
                }
            }
        }

        StringBuilder value = new StringBuilder();

        for (Profile profile1 : profiles) {
            value.append(profile1.getName()).append("=").append(profile1.isCurrent()).append(";");
        }

        final String key = "PROFILES";
        sharedPreferences.edit().putString(key, value.toString()).apply();

    }

    public void deleteProfile(Profile profile) {

        profiles.remove(profile);

        for (Spell spell : spells) {
            final String key = profile.getName() + spell.getName().toUpperCase().replace(" ", "_");
            sharedPreferences.edit().remove(key).apply();
        }

        for (Monster monster : monsters) {
            final String key = profile.getName() + "MONSTER_" + monster.getName().replace(" ", "_");
            sharedPreferences.edit().remove(key).apply();
        }

        StringBuilder value = new StringBuilder();
        for (Profile profile1 : profiles) {
            value.append(profile1.getName()).append("=").append(profile1.isCurrent()).append(";");
        }
        final String key = "PROFILES";
        sharedPreferences.edit().putString(key, value.toString()).apply();

    }


    public void addProfile(Profile profile) {
        for (Profile profile1 : profiles) {
            if (profile1.getName().equals(profile.getName())) {
                return;
            }
        }
        profiles.add(profile);

        StringBuilder value = new StringBuilder();

        for (Profile profile1 : profiles) {
            value.append(profile1.getName()).append("=").append(profile1.isCurrent()).append(";");
        }

        final String key = "PROFILES";
        sharedPreferences.edit().putString(key, value.toString()).apply();

    }

    public Profile getCurrent() {
        for (Profile profile1 : profiles) {
            if (profile1.isCurrent()) {
                return profile1;
            }
        }
        return null;
    }
}
