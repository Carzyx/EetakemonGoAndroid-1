package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import edu.upc.eetac.dsa.eetakemongoandroid.JSONservice;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Markers;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Party;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.User;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.ValuesForActivites;
import edu.upc.eetac.dsa.eetakemongoandroid.R;
import edu.upc.eetac.dsa.eetakemongoandroid.View.ViewProfile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION=0;
    private GoogleMap mMap;
    private Marker marker;
    ImageView mifoto;
    private String token;
    private List<Markers> markers;
    private User user;
    double lat = 41.275603;
    double lon = 1.986584;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_principal);

        user = (User) getIntent().getSerializableExtra("User");
        token = getIntent().getStringExtra("Token");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Principal.this, ViewProfile.class);
                intent.putExtra("User",(Serializable) user);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        datos();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.capturados) {
            Intent intent = new Intent(Principal.this, EetakemonsCatched.class);
            intent.putExtra("User", (Serializable) user);
            startActivity(intent);
        } else if (id == R.id.eetakedex) {
            goToEetakedex();
        } else if (id == R.id.pelea) {
            Intent intent = new Intent(Principal.this, SelectEetackemon.class);
            intent.putExtra("User", (Serializable) user);
            startActivityForResult(intent, ValuesForActivites.StartFight.getValue());
        } else if (id == R.id.exit){
            Toast.makeText(this, "Hasta otra", Toast.LENGTH_SHORT).show();
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        miUbicacion();
        mMap.setOnMarkerClickListener(this);
    }

    private void datos() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView name = (TextView)header.findViewById(R.id.nombre);
        mifoto=(ImageView)header.findViewById(R.id.mifoto);
        Picasso.with(this).load(JSONservice.URL+user.getImage()).into(mifoto);
        name.setText(user.getName());
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        Picasso.with(this).load(JSONservice.URL+user.getImage()).into(fab);

    }

    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            Toast.makeText(this, "No tienes permisos para ubicarte", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, locationListener);
    }
    private void agregarMarcador(double lat, double lon) {
        LatLng coordenadas = new LatLng(lat, lon);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 18);
        if (marker != null) marker.remove();{
            try {
                URL url = new URL(JSONservice.URL+user.getImage());
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                marker = mMap.addMarker(new MarkerOptions().position(coordenadas).title("mi posicion").icon(BitmapDescriptorFactory.fromBitmap(bmp)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mMap.animateCamera(miUbicacion);
    }

    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            agregarMarcadores();
        } else {
            Toast.makeText(this, "No se ha encontrado la ubicación", Toast.LENGTH_SHORT).show();
        }

    }

    private void agregarMarcadores() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(JSONservice.URL).addConverterFactory(GsonConverterFactory.create()).build();
        JSONservice service = retrofit.create(JSONservice.class);
        mMap.clear();
        agregarMarcador(lat, lon);
        Markers mar = new Markers();
        mar.setLat(lat);
        mar.setLng(lon);
        Call<List<Markers>> callMarkers = service.miPos(mar, token);
        callMarkers.enqueue(new Callback<List<Markers>>() {
            @Override
            public void onResponse(Call<List<Markers>> call, Response<List<Markers>> response) {
                markers = response.body();
                token=response.headers().get("authoritzation");
                for (int i = 0; i < markers.size(); i++) {
                    try {
                        URL url = new URL(JSONservice.URL+"profile/brock.png");
                        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        Marker marker1 = mMap.addMarker(new MarkerOptions().position(new LatLng(markers.get(i).getLat(), markers.get(i).getLng())).title(markers.get(i).getEetakemon().getName()).icon(BitmapDescriptorFactory.fromBitmap(bmp)));
                        //Marker marker1 = mMap.addMarker(new MarkerOptions().position(new LatLng(markers.get(i).getLat(), markers.get(i).getLng())).title(markers.get(i).getEetakemon().getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Markers>> call, Throwable t) {

            }
        });
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == ValuesForActivites.StartCapture.getValue()) {
            if (resultCode==ValuesForActivites.SelectEetackemon.getValue()){
                Intent intent1 = new Intent(getApplicationContext(), CatchEetakemon.class);
                Eetakemon suEetakemon = (Eetakemon) intent.getSerializableExtra("Eetakemon");
                Eetakemon miEetakemon = (Eetakemon) intent.getSerializableExtra("miEetakemon");
                intent1.putExtra("Eetakemon", (Serializable) suEetakemon);
                intent1.putExtra("miEetakemon", (Serializable) miEetakemon);
                intent1.putExtra("User", (Serializable) user);
                startActivityForResult(intent1, ValuesForActivites.StartCapture.getValue());
            }
            else if(resultCode == ValuesForActivites.CaptureOk.getValue()){
                Retrofit retrofit = new Retrofit.Builder().baseUrl(JSONservice.URL).addConverterFactory(GsonConverterFactory.create()).build();
                JSONservice service = retrofit.create(JSONservice.class);
                User user1=user;
                List<Eetakemon>list = new ArrayList<Eetakemon>();
                Eetakemon suEetakemon = (Eetakemon) intent.getSerializableExtra("Eetakemon");
                list.add(suEetakemon);
                user1.setEetakemons(list);
                final Call<User> getEetakemons = service.addAEetakemonsToUser(user1,token);
                getEetakemons.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        user=response.body();
                        token=response.headers().get("authoritzation");
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }

        } else if (requestCode == ValuesForActivites.StartFight.getValue()) {
            if(resultCode==ValuesForActivites.FinishFight.getValue()){

            }else{
                Intent intent1=new Intent(Principal.this,Fight.class);
                Eetakemon miEetakemon = (Eetakemon) intent.getSerializableExtra("miEetakemon");
                List<Eetakemon>list=new ArrayList<>();
                list.add(miEetakemon);
                User user1=user;
                user1.setEetakemons(list);
                intent1.putExtra("Token", token);
                intent1.putExtra("User", (Serializable) user1);
                startActivity(intent1);}
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng mi = this.marker.getPosition();
        LatLng click = marker.getPosition();
        if ((mi.longitude != click.longitude) && (mi.latitude != click.latitude)) {
            Eetakemon eetakemon = new Eetakemon();
            for (int i = 0; i < markers.size(); i++) {
                if (markers.get(i).getEetakemon().getName().equals(marker.getTitle()))
                    eetakemon = markers.get(i).getEetakemon();
            }
            Intent intent = new Intent(getApplicationContext(), SelectEetackemon.class);
            intent.putExtra("Eetakemon", (Serializable) eetakemon);
            intent.putExtra("User", (Serializable) user);
            startActivityForResult(intent, ValuesForActivites.StartCapture.getValue());
            marker.remove();
        }
        return false;
    }

    private void goToEetakedex()
    {
        Intent intent = new Intent(Principal.this, Eetakedex.class);
        intent.putExtra("Eetakemons", (Serializable) user);
        intent.putExtra("Token", token);
        startActivity(intent);
    }

}
