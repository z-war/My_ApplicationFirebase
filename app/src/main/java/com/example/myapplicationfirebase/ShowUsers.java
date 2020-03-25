package com.example.myapplicationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowUsers extends AppCompatActivity {
    private FirebaseFirestore db;
    private static String collectionName = "Users";
    String mylist;
    ArrayAdapter<String> adp;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);
        try{
           text = findViewById(R.id.tv);
            db = FirebaseFirestore.getInstance();
            db.collection(collectionName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful())
                    {

                        for(QueryDocumentSnapshot doc : task.getResult())
                        {
                            mylist += "\n User Name " + doc.getId();
                        }
                        text.setText(mylist);
                        Toast.makeText(ShowUsers.this, "Data Success", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(ShowUsers.this, "Data NOT Success", Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }
        catch (Exception e)
        {

        }
    }
}
