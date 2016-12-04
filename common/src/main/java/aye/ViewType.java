package aye;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import aye.content.Contexts;

/**
 * Created by reid on 2016/12/4.
 */

public class ViewType {
    public int type;
    public int spanSize;
    public int height;
    public int bottomPadding;

    public ViewType(int type) {
        this(type, 0);
    }

    public ViewType(int type, int height) {
        this(type, height, Constants.DEFAULT_RECYCLER_SPAN_COUNT);
    }

    public ViewType(int type, int height, int spanSize) {
        this(type, height, spanSize, Constants.DEFAULT_ITEM_PADDING);
    }

    public ViewType(int type, int height, int spanSize, int bottomPadding) {
        this.type = type;
        this.height = height;
        this.spanSize = spanSize;
        this.bottomPadding = bottomPadding;
    }

    public static final Map<Integer, ViewType> DEFAULT = new ConcurrentHashMap<>();

    static {
        DEFAULT.put(LayoutConstants.ID_CARD_SLIDE, new ViewType(LayoutConstants.ID_CARD_SLIDE,
                Contexts.dp2px(160f)));
        DEFAULT.put(LayoutConstants.ID_CARD_PORT, new ViewType(LayoutConstants.ID_CARD_PORT));
        DEFAULT.put(LayoutConstants.ID_CARD_TITLE, new ViewType(LayoutConstants.ID_CARD_TITLE,
                Contexts.dp2px(46.67f), Constants.DEFAULT_RECYCLER_SPAN_COUNT, 0));
        DEFAULT.put(LayoutConstants.ID_CARD_SHORT, new ViewType(LayoutConstants.ID_CARD_SHORT,
                Contexts.dp2px(100f), Constants.DEFAULT_RECYCLER_SPAN_COUNT, 0));

        DEFAULT.put(LayoutConstants.ID_VIEW_DEFAULT, new ViewType(LayoutConstants
                .ID_VIEW_DEFAULT, Contexts.dp2px(50f)));
    }

    public static int getSpanSize(int type) {
        ViewType viewType = DEFAULT.get(type);
        if (viewType != null) {
            return viewType.spanSize;
        }
        return Constants.DEFAULT_RECYCLER_SPAN_COUNT;
    }

    public static int getBottomPadding(int type) {
        ViewType viewType = DEFAULT.get(type);
        if (viewType != null) {
            return viewType.bottomPadding;
        }
        return Constants.DEFAULT_ITEM_PADDING;
    }

    public static int getHeight(int type) {
        ViewType viewType = DEFAULT.get(type);
        if (viewType != null) {
            return viewType.height;
        }
        return 0;
    }
}
