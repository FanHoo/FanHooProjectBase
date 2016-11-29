package com.fanhoo.lib.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * 创建人 胡焕
 * 创建时间 2016年11月28日  17:26
 * 用途
 */

public class DialogUtil {


    public static void showSimpleDialog(Context context, String title, DialogInterface.OnClickListener clickListener) {
        new AlertDialog.Builder(context).setTitle(title).setPositiveButton("知道了", clickListener).show();
    }
}
