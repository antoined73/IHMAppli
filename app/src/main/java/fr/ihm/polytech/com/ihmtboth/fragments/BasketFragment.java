package fr.ihm.polytech.com.ihmtboth.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import fr.ihm.polytech.com.ihmtboth.R;
import fr.ihm.polytech.com.ihmtboth.activities.MainActivity;
import fr.ihm.polytech.com.ihmtboth.model.saves.Basket;
import fr.ihm.polytech.com.ihmtboth.model.saves.Loader;
import fr.ihm.polytech.com.ihmtboth.RecyclerAdapter;
import fr.ihm.polytech.com.ihmtboth.model.Article;
import fr.ihm.polytech.com.ihmtboth.model.saves.Request;
import fr.ihm.polytech.com.ihmtboth.model.saves.Saver;

/**
 * Created by Antoine on 5/12/2017.
 */

public class BasketFragment extends SearchBarFragment {

    public static Fragment newInstance() {
        Fragment frag = new BasketFragment();
        return frag;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Set content
        Toolbar toolbar = ((MainActivity) getActivity()).getToolBar();
        toolbar.setTitle("Panier");

        updateListAndFilter();

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                buyBasket();
            }
        });
        button.setImageResource(R.drawable.ic_shopping_cart_24dp);

    }

    private void updateListAndFilter() {
        Loader loader = new Loader(getActivity());
        Basket basket = loader.loadBasket();
        Request request = loader.loadRequest();
        List<Article> articleList = basket.getArticlesWithRequest(request);
        filterFragment.initialise(basket.getArticles(), articleList);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getContext(),2);
        suggestions.setLayoutManager(lm);

        this.recyclerAdapter = new RecyclerAdapter(articleList, this);
        suggestions.setAdapter(recyclerAdapter);
        recyclerAdapter.setFilterListener(filterFragment);
    }

    private void buyBasket() {
        Loader loader = new Loader(getActivity());
        Basket basket = loader.loadBasket();
        new Saver(this.getActivity()).resetBasket();
        Snackbar.make(getView().findViewById(R.id.linear), basket.getArticles().size()+" articles achetés !", Snackbar.LENGTH_LONG).show();
        updateListAndFilter();
    }

    @Override
    public int getMenu() {
        return R.menu.menu_basket;
    }
}
