package com.ichmal.jokiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PerBintangActivity extends AppCompatActivity {

    private EditText editNama, editEmail, editNoHp, editIdAkun, editPassAkun, editOrderBintang, editHarga, editTotal;
    private Button btnCekHarga, btnOrder;
    private Spinner spinTier;
    private String[] tier = {"Grandmaster", "Epic", "Legend"};
    private DatabaseReference reff, reff_admin;
    private Order order;
    private String UserID, username;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Date date = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    private String formatDate = df.format(date);

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
        editTotal = findViewById(R.id.editTotal);
        btnCekHarga = findViewById(R.id.btnCekHarga);
        btnOrder = findViewById(R.id.btnOrder);

        editTotal.setEnabled(false);
        editHarga.setEnabled(false);

        UserID = FirebaseAuth.getInstance().getUid();
        username = user.getEmail();
        reff = FirebaseDatabase.getInstance().getReference().child("Orders");
        order = new Order();

        spinTier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        editHarga.setText("4000");
                        break;

                    case 1:
                        editHarga.setText("6000");
                        break;

                    case 2:
                        editHarga.setText("8000");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnCekHarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekTotal();
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = editNama.getText().toString();
                String email = editEmail.getText().toString();
                String phone = editNoHp.getText().toString();
                String idAkun = editIdAkun.getText().toString();
                String pass = editPassAkun.getText().toString();
                String total = editTotal.getText().toString();
                String bintang = editOrderBintang.getText().toString();
                if (nama.isEmpty()){
                    editNama.setError("Nama Tidak Boleh Kosong");
                }
                if (email.isEmpty()){
                    editEmail.setError("Email Tidak Boleh Kosong");
                }
                if (phone.isEmpty()){
                    editNoHp.setError("No HP Tidak Boleh Kosong");
                }
                if (phone.length() > 14){
                    editNoHp.setError("No Hp Tidak Valid");
                }
                if (idAkun.isEmpty()){
                    editIdAkun.setError("Id Akun Tidak Boleh Kosong");
                }
                if (pass.isEmpty()){
                    editPassAkun.setError("Password Akun Tidak Boleh Kosong");
                }
                if (pass.length() < 8){
                    editPassAkun.setError("Password Akun Harus Lebih dari 7 Karakter dan Angka");
                }
                if (bintang.isEmpty()){
                    editOrderBintang.setError("Order Bintang Tidak Boleh Kosong");
                }
                if (total.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Total Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }
                if (!nama.isEmpty() && !email.isEmpty() && !phone.isEmpty() && phone.length() <= 14 && !idAkun.isEmpty() && !pass.isEmpty() && pass.length() >= 8 && !total.isEmpty()){
                    prosesOrder();
                    Toast.makeText(getApplicationContext(), "Order Berhasil", Toast.LENGTH_SHORT).show();
                    empty();
                } else {
                    Toast.makeText(getApplicationContext(), "Cek Kembali Form", Toast.LENGTH_SHORT).show();
                }

            }
        });

        setupToolbar();
    }

    private  void prosesOrder(){
        int orderBintang = Integer.parseInt(editOrderBintang.getText().toString());
        int harga = Integer.parseInt(editHarga.getText().toString());
        int total = Integer.parseInt(editTotal.getText().toString());
        order.setUsername(username);
        order.setNama(editNama.getText().toString());
        order.setEmail(editEmail.getText().toString().trim());
        order.setPhone(editNoHp.getText().toString());
        order.setIdAkun(editIdAkun.getText().toString());
        order.setPassAkun(editPassAkun.getText().toString());
        order.setTierAkun(spinTier.getSelectedItem().toString());
        order.setHarga(harga);
        order.setOrderBintang(orderBintang);
        order.setTotal(total);
        order.setStatus("Belum Selesai");
        order.setTipeOrder("Perbintang");
        order.setTanggal(formatDate);
        order.setUserID(UserID);
        reff.push().setValue(order);
    }
    private void empty(){
        editNama.setText("");
        editEmail.setText("");
        editNoHp.setText("");
        editIdAkun.setText("");
        editPassAkun.setText("");
        editOrderBintang.setText("");
        editHarga.setText("");
        editTotal.setText("");

        editNama.requestFocus();
    }

    private void cekTotal(){
        String tierAkun = spinTier.getSelectedItem().toString();
        int orderBintang = Integer.parseInt(editOrderBintang.getText().toString());
        int harga;
        if (tierAkun.equals("Grandmaster")){
            harga = orderBintang * 4000;
            editTotal.setText(String.valueOf(harga));
        } else if (tierAkun.equals("Epic")){
            harga = orderBintang * 6000;
            editTotal.setText(String.valueOf(harga));
        } else if (tierAkun.equals("Legend")){
            harga = orderBintang * 8000;
            editTotal.setText(String.valueOf(harga));
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