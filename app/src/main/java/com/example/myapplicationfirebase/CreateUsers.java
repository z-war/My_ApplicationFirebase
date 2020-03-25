package com.example.myapplicationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class CreateUsers extends AppCompatActivity {
    private FirebaseFirestore db ;
    private static String collectionName = "Users";
    private EditText documentidET , first_nameET , last_nameET , phoneET , emailET;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_users);
        init();
    }

    public void init()
    {
        try{
            db = FirebaseFirestore.getInstance();
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.please_wait_dialog_box);
            dialog.setCancelable(false);
            documentidET = findViewById(R.id.documentidET);
            first_nameET = findViewById(R.id.f_nET);
            last_nameET  = findViewById(R.id.l_nET);
            phoneET = findViewById(R.id.phoneET);
            emailET = findViewById(R.id.emailET);


        }catch (Exception e)
        {
            Toast.makeText(this, "Error In Intitializinf the objects"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void tomainmenu(View v)
    {
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);
    }

    public void addUsers(View v)
    {
        try{

            if(!documentidET.getText().toString().isEmpty() && !first_nameET.getText().toString().isEmpty()
            && !last_nameET.getText().toString().isEmpty() && !phoneET.getText().toString().isEmpty()
                    && !emailET.getText().toString().isEmpty()
            )
            {

                dialog.show();
                DocumentReference ref = db.collection(collectionName).document(documentidET.getText().toString());
                ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if(!documentSnapshot.exists())
                        {
                            Map<String , Object> mymap = new HashMap<>();
                            mymap.put("First Name",first_nameET.getText().toString());
                            mymap.put("Last Name",last_nameET.getText().toString());
                            mymap.put("Phone Number",phoneET.getText().toString());
                            mymap.put("Email" , emailET.getText().toString());
                            db.collection(collectionName).document(documentidET.getText().toString()).set(mymap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            dialog.dismiss();
                                            documentidET.setText("");
                                            phoneET.setText("");
                                            emailET.setText("");
                                            first_nameET.setText("");
                                            last_nameET.setText("");
                                            documentidET.requestFocus();
                                            Toast.makeText(CreateUsers.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(CreateUsers.this, "User Not Created SuccessFully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else
                        {

                            Toast.makeText(CreateUsers.this, "User Name Already exist Cannot create User", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }
                    }
                });

            }else
            {
                Toast.makeText(this, "Please Fill All The Fields ", Toast.LENGTH_SHORT).show();
            }


        }catch (Exception e)
        {
            Toast.makeText(this, "Adding Users "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
