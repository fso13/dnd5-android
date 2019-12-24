package ru.drudenko.dnd.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.model.CustomItem;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_singl);
        Bundle bundle = getIntent().getExtras();

        CustomItem customItem = (CustomItem) bundle.get("CustomItem");

        TextView info = findViewById(R.id.textView);
        info.setText((customItem.getText() + "\n\n"));
        actionBar.setTitle(customItem.getName());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
