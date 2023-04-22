package com.example.bustracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText busNo,busRoute,busPoint1,busPoint2;
    Button button_enter;
    String no,route,point1,point2,currLocation;
    FirebaseFirestore firebaseFirestore;
//    busDetails obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Title Bar Modification
        getSupportActionBar().setTitle("TNSTC BUS TRACKING");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        busNo = findViewById(R.id.busnumber_editText);
        busRoute = findViewById(R.id.busroute_edittext);
        busPoint1 = findViewById(R.id.point1_edittext);
        busPoint2 = findViewById(R.id.point2_edittext);

        firebaseFirestore = FirebaseFirestore.getInstance();
//        obj = new busDetails();

        //Intent To Next Page
        button_enter = findViewById(R.id.busdetails_button);
        button_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fetching data from edit text boxes
                no = busNo.getText().toString();
                route = busRoute.getText().toString();
                point1 = busPoint1.getText().toString();
                point2 = busPoint2.getText().toString();

                if(TextUtils.isEmpty(no)){
                    busNo.setError("Please Enter The Bus Number...");
                } else if(TextUtils.isEmpty(route)){
                    busRoute.setError("Please Enter The Route Number...");
                } else if(TextUtils.isEmpty(point1)){
                    busPoint1.setError("Please Enter Point 1");
                } else if(TextUtils.isEmpty(point2)){
                    busPoint2.setError("Please Enter Point 2");
                } else{
                    addToFire(no,route,point1,point2,currLocation);
                    Intent i = new Intent(MainActivity.this,locationFinder.class);
                    i.putExtra("busno",no);
                    startActivity(i);
                }
            }
        });
    }
    private void addToFire(String Bus_Number, String Bus_Route, String Point_1, String Point_2, String currLocation){
        CollectionReference collectionReference = firebaseFirestore.collection("Buses");
        HashMap addData = new HashMap();
        addData.put("busNo",Bus_Number);
        addData.put("busRoute",Bus_Route);
        addData.put("bus_Point1",Point_1);
        addData.put("bus_Point2",Point_2);
        addData.put("currLocation"," ");
//        obj.setBusNo(Bus_Number);
//        obj.setBusRoute(Bus_Route);
//        obj.setBusPoint1(Point_1);
//        obj.setBusPoint2(Point_2);
//        obj.setCurrLocation(currLocation);

        collectionReference.add(addData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(MainActivity.this,"Document Successfully Added!",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(MainActivity.this, "Error...", Toast.LENGTH_SHORT).show();
            }
        });

    }
}