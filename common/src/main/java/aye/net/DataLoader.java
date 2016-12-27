package aye.net;


import android.util.Log;

import aye.Constants;
import aye.lang.DisplayItemFactory;
import aye.lang.Logger;
import aye.model.Block;
import aye.model.DisplayItem;
import aye.model.HttpResponse;
import aye.model.history.History;
import aye.model.main.MainStory;
import aye.model.main.MainHot;
import aye.util.RxUtils;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by reid on 2016/11/26.
 */

public class DataLoader {


    public static Observable<Block<DisplayItem>> loadData(String title) {
        Observable<Block<DisplayItem>> observable = null;
        switch (title) {
            case "精选":
                observable = ApiCreator.getMainApi().getDailyList().compose(RxUtils
                        .rxSchedulerHelper()).map(new Func1<MainStory, Block<DisplayItem>>() {
                    @Override
                    public Block<DisplayItem> call(MainStory mainStory) {
                        return DisplayItemFactory.convertMainStory2Block(mainStory);
                    }
                });
                break;
            case "设计":
                observable = ApiCreator.getMainApi().getThemeList("4").compose(RxUtils
                        .rxSchedulerHelper()).map(new Func1<MainStory, Block<DisplayItem>>() {
                    @Override
                    public Block<DisplayItem> call(MainStory mainStory) {
                        return DisplayItemFactory.convertMainStory2Block(mainStory);
                    }
                });
                break;
            case "电影":
                observable = ApiCreator.getMainApi().getThemeList("3").compose(RxUtils
                        .rxSchedulerHelper()).map(new Func1<MainStory, Block<DisplayItem>>() {
                    @Override
                    public Block<DisplayItem> call(MainStory mainStory) {
                        return DisplayItemFactory.convertMainStory2Block(mainStory);
                    }
                });
                break;
            case "互联网":
                observable = ApiCreator.getMainApi().getThemeList("10").compose(RxUtils
                        .rxSchedulerHelper()).map(new Func1<MainStory, Block<DisplayItem>>() {
                    @Override
                    public Block<DisplayItem> call(MainStory mainStory) {
                        return DisplayItemFactory.convertMainStory2Block(mainStory);
                    }
                });
                break;
            case "热门":
                observable = ApiCreator.getMainApi().getHotList().compose(RxUtils
                        .rxSchedulerHelper()).map(new Func1<MainHot, Block<DisplayItem>>() {
                    @Override
                    public Block<DisplayItem> call(MainHot hot) {
                        return DisplayItemFactory.convertMainHot2Block(hot);
                    }
                });
                break;
        }

        return observable;
    }

    public static Observable<Block<DisplayItem>> loadTodayHistory(int month, int day) {
        return ApiCreator.getHistoryApi().getHistoryList(Constants.APP_HISTORY_KEY, month + "/" +
                day).compose(RxUtils.rxSchedulerHelper()).map(new Func1<HttpResponse<History>,
                Block<DisplayItem>>() {
            @Override
            public Block<DisplayItem> call(HttpResponse<History> historyHttpResponse) {
                return DisplayItemFactory.convertHistory2Block(historyHttpResponse.result);
            }
        });
    }
}
