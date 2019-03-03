package com.landmark.android.network;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //Base url for api's we can get the URL from product flavours as well once we add the product flavour in gradle file
    private static final String BASE_URL = "https://a2b7cf8676394fda75de-6e0550a16cd96615f7274fd70fa77109.r93.cf3.rackcdn.com/common/json/";

    private static Retrofit retrofit = null;

    //Returns the retrofit client
    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) //convert the response to model from the gson converter
                    .client(getOkHttpClient())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getOkHttpClient(){

        //we can add the header interceptor if headers requires
        //we can add the Network interceptor if needs to handle the access token and refresh token to handle OAuth Authentication
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);  // to log the retrofit response we can display logs only if the build type is QA/STAGE
        OkHttpClient client = new OkHttpClient.Builder()
                .hostnameVerifier(new HostnameVerifier() { //To disable the name verifier
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .addInterceptor(interceptor).build();

        return client;


    }

}
