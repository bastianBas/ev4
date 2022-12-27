package com.example.bottonnav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;


import com.example.bottonnav.adapter.HorariosAdapter;
import com.example.bottonnav.model.Horarios;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Historial extends AppCompatActivity {

    private DatabaseReference mDatabase;
    BottomNavigationView bottomNavigationView;

    private HorariosAdapter hAdapter;
    private RecyclerView hRecyclerView;
    private ArrayList<Horarios> hHorarioList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.historial);
        hRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        hRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        overridePendingTransition(0,0);
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
                        return true;
                }

                return false;
            }
        });

        getHorariosFromFirebase();
    }

    private void getHorariosFromFirebase(){
        mDatabase.child("horarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    for(DataSnapshot ds: snapshot.getChildren()) {
                        String fecha = ds.child("fecha").getValue().toString();
                        String hora = ds.child("hora").getValue().toString();
                        hHorarioList.add(new Horarios(fecha,hora));

                    }

                    hAdapter = new HorariosAdapter(hHorarioList, R.layout.horario_view);
                    hRecyclerView.setAdapter(hAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}