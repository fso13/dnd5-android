package ru.drudenko.dnd.fragment.spell;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.activity.MainActivity;
import ru.drudenko.dnd.activity.SpellActivity;
import ru.drudenko.dnd.adapter.SpellAdapter;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.magic.Clazz;
import ru.drudenko.dnd.model.magic.Spell;

public class SpellsFavoriteFragment extends Fragment {

    private static List<String> classes = Clazz.getRu();
    private static List<String> level = Arrays.asList("Все", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    @Inject
    SharedPreferences preferences;
    private SpellAdapter spellAdapter;
    private int group = 0;
    private int child = 0;
    private ExpandableListView listView;
    private boolean expander = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getComponent().inject(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.activity_favorite, container, false);
        listView = root.findViewById(R.id.grid_view_spells);


        spellAdapter = new SpellAdapter(getContext(), ((App) getActivity().getApplication()).spellsFavorite, ((App) getActivity().getApplication()), preferences);
        listView.setAdapter(spellAdapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getContext(), SpellActivity.class);
                Object item = spellAdapter.getChild(groupPosition, childPosition);
                if (item instanceof Spell) {
                    group = groupPosition;
                    child = childPosition;
                    intent.putExtra("SPELL", (Spell) item);
                    startActivityForResult(intent, 0);
                }
                return true;
            }
        });


        Spinner spinnerClass = root.findViewById(R.id.spinner_classes);
        ArrayAdapter<String> adapterClasses = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_dropdown_item, classes);
        adapterClasses.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerClass.setAdapter(adapterClasses);
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spellAdapter.getFilter().filter("class:" + classes.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerLevel = root.findViewById(R.id.spinner_level);
        ArrayAdapter<String> adapterLevel = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_dropdown_item, level);
        adapterLevel.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerLevel.setAdapter(adapterLevel);
        spinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spellAdapter.getFilter().filter("level:" + level.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (expander) {
            listView.expandGroup(0);
            listView.expandGroup(1);
            listView.expandGroup(2);
            listView.expandGroup(3);
            listView.expandGroup(4);
            listView.expandGroup(5);
            listView.expandGroup(6);
            listView.expandGroup(7);
            listView.expandGroup(8);
            listView.expandGroup(9);
        }
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        inflater.inflate(R.menu.expand, menu);

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
        searchView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                          }
                                      }
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Spell spell = ((Spell) listView.getExpandableListAdapter().getChild(group, child));
        final String key = spell.getName().replace(" ", "_");
        spell.setFavorite(preferences.getBoolean(key, spell.isFavorite()));
        if (!spell.isFavorite()) {
            spellAdapter.originalData.get(group).remove(spell);
            spellAdapter.filteredData.get(group).remove(spell);
            ((App) getActivity().getApplication()).spellsFavorite.remove(spell);

        } else {
            ((App) getActivity().getApplication()).spellsFavorite.add(spell);
        }

        spellAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_expand) {
            expander = !expander;

            if (expander) {
                listView.expandGroup(0);
                listView.expandGroup(1);
                listView.expandGroup(2);
                listView.expandGroup(3);
                listView.expandGroup(4);
                listView.expandGroup(5);
                listView.expandGroup(6);
                listView.expandGroup(7);
                listView.expandGroup(8);
                listView.expandGroup(9);
            } else {
                listView.collapseGroup(0);
                listView.collapseGroup(1);
                listView.collapseGroup(2);
                listView.collapseGroup(3);
                listView.collapseGroup(4);
                listView.collapseGroup(5);
                listView.collapseGroup(6);
                listView.collapseGroup(7);
                listView.collapseGroup(8);
                listView.collapseGroup(9);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
