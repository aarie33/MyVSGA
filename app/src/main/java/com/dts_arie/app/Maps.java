package com.dts_arie.app;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Maps extends AppCompatActivity implements OnMapReadyCallback {

    Location currenlocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    Button btnCheckLocatioan;
    TextView txtKeluar, placeName;
    PlaceAutocompleteFragment autocompleteFragment;
    SupportMapFragment supportMapFragment;

    GoogleMap myMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().hide();

        btnCheckLocatioan = findViewById(R.id.btnCheckLocation);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        txtKeluar = findViewById(R.id.txt_keluar);

        fetchLastLocation();
        btnCheckLocatioan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchLastLocation();
            }
        });

        txtKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plogout();
            }
        });

        TextView txtnama = (TextView) findViewById(R.id.txtNamaMaps);
        SharedPreferences prefs = getSharedPreferences("BelajarPref", MODE_PRIVATE);
        String restoredText = prefs.getString("Key_Username", null);
        if (restoredText != null) {
            String name = prefs.getString("Key_Username", "");//"No name defined" is the default value.
            txtnama.setText(name);
        }

        supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.maps_frame);

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        placeName = findViewById(R.id.placeName);

        setupAutoCompleteFragment();
    }

    private void fetchLastLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currenlocation = location;
                    Toast.makeText(Maps.this, currenlocation.getLatitude()+", "+currenlocation.getLongitude(), Toast.LENGTH_SHORT).show();

                    supportMapFragment.getMapAsync(Maps.this);
                }
            }
        });
    }

    public void onMapReady(GoogleMap googleMap){
        LatLng latLng = new LatLng(currenlocation.getLatitude(), currenlocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Posisi Sekarang");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        googleMap.addMarker(markerOptions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }

    private void setupAutoCompleteFragment() {
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                placeName.setText(place.getName());

//                createMarker(place.getLatLng().latitude, place.getLatLng().longitude,
//                        place.getName().toString());

                Log.d("Lokasi", String.valueOf(place));
            }
            @Override
            public void onError(Status status) {
                placeName.setText(status.toString());
            }
        });
    }

    protected Marker createMarker(double latitude, double longitude, String title) {

        return myMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title));
    }

    public void Plogout(){
        Intent login = new Intent(Maps.this, Login.class);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("BelajarPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("Key_Username");
        editor.clear();
        editor.commit();
        startActivity(login);
        finish();
    }
}
