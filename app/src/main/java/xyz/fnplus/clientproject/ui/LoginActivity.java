package xyz.fnplus.clientproject.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import xyz.fnplus.clientproject.R;
import xyz.fnplus.clientproject.app.AppConfig;
import xyz.fnplus.clientproject.app.AppController;
import xyz.fnplus.clientproject.helpers.SQLiteHandler;
import xyz.fnplus.clientproject.helpers.SessionManager;

public class LoginActivity extends Activity {
  private static final String TAG = LoginActivity.class.getSimpleName();

  @BindView(R.id.login_relative_layout) ImageView mLoginRelativeLayout;
  @BindView(R.id.field_email) EditText mFieldEmail;
  @BindView(R.id.field_password) EditText mFieldPassword;
  @BindView(R.id.btn_sign_in) AppCompatButton mBtnSignIn;
  @BindView(R.id.login_main_layout) ScrollView mLoginMainLayout;

  private ProgressDialog pDialog;
  private SessionManager session;
  private SQLiteHandler db;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);

    // Main View
    // Setup animation
    AnimationDrawable animationDrawable = (AnimationDrawable) mLoginMainLayout.getBackground();
    animationDrawable.setEnterFadeDuration(2500);
    animationDrawable.setExitFadeDuration(2500);
    animationDrawable.start();

    // SQLite database handler
    db = new SQLiteHandler(getApplicationContext());

    // Session manager
    session = new SessionManager(getApplicationContext());

    // Check if user is already logged in or not
    if (session.isLoggedIn()) {
      // User is already logged in. Take him to main activity
      Intent intent = new Intent(LoginActivity.this, MainActivity.class);
      startActivity(intent);
      finish();
    }

    // Login button Click Event
    mBtnSignIn.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {
        String email = mFieldEmail.getText().toString().trim();
        String password = mFieldPassword.getText().toString().trim();

        // Check for empty data in the form
        if (!email.isEmpty() && !password.isEmpty()) {
          // login user
          checkLogin(email, password);
        } else {
          // Prompt user to enter credentials
          Toast.makeText(getApplicationContext(), "Please enter the credentials!",
              Toast.LENGTH_LONG).show();
        }
      }
    });
  }

  /**
   * function to verify login details in mysql db
   */
  private void checkLogin(final String email, final String password) {
    // Tag used to cancel the request
    String tag_string_req = "req_login";
    // Show UI
    showProgressDialog();

    StringRequest strReq =
        new StringRequest(Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {

          @Override public void onResponse(String response) {
            Log.d(TAG, "Login Response: " + response);
            hideProgressDialog();

            try {
              JSONObject jObj = new JSONObject(response);
              boolean error = jObj.getBoolean("error");

              // Check for error node in json
              if (!error) {
                // handle UI
                hideProgressDialog();
                // user successfully logged in, create login session
                session.setLogin(true);

                // Now store the user in SQLite
                String uid = jObj.getString("uid");

                JSONObject user = jObj.getJSONObject("user");
                String name = user.getString("name");
                String email = user.getString("email");
                String created_at = user.getString("member_from");

                // Inserting row in users table
                db.addUser(name, email, uid, "admin", created_at, "admin");

                // Launch main activity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
              } else {
                // Error in login. Get the error message
                String errorMsg = jObj.getString("error_msg");
                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
              }
            } catch (JSONException e) {
              // JSON error
              e.printStackTrace();
              Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(),
                  Toast.LENGTH_LONG).show();
            }
          }
        }, new Response.ErrorListener() {

          @Override public void onErrorResponse(VolleyError error) {
            Log.e(TAG, "Login Error: " + error.getMessage());
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            hideProgressDialog();
          }
        }) {

          @Override protected Map<String, String> getParams() {
            // Posting parameters to login url
            Map<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("password", password);

            return params;
          }
        };
    // Adding request to request queue
    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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
}
