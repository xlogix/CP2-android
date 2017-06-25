package xyz.fnplus.clientproject.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import xyz.fnplus.clientproject.R;

public class SplashActivity extends Activity {
    private static final String TAG = SplashActivity.class.getSimpleName();

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

        // Splash screen timer
        final int SPLASH_TIME_OUT = 1000;

        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            public void run() {
                Log.d(TAG, "Pass to main activity");
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}