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
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    EditText input_pass;
    private ProgressDialog pDialog;
    private SessionManager session;
    PhoneEditText phone_input_layout;
    String  password,mobile_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone_input_layout = findViewById(R.id.phone_input_layout_log);
        phone_input_layout.setDefaultCountry("GH");
        input_pass = findViewById(R.id.input_pass);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void goToForgot(View v)
    {
        startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
    }
    public void goLogin(View v)
    {
        String api_name = "getUserData";
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
            Toast.makeText(LoginActivity.this, R.string.valid_phone_number, Toast.LENGTH_LONG).show();
            mobile_phone = phone_input_layout.getPhoneNumber();
            password = input_pass.getText().toString().trim() ;
            login(password,mobile_phone,api_name);
        } else {
            Toast.makeText(LoginActivity.this, R.string.invalid_phone_number, Toast.LENGTH_LONG).show();
        }

        // Return the phone number as follows
        mobile_phone = phone_input_layout.getPhoneNumber();
        password = input_pass.getText().toString().trim() ;
        login(password,mobile_phone,api_name);

    }

    public void goSignUp (View v)
    {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    private void login(final String password,final String mobile_phone ,final String api_name) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        pDialog.setMessage("Logging in ...");
        showDialog();
        System.out.println(password+" "+mobile_phone+" "+api_name);

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_MAIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);
                hideDialog();
                try {
                    System.out.println(response);
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("mobile_phone");
                    if (error.equals(mobile_phone)) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        session.setLogin(true);
                        String verifCode = jObj.getString("code");
                        /*JSONObject user = jObj.getJSONObject("user");
                        String responseCode = user.getString("responseCode");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");*/

                        // Inserting row in users table

                        Toast.makeText(getApplicationContext(), "Login successful. "+verifCode, Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(LoginActivity.this,CodeVerifActivity.class);
                        intent.putExtra("verifCode", verifCode);
                        intent.putExtra("comeFrom", "Login");
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
                Log.e(TAG, "Connection Error: " + error.getMessage());
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
                params.put("key", password);
                params.put("id", mobile_phone);

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
