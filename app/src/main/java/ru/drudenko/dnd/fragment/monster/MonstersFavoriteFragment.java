package ru.drudenko.dnd.fragment.monster;

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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.activity.MainActivity;
import ru.drudenko.dnd.activity.MonsterActivity;
import ru.drudenko.dnd.adapter.MonsterAdapter;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.monster.Monster;

public class MonstersFavoriteFragment extends Fragment implements AbsListView.OnScrollListener {
    @Inject
    SharedPreferences preferences;
    private Monster monster;
    private MonsterAdapter monsterAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getComponent().inject(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_monsters_all, container, false);
        ListView listView = root.findViewById(R.id.grid_view_monsters);


        monsterAdapter = new MonsterAdapter(getContext(), ((App) getActivity().getApplication()).monstersFavorite, ((App) getActivity().getApplication()), preferences);
        listView.setAdapter(monsterAdapter);


        AdapterView.OnItemClickListener itemListener = (parent, v, position, id) -> {
            Intent intent = new Intent(getContext(), MonsterActivity.class);
            monster = monsterAdapter.getItem(position);

            intent.putExtra("MONSTER", monster);
            startActivityForResult(intent, 0);
        };
        listView.setOnItemClickListener(itemListener);
        listView.setOnScrollListener(this);

        Spinner spinnerClass = root.findViewById(R.id.spinner_level);

        ArrayAdapter<String> adapterClasses = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_dropdown_item, MonstersAllFragment.expId);
        adapterClasses.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerClass.setAdapter(adapterClasses);
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monsterAdapter.getFilter().filter("exp:" + MonstersAllFragment.expId.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerBiom = root.findViewById(R.id.spinner_biom);
        ArrayAdapter<String> adapterLevel = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_dropdown_item, MonstersAllFragment.bioms);
        adapterLevel.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerBiom.setAdapter(adapterLevel);
        spinnerBiom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monsterAdapter.getFilter().filter("biom:" + MonstersAllFragment.bioms.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((MainActivity) getContext()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                monsterAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                monsterAdapter.getFilter().filter(newText);
                return false;
            }
        });
        searchView.setOnClickListener(v -> {
                }
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final String key = "MONSTER_" + monster.getName().replace(" ", "_");
        monster.setFavorite(preferences.getBoolean(key, monster.isFavorite()));

        if (!monster.isFavorite()) {
            monsterAdapter.list.remove(monster);
            monsterAdapter.filteredData.remove(monster);
            ((App) getActivity().getApplication()).monstersFavorite.remove(monster);
            ((App) getActivity().getApplication()).monsters.get(((App) getActivity().getApplication()).monsters.indexOf(monster)).setFavorite(false);

        } else {
            ((App) getActivity().getApplication()).monstersFavorite.add(monster);
            ((App) getActivity().getApplication()).monsters.get(((App) getActivity().getApplication()).monsters.indexOf(monster)).setFavorite(true);
        }

        monsterAdapter.notifyDataSetChanged();
    }

}
