package fr.ihm.polytech.com.ihmtboth;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.ihm.polytech.com.ihmtboth.fragments.FilterFragment;
import fr.ihm.polytech.com.ihmtboth.model.Article;

/**
 * Created by user on 11/04/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RecyclerViewClickListener{

    private static final int EMPTY_VIEW = 10;
    private final List<Article> originalList;
    private ArticleClickListener listener;
    private List<Article> articleList;
    private MyFilterListener filterListener;

    @Override
    public void recyclerViewListClicked(View v, int position) {
        listener.articleClicked(articleList.get(position));
    }

    public void setFilterListener(MyFilterListener filterListener) {
        this.filterListener = filterListener;
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public RecyclerAdapter(List<Article> articleList, ArticleClickListener listener) {
        this.articleList = articleList;
        this.originalList = articleList;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == EMPTY_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
            ArticlePreviewHolder evh = new ArticlePreviewHolder(v);
            evh.setEmpty();
            return evh;
        }
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_preview2, parent, false);
        ArticlePreviewHolder nvh = new ArticlePreviewHolder(mView);
        nvh.setListener(this);
        return nvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {
        if (vh instanceof ArticlePreviewHolder) {
            ArticlePreviewHolder holder = (ArticlePreviewHolder) vh;
            if(!holder.isEmpty){
                Article article = articleList.get(position);
                holder.setTitleTextView(article.getTitle());
                holder.setCategoryTextView(article.getCategory());
                holder.setDateTextView(article.getDate());
                holder.setImage(String.valueOf(article.getUrl()));
            }
        }
    }
    @Override
    public int getItemCount() {
        return articleList.size() > 0 ? articleList.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (articleList.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }

    public void setFilter(List<Article> filter) {
        articleList = new ArrayList<>(filter);
        notifyDataSetChanged();
        filterListener.onContentChange(articleList);
    }

    public List<Article> getArticles() {
        return articleList;
    }

    public List<Article> getOriginalArticles() {
        return originalList;
    }
}
