package ru.drudenko.dnd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
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

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.Profile;

public class ProfileAdapter extends ArrayAdapter<Profile> {
    private final App app;


    public ProfileAdapter(@NonNull Context context, App app) {
        super(context, 0, app.profiles);
        this.app = app;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view;


        view = LayoutInflater.from(getContext()).inflate(R.layout.profile_list_view_item_layout, parent, false);
        TextView textView = view.findViewById(R.id.textView2);

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTextColor(Color.WHITE);
        Profile profile = getItem(position);
        textView.setText(profile.getName());


        ToggleButton toggleButton = view.findViewById(R.id.toggleButton);
        toggleButton.setChecked(profile.isCurrent());

        if (profile.isCurrent()) {
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.start_on));
        } else {
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.start_off));
        }
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) {
                if (isChecked) {
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.start_on));
                    app.updateProfile(profile, true);
                    profile.setCurrent(true);

                }
                if (app.monsterAdapter != null) {
                    app.monsterAdapter.notifyDataSetChanged();
                }
                if (app.spellAdapter != null) {
                    app.spellAdapter.notifyDataSetChanged();
                }
                notifyDataSetChanged();
            }
        });

        ImageButton button = view.findViewById(R.id.delete_profile);
        button.setOnClickListener(v -> {
            if (profile.isCurrent()) {
                return;
            }
            app.deleteProfile(profile);
            notifyDataSetChanged();

        });
        return view;
    }

}

//in your Activity or Fragment where of Adapter is instantiated :
