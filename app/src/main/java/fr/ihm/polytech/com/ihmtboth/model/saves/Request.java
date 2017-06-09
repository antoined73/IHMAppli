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
    private float minPrice;
    private float maxPrice;

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
        boolean rightCategory = wantedCategories.get(article.getCategory());
        boolean upperMin = minPrice<=article.getPrice();
        boolean lowerMax = maxPrice>=article.getPrice();
        boolean isWanted = lowerMax && upperMin && rightCategory;
        return isWanted;
    }

    public void setAsWanted(Category category, boolean isChecked) {
        wantedCategories.put(category,isChecked);
    }

    public void setPrice(float min, float max) {
        this.minPrice = min;
        this.maxPrice = max;
    }

    public float getMinPrice() {
        return minPrice;
    }
}
