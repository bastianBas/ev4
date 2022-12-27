package com.example.bottonnav;



import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class About extends AppCompatActivity implements View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    ToggleButton myToggle;
    TextView estadoluz;
    Button bfecha,bhora,btm_add;
    EditText efecha,ehora;
    DatabaseReference mRootReference;

    private  int dia,mes,ano,hora,minutos;


    @SuppressLint("MissingInflatedId")
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        database= FirebaseDatabase.getInstance();
        myRef= database.getReference("led");

        ToggleButton led=(ToggleButton)findViewById(R.id.myToggle);
        led.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    myRef.setValue(1);
                }else{
                    myRef.setValue(0);
                }
            }
        });

        bottomNavigationView =findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        btm_add = findViewById(R.id.btm_add);
        efecha = findViewById(R.id.efecha);
        ehora = findViewById(R.id.ehora);

        mRootReference = FirebaseDatabase.getInstance().getReference();


        estadoluz = (TextView) findViewById(R.id.estadoluz);
        myToggle = (ToggleButton) findViewById(R.id.myToggle);

        btm_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fecha = efecha.getText().toString();
                String hora = ehora.getText().toString();

                Map<String, Object> horarios = new HashMap<>();
                horarios.put("fecha",fecha);
                horarios.put("hora",hora);

                mRootReference.child("horarios").push().setValue(horarios);
            }
        });

        myToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.myToggle) {
                    if (myToggle.isChecked()) {
                        estadoluz.setText("ESTADO: ENCENDIDO");
                    } else {
                        estadoluz.setText("ESTADO: APAGADO");
                    }
                }
            }
        });

        bfecha = (Button) findViewById(R.id.bfecha);
        bhora = (Button) findViewById(R.id.bhora);
        efecha = (EditText) findViewById(R.id.efecha);
        ehora = (EditText) findViewById(R.id.ehora);
        bfecha.setOnClickListener(this);
        bhora.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==bfecha){
            final Calendar c= Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    efecha.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                }
            }
            ,dia,mes,ano);
            datePickerDialog.show();
        }
        if (v==bhora){
            final Calendar c= Calendar.getInstance();
            hora=c.get(Calendar.HOUR_OF_DAY);
            minutos=c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    ehora.setText(hourOfDay + ":" + minute);
                }
            },hora,minutos,false);
            timePickerDialog.show();
        }

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.about);

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
