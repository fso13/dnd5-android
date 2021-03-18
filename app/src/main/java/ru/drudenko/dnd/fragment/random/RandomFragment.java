package ru.drudenko.dnd.fragment.random;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.activity.RandomEventActivity;
import ru.drudenko.dnd.adapter.RandomAdapter;

public class RandomFragment extends Fragment implements AbsListView.OnScrollListener {

    public RandomFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_random_list, container, false);
        ListView listView = root.findViewById(R.id.randomEvent);

        RandomAdapter adapter = new RandomAdapter(getContext());
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener itemListener = (parent, v, position, id) -> {
            Intent intent = new Intent(getContext(), RandomEventActivity.class);

            intent.putExtra("RANDOM_EVENT_ID", position);
            startActivityForResult(intent, 0);
        };
        listView.setOnItemClickListener(itemListener);
        listView.setOnScrollListener(this);

        return root;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}