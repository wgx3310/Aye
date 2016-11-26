package aye.net;

/**
 * Created by reid on 2016/11/26.
 */

public class ApiCreator {

    private static BiliApi mBiliApi;
    private static MainApi mMainApi;

    public static synchronized BiliApi getBiliApi(){
        if (mBiliApi == null){
            mBiliApi = HttpEngine.get().createApi(BiliApi.class);
        }
        return mBiliApi;
    }

    public static synchronized MainApi getMainApi(){
        if (mMainApi == null){
            mMainApi = HttpEngine.get().createApi(MainApi.class);
        }
        return mMainApi;
    }

}
