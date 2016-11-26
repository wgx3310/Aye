package aye.lang;

import java.util.ArrayList;

import aye.model.Block;
import aye.model.DisplayItem;
import aye.model.main.MainDaily;
import aye.model.main.MainHot;

/**
 * Created by reid on 2016/11/26.
 */

public class DisplayItemFactory {

    /**
     * 将精选数据转化为Block
     */
    public static Block<DisplayItem> convertMainDaily2Block(MainDaily mainDaily){
        if (mainDaily == null) return null;

        Block<DisplayItem> block = new Block<>();
        block.blocks = new ArrayList<>();
        if (mainDaily.top_stories != null && !mainDaily.top_stories.isEmpty()) {

            Block<DisplayItem> topBlock = new Block<>();
            topBlock.ui = new DisplayItem.UI();
            topBlock.ui.put("id", String.valueOf(1));
            topBlock.items = new ArrayList<>();
            for (MainDaily.Story story : mainDaily.top_stories) {
                DisplayItem item = new DisplayItem();
                item.id = String.valueOf(story.id);
                item.ui = new DisplayItem.UI();
                item.title = story.title;
                item.extra = story.prefix;
                item.images = new DisplayItem.ImageGroup();
                DisplayItem.Image image = new DisplayItem.Image();
                image.url = story.image;
                item.images.put("poster", image);
                topBlock.items.add(item);
            }
            block.blocks.add(topBlock);
        }

        if (mainDaily.stories != null && !mainDaily.stories.isEmpty()) {
            for (MainDaily.Story story : mainDaily.stories) {
                Block item = new Block();
                item.id = String.valueOf(story.id);
                item.ui = new DisplayItem.UI();
                item.ui.put("id", String.valueOf(2));
                item.title = story.title;
                item.extra = story.prefix;
                item.images = new DisplayItem.ImageGroup();
                DisplayItem.Image image = new DisplayItem.Image();
                image.urls = story.images;
                item.images.put("poster", image);
                block.blocks.add(item);
            }
        }
        return block;
    }

    /**
     * 将热门数据转化为Block
     */
    public static Block<DisplayItem> convertMainHot2Block(MainHot hot){
        if(hot == null) return null;

        Block<DisplayItem> block = new Block<DisplayItem>();
        block.blocks = new ArrayList<>();
        if (hot.recent != null && !hot.recent.isEmpty()){
            for (MainHot.Recent recent : hot.recent){
                Block item = new Block();
                item.id = String.valueOf(recent.id);
                item.ui = new DisplayItem.UI();
                item.ui.put("id", String.valueOf(3));
                item.title = recent.title;
                item.images = new DisplayItem.ImageGroup();
                DisplayItem.Image image = new DisplayItem.Image();
                image.url = recent.thumbnail;
                item.images.put("poster", image);
                item.target = new DisplayItem.Target();
                item.target.url = recent.url;
                block.blocks.add(item);
            }
        }
        return block;
    }

}
