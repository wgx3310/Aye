package aye.net;

/**
 * Created by reid on 2016/11/26.
 */

public class ApiCreator {

    private static BiliApi mBiliApi;
    public static synchronized BiliApi getBiliApi(){
        if (mBiliApi == null){
            mBiliApi = HttpEngine.get().createApi(BiliApi.class);
        }
        return mBiliApi;
    }



}
