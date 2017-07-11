package xyz.fnplus.clientproject.helpers;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static xyz.fnplus.clientproject.app.AppConfig.URL_API;

/**
 * Created by krsnv on 05-Jul-17.
 */

public class RetrofitBuilder {

    // Log requests in Stetho
    public static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor());

    // Single Instance of Builder
    public static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(URL_API)
                    .addConverterFactory(
                            GsonConverterFactory.create()
                    );

    public static Retrofit retrofit = builder
            .client(httpClient.build())
            .build();

    public static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}