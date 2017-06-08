package fr.ihm.polytech.com.ihmtboth.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import fr.ihm.polytech.com.ihmtboth.MyFilterListener;
import fr.ihm.polytech.com.ihmtboth.R;
import fr.ihm.polytech.com.ihmtboth.RecyclerAdapter;
import fr.ihm.polytech.com.ihmtboth.activities.MainActivity;
import fr.ihm.polytech.com.ihmtboth.model.Article;
import fr.ihm.polytech.com.ihmtboth.model.DayE;

/**
 * Created by Antoine on 5/17/2017.
 */

public class FilterFragment extends Fragment implements MyFilterListener {

    private TextView filterText;
    private RecyclerAdapter recyclerAdapter;
    private int totalNumber;
    private List<Article> filteredList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.filter_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        filterText = (TextView) view.findViewById(R.id.filter);
        filterText.setText(filteredList.size()+" articles trouvés sur "+totalNumber);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    public void setFilterText(String filterText) {
        this.filterText.setText(filterText);
    }

    public void setRecyclerAdapter(RecyclerAdapter recyclerAdapter) {
        this.recyclerAdapter = recyclerAdapter;
    }

    @Override
    public void onContentChange(List<Article> newArticleList) {
        this.filteredList = newArticleList;
        setFilterText(filteredList.size()+" articles trouvés sur "+totalNumber);
    }

    @Override
    public void initialise(List<Article> initialArticleList, List<Article> filteredArticleList) {
        this.totalNumber = initialArticleList.size();
        this.filteredList = filteredArticleList;
    }
}