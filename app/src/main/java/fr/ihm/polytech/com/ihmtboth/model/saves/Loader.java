package fr.ihm.polytech.com.ihmtboth.model.saves;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import fr.ihm.polytech.com.ihmtboth.model.Article;

/**
 * Created by Antoine on 5/12/2017.
 */

public class Loader {
    private Activity activity;

    public Loader(Activity activity) {
        this.activity = activity;
    }

    public Article loadSelectedArticle() {
        return (Article) load("PREF_SELECTED",Article.class);
    }

    private Object load(String tag, Class type){
        SharedPreferences sharedPref = activity.getSharedPreferences(tag,0);
        Gson gson = new Gson();
        String json = sharedPref.getString(type.getSimpleName(), "");
        return gson.fromJson(json, type);
    }

    public Basket loadBasket() {
        return (Basket) load("BASKET",Basket.class);
    }

    public Request loadRequest() {
        return (Request) load("REQUEST",Request.class);
    }

    public PrevFrag getPreviousFragmentMainActivity() {
        return (PrevFrag) load("PREVIOUS_FRAG",PrevFrag.class);
    }

    public NotificationPrefs loadNotification() {
        return (NotificationPrefs) load("NOTIFICATION",NotificationPrefs.class);
    }

    public Request loadInterest() {
       Request result = (Request) load("INTEREST",Interests.class);
        if(result==null){
            new Saver(activity).saveInterests(new Interests());
        }
        return (Request) load("INTEREST",Interests.class);
    }
}
