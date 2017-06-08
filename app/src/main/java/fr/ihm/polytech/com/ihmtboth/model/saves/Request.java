package fr.ihm.polytech.com.ihmtboth.model.saves;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import fr.ihm.polytech.com.ihmtboth.model.Article;
import fr.ihm.polytech.com.ihmtboth.model.Category;

/**
 * Created by Antoine on 5/11/2017.
 */

public class Request implements Serializable {
    Map<Category,Boolean> wantedCategories;

    public Request() {
        wantedCategories = new HashMap<>();
        for(Category c : Category.values()){
            wantedCategories.put(c,true);
        }
    }

    public boolean isWanted(Category category) {
        return wantedCategories.get(category);
    }

    public boolean isWanted(Article article) {
        return wantedCategories.get(article.getCategory());
    }

    public void setAsWanted(Category category, boolean isChecked) {
        wantedCategories.put(category,isChecked);
    }
}
