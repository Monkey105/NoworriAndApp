package com.noworri.noworri;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CodeVerifActivity extends AppCompatActivity {
    EditText number1, number2,number3, number4;
    Button verify;
    String verifCode,comeFrom,verifCode2,number5, number6,number7, number8;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verif);


        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        number3 = findViewById(R.id.number3);
        number4 = findViewById(R.id.number4);
        verify = findViewById(R.id.btn_home);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            verifCode = extras.getString("verifCode");
            comeFrom = extras.getString("comeFrom");
            //The key argument here must match that used in the other activity
        }
        number1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(number1.getText().toString().length()==1)     //size as per your requirement
                {
                    number2.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        number2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(number2.getText().toString().length()==1)     //size as per your requirement
                {
                    number3.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        number3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(number3.getText().toString().length()==1)     //size as per your requirement
                {
                    number4.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        number4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(number1.getText().toString().length()==1)     //size as per your requirement
                {
                    verify.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
    }
    public void goHome(View v) {
        number5  = number1.getText().toString().trim();
        number6  = number2.getText().toString().trim();
        number7  = number3.getText().toString().trim();
        number8  = number4.getText().toString().trim();
        verifCode2 = number5+number6+number7+number8;
        System.out.println(verifCode2 +"-"+ verifCode );
        if(comeFrom.equals("Register"))
        {
            if (Integer.parseInt(verifCode2) == Integer.parseInt(verifCode) )
            {
                builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("Registration Successful. Now Please Login!!")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(CodeVerifActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                alert.show();



            }
        }
        if(comeFrom.equals("Login"))
        {
            if (Integer.parseInt(verifCode2) == Integer.parseInt(verifCode) )
            {
                Intent intent = new Intent(CodeVerifActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }


    }
}
