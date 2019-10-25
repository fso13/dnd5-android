package com.example.myapplication.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.R;
import com.example.myapplication.model.ClassInfo;
import com.example.myapplication.model.Clazz;
import com.example.myapplication.model.Spell;
import com.example.myapplication.service.SpellService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
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


}