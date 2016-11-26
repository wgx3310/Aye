package aye.net;


import aye.model.main.MainDaily;
import aye.model.main.MainHot;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by reid on 2016/11/26.
 */

public interface MainApi {

    @GET("news/latest")
    Observable<MainDaily> getDailyList();

    @GET("news/hot")
    Observable<MainHot> getHotList();
}
