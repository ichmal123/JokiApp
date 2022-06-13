package com.ichmal.jokiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText editNewPassword, editConfirmNewPassword;
    private Button btnUpdate;
    private ProgressDialog dialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(ChangePasswordActivity.this);
        dialog.setTitle("Loading");
        dialog.setMessage("Silahkan tunggu");
        dialog.setCancelable(false);

        editNewPassword = findViewById(R.id.passwordNew);
        editConfirmNewPassword = findViewById(R.id.passwordNewConfirm);
        btnUpdate = findViewById(R.id.btnUpdatePassword);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = editNewPassword.getText().toString().trim();
                String newConfirmPassword = editConfirmNewPassword.getText().toString().trim();
                FirebaseUser user = auth.getCurrentUser();
                if (user != null){
                    if (editNewPassword.length() < 6){
                        editNewPassword.setError("New Password Harus Lebih dari 6 Character");
                    }
                    if (TextUtils.isEmpty(newPassword)){
                        editNewPassword.setError("New Password Tidak Boleh Kosong");
                    }
                    if (editConfirmNewPassword.length() < 6){
                        editConfirmNewPassword.setError("New Confirm Password Harus Lebih dari 6 Character");
                    }
                    if (TextUtils.isEmpty(newConfirmPassword)){
                        editNewPassword.setError("New Confirm Password Tidak Boleh Kosong");
                    }
                    if (editNewPassword.length() > 6 && editConfirmNewPassword.length() > 6){
                        if (editNewPassword.getText().toString().equals(editConfirmNewPassword.getText().toString())){
                            changePassword(newPassword);
                        } else {
                            Toast.makeText(getApplicationContext(), "New Password dan Confirm New Password Harus Sama", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        setupToolbar();
    }

    private void changePassword(String passowrd){
        dialog.show();
        FirebaseUser user = auth.getCurrentUser();
        user.updatePassword(passowrd).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update Password Sukses", Toast.LENGTH_SHORT).show();
                    editNewPassword.setText("");
                    editConfirmNewPassword.setText("");
                    editNewPassword.requestFocus();
                } else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update Password Gagal Karena Password yang Lama dan Baru sama", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbChangePassword);
        toolbar.setTitle("Change Password User");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}