package com.ichmal.jokiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PerPaketActivity extends AppCompatActivity {

    private EditText editNama, editEmail, editPhone, editIdAkun, editPass, editHarga, editTotal;
    private Spinner spinPaket, spinRole;
    private Button btnOrder, btnCekHarga;
    private String[] paket = {"Grandmaster - Epic", "Grandmaster - Legend", "Grandmaster - Mythic V",
            "Epic - Legend", "Epic - Mythic V", "Legend - Mythic IV", "Legend - Mythic Glory",
            "Mythic V(Belum Grading) - Mythic IV", "Mythic V(Belum Grading) - Mythic Glory"};
    private String[] role = {"Bebas", "Assassins", "Tank", "Marksman", "Support", "Mage", "Fighter"};
    private DatabaseReference reff, reff_admin;
    private Order order;
    private String UserID, usernameAccount;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Date date = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    private String formatDate = df.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_paket);

        editNama = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        editIdAkun = findViewById(R.id.editId);
        editPass = findViewById(R.id.editPass);
        editHarga = findViewById(R.id.editHarga);
        editTotal = findViewById(R.id.editTotal);
        spinPaket = findViewById(R.id.spinTier);
        ArrayAdapter aaSpinPaket = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, paket);
        spinPaket.setAdapter(aaSpinPaket);
        spinRole = findViewById(R.id.spinRole);
        ArrayAdapter aaSpinRole = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, role);
        spinRole.setAdapter(aaSpinRole);
        btnOrder = findViewById(R.id.btnOrder);
        btnCekHarga = findViewById(R.id.btnCek);

        editTotal.setEnabled(false);
        editHarga.setEnabled(false);

        UserID = FirebaseAuth.getInstance().getUid();
        usernameAccount = user.getEmail();
        reff = FirebaseDatabase.getInstance().getReference().child("Orders");
        order = new Order();

        spinPaket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        editHarga.setText("120000");
                        break;

                    case 1:
                        editHarga.setText("290000");
                        break;

                    case 2:
                        editHarga.setText("540000");
                        break;

                    case 3:
                        editHarga.setText("170000");
                        break;

                    case 4:
                        editHarga.setText("420000");
                        break;

                    case 5:
                        editHarga.setText("500000");
                        break;

                    case 6:
                        editHarga.setText("1500000");
                        break;

                    case 7:
                        editHarga.setText("270000");
                        break;

                    case 8:
                        editHarga.setText("1250000");
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
                cekHarga();
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = editNama.getText().toString();
                String email = editEmail.getText().toString();
                String phone = editPhone.getText().toString();
                String idAkun = editIdAkun.getText().toString();
                String pass = editPass.getText().toString();
                String total = editTotal.getText().toString();
                if (nama.isEmpty()){
                    editNama.setError("Nama Tidak Boleh Kosong");
                }
                if (email.isEmpty()){
                    editEmail.setError("Email Tidak Boleh Kosong");
                }
                if (phone.isEmpty()){
                    editPhone.setError("No HP Tidak Boleh Kosong");
                }
                if (phone.length() > 14){
                    editPhone.setError("No Hp tidak Valid");
                }
                if (idAkun.isEmpty()){
                    editIdAkun.setError("Id Akun Tidak Boleh Kosong");
                }
                if (pass.isEmpty()){
                    editPass.setError("Password Akun Tidak Boleh Kosong");
                }
                if (pass.length() < 8){
                    editPass.setError("Password Akun Harus Lebih dari 7 Karakter dan Angka");
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

    private void prosesOrder(){
        int harga = Integer.parseInt(editHarga.getText().toString());
        int total = Integer.parseInt(editTotal.getText().toString());
        order.setUsername(usernameAccount);
        order.setNama(editNama.getText().toString());
        order.setEmail(editEmail.getText().toString().trim());
        order.setPhone(editPhone.getText().toString());
        order.setIdAkun(editIdAkun.getText().toString());
        order.setPassAkun(editPass.getText().toString());
        order.setTierAkun(spinPaket.getSelectedItem().toString());
        order.setHarga(harga);
        order.setRole(spinRole.getSelectedItem().toString());
        order.setTotal(total);
        order.setTanggal(formatDate);
        order.setStatus("Belum Selesai");
        order.setTipeOrder("Paketan");
        order.setUserID(UserID);
        reff.push().setValue(order);
    }

    private void cekHarga(){
        String requestRole = spinRole.getSelectedItem().toString();
        int harga = Integer.parseInt(editHarga.getText().toString());
        int total;

        if (requestRole.equals("Bebas")){
            total = harga;
            editTotal.setText(String.valueOf(total));
        } else if (requestRole.equals("Assassins")){
            total = harga + 30000;
            editTotal.setText(String.valueOf(total));
        } else if (requestRole.equals("Tank")){
            total = harga + 50000;
            editTotal.setText(String.valueOf(total));
        } else if (requestRole.equals("Marksman")){
            total = harga + 30000;
            editTotal.setText(String.valueOf(total));
        } else if (requestRole.equals("Support")){
            total = harga + 50000;
            editTotal.setText(String.valueOf(total));
        } else if (requestRole.equals("Mage")){
            total = harga + 40000;
            editTotal.setText(String.valueOf(total));
        } else if (requestRole.equals("Fighter")){
            total = harga + 30000;
            editTotal.setText(String.valueOf(total));
        }
    }

    private void empty(){
        editNama.setText("");
        editEmail.setText("");
        editPhone.setText("");
        editIdAkun.setText("");
        editPass.setText("");
        editHarga.setText("");
        editTotal.setText("");

        editNama.requestFocus();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbPerPaket);
        toolbar.setTitle("Menu Order Joki Paketan");
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