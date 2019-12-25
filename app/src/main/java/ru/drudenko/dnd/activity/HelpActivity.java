package ru.drudenko.dnd.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

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

        ArrayList<CustomItem> customItems = (ArrayList<CustomItem>) bundle.get("CustomItems");

        StringBuilder text = new StringBuilder();

        for (CustomItem item : customItems) {
            text.append("<b>").append(item.getName()).append("</b><br>");
            text.append(item.getText()).append("<br><br>");
        }

        TextView info = findViewById(R.id.textView);
        info.setText(Html.fromHtml(text + "<br><br>"), TextView.BufferType.SPANNABLE);
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
