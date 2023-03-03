package ru.drudenko.dnd.fragment.order;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.adapter.OrderAdapter;
import ru.drudenko.dnd.adapter.ProfileAdapter;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.Order;
import ru.drudenko.dnd.model.Profile;

public class OrderInitFragment extends Fragment implements AbsListView.OnScrollListener {

    public OrderInitFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View root = inflater.inflate(R.layout.fragment_order_init, container, false);
        ListView listView = root.findViewById(R.id.orderList);

        EditText editText = root.findViewById(R.id.init_name);

        OrderAdapter adapter = new OrderAdapter(getContext(), ((App) getActivity().getApplication()));
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);

        Button button = root.findViewById(R.id.add_init);
        button.setOnClickListener(v -> {
            if (!editText.getText().toString().isEmpty()) {
                ((App) getActivity().getApplication()).addOrder(new Order(editText.getText().toString()));
                editText.getText().clear();
                adapter.notifyDataSetChanged();
            }

        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}