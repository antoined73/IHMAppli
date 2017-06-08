package fr.ihm.polytech.com.ihmtboth.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import fr.ihm.polytech.com.ihmtboth.model.saves.Loader;
import fr.ihm.polytech.com.ihmtboth.R;
import fr.ihm.polytech.com.ihmtboth.model.Category;
import fr.ihm.polytech.com.ihmtboth.model.saves.Request;

/**
 * Created by Antoine on 5/15/2017.
 */

public class InterestFragment extends Fragment {

    private View mContent;
    private LinearLayout mcategoryCBList;
    private Request request;

    public static Fragment newInstance() {
        Fragment frag = new InterestFragment();
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        menu.clear();
        inflater.inflate(R.menu.menu_criterias,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.criterias_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // initialize views
        mcategoryCBList = (LinearLayout) view.findViewById(R.id.categoryCBList);

        // set text and background color
        this.request = new Loader(getActivity()).loadRequest();

        List<CheckBox> mcheckboxes = getCheckBoxes(mcategoryCBList);
        syncCheckBoxWithRequest(mcheckboxes);
    }

    private void syncCheckBoxWithRequest(List<CheckBox> mcheckboxes) {
        for (CheckBox c : mcheckboxes) {
            final Category category = Category.valueOf(c.getText().toString().toUpperCase());
            c.setChecked(request.isWanted(category));
            c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                 request.setAsWanted(category, isChecked);
                                             }
                                         }
            );
        }
    }

    private List<CheckBox> getCheckBoxes(LinearLayout linearLayout) {
        List<CheckBox> list = new ArrayList<>();
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            list.add((CheckBox) linearLayout.getChildAt(i));
        }
        return list;
    }

    public Request getNewRequest() {
        return request;
    }
}

