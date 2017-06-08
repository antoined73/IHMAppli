package fr.ihm.polytech.com.ihmtboth.model.saves;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.ihm.polytech.com.ihmtboth.model.Article;
import fr.ihm.polytech.com.ihmtboth.model.saves.Request;

/**
 * Created by Antoine on 5/12/2017.
 */

public class Basket {
    List<Article> articles;

    public Basket() {
        this.articles = new ArrayList<>();
    }

    public void add(Article article) {
        if(!containArticle(article)){
            articles.add(article);
        }
        System.out.println(articles.size());
    }

    public boolean containArticle(Article article) {
        for(Article a: articles){
            if(article.getId()==a.getId())return true;
        }
        return false;
    }

    public void removeArticle(Article article) {
        Article toRemove = null;
        for(Article a: articles){
            if(article.getId()==a.getId())toRemove = a;
        }
        if(toRemove!=null)articles.remove(toRemove);
        System.out.println(articles.size());
    }

    public List<Article> getArticles() {
        return articles;
    }

    public List<Article> getArticlesWithRequest(Request request) {
        List<Article> list = new ArrayList<>(articles);
        Iterator<Article> iter = list.iterator();

        while (iter.hasNext()) {
            Article article = iter.next();

            if (!request.isWanted(article.getCategory())) iter.remove();
        }
        return list;
    }
}
