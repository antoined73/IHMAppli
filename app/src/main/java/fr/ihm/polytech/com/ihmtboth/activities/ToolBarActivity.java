package fr.ihm.polytech.com.ihmtboth.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import fr.ihm.polytech.com.ihmtboth.R;

/**
 * Created by user on 26/04/2017.
 */

public abstract class ToolBarActivity extends AppCompatActivity {

    protected Toolbar toolBar;
    protected ActionBar actionBar;
    protected Fragment frag;
    protected Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // enable transitions
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(getLayoutResourceId());

        //ToolBar
        this.toolBar = (Toolbar) findViewById(R.id.toolbar);
        toolBar.setTitle("");
        if(toolBar!= null) {
            this.setSupportActionBar(toolBar);
        }
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.frag = getInitialFragment();
        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(getFragmentContainer(), frag, frag.getTag());
            ft.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(getMenuToInflate(), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Fragment previousFragment = this.frag;
        selectFragment(menuItem);
//        if(previousFragment!=frag)updateActivity(menuItem);
        updateActivity(menuItem);
        return super.onOptionsItemSelected(menuItem);
    }

    private void updateActivity(MenuItem menuItem){
        updateToolbarText(menuItem.getTitle());
        if (frag != null && !frag.isAdded()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(getFragmentContainer(), frag, frag.getTag());
            ft.commit();
        }
    }

    private void updateToolbarText(CharSequence text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBar.setTitle(text);
        }
    }

    protected abstract int getLayoutResourceId();

    protected abstract void selectFragment(MenuItem item);

    protected abstract int getMenuToInflate();

    protected abstract int getFragmentContainer();

    public abstract Fragment getInitialFragment();

    public Toolbar getToolBar() {
        return toolBar;
    }
}
