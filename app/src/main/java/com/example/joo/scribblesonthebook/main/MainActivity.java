package com.example.joo.scribblesonthebook.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.ChangePasswordActivity;
import com.example.joo.scribblesonthebook.Events.SearchingIconClickEvent;
import com.example.joo.scribblesonthebook.R;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity implements MenuFragment.OnMenuItemSeletedListener {
    public static final String TABSPEC_BOOKSHELF = "bookshelf";
    public static final String TABSPEC_SCRIBBLE = "scribble";
    public static final String TABSPEC_SEARCHING_RECOMM = "searchingRecomm";

    public static final int SCRIBBLE_TAB_INDEX = 1;
    public static final int SEARCH_RECOMM_TAB_INDEX = 2;

    FragmentTabHost fragmentTabHost;
    Button fab;
    DrawerLayout mDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                //startActivity(new Intent(MainActivity.this, WritingScribbleActivity.class));
            }
        });

        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);

        fragmentTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        View tabScribble = View.inflate(this, R.layout.view_tab_scribble, null);
        View tabBookshelf = View.inflate(this, R.layout.view_tab_bookshelf, null);
        View tabRecomm = View.inflate(this, R.layout.view_tab_recomm, null);

        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(TABSPEC_BOOKSHELF).setIndicator(tabBookshelf), BookshelfFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(TABSPEC_SCRIBBLE).setIndicator(tabScribble), ScribbleFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(TABSPEC_SEARCHING_RECOMM).setIndicator(tabRecomm), SearchRecommFragment.class, null);
        fragmentTabHost.setCurrentTab(SCRIBBLE_TAB_INDEX);

        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (fragmentTabHost.getCurrentTab() != SCRIBBLE_TAB_INDEX)
                    fab.setVisibility(View.GONE);
                else fab.setVisibility(View.VISIBLE);
            }
        });

        if (savedInstanceState == null) {
            Fragment fragment = new MenuFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.menu_container, fragment);
            ft.commit();
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.btn_menu_nor);
        actionBar.setTitle("");
    }

    public static final String FILTER_DIALOG_TAG = "filterDialog";

    @Override
    public void onMenuItemSelected(int menuId) {
        switch (menuId) {
            case MenuFragment.MENU_ID_CHANGE_PASSWORD :
                startActivity(new Intent(MainActivity.this, ChangePasswordActivity.class));
                break;
            case MenuFragment.MENU_ID_LOGOUT :
                Toast.makeText(this, "Logout...", Toast.LENGTH_SHORT).show();
                break;
            case MenuFragment.MENU_ID_FILTER_SETTING :
                FilterDialogFragment fragment = new FilterDialogFragment();
                fragment.show(getSupportFragmentManager(), FILTER_DIALOG_TAG);
                break;

        }
        mDrawer.closeDrawers();
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

        if (id == android.R.id.home) {
            mDrawer.openDrawer(GravityCompat.START);
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }*/

    @Subscribe
    public void onSearchingIconClick(SearchingIconClickEvent searchingIconClickEvent) {
        fragmentTabHost.setCurrentTab(SEARCH_RECOMM_TAB_INDEX);
    }
}
