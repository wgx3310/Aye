package aye.view;

import android.content.Context;

import aye.app.LayoutConstants;
import aye.model.Block;
import aye.model.DisplayItem;
import aye.view.block.DefaultCardView;
import aye.view.block.SliderCardView;

/**
 * Created by reid on 2016/9/27.
 */

public class ViewCreator {

    public static BaseCardView createCardView(Context context, int type){

        BaseCardView view = null;
        switch (type){
            case LayoutConstants.ID_CARD_SLIDE:
                view = new SliderCardView(context);
                break;
            default:
                view = new DefaultCardView(context);
                break;
        }
        return view;
    }
}
