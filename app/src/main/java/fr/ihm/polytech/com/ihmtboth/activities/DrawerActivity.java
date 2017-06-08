package fr.ihm.polytech.com.ihmtboth.activities;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.widget.CompoundButton;

import com.google.android.gms.maps.MapFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ToggleDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;

import fr.ihm.polytech.com.ihmtboth.DataBaseChangeListener;
import fr.ihm.polytech.com.ihmtboth.NewsDBHelper;
import fr.ihm.polytech.com.ihmtboth.fragments.MainFragment;
import fr.ihm.polytech.com.ihmtboth.model.Article;
import fr.ihm.polytech.com.ihmtboth.model.Category;
import fr.ihm.polytech.com.ihmtboth.model.Notifier;
import fr.ihm.polytech.com.ihmtboth.model.TypeE;
import fr.ihm.polytech.com.ihmtboth.model.saves.Loader;
import fr.ihm.polytech.com.ihmtboth.model.saves.NotificationPrefs;
import fr.ihm.polytech.com.ihmtboth.model.saves.PrevFrag;
import fr.ihm.polytech.com.ihmtboth.R;
import fr.ihm.polytech.com.ihmtboth.model.saves.Saver;
import fr.ihm.polytech.com.ihmtboth.fragments.BasketFragment;
import fr.ihm.polytech.com.ihmtboth.fragments.MyMapFragment;
import fr.ihm.polytech.com.ihmtboth.fragments.SearchFragment;

/**
 * Created by user on 26/04/2017.
 */

public abstract class DrawerActivity extends ToolBarActivity implements DataBaseChangeListener{

    private Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity activity = this;
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item0 = new PrimaryDrawerItem().withIdentifier(1).withName("Home").withIcon(new IconicsDrawable(this)
                .icon(MaterialDesignIconic.Icon.gmi_home)
                .color(Color.DKGRAY)
                .sizeDp(24));
        ToggleDrawerItem item1 = new ToggleDrawerItem().withIdentifier(1).withName(R.string.drawer_item_notifs).withIcon(new IconicsDrawable(this)
                .icon(MaterialDesignIconic.Icon.gmi_notifications)
                .color(Color.DKGRAY)
                .sizeDp(24));
        NotificationPrefs pref =  new Loader(activity).loadNotification();
        if(pref ==null){
            pref= new NotificationPrefs(false);
            new Saver(activity).saveNotificationPref(pref);
        }
        item1.setChecked(pref.isEnable());
        item1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
                new Saver(activity).saveNotificationPref(new NotificationPrefs(isChecked));
            }
        });
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_interests).withIcon(new IconicsDrawable(this)
                .icon(MaterialDesignIconic.Icon.gmi_mood)
                .color(Color.DKGRAY)
                .sizeDp(24));
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Contact").withIcon(new IconicsDrawable(this)
                .icon(MaterialDesignIconic.Icon.gmi_pin)
                .color(Color.DKGRAY)
                .sizeDp(24));
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName("Panier").withIcon(new IconicsDrawable(this)
                .icon(MaterialDesignIconic.Icon.gmi_shopping_cart)
                .color(Color.DKGRAY)
                .sizeDp(24));
        ToggleDrawerItem item5 = new ToggleDrawerItem().withIdentifier(5).withName("Créer un article").withIcon(new IconicsDrawable(this)
                .icon(MaterialDesignIconic.Icon.gmi_notifications)
                .color(Color.DKGRAY)
                .sizeDp(24));
        item5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
                NewsDBHelper bdd = new NewsDBHelper(activity);
                bdd.setListener((DrawerActivity) activity);
                try {
                    bdd.createDataBase();

                    bdd.openDataBase();
                    try {
                        bdd.addArticle(new Article(bdd.getAllArticles().size() + 1, "ArticleExample", "Ceci est un article génial !", TypeE.BOOK, Category.CADEAU, new URL("https://www.w3schools.com/css/img_fjords.jpg"), new Date(), 13.4f));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(getLogoRessourceId())
                .build();

        this.drawer = new DrawerBuilder().withActivity(this)
                .withToolbar(toolBar)
                .addDrawerItems(
                        item0,
                        item1,
                        item2,
                        item3,
                        item4,
                        item5)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position){
                            case 1:
                                frag = SearchFragment.newInstance();
                                if (frag != null) {
                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    ft.add(getFragmentContainer(), frag, frag.getTag());
                                    ft.commit();
                                }
                                drawer.closeDrawer();
                                toolBar.setTitle("Search");
                                new Saver(activity).savePreviousFragmentMainActivity(new PrevFrag(SearchFragment.class.getSimpleName()));
                                break;
                            case 2:
                                break;
                            case 3:
                                Intent myIntent = new Intent(getApplicationContext(), InterestsActivity.class);
                                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                                startActivityForResult(myIntent, 0);
                                break;
                            case 4:
                                frag = MyMapFragment.newInstance();
                                if (frag != null) {
                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    ft.add(getFragmentContainer(), frag, frag.getTag());
                                    ft.commit();
                                }
                                drawer.closeDrawer();
                                toolBar.setTitle("Map");
                                break;
                            case 5:
                                frag = BasketFragment.newInstance();
                                if (frag != null) {
                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    ft.add(getFragmentContainer(), frag, frag.getTag());
                                    ft.commit();
                                }
                                drawer.closeDrawer();
                                toolBar.setTitle("Panier");
                                new Saver(activity).savePreviousFragmentMainActivity(new PrevFrag(BasketFragment.class.getSimpleName()));
                                break;
                        }
                        return true;
                    }
                })
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .build();
    }

    @Override
    protected abstract int getLayoutResourceId();

    public abstract int getLogoRessourceId();

    @Override
    public void onDatabaseChanged(Article article) {
        new Notifier(this).pushNewArticleNotification(article);
    }
}
