package com.example.todoappwithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class viewactivity extends AppCompatActivity {
    private DatabaseReference mdatabase;
    Button update,delete;
    EditText title,description;
    String getTitle,getDescription;
    String titlesend,descend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewactivity);
        mdatabase = FirebaseDatabase.getInstance().getReference();
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        getTitle = getIntent().getStringExtra("title");
        title.setText(getTitle);
        getDescription = getIntent().getStringExtra("description");

        final String id = getIntent().getStringExtra("id");


        description.setText(getDescription);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateNotes(id);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(id);

            }
        });

    }
    private void UpdateNotes(String id) {
        titlesend = title.getText().toString();
        descend = description.getText().toString();
        Notes notes = new Notes(id,titlesend,descend);
        mdatabase.child("Notes").child(id).setValue(notes).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Notes updated",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                });
    }

    private void deleteNote(String id) {
        mdatabase.child("Notes").child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Note Deleted",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                });
    }
}
