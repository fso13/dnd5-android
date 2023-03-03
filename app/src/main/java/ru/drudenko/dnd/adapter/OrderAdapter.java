package ru.drudenko.dnd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.Order;
import ru.drudenko.dnd.model.Profile;

public class OrderAdapter extends ArrayAdapter<Order> {
    private final App app;


    public OrderAdapter(@NonNull Context context, App app) {
        super(context, 0, app.orders);
        this.app = app;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view;

        view = LayoutInflater.from(getContext()).inflate(R.layout.order_list_view_item_layout, parent, false);
        TextView textView = view.findViewById(R.id.textView2);

        textView.setTextColor(Color.WHITE);
        Order order = getItem(position);
        textView.setText(order.getName());


        ImageButton button = view.findViewById(R.id.delete_profile);
        button.setOnClickListener(v -> {
            app.deleteOrder(order);
            notifyDataSetChanged();

        });
        return view;
    }

}

//in your Activity or Fragment where of Adapter is instantiated :
