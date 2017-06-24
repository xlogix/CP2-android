package xyz.fnplus.clientproject.app;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;

import xyz.fnplus.clientproject.BuildConfig;

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
