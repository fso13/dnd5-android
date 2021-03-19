package ru.drudenko.dnd.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.di.RandomConstant;
import ru.drudenko.dnd.model.random.RandomEvent;

public class RandomAdapter extends ArrayAdapter<RandomEvent> {

    public RandomAdapter(@NonNull Context context) {
        super(context, 0, RandomConstant.randomEventList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view;


        view = LayoutInflater.from(getContext()).inflate(R.layout.random_event_list_view_item_layout, parent, false);
        TextView textView = view.findViewById(R.id.textView2);

        textView.setTextColor(Color.WHITE);
        RandomEvent randomEvent = getItem(position);
        textView.setText(randomEvent.getTitle());


        ImageButton button = view.findViewById(R.id.generate_event);
        button.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setMessage(randomEvent.random().getDescription());
            // alert.setMessage("Message");

            alert.setPositiveButton("Ok", (dialog, whichButton) -> {
                //Your action here
            });

            alert.setNegativeButton("Cancel",
                    (dialog, whichButton) -> {
                    });

            alert.show();

        });
        return view;
    }
}

//in your Activity or Fragment where of Adapter is instantiated :
