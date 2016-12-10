package aye.net;

import aye.model.HttpResponse;
import aye.model.history.History;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by reid on 2016/12/10.
 */

public interface HistoryApi {
    /**
     * 获取历史列表
     */
    @FormUrlEncoded
    @POST("queryEvent.php")
    Observable<HttpResponse<History>> getHistoryList(@Field("key") String key, @Field("date") String date);
}
