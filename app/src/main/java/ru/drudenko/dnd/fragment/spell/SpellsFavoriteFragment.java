package ru.drudenko.dnd.fragment.spell;

import java.util.List;

import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.magic.Spell;

public class SpellsFavoriteFragment extends AbstractSpellListFragment {

    @Override
    List<Spell> getSpellList() {
        return ((App) getActivity().getApplication()).spellsFavorite;
    }

}
