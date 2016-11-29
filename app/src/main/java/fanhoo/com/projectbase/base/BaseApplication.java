package fanhoo.com.projectbase.base;

import android.app.Application;
import android.content.Context;

import fanhoo.com.projectbase.image.ImageLoaderManager;

/**
 * 创建人 胡焕
 * 创建时间 2016年11月23日  14:15
 * 用途
 */

public class BaseApplication extends Application {
    private static Context app;


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        ImageLoaderManager.initImageLoader(this);

    }


    public static Context getContext() {
        return app;
    }

}
