package com.example.myapplication.di;

import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.fragment.MonstersAllFragment;
import com.example.myapplication.fragment.MonstersFavoriteFragment;
import com.example.myapplication.fragment.SpellsAllFragment;
import com.example.myapplication.fragment.SpellsFavoriteFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MainActivity target);

    void inject(SpellsAllFragment target);

    void inject(SpellsFavoriteFragment target);

    void inject(MonstersAllFragment target);

    void inject(MonstersFavoriteFragment target);
}
