package comp.ofertapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONArray;
import org.json.JSONObject;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_CYAN;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    Toolbar toolbar;
    DrawerLayout dl;
    NavigationView nav;
    JSONObject o = new JSONObject();
    JSONObject o1 = new JSONObject();
    JSONObject o2 = new JSONObject();
    JSONObject o3 = new JSONObject();
    JSONObject o4 = new JSONObject();
    JSONArray a = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        try {
            //Bistecca1
            o.put("lat", -12.110692900);
            o.put("long", -76.987740400);
            o.put("nombre", "Bistecca");
            o.put("direccion", "Av. Primavera 545,Lima");
            a.put(0, o);

            //Bistecca2
            o1.put("lat", -12.106644700);
            o1.put("long", -77.037124100);
            o1.put("nombre", "Bistecca");
            o1.put("direccion", "Av. Los Conquistadores 1048, San Isidro");
            a.put(1, o1);

            //Westin
            o2.put("lat", -12.092945300);
            o2.put("long", -77.024508100);
            o2.put("nombre", "Westin");
            o2.put("direccion", "Calle Las Begonias 450, San Isidro");
            a.put(2, o2);

            //H&M
            o3.put("lat", -12.0858458);
            o3.put("long", -76.977555);
            o3.put("nombre", "H&M");
            o3.put("direccion", "Av. Javier Prado Este 10");
            a.put(3, o3);

            //Cineplamnet
            o4.put("lat", -12.0895686);
            o4.put("long", -77.0073266);
            o4.put("nombre", "Cineplanet");
            o4.put("direccion", "Ucello, San Borja");
            a.put(4, o4);
        } catch (Exception e) {

        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_map);
        nav = (NavigationView) findViewById(R.id.navigation);
        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapsActivity.this, InicioActivity.class);
                startActivity(i);
                MapsActivity.this.finish();
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1000, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Toast.makeText(MapsActivity.this, "Ubicando...", Toast.LENGTH_LONG).show();
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                mMap.animateCamera(cameraUpdate);
                try {
                    for (int i = 0; i < a.length(); i++) {
                        LatLng l = new LatLng(a.getJSONObject(i).getDouble("lat"), a.getJSONObject(i).getDouble("long"));

                        mMap.addMarker(new MarkerOptions().position(l)
                                .icon(BitmapDescriptorFactory.defaultMarker(HUE_CYAN))
                                .snippet(a.getJSONObject(i).getString("direccion"))
                                .title(a.getJSONObject(i).getString("nombre").toUpperCase()));

                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                createRadioListDialog(marker.getTitle(),marker.getSnippet());
                                return true;
                            }
                        });
                    }
                } catch (Exception e) {

                }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

                Toast.makeText(MapsActivity.this, "Active su GPS porfavor", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        GoogleMapOptions option = new GoogleMapOptions().liteMode(true);
        option.mapToolbarEnabled(true);
        option.mapType(GoogleMap.MAP_TYPE_NORMAL)
                .rotateGesturesEnabled(true)
                .ambientEnabled(true);

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.tag_map)));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        UiSettings u = mMap.getUiSettings();
        u.setZoomControlsEnabled(true);
        u.setMyLocationButtonEnabled(true);

    }

    public AlertDialog createRadioListDialog(final String titulo,final String direccion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);


        builder.setTitle(titulo).setMessage(direccion);

        return builder.show();
    }
}
