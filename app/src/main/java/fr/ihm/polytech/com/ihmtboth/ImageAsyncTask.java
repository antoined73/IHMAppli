package fr.ihm.polytech.com.ihmtboth;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static android.R.attr.name;

/**
 * Created by user on 29/03/2017.
 */

public class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private final ImageView imageView;

    public ImageAsyncTask(ImageView imageView) {
        super();
        this.imageView = imageView;
        Bitmap defaultImage = BitmapFactory.decodeResource(imageView.getContext().getResources(),
                R.drawable.blur);
        imageView.setImageBitmap(defaultImage);
    }

    @Override
    protected Bitmap doInBackground(String... names) {
        // Read from a URL
        URL url = null;
        InputStream inputStream = null;
        try {
            url = new URL(names[0]);
            inputStream = url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(inputStream);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
