package reid.aye.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by wgx33 on 2016/9/27.
 */

public class GridRecyclerView extends RecyclerView {
    public GridRecyclerView(Context context) {
        this(context, null);
    }

    public GridRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }



}
