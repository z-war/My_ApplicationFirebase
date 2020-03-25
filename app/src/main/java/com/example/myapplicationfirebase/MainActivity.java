package com.example.myapplicationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private static String hotel_details = "hotel_details";
    private FirebaseFirestore firestore;
    private TextView textViewshowdata;
    private Dialog dialog;
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            firestore = FirebaseFirestore.getInstance();
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.please_wait_dialog_box);
            dialog.setCancelable(false);


        } catch (Exception e) {
            Toast.makeText(this, "Error Connecting FireBase" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void adddatatodatabase(View v) {
        try {
            dialog.show();
            Map<String, Object> mymap = new HashMap<>();
            mymap.put("Rooms", 230);
            mymap.put("bookedrooms", 100);
            mymap.put("Location", "Mall Road");
            firestore.collection(hotel_details).document("avariHotel").set(mymap).addOnSuccessListener(
                    new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, "Values Added Successfully", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
            ).addOnFailureListener(
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Values Not Added", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
            );


        } catch (Exception e) {
            Toast.makeText(this, "add data to database" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void GetValues(View v) {
        try {
            Toast.makeText(this, "Get Values Clicked", Toast.LENGTH_SHORT).show();
            dialog.show();


            documentReference = firestore.collection(hotel_details).document("avariHotel");
            documentReference.get().addOnSuccessListener(
                    new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            dialog.dismiss();


                            if (!documentSnapshot.exists()) {
                                Toast.makeText(MainActivity.this, "No data Returned", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Toast.makeText(MainActivity.this, "Data Returned", Toast.LENGTH_SHORT).show();
                                String name = documentSnapshot.getId();
                                String roomno = documentSnapshot.get("Rooms").toString();

                                String res = name + " room no "+roomno;

                                textViewshowdata.setText(res);

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                 dialog.dismiss();
                    Toast.makeText(MainActivity.this, "Fialed to get data "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Get Values From DataBase" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void tocreateactivity(View v)
    {
        Intent intent = new Intent(getBaseContext(),CreateUsers.class);
        startActivity(intent);
    }
    public void toupdateactivity(View v)
    {
        Intent intent = new Intent(getBaseContext(),Update_users.class);
        startActivity(intent);
    }
    public void toread(View v)
    {
        Intent intent = new Intent(getBaseContext() , ShowUsers.class);
        startActivity(intent);
    }
    public void todel(View v)
    {
        Intent intent = new Intent(getBaseContext() , DeleteUser.class);
        startActivity(intent);
    }
}
