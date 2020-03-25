package com.example.myapplicationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Update_users extends AppCompatActivity {
    private static String collectionname = "Users";
    private FirebaseFirestore db;
    private EditText DocId, firstnameET, lastnameET, phoneET, emailET;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_users);
        init();
    }

    public void init() {
        try {
            db = FirebaseFirestore.getInstance();
            DocId = findViewById(R.id.usernameET);
            firstnameET = findViewById(R.id.firstnameET);
            lastnameET = findViewById(R.id.lastnameET);
            emailET = findViewById(R.id.emailETU);
            phoneET = findViewById(R.id.phoneETU);
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.please_wait_dialog_box);
            dialog.setCancelable(false);

        } catch (Exception e) {
            Toast.makeText(this, "Initializing Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void gotomain(View v) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    public void UpdateUser(View v) {
        try {
            if (!DocId.getText().toString().isEmpty()) {
                dialog.show();
                Map<String , Object> map = new HashMap<>();
                map.put("First Name" , firstnameET.getText().toString());
                map.put("Last Name" , lastnameET.getText().toString());
                map.put("Email" , emailET.getText().toString());
                map.put("Phone Number" , phoneET.getText().toString());
                DocumentReference documentReference = db.collection(collectionname).document(DocId.getText().toString());

                documentReference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dialog.dismiss();
                        Toast.makeText(Update_users.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(Update_users.this, "User Details Not Updated"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Toast.makeText(this, "Please Enter User Name To update", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            Toast.makeText(this, "Error in Updating " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
