package com.example.bustracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class locationFinder extends AppCompatActivity implements LocationListener {

    private static final String TAG = "TAG";
    Button locButton;
    TextView locTextView;
    LocationManager locationManager;
    FirebaseFirestore firebaseFirestore;
    busDetails object;
    String[] city;
    String input,busNoIntent;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_finder);

        locButton = findViewById(R.id.button_location);
        locTextView = findViewById(R.id.textView_currentLocation);
        Intent i = getIntent();
        busNoIntent = i.getStringExtra("busno");
        firebaseFirestore = FirebaseFirestore.getInstance();

        if(ContextCompat.checkSelfPermission(locationFinder.this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(locationFinder.this,new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }

        locButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(locationFinder.this,StoreLocation.class);
                        i.putExtra("curr",input);
                        i.putExtra("busNo",busNoIntent);
                        startActivity(i);
                        finish();
                    }
                },10000);

                //finishAffinity();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLocation(){

        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,locationFinder.this);


    }
    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(locationFinder.this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try{
            Geocoder geocoder = new Geocoder(locationFinder.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address  = addresses.get(0).getAddressLine(0);
            city = address.split(",");
            locTextView.setText(city[1]+city[2]);
            input=city[1]+city[2];

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}