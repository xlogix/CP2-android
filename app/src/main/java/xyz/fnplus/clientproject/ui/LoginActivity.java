package xyz.fnplus.clientproject.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import xyz.fnplus.clientproject.R;

/**
 * Created by Abhish3k on 25-06-2017.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private ProgressDialog mProgressDialog;
    private TextInputEditText mEditText;
    private String mSecret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Setting the secret
        mSecret = "1234";

        // Declare layout
        ScrollView mScrollView = (ScrollView) findViewById(R.id.login_main_layout);
        Button mButton = (Button) findViewById(R.id.btn_unlock_submit);
        mEditText = (TextInputEditText) findViewById(R.id.edit_txt_unlock_code);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show changes in the UI
                showProgressDialog();
                if (mEditText.getText().toString().equals(mSecret)) {
                    getSharedPreferences("userData", MODE_PRIVATE).edit().putBoolean("secret_code_entered", true).apply();
                    // Show changes in the UI
                    hideProgressDialog();
                    // Main Intent
                    Intent intentLoginMain = new Intent(LoginActivity.this, MainActivity.class);
                    intentLoginMain.setAction(Intent.ACTION_MAIN);
                    intentLoginMain.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
                            | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    // show else statement
                    mEditText.setError("Invalid Secret Code");
                    // Set in shared preferences
                    getSharedPreferences("userData", MODE_PRIVATE).edit().putBoolean("secret_code_entered", false).apply();
                    // show changes in the UI
                    hideProgressDialog();
                }
            }
        });

        // Setup animation
        AnimationDrawable animationDrawable = (AnimationDrawable) mScrollView.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isDeviceOnline()) {
            Log.i(TAG, "Device is online!");
        } else {
            Toast.makeText(this, "Device is offline. Functionality may be limited", Toast.LENGTH_SHORT).show();
        }
    }


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
