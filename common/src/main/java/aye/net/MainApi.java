package aye.net;


import aye.model.main.MainStory;
import aye.model.main.MainHot;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by reid on 2016/11/26.
 */

public interface MainApi {

    @GET("news/latest")
    Observable<MainStory> getDailyList();

    @GET("news/hot")
    Observable<MainHot> getHotList();

    @GET("theme/{id}")
    Observable<MainStory> getThemeList(@Path("id") String id);
}
