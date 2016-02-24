package com.example.joo.scribblesonthebook.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.joo.scribblesonthebook.R;

public class MainActivity extends AppCompatActivity {
    public static final String TABSPEC_BOOKSHELF = "bookshelf";
    public static final String TABSPEC_SCRIBBLE = "scribble";
    public static final String TABSPEC_SEARCHING_RECOMM = "searchingRecomm";

    public static final int SCRIBBLE_TAB_INDEX = 1;

    Spinner spinner;
    ArrayAdapter<String> mAdapter;
    FragmentTabHost fragmentTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mAdapter);

        fragmentTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        String bookshelf = getResources().getString(R.string.tab_bookshelf_indicator);
        String scribble = getResources().getString(R.string.tab_scribble_indicator);
        String searchingRecomm = getResources().getString(R.string.tab_searchingRecomm_indicator);

        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(TABSPEC_BOOKSHELF).setIndicator(bookshelf), MainDefaultFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(TABSPEC_SCRIBBLE).setIndicator(scribble), ScribbleFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(TABSPEC_SEARCHING_RECOMM).setIndicator(searchingRecomm), SearchRecommFragment.class, null);
        fragmentTabHost.setCurrentTab(SCRIBBLE_TAB_INDEX);

        initSpinner();
    }

    private void initSpinner() {
        mAdapter.add(getResources().getString(R.string.book_reading));
        mAdapter.add(getResources().getString(R.string.book_done));
        mAdapter.add(getResources().getString(R.string.book_will));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
