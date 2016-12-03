package aye.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import aye.model.DisplayItem;

/**
 * Created by reid on 2016/9/27.
 */

public abstract class BaseCardView<T extends DisplayItem> extends RelativeLayout{

    protected T data;

    public BaseCardView(Context context){
        super(context, null, 0);
        initView();
    }

    private final void initView(){
        LayoutInflater.from(getContext()).inflate(getLayoutId(), this);
        onInitView();
    }

    public void bindData(T data){
        if (data != null){
            this.data = data;
            onBindData(this.data);
        }
    }

    protected abstract void onBindData(T data);

    protected abstract void onInitView();

    protected abstract int getLayoutId();

}
