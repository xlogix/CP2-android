package xyz.fnplus.clientproject.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import xyz.fnplus.clientproject.R;
import xyz.fnplus.clientproject.helpers.SQLiteHandler;
import xyz.fnplus.clientproject.helpers.SessionManager;

public class SplashActivity extends Activity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    private SessionManager session;

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Session manager
        session = new SessionManager(getApplicationContext());
        // Splash screen timer
        final int SPLASH_TIME_OUT = 1000;

        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            public void run() {
                if (!session.isLoggedIn()) {
                    Log.d(TAG, "Pass to login activity");
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Log.d(TAG, "Pass to main activity");
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}