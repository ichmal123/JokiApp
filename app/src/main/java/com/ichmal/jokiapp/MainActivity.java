package com.ichmal.jokiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogout = findViewById(R.id.out);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                Toast.makeText(getApplicationContext(), "Logout Berhasil", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void profileMenu(View v){
        Toast.makeText(getApplicationContext(), "Mohon maaf, sistem sedang dalam pengembangan.", Toast.LENGTH_LONG).show();
    }
    public void historyMenu(View v){
        Toast.makeText(getApplicationContext(), "Mohon maaf, sistem sedang dalam pengembangan.", Toast.LENGTH_LONG).show();
    }
    public void jokiMlMenu(View v){
        Toast.makeText(getApplicationContext(), "Mohon maaf, sistem sedang dalam pengembangan.", Toast.LENGTH_LONG).show();
    }
    public void comingSoon(View v){
        Toast.makeText(getApplicationContext(), "Coming Soon.", Toast.LENGTH_LONG).show();
    }
}