package com.example.bustracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.List;

public class StoreLocation extends AppCompatActivity {

    private static final String TAG = "TAG";
    String currLoc,busNo,id,s_loc,d_loc;
    FirebaseFirestore firebaseFirestore;
    TextView currentLocation,startingLocation,destinationLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_location);
        firebaseFirestore = FirebaseFirestore.getInstance();
        busDetails obj = new busDetails();
        Intent i = getIntent();
        currLoc=i.getStringExtra("curr");
        busNo=i.getStringExtra("busNo");
        currentLocation = findViewById(R.id.current_Point_field);
//        startingLocation = findViewById(R.id.starting_Point_field);
//        destinationLocation = findViewById(R.id.destination_Point_field);
        currentLocation.setText(currLoc);
//        s_loc = obj.getBusPoint1();
//        d_loc = obj.getBusPoint2();
        addToFire(currLoc);
        //Toast.makeText(StoreLocation.this, "id: ", Toast.LENGTH_SHORT).show();
//        startingLocation.setText(s_loc);
//        destinationLocation.setText(d_loc);

    }

    private void addToFire(String input) {
        CollectionReference collectionReference = firebaseFirestore.collection("Buses");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list){
                        busDetails inst =d.toObject(busDetails.class);
                        String fetchedBusNo = inst.getBusNo();
                        if(busNo.equals(fetchedBusNo)){
                            id=d.getId();
                            DocumentReference documentReference = collectionReference.document(id);
                            HashMap locUpdate = new HashMap();
                            locUpdate.put("currLocation",currLoc);
                            documentReference.update(locUpdate).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    Toast.makeText(StoreLocation.this, "Location updated to Firestore",Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(Exception e) {
                                    Log.w(TAG, "Error updating document", e);
                                }
                            });
                            Toast.makeText(StoreLocation.this, "ID fetched successfully"+id, Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "document id: ");

                        }

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StoreLocation.this, "Failed...", Toast.LENGTH_SHORT).show();
            }
        });

//        DocumentReference documentReference = collectionReference.document(id);
//        HashMap locUpdate = new HashMap();
//        locUpdate.put("currLocation",currLoc);
//        documentReference.update(locUpdate).addOnSuccessListener(new OnSuccessListener() {
//            @Override
//            public void onSuccess(Object o) {
//                Toast.makeText(StoreLocation.this, "Location updated to Firestore",Toast.LENGTH_SHORT).show();
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(Exception e) {
//                Log.w(TAG, "Error updating document", e);
//            }
//        });

    }
}