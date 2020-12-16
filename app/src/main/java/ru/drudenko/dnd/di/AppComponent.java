package ru.drudenko.dnd.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.drudenko.dnd.activity.MainActivity;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MainActivity target);

    void inject(App target);
}
