package ru.drudenko.dnd.fragment.order;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Collections;
import java.util.List;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.adapter.OrderAdapter;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.Order;

public class OrderInitFragment extends Fragment{

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

        Spinner spinner = root.findViewById(R.id.spinner_person_type);
        ArrayAdapter<String> adapterLevel = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_dropdown_item, List.of("Игрок", "Монстр", "НПС"));
        adapterLevel.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapterLevel);

        RecyclerView listView = root.findViewById(R.id.orderList);


        ArrayAdapter<String> adapterTextEdit = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, ((App) getActivity().getApplication()).getMonsterNames());
        AutoCompleteTextView editText = root.findViewById(R.id.init_name);
        editText.setAdapter(adapterTextEdit);

        OrderAdapter adapter = new OrderAdapter(((App) getActivity().getApplication()));
        listView.setAdapter(adapter);

        Button button = root.findViewById(R.id.add_init);
        button.setOnClickListener(v -> {
            if (!editText.getText().toString().isEmpty()) {
                ((App) getActivity().getApplication()).addOrder(new Order(editText.getText().toString(), spinner.getSelectedItem().toString()));
                editText.getText().clear();
                adapter.notifyDataSetChanged();
            }

        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable( new ColorDrawable(this.getResources().getColor(R.color.colorPrimary)));
        listView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(listView);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(((App) getActivity().getApplication()).orders, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemChanged(fromPosition);
            recyclerView.getAdapter().notifyItemChanged(toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

}