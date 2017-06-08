package fr.ihm.polytech.com.ihmtboth.model;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import fr.ihm.polytech.com.ihmtboth.R;
import fr.ihm.polytech.com.ihmtboth.activities.MainActivity;
import fr.ihm.polytech.com.ihmtboth.model.saves.Loader;

/**
 * Created by Antoine on 5/16/2017.
 */

public class Notifier {
    private final Activity activity;

    public Notifier(Activity activity) {
        this.activity = activity;
    }

    public void pushNotification(String title, String content,int icon, Class activityToBegin){
        if(!new Loader(activity).loadNotification().isEnable()) return;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(activity)
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setContentText(content);
        Intent resultIntent = new Intent(activity, activityToBegin);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(activity);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }

    public void pushNewArticleNotification(Article article) {
        if(new Loader(activity).loadInterest().isWanted(article) && new Loader(activity).loadNotification().isEnable()) pushNotification("Nouvel article arrivé!","Venez vite voir notre nouvel article : "+article.getTitle(),R.drawable.ic_add_shopping_cart_24dp,MainActivity.class);
    }
}
