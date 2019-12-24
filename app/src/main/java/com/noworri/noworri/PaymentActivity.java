package com.noworri.noworri;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {

    TextView enter_coupon, receiverCountryP,currencyS, currencyR;
    ImageView receiverCountryFlag, senderCountryFlag;
    String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        receiverCountryFlag = findViewById(R.id.receiverCountryFlag);
        senderCountryFlag = findViewById(R.id.senderCountryFlag);
        currencyR = findViewById(R.id.currencyR);
        currencyS = findViewById(R.id.currencyS);

        senderCountryFlag.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ghs));
        /*senderCountryFlag.getLayoutParams().height = 50;
        senderCountryFlag.requestLayout();*/
        currencyS.setText("GHS");
        receiverCountryP = findViewById(R.id.receiverCountryP);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            country = extras.getString("receiverCountry");
            //The key argument here must match that used in the other activity
        }
        SpannableString contentReceiver = new SpannableString("You're sending to "+country);
        contentReceiver.setSpan(new UnderlineSpan(), 0, contentReceiver.length(), 0);
        receiverCountryP.setText(contentReceiver);
        enter_coupon = findViewById(R.id.buttonCoupon);
        SpannableString content = new SpannableString("Enter coupon");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        enter_coupon.setText(content);

        switch (country)
        {
            case "Nigeria":
                receiverCountryFlag.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ngn));
                currencyR.setText("NGN");
                break;
            case "Uganda":
                receiverCountryFlag.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ugx));
                currencyR.setText("UGX");
                break;
            case "Morocco":
                receiverCountryFlag.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.mad));
                currencyR.setText("MAD");
                break;
            case "Ghana":
                receiverCountryFlag.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ghs));
                currencyR.setText("GHS");
                break;
            case "Tanzania":
                receiverCountryFlag.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.tzs));
                currencyR.setText("TZS");
                break;
            case "Senegal":
                receiverCountryFlag.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.sen));
                currencyR.setText("SEN");
                break;
            case "European Union":
                receiverCountryFlag.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.eur));
                currencyR.setText("EUR");
                break;
            case "United Kingdom":
                receiverCountryFlag.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.gbp));
                currencyR.setText("UK");
                break;
            default:
                receiverCountryFlag.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.country_flag_bj));
        }

    }

    public void goPay(View v)
    {
        Intent intent = new Intent(PaymentActivity.this, PaymentSelectActivity.class);
        intent.putExtra("receiverCountry",country);
        startActivity(intent);
    }
    public void goContactSelect(View v)
    {
        startActivity(new Intent(PaymentActivity.this, ContactSelectActivity.class));
    }

}
