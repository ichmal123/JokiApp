package com.ichmal.jokiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ResetPassword extends AppCompatActivity {

    private EditText editReset;
    private Button btnReset, btnKeLogin;
    private ProgressDialog dialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(ResetPassword.this);
        dialog.setTitle("Loading");
        dialog.setMessage("Silahkan tunggu");
        dialog.setCancelable(false);

        editReset = findViewById(R.id.editEmailForget);
        btnKeLogin = findViewById(R.id.keLogin);
        btnReset = findViewById(R.id.btnReset);

        btnKeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }
    private void resetPassword(){
        String email = editReset.getText().toString().trim();

        if (email.isEmpty()){
            editReset.setError("Email tidak boleh kosong");
            editReset.requestFocus();
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editReset.setError("Masukan email yang valid");
            editReset.requestFocus();
        }

        dialog.show();
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Cek Email anda untuk mereset password", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else{
                    Toast.makeText(getApplicationContext(), "Coba lagi sesuatu sedang tidak benar", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }
}