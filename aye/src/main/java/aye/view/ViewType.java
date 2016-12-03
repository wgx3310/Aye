package aye.view;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import aye.Constants;
import aye.app.LayoutConstants;

/**
 * Created by reid on 2016/12/4.
 */

public class ViewType {
    public int type;
    public int spanSize;
    public int bottomPadding;

    public ViewType(int type) {
        this(type, Constants.DEFAULT_RECYCLER_SPAN_COUNT);
    }

    public ViewType(int type, int spanSize) {
        this(type, spanSize, Constants.DEFAULT_ITEM_PADDING);
    }

    public ViewType(int type, int spanSize, int bottomPadding) {
        this.type = type;
        this.spanSize = spanSize;
        this.bottomPadding = bottomPadding;
    }

    public static final Map<Integer, ViewType> DEFAULT = new ConcurrentHashMap<>();

    static {
        DEFAULT.put(LayoutConstants.ID_CARD_SLIDE, new ViewType(LayoutConstants.ID_CARD_SLIDE));

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
}
