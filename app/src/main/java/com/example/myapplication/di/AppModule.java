package com.example.myapplication.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.example.myapplication.R;
import com.example.myapplication.model.magic.ClassInfo;
import com.example.myapplication.model.magic.Clazz;
import com.example.myapplication.model.magic.Spell;
import com.example.myapplication.model.monster.Monster;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
            BufferedReader r = new BufferedReader(new InputStreamReader(application.getResources().openRawResource(R.raw.spell)));
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }

            ByteArrayInputStream fis = new ByteArrayInputStream(Base64.decode(total.toString(), Base64.DEFAULT));
            ObjectInputStream in = new ObjectInputStream(fis);
            in.close();

            List<Spell> spells = (List<Spell>) in.readObject();
            for (Spell spell : spells) {
                final String key = spell.getRu().getName().replace(" ", "_");
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
    public Map<Clazz, ClassInfo> provideClazzMap() {
        try {

            StringBuilder total = new StringBuilder();
            BufferedReader r;
            r = new BufferedReader(new InputStreamReader(application.getResources().openRawResource(R.raw.class_info)));
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }

            ByteArrayInputStream fis = new ByteArrayInputStream(Base64.decode(total.toString(), Base64.DEFAULT));
            ObjectInputStream in = new ObjectInputStream(fis);
            in.close();

            return (Map<Clazz, ClassInfo>) in.readObject();

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
        List<Monster> monsters = new LinkedList<>();
        try {
            SharedPreferences preferences = application.getApplicationContext().getSharedPreferences("application_preferences", Context.MODE_PRIVATE);

            StringBuilder total = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(application.getResources().openRawResource(R.raw.monster)));
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }

            ByteArrayInputStream fis = new ByteArrayInputStream(Base64.decode(total.toString(), Base64.DEFAULT));
            ObjectInputStream in = new ObjectInputStream(fis);
            in.close();

            monsters = (List<Monster>) in.readObject();


            for (Monster monster : monsters) {
                final String key = "MONSTER_" + monster.getName().replace(" ", "_");
                monster.setFavorite(preferences.getBoolean(key, false));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return monsters;
    }
}