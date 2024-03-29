package ru.drudenko.dnd.di;

import android.app.Application;
import android.content.SharedPreferences;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import ru.drudenko.dnd.adapter.MonsterAdapter;
import ru.drudenko.dnd.adapter.OrderAdapter;
import ru.drudenko.dnd.adapter.SpellAdapter;
import ru.drudenko.dnd.model.Order;
import ru.drudenko.dnd.model.Profile;
import ru.drudenko.dnd.model.magic.Spell;
import ru.drudenko.dnd.model.monster.Monster;
import ru.drudenko.dnd.model.random.RandomEvent;
import ru.drudenko.dnd.service.MonsterFavoriteService;
import ru.drudenko.dnd.service.SpellFavoriteService;

public class App extends Application {
    public static final String MIXPANEL_TOKEN = "fb4524b60ad1c7da4eabf26e7bb6d714";

    @Inject
    public List<Spell> spells;

    @Inject
    public List<Monster> monsters;

   private List<String> monsterName;
    @Inject
    public List<Profile> profiles;

    @Inject
    public List<Order> orders;

    public MonsterAdapter monsterAdapter;
    public SpellAdapter spellAdapter;

    @Inject
    public SharedPreferences sharedPreferences;

    private List<RandomEvent> randomEventList;

    public SpellFavoriteService spellFavoriteService;
    public MonsterFavoriteService monsterFavoriteService;

    private AppComponent component;
    public MixpanelAPI mixpanel;

    @Override
    public void onCreate() {
        super.onCreate();


// Initialize the library with your
// Mixpanel project token, MIXPANEL_TOKEN, and a reference
// to your application context.
        mixpanel =
                MixpanelAPI.getInstance(getApplicationContext(), MIXPANEL_TOKEN);
        //needs to run once to generate it
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        component.inject(this);


        spellFavoriteService = new SpellFavoriteService(sharedPreferences, this);
        monsterFavoriteService = new MonsterFavoriteService(sharedPreferences, this);


    }


    public List<String> getMonsterNames(){

        if(monsterName == null) {
            monsterName = new ArrayList<>();
            for (Monster monster : monsters) {
                String name = monster.getName();
                monsterName.add(name);
            }
        }
        return monsterName;
    }

    public AppComponent getComponent() {
        return component;
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

    public void addOrder(Order order) {
        for (Order order1 : orders) {
            if (order1.getName().equals(order.getName())) {
                return;
            }
        }
        orders.add(order);

        StringBuilder value = new StringBuilder();

        for (Order order1 : orders) {
            value.append(order1.getName()).append("=").append(order1.getType()).append(";");
        }

        final String key = "ORDERS";
        sharedPreferences.edit().putString(key, value.toString()).apply();

    }

    public void deleteOrder(Order order) {

        orders.remove(order);

        StringBuilder value = new StringBuilder();
        for (Order order1 : orders) {
            value.append(order1.getName()).append("=").append(order1.getType()).append(";");
        }
        final String key = "ORDERS";
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