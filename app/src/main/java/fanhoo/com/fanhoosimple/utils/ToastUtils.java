package fanhoo.com.fanhoosimple.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 创建人 胡焕
 * 创建时间 2016年11月23日  00:32
 * 用途 toast工具类
 */

public class ToastUtils {
    private static Toast toast = null;

    public static void showToast(String msg, Context ctx) {
        if (toast == null)
            toast = Toast.makeText(ctx, msg, Toast.LENGTH_LONG);
        else
            toast.setText(msg);
        toast.show();
    }

}
