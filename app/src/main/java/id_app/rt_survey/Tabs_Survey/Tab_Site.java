package id_app.rt_survey.Tabs_Survey;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import id_app.rt_survey.R;

import static id_app.rt_survey.R.drawable.ic_circle;

/**
 * Created by Carlos_Lopez on 2/8/16.
 */
public class Tab_Site extends Fragment implements OnMapReadyCallback {

    private View view;
    private GoogleMap mMap;
    private FloatingActionButton actionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.tab_site,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(Tab_Site.this);
        ImageView icon = new ImageView(getActivity()); // Create an icon
        icon.setImageResource(R.drawable.ic_circle);
        actionButton = new FloatingActionButton
                .Builder(getActivity())
                .setContentView(icon)
                .build();
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng panama = new LatLng(8.983333,-79.516670);
        mMap.addMarker(new MarkerOptions().position(panama).title("RT_Survey"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(panama));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
