package ru.drudenko.dnd.fragment.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.Profile;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        ListView listView = root.findViewById(R.id.profileList);

        EditText editText = root.findViewById(R.id.username);

        ProfileAdapter adapter = new ProfileAdapter(getContext(), ((App) getActivity().getApplication()));
        listView.setAdapter(adapter);

        Button button = root.findViewById(R.id.add_profile);
        button.setOnClickListener(v -> {
            if (!editText.getText().toString().isEmpty()) {
                ((App) getActivity().getApplication()).addProfile(new Profile(editText.getText().toString(), false));
                editText.getText().clear();
                adapter.notifyDataSetChanged();
            }

        });

        return root;
    }
}