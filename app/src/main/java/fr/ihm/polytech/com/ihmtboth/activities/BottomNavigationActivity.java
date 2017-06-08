package fr.ihm.polytech.com.ihmtboth.activities;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;


/**
 * Created by user on 26/04/2017.
 */

public abstract class BottomNavigationActivity extends DrawerActivity{

    @Override
    protected abstract int getLayoutResourceId();

    public abstract int getLogoRessourceId();

    public abstract int getBottomNavigationRessourceId();

    private static final String SELECTED_ITEM = "arg_selected_item";

    private BottomNavigationView mBottomNav;
    private int mSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBottomNav = (BottomNavigationView) findViewById(getBottomNavigationRessourceId());
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                selectFragment(item);
                return true;
            }
        });
        MenuItem selectedItem;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);
        } else {
            selectedItem = mBottomNav.getMenu().getItem(0);
        }
//        mBottomNav.setSelectedItemId(R.id.action_home);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

//    private void selectFragment(MenuItem item) {
//        Fragment frag = null;
//        // init corresponding fragment
//        switch (item.getItemId()) {
//            case R.id.action_home:
//                frag = HomeFragment.newInstance(getColorFromRes(R.color.md_white_1000));
//                setActionMode(null);
//                break;
//            case R.id.action_search:
////                frag = SearchFragment.newInstance(getColorFromRes(R.color.colorAccent));
//                setActionMode(new SearchActionBarCallBack(actionBar,(SearchFragment) frag));
//                break;
//            case R.id.action_contact:
//                frag = MainFragment.newInstance(getString(R.string.text_contact),
//                        getColorFromRes(R.color.colorPrimaryDark));
//                break;
//        }
//
//        // update selected item
//        mSelectedItem = item.getItemId();
//
//        // uncheck the other items.
//        for (int i = 0; i< mBottomNav.getMenu().size(); i++) {
//            MenuItem menuItem = mBottomNav.getMenu().getItem(i);
//            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
//        }
//
//        updateToolbarText(item.getTitle());
//
//        if (frag != null) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.add(R.id.main_content_container, frag, frag.getTag());
//            ft.commit();
//        }
//    }

    private void updateToolbarText(CharSequence text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBar.setTitle(text);
        }
    }

    private int getColorFromRes(@ColorRes int resId) {
        return ContextCompat.getColor(this, resId);
    }


}
