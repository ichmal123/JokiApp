package com.ichmal.jokiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class JokiListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joki_list);

        setupToolbar();
    }

    public void tierGrandmaster(View v){
        Intent perBintang = new Intent(getApplicationContext(), PerBintangActivity.class);
        startActivity(perBintang);
    }

    public void tierEpic(View v){
        startActivity(new Intent(getApplicationContext(), PerPaketActivity.class));
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbListTier);
        toolbar.setTitle("Menu Tier Joki");
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