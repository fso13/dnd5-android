package ru.drudenko.dnd.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.drudenko.dnd.R;
import ru.drudenko.dnd.model.magic.Spell;
import ru.drudenko.dnd.model.monster.Monster;
import ru.drudenko.dnd.model.monster.MonsterList;


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
            long l1 = System.currentTimeMillis();
            System.out.println("Start parse spells: " + l1);
            SharedPreferences preferences = application.getApplicationContext().getSharedPreferences("application_preferences", Context.MODE_PRIVATE);
            StringBuilder total = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(application.getResources().openRawResource(R.raw.spell)));
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }

            ByteArrayInputStream fis = new ByteArrayInputStream(Base64.decode(total.toString(), Base64.DEFAULT));
            ObjectInputStream in = new ObjectInputStream(fis);
            in.close();

            List<Spell> spells = (List<Spell>) in.readObject();

//            List<Spell> spells = SpellService.getAllSpells(total.toString());
//
//                        ByteArrayOutputStream fileOutputStream
//                    = new ByteArrayOutputStream();
//            ObjectOutputStream objectOutputStream
//                    = new ObjectOutputStream(fileOutputStream);
//            objectOutputStream.writeObject(spells);
//            objectOutputStream.flush();
//            objectOutputStream.close();
//
//
//            String s = Base64.encodeToString(fileOutputStream.toByteArray(),Base64.DEFAULT);

            Spell delete = null;
            for (Spell spell : spells) {
                if (spell.getName().equals("Гнев Гор")) {
                    delete = spell;
                } else {
                    final String key = spell.getName().toUpperCase().replace(" ", "_");
                    spell.setFavorite(preferences.getBoolean(key, false));
                }
            }
            if (delete != null) {
                spells.remove(delete);
            }

            long l2 = System.currentTimeMillis();
            System.out.println("finish parse spells: " + l2);
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
            System.out.println("Start parse monster: " + System.currentTimeMillis());

            SharedPreferences preferences = application.getApplicationContext().getSharedPreferences("application_preferences", Context.MODE_PRIVATE);

            StringBuilder total = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(application.getResources().openRawResource(R.raw.monster)));
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }

            ByteArrayInputStream fis = new ByteArrayInputStream(Base64.decode(total.toString(), Base64.DEFAULT));
            ObjectInputStream in = new ObjectInputStream(fis);
            in.close();

            List<Monster> monsters = (List<Monster>) in.readObject();
            System.out.println("start sorting monster: " + System.currentTimeMillis());
            System.out.println("end sorting monster: " + System.currentTimeMillis());

            for (Monster monster : monsters) {
                final String key = "MONSTER_" + monster.getName().replace(" ", "_");
                monster.setFavorite(preferences.getBoolean(key, false));
            }

            System.out.println("end setFavorite monster: " + System.currentTimeMillis());
            System.out.println("finish parse monster: " + System.currentTimeMillis());

            return monsters;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

        static List<Spell> getAllSpells(List<Spell> spells, String txt) {

            final Map<String, String> map = new HashMap<>();
            for (String s : txt.split("\n")) {
                Matcher m = PATTERN.matcher(s);
                if (m.find()) {
                    map.put(s.substring(0, m.toMatchResult().start()).trim(), s.substring(m.toMatchResult().start()));
                }
            }

            for (final Spell spell : spells) {
                String s = map.get(spell.getName().toUpperCase());
                if (s != null) {
                    spell.setName(s);
                } else {
                    spell.setName(spell.getName().toUpperCase());
                }
            }
            return spells;
        }

    }
}