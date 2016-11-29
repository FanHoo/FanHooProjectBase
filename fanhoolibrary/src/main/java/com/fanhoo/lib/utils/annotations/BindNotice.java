package com.fanhoo.lib.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建人 胡焕
 * 创建时间 2016年11月23日  19:09
 * 用途 用于不bean字段为空的检测及提示.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindNotice {
    //字段的描述
    String title();

    //要提示的信息
    String msg() default "不能为空!";
}
