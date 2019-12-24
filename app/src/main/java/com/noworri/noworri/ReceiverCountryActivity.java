package com.noworri.noworri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ReceiverCountryActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {

    Spinner country;
    String countryName;
    String[] countryList = { "Nigeria", "Uganda", "Morocco",
            "Ghana", "Tanzania","Senegal","European Union",
            "United Kingdom"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_country);
        country = findViewById(R.id.countrySpinner);
        country.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,countryList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(aa);
    }
    public void goCountrySelect(View v)
    {
        Toast.makeText(getApplicationContext(),countryName , Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ReceiverCountryActivity.this, ContactSelectActivity.class);
        intent.putExtra("receiverCountry",countryName);
        startActivity(intent);
    }
    public void goMain(View v)
    {
        startActivity(new Intent(ReceiverCountryActivity.this, MainActivity.class));
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        countryName = countryList[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
