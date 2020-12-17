package ru.drudenko.dnd.fragment.monster;

import java.util.List;

import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.monster.Monster;

public class MonstersAllFragment extends AbstractMonsterListFragment {

    @Override
    protected List<Monster> getMonstersList() {
        return ((App) getActivity().getApplication()).monsters;
    }

}
