package fr.ihm.polytech.com.ihmtboth;

import android.icu.text.LocaleDisplayNames;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import fr.ihm.polytech.com.ihmtboth.model.Category;

/**
 * Created by user on 26/04/2017.
 */

public class ArticlePreviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView image_article;
    private TextView titleTextView;
    private TextView categoryTextView;
    public boolean isEmpty = false;
    private RecyclerViewClickListener listener;

    public ArticlePreviewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        image_article = (ImageView) itemView.findViewById(R.id.image_article);
        titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
//        dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
        categoryTextView = (TextView) itemView.findViewById(R.id.categoryTextView);
    }

    public void setTitleTextView(String title) {
        this.titleTextView.setText(title);
        isEmpty=false;
    }

    public void setDateTextView(Date date) {
//        this.dateTextView.setText(date.toString());
    }

    public void setCategoryTextView(Category category) {
        this.categoryTextView.setText(category.toString());
        isEmpty=false;
    }

    public void setVideoImage(String videoString) {
        if(videoString.contains("youtube.com")) {
            String id = videoString.split("/")[3].split("=")[1];
            new ImageAsyncTask(image_article).execute("http://img.youtube.com/vi/" + id + "/default.jpg");
            isEmpty=false;
//            play_logo.setVisibility(View.VISIBLE);
        }
    }

    public void setImage(String imageURL) {
        new ImageAsyncTask(image_article).execute(imageURL);
        isEmpty=false;
//        play_logo.setVisibility(View.INVISIBLE);
    }

    public void setEmpty() {
        image_article=null;
        titleTextView=null;
        categoryTextView=null;
        isEmpty = true;
    }

    @Override
    public void onClick(View v) {
        listener.recyclerViewListClicked(v,this.getLayoutPosition());
    }

    public void setListener(RecyclerViewClickListener listener) {
        this.listener = listener;
    }
}
