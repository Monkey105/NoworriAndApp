package com.noworri.noworri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

public class PaymentSelectActivity extends AppCompatActivity {

    TextView moneyPay;
    TextView receiverCountryP;
    String country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_select);
        receiverCountryP = findViewById(R.id.receiverCountryC);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            country = extras.getString("receiverCountry");
            //The key argument here must match that used in the other activity
        }
        SpannableString contentReceiver = new SpannableString("You're sending to "+country);
        contentReceiver.setSpan(new UnderlineSpan(), 0, contentReceiver.length(), 0);
        receiverCountryP.setText(contentReceiver);

    }
    public void  goPayment(View v)
    {
        startActivity(new Intent(PaymentSelectActivity.this, PaymentActivity.class));
    }
    public void  addCard(View v)
    {
        startActivity(new Intent(PaymentSelectActivity.this, AddCardActivity.class));
    }
}
