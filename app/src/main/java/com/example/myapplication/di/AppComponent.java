package com.example.myapplication.di;

import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.fragment.FavoriteActivity;
import com.example.myapplication.fragment.SpellsAllActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MainActivity target);

    void inject(SpellsAllActivity target);

    void inject(FavoriteActivity favoriteActivity);
}
