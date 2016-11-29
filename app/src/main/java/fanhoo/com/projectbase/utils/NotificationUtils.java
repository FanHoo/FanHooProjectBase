package fanhoo.com.projectbase.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import fanhoo.com.projectbase.R;
import fanhoo.com.projectbase.base.BaseApplication;


public class NotificationUtils {
    private final static String title = "";

    /**
     * 显示一个Notification
     *
     * @param clas    点击后要启动的Activity
     * @param content 文字提示信息.
     * @author 胡焕
     * @date 2016/11/28 11:43
     */
    public static void showAlarmNotifications(String content, Class<?> clas) {
        Context context = BaseApplication.getContext();
        Bitmap btm = BitmapFactory.decodeResource(context.getResources(),
                R.mipmap.ic_launcher);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content);
        mBuilder.setTicker(content);//第一次提示消息的时候显示在通知栏上
        mBuilder.setNumber(12);
        mBuilder.setLargeIcon(btm);
        mBuilder.setAutoCancel(true);//自己维护通知的消失
        //构建一个Intent
        Intent resultIntent = new Intent(context, clas);
        //封装一个Intent
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // 设置通知主题的意图
        mBuilder.setContentIntent(resultPendingIntent);
        //获取通知管理器对象
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }
}
