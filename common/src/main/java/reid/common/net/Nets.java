package reid.common.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by wgx33 on 2016/9/10.
 */

public class Nets {

    private static OkHttpClient mOkHttpClient;

    public static OkHttpClient client(){
        if (mOkHttpClient == null){
            synchronized (Nets.class){
                if (mOkHttpClient == null){
                    initOkHttpClient();
                }
            }
        }
        return mOkHttpClient;
    }

    private static void initOkHttpClient() {
        if (mOkHttpClient == null){
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();
        }
    }

    public static void init(){
        initOkHttpClient();
    }


}
