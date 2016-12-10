package aye.net;

/**
 * Created by reid on 2016/11/26.
 */

public class ApiCreator {

    private static BiliApi mBiliApi;
    private static MainApi mMainApi;
    private static HistoryApi mHistoryApi;

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

    public static synchronized HistoryApi getHistoryApi(){
        if (mHistoryApi == null){
            mHistoryApi = HttpEngine.get().createApi(HistoryApi.class, "http://v.juhe.cn/todayOnhistory/");
        }
        return mHistoryApi;
    }
}
