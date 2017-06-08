package fr.ihm.polytech.com.ihmtboth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import fr.ihm.polytech.com.ihmtboth.fragments.MenuFragment;
import fr.ihm.polytech.com.ihmtboth.model.saves.PrevFrag;
import fr.ihm.polytech.com.ihmtboth.fragments.BasketFragment;
import fr.ihm.polytech.com.ihmtboth.fragments.SearchBarFragment;
import fr.ihm.polytech.com.ihmtboth.model.saves.Basket;
import fr.ihm.polytech.com.ihmtboth.model.saves.Loader;
import fr.ihm.polytech.com.ihmtboth.R;
import fr.ihm.polytech.com.ihmtboth.model.saves.Saver;
import fr.ihm.polytech.com.ihmtboth.fragments.SearchFragment;
import fr.ihm.polytech.com.ihmtboth.model.saves.Request;


public class MainActivity extends DrawerActivity{

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        syncSavedPrefs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        if(searchView!=null){
            searchView.setOnQueryTextListener((SearchBarFragment) frag);
        }
        return true;
    }

    private void syncSavedPrefs() {
        Loader loader = new Loader(this);
        Request request = loader.loadRequest();
        Basket basket = loader.loadBasket();
        if(request==null){
            new Saver(this).saveRequest(new Request());
        }
        if(basket==null){
            new Saver(this).saveBasket(new Basket());
        }
    }

    @Override
    public Fragment getInitialFragment() {
//        Loader loader = new Loader(this);
//        PrevFrag prevFrag = loader.getPreviousFragmentMainActivity();
//        if(prevFrag!=null){
//            if(prevFrag.getFragClass().equals(SearchFragment.class.getSimpleName())){
//                return SearchFragment.newInstance();
//            }
//            return BasketFragment.newInstance();
//        }
        PrevFrag prevFrag = new PrevFrag(SearchFragment.class.getSimpleName());
        new Saver(this).savePreviousFragmentMainActivity(prevFrag);
        return SearchFragment.newInstance();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.main_activity;
    }

    @Override
    public int getLogoRessourceId() {
        return R.drawable.tboth;
    }

    @Override
    protected void selectFragment(MenuItem item) {
        this.frag = null;
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.action_criterias:
                Intent myIntent = new Intent(this, CriteriaActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivityForResult(myIntent, 0);
                break;
            case android.R.id.home:
                new Saver(this).savePreviousFragmentMainActivity(new PrevFrag(SearchFragment.class.getSimpleName()));
                finish();
                break;
        }
    }

    @Override
    protected int getMenuToInflate() {
        return ((MenuFragment)frag).getMenu();
    }

    @Override
    protected int getFragmentContainer() {
        return R.id.main_content_container;
    }

    @Override
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();

        Loader loader = new Loader(this);
        PrevFrag prevFrag = loader.getPreviousFragmentMainActivity();
        if(prevFrag!=null){
            if(prevFrag.getFragClass().equals(SearchFragment.class.getSimpleName())){
                frag= SearchFragment.newInstance();
            }else{
                frag = BasketFragment.newInstance();
            }
        }
        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(getFragmentContainer(), frag, frag.getTag());
            ft.commit();
        }
//        SearchBarFragment sbf = ((SearchBarFragment)frag);
//        if(sbf!=null)sbf.refresh();
    }
}
