package fr.ihm.polytech.com.ihmtboth.activities;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import fr.ihm.polytech.com.ihmtboth.R;
import fr.ihm.polytech.com.ihmtboth.model.saves.Saver;
import fr.ihm.polytech.com.ihmtboth.fragments.CriteriaFragment;


/**
 * Created by Antoine on 5/10/2017.
 */

public class CriteriaActivity extends ToolBarActivity {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.main_activity;
    }

    @Override
    protected void selectFragment(MenuItem item) {
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.action_done:
                new Saver(this).saveRequest(((CriteriaFragment)frag).getNewRequest());
                this.finish();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        this.frag = null;

    }

    @Override
    protected int getMenuToInflate() {
        return R.menu.menu_criterias;
    }

    @Override
    protected int getFragmentContainer() {
        return R.id.main_content_container;
    }

    @Override
    public Fragment getInitialFragment() {
        return CriteriaFragment.newInstance(R.color.md_white_1000,false);
    }

}
