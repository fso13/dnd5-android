package ru.drudenko.dnd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ru.drudenko.dnd.R;

public class SingleAdapter extends ArrayAdapter<String> {


    public SingleAdapter(@NonNull Context context, @NonNull List<String> data) {
        super(context, 0, data);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view;


        view = LayoutInflater.from(getContext()).inflate(R.layout.listview_layout_single_text, parent, false);
        TextView textView = view.findViewById(R.id.textView2);

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(Color.WHITE);
        textView.setText(getItem(position));

        return view;
    }
}

//in your Activity or Fragment where of Adapter is instantiated :
