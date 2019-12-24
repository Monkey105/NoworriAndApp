package com.noworri.noworri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class AddActivity extends AppCompatActivity {

    View v;
    String code;
    LinearLayout addBank, addMobile, addCreditCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        addBank = findViewById(R.id.addBank);
        addMobile = findViewById(R.id.addMobile);
        addCreditCard = findViewById(R.id.addCreditCard);
        Intent intent =  getIntent();
        code = intent.getStringExtra("code");
        if (code.matches("bank"))
        {
            addBank.setVisibility(View.VISIBLE);
        }
        if (code.matches("mobile"))
        {
            addMobile.setVisibility(View.VISIBLE);
        }

        if (code.matches("card"))
        {
            addCreditCard.setVisibility(View.VISIBLE);
        }
    }
}
