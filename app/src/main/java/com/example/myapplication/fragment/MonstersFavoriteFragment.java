package com.example.myapplication.fragment;

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
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.activity.MonsterActivity;
import com.example.myapplication.adapter.MonsterAdapter;
import com.example.myapplication.di.App;
import com.example.myapplication.model.monster.Monster;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MonstersFavoriteFragment extends Fragment {
    @Inject
    SharedPreferences preferences;

    @Inject
    List<Monster> monsters;

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

        List<Monster> m = new ArrayList<>();
        for (Monster s : monsters) {
            if (s.isFavorite()) {
                m.add(s);
            }
        }


        monsterAdapter = new MonsterAdapter(getContext(), m);
        listView.setAdapter(monsterAdapter);


        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(getContext(), MonsterActivity.class);
                Monster monster = monsterAdapter.getItem(position);

                String nameMessage = monster.getName();
                intent.putExtra("MONSTER_NAME", nameMessage);
                intent.putExtra("MONSTER_INFO1", monster.getInfo1());
                intent.putExtra("MONSTER_INFO2", monster.getInfo2());
                intent.putExtra("MONSTER_TEXT", monster.getText());
                startActivityForResult(intent, 0);
            }
        };
        listView.setOnItemClickListener(itemListener);

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
        searchView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                          }
                                      }
        );
    }

}
