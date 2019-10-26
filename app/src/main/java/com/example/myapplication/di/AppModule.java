package com.example.myapplication.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.R;
import com.example.myapplication.adapter.MonsterAdapter;
import com.example.myapplication.model.magic.ClassInfo;
import com.example.myapplication.model.magic.Clazz;
import com.example.myapplication.model.magic.Spell;
import com.example.myapplication.model.monster.Monster;
import com.example.myapplication.model.monster.MonsterList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {

    private Application application;

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
            StringBuilder total = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(application.getResources().openRawResource(R.raw.spells)));
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }
            List<Spell> spells = SpellService.getAllSpells(android.text.Html.fromHtml(total.toString()).toString());

            total = new StringBuilder();
            r = new BufferedReader(new InputStreamReader(application.getResources().openRawResource(R.raw.name)));
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }
            List<Spell> spells1 = SpellService.getAllSpells(spells, total.toString());


            for (Spell spell : spells1) {
                final String key = spell.getRu().getName().replace(" ", "_");
                spell.setFavorite(preferences.getBoolean(key, spell.isFavorite()));
            }

            Collections.sort(spells1, new Comparator<Spell>() {
                @Override
                public int compare(Spell o1, Spell o2) {
                    int c = o1.getEn().getLevel().compareTo(o2.getEn().getLevel());
                    return c == 0 ? o1.getRu().getName().compareTo(o2.getRu().getName()) : c;
                }
            });

            return spells1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Provides
    @Singleton
    public Map<Clazz, ClassInfo> provideClazzMap() {
        try {

            StringBuilder total = new StringBuilder();
            BufferedReader r;
            r = new BufferedReader(new InputStreamReader(application.getResources().openRawResource(R.raw.class_spells)));
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }

            return SpellService.getClassSpells(android.text.Html.fromHtml(total.toString()).toString());

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

            StringBuilder total = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(application.getResources().openRawResource(R.raw.monsters)));
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }
            List<Monster> monsters = SpellService.getAllMonsters(android.text.Html.fromHtml(total.toString()).toString());

            for (Monster monster : monsters) {
                final String key = "MONSTER_" + monster.getName().replace(" ", "_");
                monster.setFavorite(preferences.getBoolean(key, monster.isFavorite()));
            }

            Collections.sort(monsters, new Comparator<Monster>() {
                @Override
                public int compare(Monster o1, Monster o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });

            return monsters;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Provides
    @Singleton
    public MonsterAdapter monsterAdapterAll() {
        return new MonsterAdapter(application, provideListMonsters());

    }

    static class SpellService {
        private static final Pattern PATTERN = Pattern.compile("[А-я]", Pattern.MULTILINE);

        static List<Spell> getAllSpells(String json) throws IOException {
            Type listType = new TypeToken<List<Spell>>() {
            }.getType();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            return gson.fromJson(json, listType);
        }

        static List<Monster> getAllMonsters(String json) throws IOException {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            return gson.fromJson(json, MonsterList.class).getDataList();
        }

        static Map<Clazz, ClassInfo> getClassSpells(String json) throws IOException {
            Type listType = new TypeToken<Map<Clazz, ClassInfo>>() {
            }.getType();

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            return gson.fromJson(json, listType);

        }

        static List<Spell> getAllSpells(List<Spell> spells, String txt) {

            final Map<String, String> map = new HashMap<>();
            for (String s : txt.split("\n")) {
                Matcher m = PATTERN.matcher(s);
                if (m.find()) {
                    map.put(s.substring(0, m.toMatchResult().start()).trim(), s.substring(m.toMatchResult().start()));
                }
            }

            for (final Spell spell : spells) {
                String s = map.get(spell.getEn().getName().toUpperCase());
                if (s != null) {
                    spell.getRu().setName(s);
                } else {
                    spell.getRu().setName(spell.getRu().getName().toUpperCase());
                }
            }
            return spells;
        }

    }
}