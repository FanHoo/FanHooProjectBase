package com.fanhoo.lib.utils.annotations;

import java.lang.reflect.Field;

/**
 * 创建人 胡焕
 * 创建时间 2016年11月23日  18:30
 * 用途 用于提示的工具类采用注解实现
 */

public class BindNoticeProcess {
    private static BindNoticeProcess bindNoticeProcess = new BindNoticeProcess();

    private BindNoticeProcess() {
    }

    public static BindNoticeProcess getInstance() {
        return bindNoticeProcess;
    }

    /**
     * 处理Bean中标记了需要做空判断并提示的字段.
     * 如果有标注字段为空,则根据注解标注的 title 和 msg 提示出来.
     * 如果都不为空, 则返回空字符串,可通过返回是否为空判断需不需要提示.
     *
     * @author 胡焕
     * @date 2016/11/23 20:14
     */
    public String getNotice(Object obj) throws IllegalAccessException {
        final Class<?> tClass = obj.getClass();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(BindNotice.class)) {
                BindNotice bind = field.getAnnotation(BindNotice.class);
                field.setAccessible(true);
                Object o = field.get(obj);
                String value = String.valueOf(o);
                if (value.equals("null") || value.length() == 0) {
                    return bind.title() + bind.msg();
                }
            }
        }
        return "";
    }

}