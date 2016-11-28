package aye.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import aye.content.Contexts;
import reid.common.R;

/**
 * Created by reid on 2016/11/26.
 */

public class ToastUtils {

    private static ToastUtils tu;
    private Context context;
    private Toast toast;
    private String msg;

    public static void show(int resId) {
        show(Contexts.getContext().getString(resId));
    }

    public static void show(String msg) {
        if (tu == null) {
            tu = new ToastUtils();
        }
        tu.setText(msg);
        tu.create().show();
    }

    public ToastUtils() {
        this.context = Contexts.getContext();
    }

    public Toast create() {
        View contentView = View.inflate(context, R.layout.layout_toast, null);
        TextView tvMsg = (TextView) contentView.findViewById(R.id.toast_msg);
        toast = new Toast(context);
        toast.setView(contentView);
        toast.setDuration(Toast.LENGTH_SHORT);
        tvMsg.setText(msg);
        return toast;
    }

    public void show() {
        if (toast != null) {
            toast.show();
        }
    }

    public void setText(String text) {
        msg = text;
    }
}
