package xyz.fnplus.clientproject.app;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import xyz.fnplus.clientproject.BuildConfig;
import xyz.fnplus.clientproject.ui.SplashActivity;

/**
 * Created by Abhish3k on 24-06-2017.
 */

public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();

    private static AppController mInstance;
    public FirebaseAnalytics mFirebaseAnalytics;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        if (BuildConfig.DEBUG) {
            // Do something
        } else {
            // Obtain the FirebaseAnalytics
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            // Set Analytics collection to true
            mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        }
    }
}
