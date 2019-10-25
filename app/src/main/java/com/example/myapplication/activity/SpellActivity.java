package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class SpellActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        String mActionBarTitle = intent.getStringExtra("actionBarTitle");

        //set actionbar title
        actionBar.setTitle(mActionBarTitle);

        setContentView(R.layout.activity_spell);
        Bundle bundle = getIntent().getExtras();

        TextView name = findViewById(R.id.textView_name);
        name.setText(bundle.getString("SPELL_NAME"));
        TextView info = findViewById(R.id.textView_info);
        info.setText(bundle.getString("SPELL_INFO"));
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
