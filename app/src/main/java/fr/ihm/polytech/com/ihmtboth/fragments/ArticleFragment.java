package fr.ihm.polytech.com.ihmtboth.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import fr.ihm.polytech.com.ihmtboth.ImageAsyncTask;
import fr.ihm.polytech.com.ihmtboth.activities.ArticleActivity;
import fr.ihm.polytech.com.ihmtboth.activities.MainActivity;
import fr.ihm.polytech.com.ihmtboth.model.saves.Basket;
import fr.ihm.polytech.com.ihmtboth.model.saves.Loader;
import fr.ihm.polytech.com.ihmtboth.R;
import fr.ihm.polytech.com.ihmtboth.model.Article;
import fr.ihm.polytech.com.ihmtboth.model.saves.Saver;

/**
 * Created by Antoine on 5/11/2017.
 */

public class ArticleFragment extends Fragment {

    private View mContent;
    private Button orderButton;
    private ImageView image_article;
    private TextView titleTextView;
    private TextView categoryTextView;
    private Article mArticle;
    private TextView priceTextView;
    private TextView descipritionTewtView;

    public static Fragment newInstance() {
        Fragment frag = new ArticleFragment();
        return frag;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = ((ArticleActivity) getActivity()).getToolBar();
        toolbar.setTitle(mArticle.getTitle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.article_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // initialize views
        this.mArticle = new Loader(getActivity()).loadSelectedArticle();
        mContent = view.findViewById(R.id.fragment_content);
        image_article = (ImageView) view.findViewById(R.id.image_article);
        titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        categoryTextView = (TextView) view.findViewById(R.id.categoryTextView);
        orderButton = (Button) view.findViewById(R.id.orderButton);
        priceTextView = (TextView) view.findViewById(R.id.priceTW);
        descipritionTewtView = (TextView) view.findViewById(R.id.descriptionTW);

        //fill article
        new ImageAsyncTask(image_article).execute(String.valueOf(mArticle.getUrl()));
        titleTextView.setText(mArticle.getTitle());
        categoryTextView.setText(mArticle.getCategory().toString());
        priceTextView.setText(mArticle.getPrice()+" €");
        descipritionTewtView.setText(mArticle.getContent());
        final ArticleActivity activity = (ArticleActivity) this.getActivity();
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.checkBasket();
            }
        });
    }

    public Button getOrderButton() {
        return orderButton;
    }
}