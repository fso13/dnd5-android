package ru.drudenko.dnd.fragment.master;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ru.drudenko.dnd.R;


public class ActionFragment extends Fragment {
    private String text;

    public ActionFragment(String text) {
        this.text = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.action_fragment_one, container, false);

        TextView info = root.findViewById(R.id.textView);
        info.setText(Html.fromHtml(text + "<br><br>"), TextView.BufferType.SPANNABLE);

        return root;
    }
}
