package com.example.bottonnav;

import static com.example.bottonnav.R.id.estadotemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Dashboard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ToggleButton myToggle;
    TextView estadotemp;

    @SuppressLint("MissingInflatedId")
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        database = FirebaseDatabase.getInstance();
        myRef= database.getReference("ventilador");

        ToggleButton ventilador=(ToggleButton)findViewById(R.id.myToggle);
        ventilador.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    myRef.setValue(1);
                }else{
                    myRef.setValue(0);
                }
            }
        });






        estadotemp=(TextView) findViewById(R.id.estadotemp);
        myToggle=(ToggleButton) findViewById(R.id.myToggle);
        myToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId()== R.id.myToggle){
                    if (myToggle.isChecked()){
                        estadotemp.setText("ESTADO: ENCENDIDO");
                    }else{
                        estadotemp .setText("ESTADO: APAGADO");
                    }
                }
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.dashboard:
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(),About.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.historial:
                        startActivity(new Intent(getApplicationContext(),Historial.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

    }
}