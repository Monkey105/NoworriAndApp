package com.noworri.noworri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.noworri.noworri.LoginActivity;
import com.noworri.noworri.RegisterPassActivity;

public class RegisterActivity extends AppCompatActivity {

    TextView emailVerificator, usernameVerificator, lastnameVerificator, firstnameVerificator;
    EditText input_lastname, input_firstname, input_email, input_username;
    String lastname, firstname, email, username;
    Button btn_nextToNetwork;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailVerificator = findViewById(R.id.emailVerificator);
        usernameVerificator = findViewById(R.id.usernameVerificator);
        firstnameVerificator = findViewById(R.id.firstnameVerificator);
        lastnameVerificator = findViewById(R.id.lastnameVerificator);
        input_lastname = findViewById(R.id.input_lastname);
        input_firstname = findViewById(R.id.input_firstname);
        input_email = findViewById(R.id.input_email);
        input_username = findViewById(R.id.input_username);
        btn_nextToNetwork = findViewById(R.id.btn_nextToNetwork);

        btn_nextToNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = input_email.getText().toString().trim();
                lastname = input_lastname.getText().toString().trim();
                firstname = input_firstname.getText().toString().trim();
                username = input_username.getText().toString().trim();

                if (!email.isEmpty() && !lastname.isEmpty() && !firstname.isEmpty() && !username.isEmpty())
                {
                    usernameVerificator.setVisibility(View.GONE);
                    if (username.length()<5)
                    {
                        usernameVerificator.setTextColor(Color.RED);
                        usernameVerificator.setText("Your username must contains at least 5 characters");
                        usernameVerificator.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        usernameVerificator.setVisibility(View.GONE);
                        if (lastname.length()<3)
                        {
                            lastnameVerificator.setTextColor(Color.RED);
                            lastnameVerificator.setText("Your lastname must contains at least 3 characters");
                            lastnameVerificator.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            lastnameVerificator.setVisibility(View.GONE);
                            if (firstname.length()<4)
                            {
                                firstnameVerificator.setTextColor(Color.RED);
                                firstnameVerificator.setText("Your firstname must contains at least 4 characters");
                                firstnameVerificator.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                firstnameVerificator.setVisibility(View.GONE);
                                if (email.matches(emailPattern))
                                {
                                    emailVerificator.setVisibility(View.GONE);
                                    System.out.println( lastname+" "+firstname+" "+username+" "+email);
                                    Intent intent = new Intent(RegisterActivity.this, RegisterPassActivity.class);
                                    intent.putExtra("lastname",lastname);
                                    intent.putExtra("firstname",firstname);
                                    intent.putExtra("username",username);
                                    intent.putExtra("email",email);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
                                    emailVerificator.setText("Invalid email address");
                                    emailVerificator.setTextColor(Color.RED);
                                    emailVerificator.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                }
                else
                {
                    usernameVerificator.setText("Please fill all the field");
                    usernameVerificator.setTextColor(Color.RED);
                    usernameVerificator.setVisibility(View.VISIBLE);

                }

            }
        });

    }
    public void goLogin(View v)
    {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
    public void terms(View v)
    {
        startActivity(new Intent(RegisterActivity.this, TermsActivity.class));
    }
}
