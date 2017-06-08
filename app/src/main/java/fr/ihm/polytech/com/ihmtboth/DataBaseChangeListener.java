package fr.ihm.polytech.com.ihmtboth;

import fr.ihm.polytech.com.ihmtboth.model.Article;

/**
 * Created by Antoine on 5/16/2017.
 */

public interface DataBaseChangeListener {
    void onDatabaseChanged(Article article);
}
