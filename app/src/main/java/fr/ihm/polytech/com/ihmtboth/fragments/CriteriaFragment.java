package fr.ihm.polytech.com.ihmtboth.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import java.util.ArrayList;
import java.util.List;

import fr.ihm.polytech.com.ihmtboth.model.saves.Loader;
import fr.ihm.polytech.com.ihmtboth.R;
import fr.ihm.polytech.com.ihmtboth.model.Category;
import fr.ihm.polytech.com.ihmtboth.model.saves.Request;

/**
 * Created by Antoine on 5/11/2017.
 */

public class CriteriaFragment extends Fragment {

    private static final String ARG_COLOR = "arg_color";
    private static final String ARG_INTEREST = "arg_interest";


    private int mColor;
    private boolean interest;

    private View mContent;
    private LinearLayout mcategoryCBList;
    private Request request;
    private CrystalRangeSeekbar rangeSeekbar;

    public static Fragment newInstance(int color, boolean interest) {
        Fragment frag = new CriteriaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLOR, color);
        args.putBoolean(ARG_INTEREST, interest);
        frag.setArguments(args);
        return frag;
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

        // retrieve text and color from bundle or savedInstanceState
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            mColor = args.getInt(ARG_COLOR);
            interest = args.getBoolean(ARG_INTEREST);
//            request = (Request) args.getSerializable(ARG_REQUEST);
        } else {
            mColor = savedInstanceState.getInt(ARG_COLOR);
            interest = savedInstanceState.getBoolean(ARG_INTEREST);
//            request = (Request) savedInstanceState.getSerializable(ARG_REQUEST);
        }

        // initialize views
        mContent = view.findViewById(R.id.fragment_content);
        mcategoryCBList = (LinearLayout) view.findViewById(R.id.categoryCBList);

        // set text and background color
        if(interest) this.request = new Loader(getActivity()).loadInterest();
        else this.request = new Loader(getActivity()).loadRequest();

        mContent.setBackgroundColor(100);
        List<CheckBox> mcheckboxes = getCheckBoxes(mcategoryCBList);
        syncCheckBoxWithRequest(mcheckboxes);


        //SeekBar
        rangeSeekbar = (CrystalRangeSeekbar) view.findViewById(R.id.rangeSeekBar);

        // get min and max text view
        final TextView tvMin = (TextView) view.findViewById(R.id.textMin);
        final TextView tvMax = (TextView) view.findViewById(R.id.textMax);

        // set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText(String.valueOf(minValue));
                tvMax.setText(String.valueOf(maxValue));
            }
        });

        // set final value listener
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                request.setPrice(minValue.floatValue(),maxValue.floatValue());
            }
        });
    }

    private void syncCheckBoxWithRequest(List<CheckBox> mcheckboxes) {
        for (CheckBox c: mcheckboxes) {
            final Category category = Category.valueOf(c.getText().toString().toUpperCase());
            c.setChecked(request.isWanted(category));
            c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                     @Override
                     public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                         request.setAsWanted(category,isChecked);
                     }
                }
            );
        }
    }

    private List<CheckBox> getCheckBoxes(LinearLayout linearLayout) {
        List<CheckBox> list = new ArrayList<>();
        for(int i = 0; i < linearLayout.getChildCount(); i++){
            list.add((CheckBox) linearLayout.getChildAt(i));
        }
        return list;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(ARG_COLOR, mColor);
        super.onSaveInstanceState(outState);
    }

    public Request getNewRequest(){
        return request;
    }

}