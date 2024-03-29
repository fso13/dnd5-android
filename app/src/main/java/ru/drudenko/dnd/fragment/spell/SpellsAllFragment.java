package ru.drudenko.dnd.fragment.spell;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.activity.MainActivity;
import ru.drudenko.dnd.activity.SpellActivity;
import ru.drudenko.dnd.adapter.SpellAdapter;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.di.ConstantSpells;
import ru.drudenko.dnd.model.Profile;
import ru.drudenko.dnd.model.magic.Spell;

public class SpellsAllFragment extends Fragment implements AbsListView.OnScrollListener {
    protected SpellAdapter spellAdapter;
    protected Spell spell;
    protected MenuItem itemFavorite;
    protected boolean isFavorite = false;

    protected List<Spell> getSpellList() {


        Profile profile = ((App) getActivity().getApplication()).getCurrent();
        for (Spell spell : ((App) getActivity().getApplication()).spells) {
            spell.setFavorite(false);
            for (Spell profileSpell : profile.getSpells()) {
                if (profileSpell.getName().equals(spell.getName())) {
                    spell.setFavorite(profileSpell.isFavorite());
                    break;
                }
            }
        }
        if (spellAdapter != null) {
            spellAdapter.notifyDataSetChanged();
        }

        return ((App) getActivity().getApplication()).spells;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View root = inflater.inflate(R.layout.fragment_spells_all, container, false);
        ListView listView = root.findViewById(R.id.grid_view_spells);

        spellAdapter = new SpellAdapter(getContext(), getSpellList(), ((App) getActivity().getApplication()));
        listView.setAdapter(spellAdapter);
        ((App) getActivity().getApplication()).spellAdapter = spellAdapter;

        AdapterView.OnItemClickListener itemListener = (parent, v, position, id) -> {
            Intent intent = new Intent(getContext(), SpellActivity.class);
            spell = spellAdapter.getItem(position);
            if (spell != null) {
                intent.putExtra("SPELLID", ((App) getActivity().getApplication()).spells.indexOf(spell));
                startActivityForResult(intent, 0);
            }
        };
        listView.setOnItemClickListener(itemListener);

        listView.setOnScrollListener(this);

        Spinner spinnerClass = root.findViewById(R.id.spinner_classes);
        ArrayAdapter<String> adapterClasses = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_dropdown_item, ConstantSpells.classes);
        adapterClasses.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerClass.setAdapter(adapterClasses);
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spellAdapter.getFilter().filter("class:" + ConstantSpells.classes.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerLevel = root.findViewById(R.id.spinner_level);
        ArrayAdapter<String> adapterLevel = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_dropdown_item, ConstantSpells.level);
        adapterLevel.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerLevel.setAdapter(adapterLevel);
        spinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spellAdapter.getFilter().filter("level:" + ConstantSpells.level.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerSchools = root.findViewById(R.id.spinner_schools);
        ArrayAdapter<String> adapterSchools = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_dropdown_item, ConstantSpells.schools);
        adapterSchools.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerSchools.setAdapter(adapterSchools);
        spinnerSchools.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spellAdapter.getFilter().filter("school:" + ConstantSpells.schools.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        try {
            JSONObject props = new JSONObject();

            props.put("Android Version", String.format(Locale.getDefault(), "Версия Android: %s (%d)", Build.VERSION.RELEASE, Build.VERSION.SDK_INT));
            ((App) getActivity().getApplication()).mixpanel.track("Spell fragment activity", props);

        } catch (Exception ignored) {
        }

        return root;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.search_favorite_list, menu);
        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView = new SearchView(((MainActivity) getContext()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                spellAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                spellAdapter.getFilter().filter(newText);
                return false;
            }
        });
        searchView.setOnClickListener(v -> {
                }
        );


        itemFavorite = menu.findItem(R.id.app_bar_switch);

        final Switch actionView = (Switch) itemFavorite.getActionView();

        actionView.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isFavorite = !isFavorite;
            spellAdapter.getFilter().filter("favorite:" + isFavorite);
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((App) getActivity().getApplication()).spellFavoriteService.setFavorite(spell, spell.isFavorite());
        spellAdapter.getFilter().filter(spellAdapter.getConstraintAdapter());
        spellAdapter.notifyDataSetChanged();
    }
}
