package ru.drudenko.dnd.fragment.monster;

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

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.activity.MainActivity;
import ru.drudenko.dnd.activity.MonsterActivity;
import ru.drudenko.dnd.adapter.MonsterAdapter;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.di.ConstantMonsters;
import ru.drudenko.dnd.model.Profile;
import ru.drudenko.dnd.model.monster.Monster;

public class MonsterListFragment extends Fragment implements AbsListView.OnScrollListener {
    protected MonsterAdapter monsterAdapter;
    protected Monster monster;
    protected MenuItem itemFavorite;
    protected boolean isFavorite = false;

    protected List<Monster> getMonstersList() {

        Profile profile = ((App) getActivity().getApplication()).getCurrent();
        for (Monster monster1 : ((App) getActivity().getApplication()).monsters) {
            monster1.setFavorite(false);
            for (Monster profileMonster : profile.getMonsters()) {
                if (profileMonster.getName().equals(monster1.getName())) {
                    monster1.setFavorite(profileMonster.isFavorite());
                    break;
                }
            }
        }
        if (monsterAdapter != null) {
            monsterAdapter.notifyDataSetChanged();
        }
        return ((App) getActivity().getApplication()).monsters;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_monsters_all, container, false);
        ListView listView = root.findViewById(R.id.grid_view_monsters);

        monsterAdapter = new MonsterAdapter(getContext(), getMonstersList(), ((App) getActivity().getApplication()));
        ((App) getActivity().getApplication()).monsterAdapter = monsterAdapter;

        listView.setAdapter(monsterAdapter);
        listView.setOnScrollListener(this);

        AdapterView.OnItemClickListener itemListener = (parent, v, position, id) -> {
            Intent intent = new Intent(getContext(), MonsterActivity.class);
            monster = monsterAdapter.getItem(position);

            if (monster != null) {
                intent.putExtra("MONSTERID", ((App) getActivity().getApplication()).monsters.indexOf(monster));
                startActivityForResult(intent, 0);
            }


        };
        listView.setOnItemClickListener(itemListener);

        Spinner spinnerClass = root.findViewById(R.id.spinner_level);

        ArrayAdapter<String> adapterClasses = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_dropdown_item, ConstantMonsters.expId);
        adapterClasses.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerClass.setAdapter(adapterClasses);
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monsterAdapter.getFilter().filter("exp:" + ConstantMonsters.expId.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerType = root.findViewById(R.id.spinner_type);
        ArrayAdapter<String> adapterTypes = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_dropdown_item, ConstantMonsters.types);
        adapterTypes.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerType.setAdapter(adapterTypes);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monsterAdapter.getFilter().filter("type:" + ConstantMonsters.types.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerBiom = root.findViewById(R.id.spinner_biom);
        ArrayAdapter<String> adapterLevel = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_dropdown_item, ConstantMonsters.bioms);
        adapterLevel.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerBiom.setAdapter(adapterLevel);
        spinnerBiom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monsterAdapter.getFilter().filter("biom:" + ConstantMonsters.bioms.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        try {
            JSONObject props = new JSONObject();

            props.put("Android Version", String.format(Locale.getDefault(), "Версия Android: %s (%d)", Build.VERSION.RELEASE, Build.VERSION.SDK_INT));
            ((App) getActivity().getApplication()).mixpanel.track("Monster fragment activity", props);

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


        itemFavorite = menu.findItem(R.id.app_bar_switch);
        final Switch actionView = (Switch) itemFavorite.getActionView();

        actionView.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isFavorite = !isFavorite;
            monsterAdapter.getFilter().filter("favorite:" + isFavorite);
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((App) getActivity().getApplication()).monsterFavoriteService.setFavorite(monster, monster.isFavorite());
        monsterAdapter.getFilter().filter(monsterAdapter.getConstraintAdapter());
        monsterAdapter.notifyDataSetChanged();
    }
}
