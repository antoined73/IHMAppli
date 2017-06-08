package fr.ihm.polytech.com.ihmtboth.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import fr.ihm.polytech.com.ihmtboth.model.saves.Basket;
import fr.ihm.polytech.com.ihmtboth.model.saves.Loader;
import fr.ihm.polytech.com.ihmtboth.R;
import fr.ihm.polytech.com.ihmtboth.model.saves.Saver;
import fr.ihm.polytech.com.ihmtboth.fragments.ArticleFragment;
import fr.ihm.polytech.com.ihmtboth.model.Article;

/**
 * Created by Antoine on 5/12/2017.
 */

public class ArticleActivity extends ToolBarActivity {
    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Loader loader = new Loader(this);
        this.article = loader.loadSelectedArticle();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        Loader loader = new Loader(this);
        updateBasketButtons(loader.loadBasket());
        return true;
    }

    public void checkBasket() {
        Loader loader = new Loader(this);
        Basket basket = loader.loadBasket();
        Saver saver = new Saver(this);
        if(basket.containArticle(article)){
            basket.removeArticle(article);
            saver.saveBasket(basket);
        }else{
            basket.add(article);
            saver.saveBasket(basket);
        }
        updateBasketButtons(basket);
    }

    private void updateBasketButtons(Basket basket) {
        ArticleFragment articleFragment = ((ArticleFragment)frag);
        if(basket.containArticle(article)){
            menu.findItem(R.id.action_add_basket).setIcon(R.drawable.ic_remove_shopping_cart_24dp);
            articleFragment.getOrderButton().setText("Enlever du panier");
        }else{
            menu.findItem(R.id.action_add_basket).setIcon(R.drawable.ic_add_shopping_cart_24dp);
            articleFragment.getOrderButton().setText("Ajouter au panier");
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.main_activity;
    }

    @Override
    protected void selectFragment(MenuItem item) {
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.action_add_basket:
                checkBasket();
                break;
            case android.R.id.home:
                finish();
                break;
        }
    }

    @Override
    protected int getMenuToInflate() {
        return R.menu.menu_article;
    }

    @Override
    protected int getFragmentContainer() {
        return R.id.main_content_container;
    }

    @Override
    public Fragment getInitialFragment() {
        return ArticleFragment.newInstance();
    }
}
