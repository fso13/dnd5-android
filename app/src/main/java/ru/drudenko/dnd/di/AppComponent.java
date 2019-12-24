package ru.drudenko.dnd.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.drudenko.dnd.activity.MainActivity;
import ru.drudenko.dnd.activity.SpellActivity;
import ru.drudenko.dnd.fragment.monster.MonstersAllFragment;
import ru.drudenko.dnd.fragment.monster.MonstersFavoriteFragment;
import ru.drudenko.dnd.fragment.spell.SpellsAllFragment;
import ru.drudenko.dnd.fragment.spell.SpellsFavoriteFragment;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MainActivity target);

    void inject(SpellsAllFragment target);

    void inject(SpellsFavoriteFragment target);

    void inject(MonstersAllFragment target);

    void inject(MonstersFavoriteFragment target);

    void inject(SpellActivity target);
}
