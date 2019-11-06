package ru.drudenko.dnd.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ru.drudenko.dnd.R;

public class SpellActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_spell);
        Bundle bundle = getIntent().getExtras();

        TextView info = findViewById(R.id.textView_spell_info);
        info.setText(Html.fromHtml(bundle.getString("SPELL_INFO") + "\n\n"), TextView.BufferType.SPANNABLE);
        actionBar.setTitle(bundle.getString("SPELL_NAME"));

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
