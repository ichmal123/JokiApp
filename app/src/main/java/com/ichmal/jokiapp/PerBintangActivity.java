package com.ichmal.jokiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PerBintangActivity extends AppCompatActivity {

    private EditText editNama, editEmail, editNoHp, editIdAkun, editPassAkun, editOrderBintang, editHarga;
    private Button btnCekHarga, btnOrder;
    private Spinner spinTier;
    private String[] tier = {"Grandmaster", "Epic", "Legend"};
    private DatabaseReference reff;
    private Order order;
    private String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_bintang);

        editNama = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editNoHp = findViewById(R.id.editPhone);
        editIdAkun = findViewById(R.id.editId);
        editPassAkun = findViewById(R.id.editPass);
        spinTier = findViewById(R.id.spinTier);
        ArrayAdapter aaSpinTier = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tier);
        spinTier.setAdapter(aaSpinTier);
        editOrderBintang = findViewById(R.id.editBintang);
        editHarga = findViewById(R.id.editHarga);
        editHarga.setVisibility(View.INVISIBLE);
        btnCekHarga = findViewById(R.id.btnCekHarga);
        btnOrder = findViewById(R.id.btnOrder);

        UserID = FirebaseAuth.getInstance().getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("Orders").child(UserID);
        order = new Order();

        btnCekHarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekHarga();
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderBintang = Integer.parseInt(editOrderBintang.getText().toString());
                int harga = Integer.parseInt(editHarga.getText().toString());
                order.setNama(editNama.getText().toString());
                order.setEmail(editEmail.getText().toString().trim());
                order.setPhone(editNoHp.getText().toString());
                order.setIdAkun(editIdAkun.getText().toString());
                order.setPassAkun(editPassAkun.getText().toString());
                order.setTierAkun(spinTier.getSelectedItem().toString());
                order.setOrderBintang(orderBintang);
                order.setHarga(harga);
                order.setStatus("Belum Selesai");
                order.setTipeOrder("Perbintang");
                reff.push().setValue(order);
                Toast.makeText(getApplicationContext(), "Order Berhasil", Toast.LENGTH_SHORT).show();

                empty();
            }
        });

        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        setupToolbar();
    }

    private void empty(){
        editNama.setText("");
        editEmail.setText("");
        editNoHp.setText("");
        editIdAkun.setText("");
        editPassAkun.setText("");
        editOrderBintang.setText("");
        editHarga.setText("");

        editNama.requestFocus();
    }

    private void cekHarga(){
        editHarga.setVisibility(View.VISIBLE);
        String tierAkun = spinTier.getSelectedItem().toString();
        int orderBintang = Integer.parseInt(editOrderBintang.getText().toString());
        int harga;

        if (tierAkun.equals("Grandmaster")){
            harga = orderBintang * 4000;
            editHarga.setText(String.valueOf(harga));
        } else if (tierAkun.equals("Epic")){
            harga = orderBintang * 6000;
            editHarga.setText(String.valueOf(harga));
        } else if (tierAkun.equals("Legend")){
            harga = orderBintang * 8000;
            editHarga.setText(String.valueOf(harga));
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbPerBintang);
        toolbar.setTitle("Menu Order Joki Perbintang");
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