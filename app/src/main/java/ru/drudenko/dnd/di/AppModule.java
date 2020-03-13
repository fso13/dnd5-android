package ru.drudenko.dnd.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.drudenko.dnd.R;
import ru.drudenko.dnd.model.magic.ClassInfo;
import ru.drudenko.dnd.model.magic.Clazz;
import ru.drudenko.dnd.model.magic.Spell;
import ru.drudenko.dnd.model.monster.Monster;
import ru.drudenko.dnd.model.monster.Trait;


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
                if(monster.getName().equals("Колючий Дьявол (Spined Devil)")){
                    List<Trait> traits = new LinkedList<>();
                    Trait t1 = new Trait();
                    t1.setName("Мультиатака");
                    t1.setText(Collections.singletonList("Дьявол совершает две атаки: одну укусом, и одну вилами, или же две атаки иглами на хвосте"));
                    traits.add(t1);

                    Trait t2 = new Trait();
                    t2.setName("Укус");
                    t2.setAttack(Collections.singletonList("Рубящий урон 5 (2d4)"));
                    t2.setText(Collections.singletonList("Рукопашная атака оружием: +2 к попаданию, досягаемость 5 фт., одна цель"));
                    traits.add(t2);

                    Trait t3 = new Trait();
                    t3.setName("Вилы");
                    t3.setAttack(Collections.singletonList("Колющий урон 3 (1d6)"));
                    t3.setText(Collections.singletonList("Рукопашная атака оружием: +2 к попаданию, досягаемость 5 фт., одна цель"));
                    traits.add(t3);


                    Trait t4 = new Trait();
                    t4.setName("Игла на хвосте");
                    t4.setAttack(Collections.singletonList("Колющий урон 4 (1d4 + 2) плюс урон огнём 3 (1d6)"));
                    t4.setText(Collections.singletonList("Дальнобойная атака оружием: +4 к попаданию, дистанция 20/80 фт., одна цель"));
                    traits.add(t4);

                    monster.setAction(traits);
                }
                monster.setFavorite(preferences.getBoolean(key, false));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return monsters;
    }
}