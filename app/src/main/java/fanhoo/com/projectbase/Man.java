package fanhoo.com.projectbase;

import fanhoo.com.projectbase.utils.annotations.BindNotice;

/**
 * 创建人 胡焕
 * 创建时间 2016年11月23日  20:42
 * 用途
 */

public class Man {
    public String Name = "HH";
    public int age = 0;

    @BindNotice(title = "姓名")
    public String Sss;
}
