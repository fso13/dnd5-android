package ru.drudenko.dnd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.activity.HelpActivity;
import ru.drudenko.dnd.activity.MainActivity;
import ru.drudenko.dnd.adapter.HelpAdapter;
import ru.drudenko.dnd.model.CustomItem;


public class MasterHelpFragment extends Fragment {

    private ExpandableListView listView;
    private HelpAdapter adapter;
    private boolean expander = true;


    public MasterHelpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_master_help, container, false);
        listView = root.findViewById(R.id.list_help);
        adapter = new HelpAdapter(getContext());
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getContext(), HelpActivity.class);
                Object item = adapter.getChild(groupPosition, childPosition);
                if (item instanceof CustomItem) {
                    intent.putExtra("CustomItem", (CustomItem) item);
                    startActivityForResult(intent, 0);
                }
                return true;
            }
        });
        if (expander) {
            listView.expandGroup(0);
            listView.expandGroup(1);
            listView.expandGroup(2);
            listView.expandGroup(3);
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
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_expand) {
            expander = !expander;

            if (expander) {
                listView.expandGroup(0);
                listView.expandGroup(1);
                listView.expandGroup(2);
                listView.expandGroup(3);
            } else {
                listView.collapseGroup(0);
                listView.collapseGroup(1);
                listView.collapseGroup(2);
                listView.collapseGroup(3);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
