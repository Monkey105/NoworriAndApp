package com.noworri.noworri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterPassActivity extends AppCompatActivity {

    EditText input_password, input_passChk;
    String lastname, firstname, email, username, password, passwordConf;
    TextView passComparator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pass);
        input_password =  findViewById(R.id.input_lastname);
        input_passChk = findViewById(R.id.input_email);
        passComparator = findViewById(R.id.passComparator);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lastname = extras.getString("lastname");
            firstname = extras.getString("firstname");
            username = extras.getString("username");
            email = extras.getString("email");
            //The key argument here must match that used in the other activity
        }
    }
    public void goCheckPhone(View v)
    {
        password = input_password.getText().toString().trim();
        passwordConf = input_passChk.getText().toString().trim();
        if(!password.isEmpty()&& !passwordConf.isEmpty())
        {
            passComparator.setVisibility(View.GONE);
            if(password.equals(passwordConf) ) {

                Toast.makeText(getApplicationContext(),lastname+" "+firstname+" "+username+" "+email+" "+password,Toast.LENGTH_LONG).show();
                System.out.println(lastname+" "+firstname+" "+username+" "+email+" "+password);
                Intent intent = new Intent(RegisterPassActivity.this, MobileVerifActivity.class);
                intent.putExtra("lastname", lastname);
                intent.putExtra("firstname", firstname);
                intent.putExtra("username", username);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
            }
            else
            {
                passComparator.setText("The passwords entered are not the same");
                passComparator.setTextColor(Color.RED);
                passComparator.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Les mots de passes ne correspondent pas",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            passComparator.setText("Please fill all the field");
            passComparator.setTextColor(Color.RED);
            passComparator.setVisibility(View.VISIBLE);
        }

    }



}
