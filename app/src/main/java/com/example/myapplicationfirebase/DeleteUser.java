package com.example.myapplicationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.PrivateKey;

public class DeleteUser extends AppCompatActivity {
    private FirebaseFirestore db;
    private static String colname = "Users";
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
        try {
            dialog = new Dialog(this);

            dialog.setContentView(R.layout.please_wait_dialog_box);
            dialog.setCancelable(false);


        }catch (Exception e)
        {

        }

    }

    public  void deleteAll(View v)
    {
        try {

            db = FirebaseFirestore.getInstance();
            dialog.show();
            db.collection(colname).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful())
                    {
                        for (QueryDocumentSnapshot doc : task.getResult())
                        {
                            db.collection(colname).document(doc.getId()).delete();
                        }
                        dialog.dismiss();
                        Toast.makeText(DeleteUser.this, "All Users Deleted", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        dialog.dismiss();
                        Toast.makeText(DeleteUser.this, "No User Exists", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e)
        {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
