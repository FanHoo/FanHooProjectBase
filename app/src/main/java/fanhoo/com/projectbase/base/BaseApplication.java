package fanhoo.com.projectbase.base;

import android.app.Application;

import fanhoo.com.projectbase.image.ImageLoader;

/**
 * 创建人 胡焕
 * 创建时间 2016年11月23日  14:15
 * 用途
 */

public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.initImageLoader(this);
    }


}
