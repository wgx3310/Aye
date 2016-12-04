package aye.util;

import android.text.TextUtils;

import aye.LayoutConstants;
import aye.model.DisplayItem;

/**
 * Created by reid on 2016/12/4.
 */

public class DisplayItemUtils {

    public static String getPostUrl(DisplayItem item){
        if (item != null && item.images != null && item.images.poster() != null && !TextUtils.isEmpty(item.images.poster().url)){
            return item.images.poster().url;
        }
        return null;
    }

    public static String getIconUrl(DisplayItem item){
        if (item != null && item.images != null && item.images.icon() != null && !TextUtils.isEmpty(item.images.icon().url)){
            return item.images.icon().url;
        }
        return null;
    }

    public static int getUiId(DisplayItem item){
        if (item != null && item.ui != null && item.ui.id() > 0){
            return item.ui.id();
        }
        return LayoutConstants.ID_VIEW_DEFAULT;
    }
}
