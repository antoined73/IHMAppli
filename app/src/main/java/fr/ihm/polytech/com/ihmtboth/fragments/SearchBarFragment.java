package fr.ihm.polytech.com.ihmtboth.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.ihm.polytech.com.ihmtboth.ArticleClickListener;
import fr.ihm.polytech.com.ihmtboth.R;
import fr.ihm.polytech.com.ihmtboth.RecyclerAdapter;
import fr.ihm.polytech.com.ihmtboth.model.saves.Saver;
import fr.ihm.polytech.com.ihmtboth.activities.ArticleActivity;
import fr.ihm.polytech.com.ihmtboth.activities.MainActivity;
import fr.ihm.polytech.com.ihmtboth.model.Article;

/**
 * Created by Antoine on 5/13/2017.
 */

public abstract class SearchBarFragment extends Fragment implements SearchView.OnQueryTextListener, ArticleClickListener, MenuFragment {

    protected RecyclerView suggestions;
    protected RecyclerAdapter recyclerAdapter;
    protected Toolbar toolbar;
    protected FilterFragment filterFragment;
    protected FloatingActionButton button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.toolbar = ((MainActivity) getActivity()).getToolBar();
        toolbar.inflateMenu(getMenu());
        toolbar.getMenu().clear();
        ((MainActivity) getActivity()).onCreateOptionsMenu(toolbar.getMenu());
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        suggestions = (RecyclerView) view.findViewById(R.id.suggestionList);
        button = (FloatingActionButton) view.findViewById(R.id.floatingButton);

        this.filterFragment = FilterFragment.newInstance();
        if (filterFragment != null) {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.add(R.id.filter_container, filterFragment, filterFragment.getTag());
            ft.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Article> newArticles = new ArrayList<>();
        List<Article> articleList = recyclerAdapter.getOriginalArticles();
        for (Article article : articleList) {
            if (article.getTitle().contains(newText)) {
                newArticles.add(article);
            }
        }
        recyclerAdapter.setFilter(newArticles);
        return false;
    }

    @Override
    public void articleClicked(Article article) {
        Saver saver = new Saver(getActivity());
        saver.saveSelectedArticle(article);

        Intent myIntent = new Intent(getActivity(), ArticleActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(myIntent, 0);
    }
}