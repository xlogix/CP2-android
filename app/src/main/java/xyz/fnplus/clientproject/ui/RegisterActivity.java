package xyz.fnplus.clientproject.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import xyz.fnplus.clientproject.R;
import xyz.fnplus.clientproject.app.AppConfig;
import xyz.fnplus.clientproject.app.AppController;
import xyz.fnplus.clientproject.helpers.SQLiteHandler;
import xyz.fnplus.clientproject.helpers.SessionManager;

public class RegisterActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();

    // Declare
    private AppCompatEditText inputFullName;
    private AppCompatEditText inputEmail;
    private AppCompatEditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Main View
        ScrollView mScrollView = (ScrollView) findViewById(R.id.register_main_layout);
        // Setup animation
        AnimationDrawable animationDrawable = (AnimationDrawable) mScrollView.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

        // Declare Text Fields
        inputFullName = (AppCompatEditText) findViewById(R.id.name);
        inputEmail = (AppCompatEditText) findViewById(R.id.email);
        inputPassword = (AppCompatEditText) findViewById(R.id.password);
        // Declare Buttons
        AppCompatButton btnRegister = (AppCompatButton) findViewById(R.id.btn_register);
        AppCompatButton btnLinkToLogin = (AppCompatButton) findViewById(R.id.btn_link_to_login_screen);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            startActivity(new Intent(RegisterActivity.this,
                    MainActivity.class));
            finish();
        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (!validateForm()) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
                // Call the function
                registerUser(name, email, password);
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     */
    private void registerUser(final String name, final String email,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        // Show UI changes
        showProgressDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideProgressDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite locally
                        String uid = jObj.getString("id");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String member_from = user
                                .getString("member_from");

                        // Inserting row in users table
                        db.addUser(name, email, password, "admin", "", member_from);

                        Toast.makeText(RegisterActivity.this,
                                "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        startActivity(new Intent(
                                RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(RegisterActivity.this,
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
                hideProgressDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private boolean validateForm() {
        boolean valid = true;

        if (!isDeviceOnline()) {
            valid = false;
            Toast.makeText(this, "Check your network connection", Toast.LENGTH_SHORT).show();
        }

        String name = inputFullName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            inputFullName.setError("Required.");
            valid = false;
        } else {
            inputFullName.setError(null);
        }

        String email = inputEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("Required.");
            valid = false;
        } else {
            inputEmail.setError(null);
        }

        String password = inputPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Required.");
            valid = false;
        } else {
            inputPassword.setError(null);
        }

        return valid;
    }

    public void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(this);
            pDialog.setMessage(getString(R.string.loading));
            pDialog.setIndeterminate(true);
        }
        pDialog.show();
    }

    public void hideProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.hide();
        }
    }

    private boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
