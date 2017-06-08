package fr.ihm.polytech.com.ihmtboth.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.ihm.polytech.com.ihmtboth.R;
import fr.ihm.polytech.com.ihmtboth.model.DayE;

/**
 * Created by Antoine on 5/15/2017.
 */

public class ScheduleFragment extends Fragment {

    private static final String ARG_DAY = "arg_day";

    private String mText;

    private View mContent;
    private TextView mDayTW;
    private TextView mMorningScheduleTW;
    private TextView mAfternoonScheduleTW;

    public static Fragment newInstance(DayE day) {
        Fragment frag = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DAY, day.name());
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.schedule_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // retrieve text and color from bundle or savedInstanceState
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            mText = args.getString(ARG_DAY);
        } else {
            mText = savedInstanceState.getString(ARG_DAY);
        }

        // initialize views
        mContent = view.findViewById(R.id.fragment_content);
        mDayTW = (TextView) view.findViewById(R.id.dayTW);

        // set text and background color
        mDayTW.setText(mText);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_DAY, mText);
        super.onSaveInstanceState(outState);
    }

}