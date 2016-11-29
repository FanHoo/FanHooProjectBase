package fanhoo.com.projectbase.image;

import android.content.Context;
import android.view.View;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import fanhoo.com.projectbase.R;

/**
 * 创建人 胡焕
 * 创建时间 2016年11月23日  15:09
 * 用途 ImageLoader基础封装,包含了初始化和Options相关的设置.
 */

public class ImageLoaderManager {
    public static void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getCacheDirectory(context);  //缓存文件夹路径
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions 内存缓存文件的最大长宽
                .threadPoolSize(3) // default  线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2) // default 设置当前线程的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)  // 内存缓存的最大值
                .memoryCacheSizePercentage(13) // default
                .diskCache(new UnlimitedDiskCache(cacheDir) {
                }) // default 可以自定义缓存路径
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
                .diskCacheFileCount(100)  // 可以缓存的文件数量
                // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(context)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs() // 打印debug log
                .build(); //开始构建
        ImageLoader.getInstance().init(config);
    }

    /**
     * 默认的
     *
     * @author 胡焕
     * @date 2016/11/24 10:59
     */
    public static DisplayImageOptions getSimpleOptions() {
        DisplayImageOptions options = getBuilder().displayer(new SimpleBitmapDisplayer()) // default  还可以设置圆角图片new RoundedBitmapDisplayer(20)
                .build();
        return options;
    }

    public static DisplayImageOptions getRoundedOptions() {
        DisplayImageOptions options =
                getBuilder().displayer(new RoundedBitmapDisplayer(20)) // default  还可以设置圆角图片new RoundedBitmapDisplayer(20)
                        .build();
        return options;
    }

    private static DisplayImageOptions.Builder getBuilder() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.imageloader_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.imageloader_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.imageloader_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // default  设置下载的图片是否缓存在内存中
                .cacheOnDisk(true); // default  设置下载的图片是否缓存在SD卡中
    }

    /**
     * 返回ImageLoader的实例
     *
     * @author 胡焕
     * @date 2016/11/24 11:05
     */
    public static ImageLoader get() {
        return ImageLoader.getInstance();
    }

    public static void onClearMemoryClick(View view) {
        ImageLoader.getInstance().clearMemoryCache();  // 清除内存缓存
    }

    public static void onClearDiskClick(View view) {
        ImageLoader.getInstance().clearDiskCache();  // 清除本地缓存
    }
}
