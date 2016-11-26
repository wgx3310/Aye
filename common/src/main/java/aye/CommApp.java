package aye;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;

import aye.content.BlockCanaryContext;
import aye.content.Contexts;
import aye.lang.CrashHandler;
import aye.util.FontUtils;

/**
 * Created by reid on 2016/11/14.
 */

public class CommApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Contexts.init(getApplicationContext());

        //设置系统默认字体
        FontUtils.resetTypefaceDefaultFont("SERIF", getApplicationContext(), "fonts/default.otf");

        //初始化内存泄漏检测
        LeakCanary.install(this);

        //初始化过度绘制检测
        BlockCanary.install(this, new BlockCanaryContext()).start();

        //初始化错误收集
        CrashHandler.init(new CrashHandler(getApplicationContext()));
    }


    public static void exit(){
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
