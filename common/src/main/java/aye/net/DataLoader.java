package aye.net;


import android.util.Log;

import aye.lang.DisplayItemFactory;
import aye.lang.Logger;
import aye.model.Block;
import aye.model.DisplayItem;
import aye.model.main.MainDaily;
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
                observable = ApiCreator.getMainApi().getDailyList()
                        .compose(RxUtils.rxSchedulerHelper())
                        .map(new Func1<MainDaily, Block<DisplayItem>>() {
                            @Override
                            public Block<DisplayItem> call(MainDaily mainDaily) {
                                Logger.e("TAG", "DataLoader loadData getDailyList" + mainDaily);
                                return DisplayItemFactory.convertMainDaily2Block(mainDaily);
                            }
                        });
                break;
            case "设计":
                break;
            case "电影":
                break;
            case "互联网":
                break;
            case "热门":
                observable = ApiCreator.getMainApi().getHotList()
                        .compose(RxUtils.rxSchedulerHelper())
                        .map(new Func1<MainHot, Block<DisplayItem>>() {
                            @Override
                            public Block<DisplayItem> call(MainHot hot) {
                                Log.e("TAG", "DataLoader loadData getHotList" + hot);
                                return DisplayItemFactory.convertMainHot2Block(hot);
                            }
                        });
                break;
        }

        return observable;
    }
}
