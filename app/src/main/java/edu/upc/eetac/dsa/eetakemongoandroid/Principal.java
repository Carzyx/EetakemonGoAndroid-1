package edu.upc.eetac.dsa.eetakemongoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
//
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
//
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetackemon;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.User;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback,GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    private Marker marker;
    private List<Marker> markers;
    private User user;
    private List<Eetackemon>eetackemonList=new ArrayList<Eetackemon>();
    double lat = 41.275603;
    double lon = 1.986584;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.eetakedex) {
            Intent intent=new Intent(Principal.this,Eetakedex.class);
            //intent.putExtra("Name and pass",string);
            startActivity(intent);
        } else if (id == R.id.pelea) {
            Toast.makeText(this,"Esta opcion no esta disponible aun",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.settings) {
            Toast.makeText(this,"Esta opcion no esta disponible aun",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.exit) {
            Toast.makeText(this,"Hasta otra",Toast.LENGTH_SHORT).show();
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
    private void llenar(){
        user=new User("Nacho","1234");
        //View v=View.inflate()
        setContentView(R.layout.nav_header_principal);
        TextView nombre=(TextView)findViewById(R.id.nombre);
        nombre.setText(user.getName());
    }
    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            Toast.makeText(this,"No tienes permisos para ubicarte",Toast.LENGTH_SHORT).show();
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,0,locationListener);
    }
    private void agregarMarcador(double lat, double lon) {
        LatLng coordenadas = new LatLng(lat, lon);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas,18);
        if (marker != null) marker.remove();
        marker = mMap.addMarker(new MarkerOptions().position(coordenadas).title("mi posicion").icon(BitmapDescriptorFactory.fromResource(R.drawable.personaje)));
        mMap.animateCamera(miUbicacion);
    }

    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            agregarMarcador(lat, lon);
            añadirEetakemon();
        } else Toast.makeText(this, "No se ha encontrado la ubicacion", Toast.LENGTH_SHORT).show();
    }
    private void añadirEetakemon(){
        //mMap.clear();
        List<LatLng>listamarkers=new ArrayList<LatLng>();
        eetackemonList=new ArrayList<Eetackemon>();
        markers=new ArrayList<Marker>();
        Eetackemon eetackemon=new Eetackemon("eetakemon1",2,3,EetakemonType.AIRE,"sda","fghjk");
        eetackemonList.add(eetackemon);
        LatLng cordenadas=new LatLng(41.275603,1.986584);
        listamarkers.add(cordenadas);
        cordenadas=new LatLng(41.275875,1.986381);
        eetackemon=new Eetackemon("eetakemon",2,3,EetakemonType.AIRE,"sda","fghjk");
        eetackemonList.add(eetackemon);
        listamarkers.add(cordenadas);
        for(int i=0;i<listamarkers.size();i++){
            Marker marker1=mMap.addMarker(new MarkerOptions().position(listamarkers.get(i)).title(eetackemonList.get(i).getName()));
            markers.add(marker1);
        }
    }
    LocationListener locationListener =new LocationListener() {
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
    protected void onActivityResult(int requestCode,int resultCode,Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        if(requestCode==RESULT_OK)
            Toast.makeText(this,"Capturado",Toast.LENGTH_SHORT);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng mi=this.marker.getPosition();
        LatLng click=marker.getPosition();
        if((mi.longitude!=click.longitude)&&(mi.latitude!=click.latitude)){
            Eetackemon eetackemon=new Eetackemon();
            for(int i=0;i<markers.size();i++){
                if(eetackemonList.get(i).getName().equals(marker.getTitle()))
                    eetackemon=eetackemonList.get(i);
            }
        Intent intent=new Intent(getApplicationContext(),Captura.class);
        intent.putExtra("Eetakemon",(Serializable)eetackemon);
        startActivityForResult(intent,100);
        }
        return false;
    }
}
