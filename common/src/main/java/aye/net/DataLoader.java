package aye.net;


import android.util.Log;

import aye.lang.DisplayItemFactory;
import aye.lang.Logger;
import aye.model.Block;
import aye.model.DisplayItem;
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
                observable = ApiCreator.getMainApi().getDailyList()
                        .compose(RxUtils.rxSchedulerHelper())
                        .map(new Func1<MainStory, Block<DisplayItem>>() {
                            @Override
                            public Block<DisplayItem> call(MainStory mainStory) {
                                Logger.e("TAG", "DataLoader loadData getDailyList" + mainStory);
                                return DisplayItemFactory.convertMainStory2Block(mainStory);
                            }
                        });
                break;
            case "设计":
                observable = ApiCreator.getMainApi().getThemeList("4")
                        .compose(RxUtils.rxSchedulerHelper())
                        .map(new Func1<MainStory, Block<DisplayItem>>() {
                            @Override
                            public Block<DisplayItem> call(MainStory mainStory) {
                                Logger.e("TAG", "DataLoader loadData getThemeList 4 " + mainStory);
                                return DisplayItemFactory.convertMainStory2Block(mainStory);
                            }
                        });
                break;
            case "电影":
                observable = ApiCreator.getMainApi().getThemeList("3")
                        .compose(RxUtils.rxSchedulerHelper())
                        .map(new Func1<MainStory, Block<DisplayItem>>() {
                            @Override
                            public Block<DisplayItem> call(MainStory mainStory) {
                                Logger.e("TAG", "DataLoader loadData getThemeList 3 " + mainStory);
                                return DisplayItemFactory.convertMainStory2Block(mainStory);
                            }
                        });
                break;
            case "互联网":
                observable = ApiCreator.getMainApi().getThemeList("10")
                        .compose(RxUtils.rxSchedulerHelper())
                        .map(new Func1<MainStory, Block<DisplayItem>>() {
                            @Override
                            public Block<DisplayItem> call(MainStory mainStory) {
                                Logger.e("TAG", "DataLoader loadData getThemeList 10 " + mainStory);
                                return DisplayItemFactory.convertMainStory2Block(mainStory);
                            }
                        });
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
