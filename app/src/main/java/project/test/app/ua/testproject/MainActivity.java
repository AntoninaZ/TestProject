package project.test.app.ua.testproject;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Random;

import project.test.app.ua.testproject.models.Location;
import project.test.app.ua.testproject.models.Order;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback{

    private static ProgressBar progressBar;
    private static SupportMapFragment mapFragment;
    private HttpClient httpClient;

    public static ArrayList<Order> ordersData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        displayData();
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private static void goneProgresBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void displayData() {
        httpClient = new HttpClient(getApplicationContext());
        ordersData = httpClient.getData();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51, 11), 5));

    }

    public static void drawRoutes(Location loc) {
        PolylineOptions polyLineOptions = null;
        mapFragment.getMap().addMarker(new MarkerOptions().position(loc.getDepartureLocation()))
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mapFragment.getMap().addMarker(new MarkerOptions().position(loc.getDestinationLocation()))
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        for (int i = 0; i < loc.getRoutes().size(); i++) {
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            polyLineOptions = new PolylineOptions().width(5).color(color).geodesic(true);
            polyLineOptions.addAll(loc.getRoutes().get(i));

        }
        mapFragment.getMap().addPolyline(polyLineOptions);
        goneProgresBar();
    }
}
