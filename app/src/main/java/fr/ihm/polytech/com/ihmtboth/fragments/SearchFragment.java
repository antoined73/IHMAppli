package fr.ihm.polytech.com.ihmtboth.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import fr.ihm.polytech.com.ihmtboth.R;
import fr.ihm.polytech.com.ihmtboth.activities.CriteriaActivity;
import fr.ihm.polytech.com.ihmtboth.activities.MainActivity;
import fr.ihm.polytech.com.ihmtboth.model.Category;
import fr.ihm.polytech.com.ihmtboth.model.TypeE;
import fr.ihm.polytech.com.ihmtboth.model.saves.Loader;
import fr.ihm.polytech.com.ihmtboth.NewsDBHelper;
import fr.ihm.polytech.com.ihmtboth.RecyclerAdapter;
import fr.ihm.polytech.com.ihmtboth.model.Article;
import fr.ihm.polytech.com.ihmtboth.model.saves.Request;


/**
 * Created by user on 26/04/2017.
 */

public class SearchFragment extends SearchBarFragment {

    public static Fragment newInstance() {
        Fragment frag = new SearchFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        toolbar.setTitle("Search");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Set content
        NewsDBHelper bdd = new NewsDBHelper(this.getContext());
        bdd.setListener(((MainActivity) getActivity()));
        try {
            bdd.createDataBase();

            bdd.openDataBase();
//            try {
//                bdd.addArticle(new Article(bdd.getAllArticles().size()+1,"ArticleExample","salut", TypeE.BOOK, Category.CADEAU,new URL("https://www.w3schools.com/css/img_fjords.jpg"),new Date(),13.4f));
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
            bdd.deleteArticleContainingArticleExample();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        Request request = new Loader(getActivity()).loadRequest();
        List<Article> articleList = bdd.getArticlesWithRequest(request);
        filterFragment.initialise(bdd.getAllArticles(), articleList);

        bdd.close();

        RecyclerView.LayoutManager lm = new GridLayoutManager(getContext(), 2);
        suggestions.setLayoutManager(lm);

        this.recyclerAdapter = new RecyclerAdapter(articleList, this);
        suggestions.setAdapter(recyclerAdapter);
        recyclerAdapter.setFilterListener(filterFragment);

        final Fragment thisFragment = this;
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent myIntent = new Intent(thisFragment.getActivity(), CriteriaActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivityForResult(myIntent, 0);
            }
        });
        button.setImageResource(R.drawable.ic_list_24dp);
    }

    @Override
    public int getMenu() {
        return R.menu.menu_search;
    }
}
