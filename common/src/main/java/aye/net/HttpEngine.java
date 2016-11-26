package aye.net;

import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by reid on 2016/9/10.
 */

public class HttpEngine {

    private static HttpEngine mEngine;

    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;
    private GsonConverterFactory mGsonConverter;
    private RxJavaCallAdapterFactory mRxJavaCallAdapter;

    public static HttpEngine get(){
        if (mEngine == null){
            synchronized (HttpEngine.class){
                if (mEngine == null){
                    mEngine = new HttpEngine();
                }
            }
        }
        return mEngine;
    }

    private HttpEngine(){
        init();
    }

    public void init(){
        if (mGsonConverter == null){
            mGsonConverter = GsonConverterFactory.create();
            mRxJavaCallAdapter = RxJavaCallAdapterFactory.create();
        }

        initOkHttpClient();
        initBaseRetrofit();
    }

    private synchronized void initOkHttpClient() {
        if (mOkHttpClient == null){
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();
        }
    }

    private synchronized void initBaseRetrofit() {
        if (mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .client(client())
                    .addConverterFactory(mGsonConverter)
                    .addCallAdapterFactory(mRxJavaCallAdapter)
                    .baseUrl("")
                    .build();
        }
    }

    private OkHttpClient client(){
        if (mOkHttpClient == null){
            synchronized (HttpEngine.class){
                if (mOkHttpClient == null){
                    initOkHttpClient();
                }
            }
        }
        return mOkHttpClient;
    }

    private Retrofit retrofit(){
        if (mRetrofit == null){
            initBaseRetrofit();
        }
        return mRetrofit;
    }

    private Retrofit retrofit(String url){
        if (TextUtils.isEmpty(url)) return retrofit();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client())
                .addConverterFactory(mGsonConverter)
                .addCallAdapterFactory(mRxJavaCallAdapter)
                .baseUrl(url)
                .build();
        return retrofit;
    }

    public <T> T createApi(Class<T> clazz){
        return mRetrofit.create(clazz);
    }

    public <T> T createApi(Class<T> clazz, String url){
        if (TextUtils.isEmpty(url)){
            return createApi(clazz);
        }else {
            return retrofit(url).create(clazz);
        }
    }
}
