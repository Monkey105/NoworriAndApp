package com.noworri.noworri;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lamudi.phonefield.PhoneEditText;
import com.noworri.noworri.Utils.AppConfig;
import com.noworri.noworri.Utils.AppController;
import com.noworri.noworri.Utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MobileVerifActivity extends AppCompatActivity {

    private static final String TAG = MobileVerifActivity.class.getSimpleName();
    EditText input_password, input_passChk;
    private ProgressDialog pDialog;
    private SessionManager session;
    PhoneEditText phone_input_layout;
    String lastname, firstname, email, username, password,mobile_phone, country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verif);
        phone_input_layout = findViewById(R.id.phone_input_layout);
        phone_input_layout.setDefaultCountry("GH");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lastname = extras.getString("lastname");
            firstname = extras.getString("firstname");
            username = extras.getString("username");
            email = extras.getString("email");
            password = extras.getString("password");
            //The key argument here must match that used in the other activity
        }
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(MobileVerifActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }



    }
    public void goCheckCode(View v)
    {
        String api_name = "addUser";
        boolean valid = true;

        if (phone_input_layout.isValid()) {
            phone_input_layout.setError(null);
        } else {
            // set error message
            phone_input_layout.setError(getString(R.string.invalid_phone_number));
            valid = false;
        }

        if (phone_input_layout.isValid()) {
            phone_input_layout.setError(null);
        } else {
            phone_input_layout.setError(getString(R.string.invalid_phone_number));
            valid = false;
        }

        if (valid) {
            Toast.makeText(MobileVerifActivity.this, R.string.valid_phone_number, Toast.LENGTH_LONG).show();
            mobile_phone = phone_input_layout.getPhoneNumber();
            String number = mobile_phone.replace((phone_input_layout.getEditText()).getText().toString().trim(), "");
            country = number.replace("+","");
            System.out.println(country);
            registerUser(lastname,firstname,username,email,password,country,mobile_phone,api_name);
        } else {
            Toast.makeText(MobileVerifActivity.this, R.string.invalid_phone_number, Toast.LENGTH_LONG).show();
        }

        // Return the phone number as follows


        //startActivity(new Intent(MobileVerifActivity.this, CodeVerifActivity.class));
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String lastname, final String firstname, final String username
            ,final String email, final String password,final String country,final String mobile_phone ,final String api_name) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        pDialog.setMessage("Registering ...");
        showDialog();
        System.out.println(lastname+" "+firstname+" "+username+" "+email+" "+password+" "+country+" "+mobile_phone+" "+api_name);

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_MAIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("responseCode");
                    if (error.equals("200")) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String verifCode = jObj.getString("code");
                        /*JSONObject user = jObj.getJSONObject("user");
                        String responseCode = user.getString("responseCode");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");*/

                        // Inserting row in users table

                        Toast.makeText(getApplicationContext(), "User successfully registered. "+verifCode, Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(MobileVerifActivity.this,CodeVerifActivity.class);
                        intent.putExtra("verifCode", verifCode);
                        intent.putExtra("comeFrom", "Register");
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("errorMessage");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            /*@Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }*/
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("apiName", api_name);
                params.put("last_name", lastname);
                params.put("first_name", firstname);
                params.put("user_name", username);
                params.put("password", password);
                params.put("email", email);
                params.put("mobile_phone", mobile_phone);
                params.put("country", country);

                for (Map.Entry entry : params.entrySet())
                {
                    System.out.println("key: " + entry.getKey() + "; value: " + entry.getValue());
                }
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
