package fr.ihm.polytech.com.ihmtboth.model.saves;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import fr.ihm.polytech.com.ihmtboth.model.Article;

/**
 * Created by Antoine on 5/12/2017.
 */

public class Saver {
    private final Activity activity;

    public Saver(Activity activity) {
        this.activity = activity;
    }

    public void saveSelectedArticle(Article article){
        save("PREF_SELECTED",article);
    }

    public void addArticleToBasket(Article article) {
        Loader loader = new Loader(activity);
        Basket basket = loader.loadBasket();
        basket.add(article);
        save("BASKET", basket);
    }

    private void save(String tag, Object object){
        SharedPreferences sharedPref = activity.getSharedPreferences(tag,0);
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(object);
        prefsEditor.putString(object.getClass().getSimpleName(), json);
        prefsEditor.apply();
    }

    public void saveRequest(Request request) {
        save("REQUEST",request);
    }

    public void saveBasket(Basket basket) {
        save("BASKET",basket);
    }

    public void savePreviousFragmentMainActivity(PrevFrag frag) {
        save("PREVIOUS_FRAG",frag);
    }

    public void saveNotificationPref(NotificationPrefs notificationPrefs) {
        save("NOTIFICATION",notificationPrefs);
    }

    public void saveInterests(Request request) {
        save("INTEREST",request);
    }

    public void resetBasket() {
        save("BASKET",new Basket());
    }
}
