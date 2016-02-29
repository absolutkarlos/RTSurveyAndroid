package id_app.rt_survey.Tabs_Survey;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import id_app.rt_survey.R;

/**
 * Created by Carlos_Lopez on 2/8/16.
 */
public class Tab_Site extends Fragment implements

        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapClickListener,
        LocationListener,
        GoogleMap.OnMyLocationButtonClickListener

{

    private View view;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;

    private final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};

    private int curMapTypeIndex = 1;

    private TextView latitud;
    private TextView longitud;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.a_tab_site, container, false);
        Log.e("site", "oncreateview");
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        latitud = (TextView) view.findViewById(R.id.latitud);
        longitud = (TextView) view.findViewById(R.id.longitud);

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);

        }

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void initCamera(Location location) {

        CameraPosition position = CameraPosition.builder().target(new LatLng(location.getLatitude(),
                location.getLongitude())).zoom(16f).bearing(0.0f).tilt(0.0f).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), null);
        mMap.setMapType(MAP_TYPES[curMapTypeIndex]);
        mMap.setTrafficEnabled(true);

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("site", "onresume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("site", "ondestroy");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("site", "onstop");
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("site", "onstart");
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            latitud.setText("Latitud "+String.valueOf(mCurrentLocation.getLatitude()));
            longitud.setText("Longitud "+String.valueOf(mCurrentLocation.getLongitude()));
            initCamera( mCurrentLocation );
            return;
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public boolean onMyLocationButtonClick() {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            latitud.setText("Latitud "+String.valueOf(mCurrentLocation.getLatitude()));
            longitud.setText("Longitud "+String.valueOf(mCurrentLocation.getLongitude()));

            initCamera( mCurrentLocation );

        }

        return false;
    }

}
