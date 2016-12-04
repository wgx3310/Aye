package aye.lang;

import java.util.ArrayList;

import aye.LayoutConstants;
import aye.model.Block;
import aye.model.DisplayItem;
import aye.model.main.MainStory;
import aye.model.main.MainHot;

/**
 * Created by reid on 2016/11/26.
 */

public class DisplayItemFactory {

    /**
     * 将精选数据转化为Block
     */
    public static Block<DisplayItem> convertMainStory2Block(MainStory mainStory) {
        if (mainStory == null) return null;

        Block<DisplayItem> block = new Block<>();
        block.blocks = new ArrayList<>();
        if (mainStory.top_stories != null && !mainStory.top_stories.isEmpty()) {

            Block<DisplayItem> topBlock = new Block<>();
            topBlock.ui = new DisplayItem.UI();
            topBlock.ui.put("id", String.valueOf(LayoutConstants.ID_CARD_SLIDE));
            topBlock.items = new ArrayList<>();
            for (MainStory.Story story : mainStory.top_stories) {
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

        if (mainStory.stories != null && !mainStory.stories.isEmpty()) {

            Block<DisplayItem> port = new Block<>();
            port.ui = new DisplayItem.UI();
            port.ui.put("id", String.valueOf(LayoutConstants.ID_CARD_PORT));
            port.items = new ArrayList<>();

            DisplayItem title = new DisplayItem();
            title.ui = new DisplayItem.UI();
            title.ui.put("id", String.valueOf(LayoutConstants.ID_CARD_TITLE));
            title.ui.put("divider", String.valueOf(true));
            title.title = "今日精选";
            port.items.add(title);

            for (int i = 0 ; i < mainStory.stories.size(); i++){
                MainStory.Story story = mainStory.stories.get(i);
                DisplayItem di = new DisplayItem();
                di.ui = new DisplayItem.UI();
                di.ui.put("id", String.valueOf(LayoutConstants.ID_CARD_SHORT));
                di.id = String.valueOf(story.id);
                di.title = story.title;
                di.extra = story.prefix;
                di.images = new DisplayItem.ImageGroup();
                DisplayItem.Image image = new DisplayItem.Image();
                image.url = story.images == null || story.images.isEmpty() ? "" : story.images.get(0);
                di.images.put("poster", image);
                port.items.add(di);
            }

            block.blocks.add(port);
        }
        return block;
    }

    /**
     * 将热门数据转化为Block
     */
    public static Block<DisplayItem> convertMainHot2Block(MainHot hot) {
        if (hot == null) return null;

        Block<DisplayItem> block = new Block<DisplayItem>();
        block.blocks = new ArrayList<>();
        if (hot.recent != null && !hot.recent.isEmpty()) {
            for (MainHot.Recent recent : hot.recent) {
                Block item = new Block();
                item.id = String.valueOf(recent.id);
                item.ui = new DisplayItem.UI();
                item.ui.put("id", String.valueOf(LayoutConstants.ID_CARD_SHORT));
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
