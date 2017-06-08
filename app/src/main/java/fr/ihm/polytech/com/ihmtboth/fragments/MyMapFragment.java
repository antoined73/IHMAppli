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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.ihm.polytech.com.ihmtboth.R;
import fr.ihm.polytech.com.ihmtboth.activities.MainActivity;
import fr.ihm.polytech.com.ihmtboth.model.DayE;

/**
 * Created by Antoine on 5/12/2017.
 */

public class MyMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LinearLayout scheduleContainer;

    public static Fragment newInstance() {
        return new MyMapFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toolbar toolbar = ((MainActivity) getActivity()).getToolBar();
        toolbar.getMenu().clear();//inflateMenu(R.menu.empty_menu);
        return inflater.inflate(R.layout.contact_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // retrieve text and color from bundle or savedInstanceState
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        scheduleContainer = (LinearLayout) view.findViewById(R.id.schedule_container);
        initSchedules();
    }

    private void initSchedules() {
        for (DayE d : DayE.values()){
            addSchedule(d);
        }
    }

    private void addSchedule(DayE d) {
        Fragment frag = ScheduleFragment.newInstance(d);
        if (frag != null) {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.add(R.id.schedule_container, frag, frag.getTag());
            ft.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sophia = new LatLng(43.6163539, 7.055221800000027);
        mMap.addMarker(new MarkerOptions().position(sophia).title("Marker in Sophia Antipolis"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sophia, 12.0f));
    }
}