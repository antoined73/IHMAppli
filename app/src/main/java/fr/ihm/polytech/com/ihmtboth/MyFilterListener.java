package fr.ihm.polytech.com.ihmtboth;

import java.util.List;

import fr.ihm.polytech.com.ihmtboth.model.Article;

/**
 * Created by Antoine on 5/23/2017.
 */

public interface MyFilterListener {
    void onContentChange(List<Article> newArticleList);

    void initialise(List<Article> initialArticleList, List<Article> filteredArticleList);
}
