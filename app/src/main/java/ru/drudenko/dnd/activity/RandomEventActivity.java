package ru.drudenko.dnd.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.adapter.SingleAdapter;
import ru.drudenko.dnd.di.RandomConstant;
import ru.drudenko.dnd.model.random.RandomEvent;

public class RandomEventActivity extends AppCompatActivity implements AbsListView.OnScrollListener {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_event);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();

        RandomEvent randomEvent = RandomConstant.randomEventList.get((Integer) bundle.get("RANDOM_EVENT_ID"));
        setTitle(randomEvent.getTitle());

        ListView listView = findViewById(R.id.events);

        listView.setAdapter(new SingleAdapter(this, randomEvent.toListString()));
        listView.setOnScrollListener(this);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

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
