package aye.view;

import android.content.Context;

import aye.LayoutConstants;
import aye.view.block.CardShortBlockView;
import aye.view.block.DefaultCardView;
import aye.view.block.PortBlockView;
import aye.view.block.SliderCardView;
import aye.view.block.TitleBlockView;

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
            case LayoutConstants.ID_CARD_PORT:
                view = new PortBlockView(context);
                break;
            case LayoutConstants.ID_CARD_TITLE:
                view = new TitleBlockView(context);
                break;
            case LayoutConstants.ID_CARD_SHORT:
                view = new CardShortBlockView(context);
                break;

            case LayoutConstants.ID_VIEW_DEFAULT:
            default:
                view = new DefaultCardView(context);
                break;
        }
        return view;
    }
}
